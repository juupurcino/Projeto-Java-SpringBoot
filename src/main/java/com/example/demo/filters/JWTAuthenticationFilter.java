package com.example.demo.filters;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.dto.Token;
import com.example.demo.services.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthenticationFilter extends OncePerRequestFilter {


    final JWTService<Token> jwtService;
    public JWTAuthenticationFilter(JWTService<Token> jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

                
        String path = request.getRequestURI();
        
        if (path.startsWith("/user") || path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = getJwt(request);
        System.out.println("esse é o jwt: " + jwt);
        if (jwt == null)
        {
            filterChain.doFilter(request, response);
            System.out.println("o jwt é nulo: " + jwt);
            return;
        }

        var token = jwtService.validate(jwt);
        
        if (token == null)
        {
            filterChain.doFilter(request, response);
            System.out.println("o token é nulo: " + token);
            return;
        }
        
        var authentication = new UsernamePasswordAuthenticationToken(null,null,null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
                
        request.setAttribute("token", token);
        filterChain.doFilter(request, response);
    }
    
    String getJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("entrou na validação do baerer");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            System.out.println("validou se o token começa com baerer, e retorunou: " +  bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }
}