package ammm_tech_labs.e_commerce_mvp.factory;

import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import ammm_tech_labs.e_commerce_mvp.dto.create.CriarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.validations.CPFValido;

@Component
public class UsuarioFactory {

    private final Faker faker = new Faker(Locale.of("pt", "BR"));

    /**
     * Gera um CriarUsuarioDTO com dados aleatórios realistas.
     */
    public CriarUsuarioDTO criarUsuarioDTOAleatorio() {
        // Nome e sobrenome separados
        String nome = faker.name().firstName();
        String sobrenome = faker.name().lastName();
        String username = (nome.charAt(0) + sobrenome).toLowerCase().replaceAll("[^a-z]", "") + faker.number().digits(3);

        // Email baseado no nome
        String email = (nome + "." + sobrenome + "@gmail.com")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\.]", "");

        // Data de aniversário aleatória entre 18 e 50 anos
        LocalDate aniversario = LocalDate.now()
                .minusYears(18 + faker.random().nextInt(0, 32))
                .withMonth(faker.random().nextInt(1, 12))
                .withDayOfMonth(faker.random().nextInt(1, 28));

        // Idade calculada a partir da data de aniversário
        int idade = LocalDate.now().getYear() - aniversario.getYear();
        
        // Senha aleatória de 8 a 16 caracteres
        String senha = faker.internet().password(8, 16, true, true, true);

        // Telefone brasileiro
        String telefone = String.format("+55 (%02d) 9%d%d%d%d-%d%d%d%d",
                faker.random().nextInt(11, 99),
                faker.random().nextInt(9),
                faker.random().nextInt(9),
                faker.random().nextInt(9),
                faker.random().nextInt(9),
                faker.random().nextInt(9),
                faker.random().nextInt(9),
                faker.random().nextInt(9),
                faker.random().nextInt(9));

        // // Endereço aleatório
        // EnderecoDTO endereco = new EnderecoDTO(
        //         faker.address().streetAddress(),
        //         faker.address().cityName(),
        //         faker.address().zipCode(),
        //         faker.address().city(),
        //         faker.address().stateAbbr(),
        //         faker.address().secondaryAddress(),
        //         faker.address().buildingNumber()
        // );

        return new CriarUsuarioDTO(
                nome,
                sobrenome,
                username,
                senha,
                telefone,
                idade,
                email,
                aniversario.atStartOfDay(), // Convertendo LocalDate para LocalDateTime
                CPFValido.gerar()
                // endereco
        );
    }
}
