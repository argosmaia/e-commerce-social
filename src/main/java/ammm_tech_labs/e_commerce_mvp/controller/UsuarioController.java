package ammm_tech_labs.e_commerce_mvp.controller;

import java.util.UUID;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ammm_tech_labs.e_commerce_mvp.dto.UsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.create.CriarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.list.ListarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.update.AtualizarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.view.VerUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.response.APIResponse;
import ammm_tech_labs.e_commerce_mvp.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<APIResponse<UsuarioDTO>> cadastrarUsuario(
            @RequestBody @Valid CriarUsuarioDTO dados,
            UriComponentsBuilder uriBuilder
    ) {
        var resposta = usuarioService.cadastrarUsuario(dados);

        var uri = uriBuilder
                .path("/usuarios/{id}")
                .buildAndExpand(resposta.getDados().id())
                .toUri();

        return ResponseEntity.created(uri).body(resposta);
    }

    @GetMapping
    public ResponseEntity<APIResponse<Page<ListarUsuarioDTO>>> listar(
            Pageable pageable
    ) {
        return ResponseEntity.ok(usuarioService.listarUsuarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<VerUsuarioDTO>> ver(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.verUsuario(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UsuarioDTO>> atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid AtualizarUsuarioDTO dados
    ) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, dados));
    }
}
