package levelUp.levelUp.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import levelUp.levelUp.Controller.CartItemController;
import levelUp.levelUp.Model.CartItem;

@Component
public class CartItemModelAssembler implements RepresentationModelAssembler<CartItem, EntityModel<CartItem>>{
    
    @Override
    public EntityModel<CartItem> toModel(CartItem cartItem){

        return EntityModel.of(cartItem,
        linkTo(methodOn(CartItemController.class).getCartItemById(cartItem.getId())).withSelfRel(),
        linkTo(methodOn(CartItemController.class).getAllCartItems()).withRel("cartItems")
        );
    }

}
