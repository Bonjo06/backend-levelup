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

import levelUp.levelUp.Model.Cart;
import levelUp.levelUp.Service.CartService;
import levelUp.levelUp.Assembler.CartModelAssembler;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartModelAssembler assembler;

    //Método GET para obtener todos los carritos
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los carritos", description = "Obtiene todos los carritos registrados en la bd")
    public CollectionModel<EntityModel<Cart>> getAllCarts(){
        List<EntityModel<Cart>> carts = cartService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(carts,
            linkTo(methodOn(CartController.class).getAllCarts()).withSelfRel()
        );
    }

    //Método GET para obtener carrito por id
    @GetMapping(value="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary= "Obtener carrito por ID", description = "Obtiene un carrito registrado en la bd mediante su ID")
    public EntityModel<Cart> getCartById(@PathVariable Long id){
        Cart cart = cartService.findById(id);
        return assembler.toModel(cart);
    }

    //Método POST para crear un nuevo carrito
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo carrito", description = "Crea un nuevo carrito en la bd")
    public ResponseEntity<EntityModel<Cart>> createCart(@RequestBody Cart cart){
        Cart newCart =  cartService.save(cart);
        return ResponseEntity
               .created(linkTo(methodOn(CartController.class).getCartById(newCart.getId())).toUri())
               .body(assembler.toModel(newCart));
    }

    //Método PUT para actualizar un carrito existente
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un carrito", description = "Actualiza los datos de un carrito existente en la bd")
    public ResponseEntity<EntityModel<Cart>> updateCart(@PathVariable Long id, @RequestBody Cart cart){
        cart.setId(id);
        Cart updatedCart = cartService.save(cart);
        return ResponseEntity
               .ok(assembler.toModel(updatedCart));
    }

    //Método DELETE para eliminar un carrito por id
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un carrito", description = "Elimina un carrito existente en la bd mediante su ID")
    public ResponseEntity<?> deleteCart(@PathVariable Long id){
        cartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
