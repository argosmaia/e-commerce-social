package ammm_tech_labs.e_commerce_mvp.models;

import java.math.BigDecimal;
import java.util.UUID;

import ammm_tech_labs.e_commerce_mvp.dto.create.CriarProdutoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Produto")
@Table(name = "produtos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    // Referenciar Loja por ID para evitar acoplamento direto futuramente
    @Column(unique = true, nullable = false) private String nome;
    @Column private BigDecimal preco;
    @Column(name = "foto_url") private String fotoUrl;
    @Column(length = 500) private String descricao;
    // private Categoria categoria; // futuramente usar enum ou entidade para categorias
    @Column private Double avaliacaoMedia; 

    // Construtor para criação de produto, sem ID
    public Produto(String nome, BigDecimal preco, String fotoUrl, String descricao) {
        validarNome(nome);
        validarPreco(preco);
        this.nome = nome;
        this.preco = preco;
        this.fotoUrl = fotoUrl;
        this.descricao = descricao;
        this.avaliacaoMedia = 0.0;

    }

    public Produto(CriarProdutoDTO dados) {
        this.nome = dados.nome();
        this.preco = dados.preco();
        this.fotoUrl = dados.fotoUrl();
        this.descricao = dados.descricao();
        this.avaliacaoMedia = dados.avaliacaoMedia();
    }

    // public void atualizarNome(String nome) {
    //     validarNome(nome);
    //     this.nome = nome;
    // }

    // public void atualizarPreco(BigDecimal preco) {
    //     validarPreco(preco);
    //     this.preco = preco;
    // }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero");
        }
    }

    public void atualizar(String nome, BigDecimal preco, String fotoUrl, String descricao) {
        this.nome = nome != null ? nome : this.nome;
        this.preco = preco != null ? preco : this.preco;
        this.fotoUrl = fotoUrl != null ? fotoUrl : this.fotoUrl;
        this.descricao = descricao != null ? descricao : this.descricao;
    }
}



    

