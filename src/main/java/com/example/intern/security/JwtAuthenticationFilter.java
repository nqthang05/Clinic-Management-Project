package com.example.intern.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Nếu API không có Token hoặc không bắt đầu bằng "Bearer ", cho đi tiếp (tới SecurityConfig xử lý từ chối)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7); // Cắt bỏ chữ "Bearer " để lấy chuỗi token
        try {
            username = jwtUtils.extractUsername(jwt);

            // Nếu có username và SecurityContext chưa có thông tin xác thực
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtils.isTokenValid(jwt)) {
                    String role = jwtUtils.extractRole(jwt);

                    // Nạp role vào Context của Spring Security. Phải có tiền tố ROLE_
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            username, null, Collections.singletonList(authority));

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Cấp thẻ xanh (Đã đăng nhập)
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token sai, hết hạn... cứ để SecurityContext rỗng, lát Spring Security sẽ báo lỗi 403
        }

        filterChain.doFilter(request, response);
    }
}