package levelUp.levelUp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import levelUp.levelUp.Model.Inventario;
import levelUp.levelUp.Service.InventarioService;
import levelUp.levelUp.Assembler.InventarioModelAssembler;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    //Método GET para obtener todos los productos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los productos", description = "Obtiene todos los productos registrados en el inventario")
    public CollectionModel<EntityModel<Inventario>> getAllInventario(){
        List<EntityModel<Inventario>> inventario = inventarioService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(inventario,
            linkTo(methodOn(InventarioController.class).getAllInventario()).withSelfRel()
        );
    }

    //Método GET para obtener producto por id
    @GetMapping(value="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary= "Obtener producto por ID", description = "Obtiene un producto del inventario mediante su ID")
    public EntityModel<Inventario> getInventarioById(@PathVariable Long id){
        Inventario inventario = inventarioService.findById(id);
        return assembler.toModel(inventario);
    }

    //Método POST para crear un nuevo producto
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo producto", description = "Crea un nuevo producto en el inventario")
    public ResponseEntity<EntityModel<Inventario>> createInventario(@RequestBody Inventario inventario){
        Inventario newInventario =  inventarioService.save(inventario);
        return ResponseEntity
               .created(linkTo(methodOn(InventarioController.class).getInventarioById(newInventario.getId())).toUri())
               .body(assembler.toModel(newInventario));
    }

    //Método PUT para actualizar un producto existente
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un producto", description = "Actualiza los datos de un producto existente en el inventario")
    public ResponseEntity<EntityModel<Inventario>> updateInventario(@PathVariable Long id, @RequestBody Inventario inventario){
        inventario.setId(id);
        Inventario updatedInventario = inventarioService.save(inventario);
        return ResponseEntity
               .ok(assembler.toModel(updatedInventario));
    }

    //Método DELETE para eliminar un producto por id
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del inventario mediante su ID")
    public ResponseEntity<?> deleteInventario(@PathVariable Long id){
        inventarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
