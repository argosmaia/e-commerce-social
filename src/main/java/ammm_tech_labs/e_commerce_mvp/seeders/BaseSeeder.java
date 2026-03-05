package ammm_tech_labs.e_commerce_mvp.seeders;

import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseSeeder {
    
    private final UsuarioSeeder usuario;
    private final ProdutoSeeder produto;

    public BaseSeeder(UsuarioSeeder usuario, ProdutoSeeder produto) {
        this.usuario = usuario;
        this.produto = produto;
    }

    @Bean
    public CommandLineRunner seedAll() {
        return args -> {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Quer popular o banco? (sim/não)");
            String resposta = scanner.nextLine().trim().toLowerCase();

            if (resposta.equals("sim") || resposta.equals("s")) {
                System.out.println("🚀 Populando o banco...");
                usuario.run();
                produto.run();
                System.out.println("✅ Todos os seeders foram executados com sucesso.");
            } else {
                System.out.println("⏭️ Seeders ignorados. Continuando execução...");
            }

            scanner.close(); // opcional
        };   
    }
}
