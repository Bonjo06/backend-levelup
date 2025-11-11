package levelUp.levelUp.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import levelUp.levelUp.Controller.PurchaseOrderController;
import levelUp.levelUp.Model.PurchaseOrder;

@Component
public class PurchaseOrderModelAssembler implements RepresentationModelAssembler<PurchaseOrder, EntityModel<PurchaseOrder>>{
    
    @Override
    public EntityModel<PurchaseOrder> toModel(PurchaseOrder purchaseOrder){

        return EntityModel.of(purchaseOrder,
        linkTo(methodOn(PurchaseOrderController.class).getPurchaseOrderById(purchaseOrder.getId())).withSelfRel(),
        linkTo(methodOn(PurchaseOrderController.class).getAllPurchaseOrders()).withRel("purchaseOrders")
        );
    }

}
