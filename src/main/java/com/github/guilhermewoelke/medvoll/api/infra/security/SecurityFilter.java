package com.github.guilhermewoelke.medvoll.api.infra.security;

import com.github.guilhermewoelke.medvoll.api.repositories.UsuarioRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);

        String subject = null;
        System.out.println("tokenJWT: " + tokenJWT);
        System.out.println("Usuario: ");
        if (tokenJWT != null) {
            // Recupera o Subject à partir do Token
            subject = tokenService.getSubject(tokenJWT);
            System.out.println("Subject: " + subject);

            //Procura o usuário cujo login seja igual ao subject
            var usuario = usuarioRepository.findByLogin(subject);
            System.out.println("Usuario: " + usuario.getUsername());
            var auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        System.out.println("doFilterInternal " + request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        System.out.println("AuthHeader: " + authHeader);
        if (authHeader != null) {
            return authHeader.replace("Bearer ", "").trim();
        }

        return null;
    }
}
