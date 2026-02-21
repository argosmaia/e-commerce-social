package ammm_tech_labs.e_commerce_mvp.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ammm_tech_labs.e_commerce_mvp.dto.ProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.create.CriarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.list.ListarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.update.AtualizarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.view.VerProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.maps.ProdutoMapper;
import ammm_tech_labs.e_commerce_mvp.models.Produto;
import ammm_tech_labs.e_commerce_mvp.repository.ProdutoRepository;
import ammm_tech_labs.e_commerce_mvp.response.APIResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtos;
    private final ProdutoMapper mapper;

    @Transactional
    public APIResponse<ProdutoDTO> cadastrarProduto(CriarProdutoDTO dados){
        if(produtos.existsByNome(dados.nome())) {
            return APIResponse.conflito("Nome de produto já cadastrado");
        }

        var produto = new Produto(dados.nome(), dados.preco());

        produtos.save(produto);

         return APIResponse.criado(
                "Produto criado com sucesso",
                mapper.toDTO(produto)
        );
    }

    public APIResponse<Page<ListarProdutoDTO>> listarProdutos(Pageable paginacao) {
        var paginas = produtos.findAll(paginacao)
                              .map(produto -> mapper.toListarDTO(produto));
        return APIResponse.sucesso("Lista de produtos", paginas);
    }

      public APIResponse<VerProdutoDTO> verProduto(UUID id) {
        var produto = produtos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        return APIResponse.sucesso(
                "Produto encontrado",
                mapper.toVerDTO(produto)
        );
    }

    @Transactional
public APIResponse<ProdutoDTO> atualizarProduto(UUID id, AtualizarProdutoDTO dados) {
    var produto = produtos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

    if (dados.nome() != null && !dados.nome().equals(produto.getNome())) {
    if (produtos.existsByNome(dados.nome())) {
        return APIResponse.conflito("Nome de produto já cadastrado");
    }
    produto.atualizarNome(dados.nome());
}

    if (dados.preco() != null) {
        produto.atualizarPreco(dados.preco());
    }

    return APIResponse.sucesso(
            "Produto atualizado",
            mapper.toDTO(produto)
    );
}

    @Transactional
    public APIResponse<Void> deletarProduto(UUID id) {
        if (!produtos.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado");
        }

        produtos.deleteById(id);

        return APIResponse.sucesso("Produto deletado");
    }



    
    
}

