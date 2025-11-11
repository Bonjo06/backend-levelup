package levelUp.levelUp.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import levelUp.levelUp.Controller.OrderItemController;
import levelUp.levelUp.Model.OrderItem;

@Component
public class OrderItemModelAssembler implements RepresentationModelAssembler<OrderItem, EntityModel<OrderItem>>{
    
    @Override
    public EntityModel<OrderItem> toModel(OrderItem orderItem){

        return EntityModel.of(orderItem,
        linkTo(methodOn(OrderItemController.class).getOrderItemById(orderItem.getId())).withSelfRel(),
        linkTo(methodOn(OrderItemController.class).getAllOrderItems()).withRel("orderItems")
        );
    }

}
