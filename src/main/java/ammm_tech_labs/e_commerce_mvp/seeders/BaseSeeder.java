package ammm_tech_labs.e_commerce_mvp.seeders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseSeeder {

    private final UsuarioSeeder usuario;
    private final ProdutoSeeder produto;

    @Value("${seeder.enabled:false}")
    private boolean seederEnabled;

    public BaseSeeder(UsuarioSeeder usuario, ProdutoSeeder produto) {
        this.usuario = usuario;
        this.produto = produto;
    }

    @Bean
    public CommandLineRunner seedAll() {
        return args -> {
            if (!seederEnabled) {
                System.out.println("⏭️ Seeders ignorados. Use --seeder.enabled=true para popular.");
                return;
            }

            System.out.println("🚀 Populando o banco...");
            usuario.run();
            produto.run();
            System.out.println("✅ Todos os seeders foram executados com sucesso.");
        };
    }
}