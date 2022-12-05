package bessa.morangon.rafael.financeiro.controller;

import bessa.morangon.rafael.financeiro.model.Usuario;
import bessa.morangon.rafael.financeiro.repository.UsuarioRepository;
import bessa.morangon.rafael.financeiro.service.CadastroService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@Controller
@RequestMapping("/cadastro")
public class CadastroController {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder encoder;

    private CadastroService service;

    @GetMapping
    public String exibeTela(){
        return "cadastro";
    }

    @PostMapping
    public String cadastra(Usuario usuario){
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuarioRepository.save(service.verificaSeEmailJáEstáCadastradoNoBanco(usuario));
        return "redirect:login";
    }

}
