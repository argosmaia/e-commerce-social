package ammm_tech_labs.e_commerce_mvp.maps;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ammm_tech_labs.e_commerce_mvp.dto.ProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.view.VerProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.create.CriarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.models.Produto;
import ammm_tech_labs.e_commerce_mvp.dto.list.ListarProdutoDTO;

import ammm_tech_labs.e_commerce_mvp.dto.update.AtualizarProdutoDTO;
@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoDTO toDTO(Produto produto);

    ListarProdutoDTO toListarDTO(Produto produto);

    VerProdutoDTO toVerDTO(Produto produto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nome", ignore = true)
    @Mapping(target = "preco", ignore = true)
    @Mapping(target = "fotoUrl", ignore = true)
    @Mapping(target = "descricao", ignore = true)
    @Mapping(target = "avaliacaoMedia", ignore = true)
    Produto toEntity(CriarProdutoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizar(@MappingTarget Produto produto, AtualizarProdutoDTO dto);
}
