package ammm_tech_labs.e_commerce_mvp.factory;

import java.time.LocalDate;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import ammm_tech_labs.e_commerce_mvp.dto.create.CriarUsuarioDTO;

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
        String email = (nome + "." + sobrenome + "@example.com")
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
                gerarCPFValido()
                // endereco
        );
    }

    /**
     * Gera CPF válido (simplificado, sem validação real, apenas números aleatórios)
     */
    private String gerarCPFValido() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int n1 = random.nextInt(0, 10);
        int n2 = random.nextInt(0, 10);
        int n3 = random.nextInt(0, 10);
        int n4 = random.nextInt(0, 10);
        int n5 = random.nextInt(0, 10);
        int n6 = random.nextInt(0, 10);
        int n7 = random.nextInt(0, 10);
        int n8 = random.nextInt(0, 10);
        int n9 = random.nextInt(0, 10);
        int d1 = 0; // Pode implementar cálculo real do dígito verificador se quiser
        int d2 = 0;

        return String.format("%d%d%d.%d%d%d.%d%d%d-%d%d",
                n1, n2, n3, n4, n5, n6, n7, n8, n9, d1, d2);
    }
}
