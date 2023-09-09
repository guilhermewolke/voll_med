package com.github.guilhermewoelke.medvoll.api.controllers;

import com.github.guilhermewoelke.medvoll.api.infra.security.TokenService;
import com.github.guilhermewoelke.medvoll.api.models.usuario.AutenticacaoInputDTO;
import com.github.guilhermewoelke.medvoll.api.models.usuario.DadosTokenJWT;
import com.github.guilhermewoelke.medvoll.api.models.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid AutenticacaoInputDTO dados) {
        try {
            var AuthToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var auth = this.manager.authenticate(AuthToken);
            var tokenJWT = tokenService.gerarToken((Usuario) auth.getPrincipal());
            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
