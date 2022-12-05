package bessa.morangon.rafael.financeiro.service;

import bessa.morangon.rafael.financeiro.exceptionHandler.UsuarioJaExisteException;
import bessa.morangon.rafael.financeiro.model.Usuario;
import bessa.morangon.rafael.financeiro.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CadastroService {

    private UsuarioRepository usuarioRepository;

    public Usuario verificaSeEmailJáEstáCadastradoNoBanco(Usuario usuario){

        Optional<Usuario> user = usuarioRepository.findByUsername(usuario.getUsername());

        if(user.isPresent()){
            throw new UsuarioJaExisteException();
        }
        return usuario;
    }
}
