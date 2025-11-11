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

import levelUp.levelUp.Model.CartItem;
import levelUp.levelUp.Service.CartItemService;
import levelUp.levelUp.Assembler.CartItemModelAssembler;

@RestController
@RequestMapping("/cart-items")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemModelAssembler assembler;

    //Método GET para obtener todos los items del carrito
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los items del carrito", description = "Obtiene todos los items del carrito registrados en la bd")
    public CollectionModel<EntityModel<CartItem>> getAllCartItems(){
        List<EntityModel<CartItem>> cartItems = cartItemService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(cartItems,
            linkTo(methodOn(CartItemController.class).getAllCartItems()).withSelfRel()
        );
    }

    //Método GET para obtener item del carrito por id
    @GetMapping(value="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary= "Obtener item del carrito por ID", description = "Obtiene un item del carrito registrado en la bd mediante su ID")
    public EntityModel<CartItem> getCartItemById(@PathVariable Long id){
        CartItem cartItem = cartItemService.findById(id);
        return assembler.toModel(cartItem);
    }

    //Método POST para crear un nuevo item del carrito
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo item del carrito", description = "Crea un nuevo item del carrito en la bd")
    public ResponseEntity<EntityModel<CartItem>> createCartItem(@RequestBody CartItem cartItem){
        CartItem newCartItem =  cartItemService.save(cartItem);
        return ResponseEntity
               .created(linkTo(methodOn(CartItemController.class).getCartItemById(newCartItem.getId())).toUri())
               .body(assembler.toModel(newCartItem));
    }

    //Método PUT para actualizar un item del carrito existente
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un item del carrito", description = "Actualiza los datos de un item del carrito existente en la bd")
    public ResponseEntity<EntityModel<CartItem>> updateCartItem(@PathVariable Long id, @RequestBody CartItem cartItem){
        cartItem.setId(id);
        CartItem updatedCartItem = cartItemService.save(cartItem);
        return ResponseEntity
               .ok(assembler.toModel(updatedCartItem));
    }

    //Método DELETE para eliminar un item del carrito por id
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un item del carrito", description = "Elimina un item del carrito existente en la bd mediante su ID")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id){
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
