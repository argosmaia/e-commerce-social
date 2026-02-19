package ammm_tech_labs.e_commerce_mvp.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import ammm_tech_labs.e_commerce_mvp.dto.create.CriarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.update.AtualizarUsuarioDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="Usuario")
@Table(name="usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String primeiroNome;
    private String ultimoNome;
    private String username;

    private String senha;
    private String telefone;

    @Column(unique = true, nullable = false)
    private String email;

    private LocalDateTime dataNascimento;
    private int idade; // futuramente usar a data de nascimento para calcular a idade
    
    @Column(unique = true, nullable = false)
    private String cpf;
    
    // Sobrecarga do construtor para criar um usuário a partir de dados essenciais
    public Usuario(
        String primeiroNome, 
        String ultimoNome, 
        String username, 
        String senha,
        String telefone,
        String email, 
        LocalDateTime dataNascimento,
        int idade,
        String cpf
        ) {
        this.primeiroNome = primeiroNome;
        this.ultimoNome = ultimoNome;
        this.username = gerarUsername(primeiroNome, ultimoNome);
        this.senha = senha;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.cpf = cpf;
    }

    public Usuario(CriarUsuarioDTO dados) {
        this.primeiroNome = dados.primeiroNome();
        this.ultimoNome = dados.ultimoNome();
        this.username = dados.username();
        this.senha = dados.senha();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.dataNascimento = dados.dataNascimento();
        this.cpf = dados.cpf();
    }

    // colocar no construtor
    private String gerarUsername(String nome, String sobrenome) {
        return (sobrenome + nome)
                .toLowerCase()
                .replaceAll("\s+", "");
    }

    public void atualizar(String primeiroNome, String ultimoNome, String username, String telefone, String email, LocalDateTime dataNascimento) {
        this.primeiroNome = primeiroNome != null ? primeiroNome : this.primeiroNome;
        this.ultimoNome = ultimoNome != null ? ultimoNome : this.ultimoNome;
        this.username = gerarUsername(this.primeiroNome, this.ultimoNome);
        //  this.username = username != null ? username : this.username;
        this.telefone = telefone != null ? telefone : this.telefone;
        this.email = email != null ? email : this.email;
        this.dataNascimento = dataNascimento != null ? dataNascimento : this.dataNascimento;
    }
}
