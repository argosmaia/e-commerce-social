package ammm_tech_labs.e_commerce_mvp.factory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import ammm_tech_labs.e_commerce_mvp.dto.create.CriarProdutoDTO;

@Component
public class ProdutoFactory {

    private final Faker faker = new Faker(Locale.of("pt", "BR"));

    public CriarProdutoDTO criarProdutoDTOAleatorio() {

        String nome = faker.commerce().productName();

        BigDecimal preco = BigDecimal
                .valueOf(faker.number().randomDouble(2, 10, 5000))
                .setScale(2, RoundingMode.HALF_UP);

        String fotoUrl = "https://picsum.photos/400/400?random=" + faker.number().digits(5);

        String descricao = faker.lorem().sentence(12);

        Double avaliacao = faker.number().randomDouble(1, 3, 5);

        return new CriarProdutoDTO(
            nome,
            preco,
            fotoUrl,
            descricao,
            avaliacao
        );
    }
}