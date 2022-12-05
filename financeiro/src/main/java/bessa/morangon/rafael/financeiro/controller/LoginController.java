package bessa.morangon.rafael.financeiro.controller;

import bessa.morangon.rafael.financeiro.config.DetalhesUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private DetalhesUsuario detalhesUsuario;

    @GetMapping
    public String login(){
        return "login";}


}
