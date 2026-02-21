package ammm_tech_labs.e_commerce_mvp.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ammm_tech_labs.e_commerce_mvp.dto.ProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.create.CriarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.list.ListarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.update.AtualizarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.view.VerProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.response.APIResponse;
import ammm_tech_labs.e_commerce_mvp.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    
    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<APIResponse<Page<ListarProdutoDTO>>> listarTodosProdutos(
            Pageable pageable
    ) {
        return ResponseEntity.ok(produtoService.listarProdutos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<VerProdutoDTO>> buscarProduto(@PathVariable UUID id) {
        return ResponseEntity.ok(produtoService.verProduto(id));
    }
    

    @PostMapping
    public ResponseEntity<APIResponse<ProdutoDTO>> adicionarProduto(
        @RequestBody @Valid CriarProdutoDTO dados,
        UriComponentsBuilder uriBuilder) {

        var resposta = produtoService.cadastrarProduto(dados);

        var uri = uriBuilder
                .path("/produtos/{id}")
                .buildAndExpand(resposta.getDados().id())
                .toUri();

        return ResponseEntity.created(uri).body(resposta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable UUID id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

     @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProdutoDTO>> atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid AtualizarProdutoDTO dados
    ) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dados));
    }
    
    
    

}


