package levelUp.levelUp.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import levelUp.levelUp.Controller.CartController;
import levelUp.levelUp.Model.Cart;

@Component
public class CartModelAssembler implements RepresentationModelAssembler<Cart, EntityModel<Cart>>{
    
    @Override
    public EntityModel<Cart> toModel(Cart cart){

        return EntityModel.of(cart,
        linkTo(methodOn(CartController.class).getCartById(cart.getId())).withSelfRel(),
        linkTo(methodOn(CartController.class).getAllCarts()).withRel("carts")
        );
    }

}
