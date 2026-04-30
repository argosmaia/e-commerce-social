package ammm_tech_labs.e_commerce_mvp.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ammm_tech_labs.e_commerce_mvp.dto.list.ListarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.models.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByUsername(String username);

    Page findAll(Pageable paginacao);
}
