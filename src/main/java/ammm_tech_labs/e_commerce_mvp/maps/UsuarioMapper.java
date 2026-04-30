package ammm_tech_labs.e_commerce_mvp.maps;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ammm_tech_labs.e_commerce_mvp.dto.UsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.create.CriarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.list.ListarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.update.AtualizarUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.dto.view.VerUsuarioDTO;
import ammm_tech_labs.e_commerce_mvp.models.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    ListarUsuarioDTO toListarDTO(Usuario usuario);

    VerUsuarioDTO toVerDTO(Usuario usuario);

    @Mapping(target = "id", ignore = true)
    // @Mapping(target = "desejos", ignore = true)
    // @Mapping(target = "compras", ignore = true)
    Usuario toEntity(CriarUsuarioDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cpf", ignore = true)
   // @Mapping(target = "idade", ignore = true)
    @Mapping(target = "senha", ignore = true)
    void atualizar(@MappingTarget Usuario usuario, AtualizarUsuarioDTO dto);
}
