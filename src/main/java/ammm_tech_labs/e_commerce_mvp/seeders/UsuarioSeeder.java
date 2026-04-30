package ammm_tech_labs.e_commerce_mvp.seeders;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import ammm_tech_labs.e_commerce_mvp.factory.UsuarioFactory;
import ammm_tech_labs.e_commerce_mvp.maps.UsuarioMapper;
import ammm_tech_labs.e_commerce_mvp.models.Usuario;
import ammm_tech_labs.e_commerce_mvp.repository.UsuarioRepository;

@Component
public class UsuarioSeeder {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioFactory usuarioFactory;
    private final UsuarioMapper usuarioMapper;

    public UsuarioSeeder(UsuarioRepository usuarioRepository, UsuarioFactory usuarioFactory, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioFactory = usuarioFactory;
        this.usuarioMapper = usuarioMapper;
    }

    public void run() {
        List<Usuario> usuarios = IntStream.range(0,10)
            .mapToObj(i -> usuarioMapper.toEntity(
                usuarioFactory.criarUsuarioDTOAleatorio()
            ))
            .toList();

        usuarioRepository.saveAll(usuarios);

        System.out.println("✅ 10 usuários foram inseridos no banco.");
    }
}