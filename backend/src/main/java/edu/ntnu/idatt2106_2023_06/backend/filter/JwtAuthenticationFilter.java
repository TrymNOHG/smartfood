package edu.ntnu.idatt2106_2023_06.backend.filter;

import edu.ntnu.idatt2106_2023_06.backend.service.security.JwtService;
import edu.ntnu.idatt2106_2023_06.backend.service.users.UserService;
import jakarta.servlet.ServletException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// https://github.com/BrageNTNU/IDATT-2105-Prosjekt/blob/main/Backend/src/main/java/edu/ntnu/idatt2105/backend/security/JWTAuthenticationFilter.java
// https://github.com/TrymNOHG/Cache-Dash/blob/master/backend/src/main/java/edu/ntnu/idatt2105/g6/backend/security/JwtAuthenticationFilter.java

/**
 *  JwtAuthenticationFilter is responsible for authentication of incoming requests with JWT token.
 *  It extends the OncePerRequestFilter class, which makes sure that the filter is only executed once per request.
 *  This class checks for the presence of a JWT token in the Authorization header of the request.
 *  If the token is present and valid, the user is authenticated and their UserDetails object is set in the security context.
 *  Otherwise, the request is passed down the filter chain.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    /**
     * This method filters the incoming request and checks for the presence of a JWT token in the Authorization header.
     * If the token is present and valid, the user is authenticated and their UserDetails object is set in the security context.
     * Otherwise, the request is passed down the filter chain.
     *
     * @param request           the incoming HttpServletRequest
     * @param response          the HttpServletResponse to be sent
     * @param filterChain       the FilterChain to be executed
     * @throws ServletException in case of a Servlet error
     * @throws IOException      in case of an IO error
     */
    @Override
    protected void doFilterInternal(@NonNull jakarta.servlet.http.HttpServletRequest request,
                                    @NonNull jakarta.servlet.http.HttpServletResponse response,
                                    @NonNull jakarta.servlet.FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt, userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            logger.info("Valid request without JWT token.");
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        logger.info("User " + userEmail + " authenticated.");
        filterChain.doFilter(request, response);

    }
}