package com.springredditclone.redditclone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    @Value("${auth.header}")
    private String TOKEN_HEADER;
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequestHeader(request);
        final SecurityContext securityContext = SecurityContextHolder.getContext();
            // if user not authenticated & token sent
        if(token != null && securityContext.getAuthentication() == null) {

            tokenUtils.validateToken(token);
            String username = tokenUtils.getUserNameFromToken(token);

            if(username!=null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

           }

        }
        filterChain.doFilter(request,response);

    }

    private String getTokenFromRequestHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);

        if(StringUtils.hasText(bearerToken) &&  bearerToken.startsWith("Bearer ")){
            return bearerToken.substring("Bearer ".length());
        }
        return bearerToken;
    }
}
