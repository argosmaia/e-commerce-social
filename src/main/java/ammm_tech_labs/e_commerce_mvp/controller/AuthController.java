package ammm_tech_labs.e_commerce_mvp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ammm_tech_labs.e_commerce_mvp.dto.UsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.create.CriarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.response.APIResponse;
import ammm_tech_labs.e_commerce_mvp.service.UsuarioService;
import ammm_tech_labs.e_commerce_mvp.security.JwtUtil;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        CriarUsuarioDTO dados = new CriarUsuarioDTO(
            body.get("nome"),
            body.get("sobrenome"),
            body.get("username"),
            body.get("password"),
            body.get("telefone"), null, null, null
        );
        APIResponse<UsuarioDTO> usuario = usuarioService.cadastrarUsuario(dados);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        var usuarioResponse = usuarioService.buscarPorUsername(body.get("username"));
        var usuario = usuarioResponse.getDados();
        if (usuario != null && passwordEncoder.matches(body.get("password"), usuario.senha())) {
            String token = JwtUtil.generateToken(usuario.username());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(usuarioService.listarUsuarios(null));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    
    
    
}


