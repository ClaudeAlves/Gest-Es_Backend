package dev.claude.security;

import dev.claude.service.UserService;
import dev.claude.service.exception.WrongCredentialsException;
import dev.claude.service.security.BlacklistTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private BlacklistTokenService blacklistTokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Filters a request to check if it has a valid token.
     * @param request request to check
     * @param response response to chain
     * @param filterChain chain filter to chain the filtering
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = jwtUtils.getTokenFromHeader(getAuthorizationHeader(request));
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // there is a token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, jwt, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // If the token is not blacklisted then we can add the auth to the context.
                if (blacklistTokenService.get(jwt).isEmpty()) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new WrongCredentialsException("The given token is black listed");
                }
            } else if(protectedEndpoint(request)) {
                throw new WrongCredentialsException("No JWT was given");
            }
        } catch (WrongCredentialsException e) {
            logger.error("Wrong credentials {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * function that returns true if a request url needs to be protected
     * @param request http request that needs to be checked
     * @return boolean
     */
    private boolean protectedEndpoint(HttpServletRequest request) {
        return  request.getRequestURL().toString().contains("/logout") 		    ||
                request.getRequestURL().toString().contains("/messages")        ||
                request.getRequestURL().toString().contains("/organisation")    ||
                request.getRequestURL().toString().contains("/creation") 	    ||
                request.getRequestURL().toString().contains("/evaluation") 	    ||
                request.getRequestURL().toString().contains("/teacher") 	    ||
                request.getRequestURL().toString().contains("/user") 	    ||
                request.getRequestURL().toString().contains("/users") 	    ||
                request.getRequestURL().toString().contains("/admin") 	    ||
                request.getRequestURL().toString().contains("/calendar");
    }

    /**
     * Get the authorization header of the given http request
     * @param request the request from which to get the authorization header
     * @return the authorization header as a string
     * @author Claude-André Alves
     */
    private String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
