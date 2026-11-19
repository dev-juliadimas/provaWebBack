package br.com.senac.api.services;

import br.com.senac.api.dtos.UsuariosRequestDto;
import br.com.senac.api.entidades.Usuarios;
import br.com.senac.api.repositorios.UsuariosRepositorio;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService implements UserDetailsService {

    private final UsuariosRepositorio usuariosRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(UsuariosRepositorio usuariosRepositorio, PasswordEncoder passwordEncoder) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuarios> listarTodos() {
        return usuariosRepositorio.findAll();
    }

    public Usuarios criar(UsuariosRequestDto dto) {
        if (dto.getLogin() == null || dto.getLogin().isBlank()
                || dto.getSenha() == null || dto.getSenha().isBlank()) {
            throw new RuntimeException("Login e senha são obrigatórios!");
        }
        if (usuariosRepositorio.findByLogin(dto.getLogin()).isPresent()) {
            throw new RuntimeException("Login já existe!");
        }

        Usuarios usuario = new Usuarios();
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuariosRepositorio.save(usuario);
    }

    public Usuarios atualizar(Long id, UsuariosRequestDto dto) {
        Usuarios usuario = usuariosRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if (dto.getLogin() == null || dto.getLogin().isBlank()) {
            throw new RuntimeException("Login é obrigatório!");
        }
        usuariosRepositorio.findByLogin(dto.getLogin())
                .filter(outro -> !outro.getId().equals(id))
                .ifPresent(outro -> {
                    throw new RuntimeException("Login já existe!");
                });

        usuario.setLogin(dto.getLogin());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return usuariosRepositorio.save(usuario);
    }

    public void deletar(Long id) {
        if (!usuariosRepositorio.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        usuariosRepositorio.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepositorio.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return User.withUsername(usuario.getLogin())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }
}