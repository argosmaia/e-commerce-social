package ammm_tech_labs.e_commerce_mvp.factory;

import java.time.LocalDateTime;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import ammm_tech_labs.e_commerce_mvp.dto.create.CriarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.validations.CPFValido;
import ammm_tech_labs.e_commerce_mvp.validations.CelularValido;

@Component
public class UsuarioFactory {

        private final Faker faker = new Faker(Locale.of("pt", "BR"));

        /**
         * Gera um CriarUsuarioDTO com dados aleatórios realistas.
         */
        public CriarUsuarioDTO criarUsuarioDTOAleatorio() {
                String nome = faker.name().firstName();
                String sobrenome = faker.name().lastName();
                String username = gerarUsernameFake(nome, sobrenome);
                String email = gerarEmailFake(nome, sobrenome);
                LocalDateTime aniversario = gerarAniversarioAdultoFake();
                int idade = calcularIdadeFake(aniversario);
                String senha = faker.internet().password(8, 16, true, true, true);
                String telefone = CelularValido.gerar();

                // implementar a construção do endereço futuramente

                return new CriarUsuarioDTO(
                                nome,
                                sobrenome,
                                username,
                                senha,
                                telefone,
                                idade,
                                email,
                                aniversario,
                                CPFValido.gerar()
                // endereco
                );
        }

        private String gerarUsernameFake(String nome, String sobrenome) {
                return (nome.charAt(0) + sobrenome).toLowerCase().replaceAll("[^a-z]", "")
                                + faker.number().digits(3);
        }

        private String gerarEmailFake(String nome, String sobrenome) {
                return (nome + "." + sobrenome + "@gmail.com")
                                .toLowerCase()
                                .replaceAll("[^a-z0-9\\.]", "");
        }

        private LocalDateTime gerarAniversarioAdultoFake() {
                return LocalDateTime.now()
                                .minusYears(18 + faker.random().nextInt(0, 32))
                                .withMonth(faker.random().nextInt(1, 12))
                                .withDayOfMonth(faker.random().nextInt(1, 28));
        }

        private int calcularIdadeFake(LocalDateTime aniversario) {
                return LocalDateTime.now().getYear() - aniversario.getYear();
        }
}
