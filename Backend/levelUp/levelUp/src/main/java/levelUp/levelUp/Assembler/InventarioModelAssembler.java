package levelUp.levelUp.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import levelUp.levelUp.Controller.InventarioController;
import levelUp.levelUp.Model.Inventario;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>>{
    
    @Override
    public EntityModel<Inventario> toModel(Inventario inventario){

        return EntityModel.of(inventario,
        linkTo(methodOn(InventarioController.class).getInventarioById(inventario.getId())).withSelfRel(),
        linkTo(methodOn(InventarioController.class).getAllInventario()).withRel("inventario")
        );
    }

}
