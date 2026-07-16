package com.example.biblio_barrio.shared.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer "; // ""Authorization: "Bearer eyjfjjkrngjkrnjkgnr"

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain //caja negra
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;

        }

        String token = authHeader.substring(BEARER_PREFIX.length()); // eyHhjknjkwrnwrnk

        try {
            String username = jwtService.extractUsername(token); // pepito
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //username no es nullo y no hay otro security context activo (aseguro mi sesión)
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //busco a username en la bd
                if (jwtService.isTokenValid(token, userDetails)) {
                    //aqui el jwt ya es válido a nivel de registros en la BD
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    //este usuario viene de tal request
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //el security context es setteado
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception ex) {
            // Invalid/expired token: leave request unauthenticated, Security returns 401.
        }

        filterChain.doFilter(request, response);
    }
}
