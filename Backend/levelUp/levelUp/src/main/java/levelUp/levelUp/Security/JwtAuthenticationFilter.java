package levelUp.levelUp.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();
        
        System.out.println("🔍 JWT Filter - Petición: " + method + " " + path);
        
        // ⭐ NO procesar JWT en rutas públicas - permitir que pasen directamente
        if (path.startsWith("/api/auth/") || 
            path.startsWith("/swagger-ui") || 
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/doc/") ||
            path.equals("/api/contact-messages") ||
            path.startsWith("/purchase-orders/") ||
            (path.startsWith("/api/inventario") && "GET".equals(method)) ||
            (path.startsWith("/users/") && "GET".equals(method))) {
            System.out.println("✅ Ruta pública - Permitiendo sin JWT");
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println("🔑 Authorization header presente: " + (authorizationHeader != null));

        String email = null;
        String jwt = null;

        // Extraer token del header
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
            System.out.println("📧 Email extraído del token: " + email);
        }

        // Validar token
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            System.out.println("👤 Usuario cargado: " + userDetails.getUsername());
            System.out.println("🔐 Authorities: " + userDetails.getAuthorities());

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("✅ Token válido - Autenticación establecida");
            } else {
                System.out.println("❌ Token inválido");
            }
        }
        filterChain.doFilter(request, response);
    }
}
