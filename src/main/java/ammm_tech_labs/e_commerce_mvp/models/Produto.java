package ammm_tech_labs.e_commerce_mvp.models;

import java.util.UUID;

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

    @Column(unique = true, nullable = false)
    private String nome;
    
    private Double preco;

    public Produto(String nome, Double preco) {
        validarNome(nome);
        validarPreco(preco);
        this.nome = nome;
        this.preco = preco;
    }

    public void atualizarNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public void atualizarPreco(Double preco) {
        validarPreco(preco);
        this.preco = preco;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }
    }

    private void validarPreco(Double preco) {
        if (preco == null || preco <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero");
        }
    }
}



    

