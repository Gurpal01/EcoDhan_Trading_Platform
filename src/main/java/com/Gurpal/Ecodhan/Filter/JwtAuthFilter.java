package com.Gurpal.Ecodhan.Filter;

import com.Gurpal.Ecodhan.Jwt.JwtUtility;
import com.Gurpal.Ecodhan.Service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    CustomUserDetailsService customUserDetailsService;
    JwtUtility jwtUtility;

    @Autowired
    public JwtAuthFilter(CustomUserDetailsService customUserDetailsService,JwtUtility jwtUtility)
    {
        this.customUserDetailsService =customUserDetailsService;
        this.jwtUtility = jwtUtility;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String  userName = null;
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token = authHeader.substring(7);
            userName = jwtUtility.extractUserName(token);
        }

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            if(jwtUtility.validateToken(userName,userDetails,token))
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);

    }
}
