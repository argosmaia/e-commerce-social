package ammm_tech_labs.e_commerce_mvp.maps;

import org.mapstruct.Mapper;

import ammm_tech_labs.e_commerce_mvp.dto.ProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.list.ListarProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.dto.view.VerProdutoDTO;
import ammm_tech_labs.e_commerce_mvp.models.Produto;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoDTO toDTO(Produto produto);

    ListarProdutoDTO toListarDTO(Produto produto);

    VerProdutoDTO toVerDTO(Produto produto);
}
