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

import levelUp.levelUp.Model.PurchaseOrder;
import levelUp.levelUp.Service.PurchaseOrderService;
import levelUp.levelUp.Assembler.PurchaseOrderModelAssembler;

@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderModelAssembler assembler;

    //Método GET para obtener todas las órdenes de compra
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todas las órdenes de compra", description = "Obtiene todas las órdenes de compra registradas en la bd")
    public CollectionModel<EntityModel<PurchaseOrder>> getAllPurchaseOrders(){
        List<EntityModel<PurchaseOrder>> purchaseOrders = purchaseOrderService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(purchaseOrders,
            linkTo(methodOn(PurchaseOrderController.class).getAllPurchaseOrders()).withSelfRel()
        );
    }

    //Método GET para obtener orden de compra por id
    @GetMapping(value="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary= "Obtener orden de compra por ID", description = "Obtiene una orden de compra registrada en la bd mediante su ID")
    public EntityModel<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id){
        PurchaseOrder purchaseOrder = purchaseOrderService.findById(id);
        return assembler.toModel(purchaseOrder);
    }

    //Método POST para crear una nueva orden de compra
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear una nueva orden de compra", description = "Crea una nueva orden de compra en la bd")
    public ResponseEntity<EntityModel<PurchaseOrder>> createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        PurchaseOrder newPurchaseOrder =  purchaseOrderService.save(purchaseOrder);
        return ResponseEntity
               .created(linkTo(methodOn(PurchaseOrderController.class).getPurchaseOrderById(newPurchaseOrder.getId())).toUri())
               .body(assembler.toModel(newPurchaseOrder));
    }

    //Método PUT para actualizar una orden de compra existente
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar una orden de compra", description = "Actualiza los datos de una orden de compra existente en la bd")
    public ResponseEntity<EntityModel<PurchaseOrder>> updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseOrder purchaseOrder){
        purchaseOrder.setId(id);
        PurchaseOrder updatedPurchaseOrder = purchaseOrderService.save(purchaseOrder);
        return ResponseEntity
               .ok(assembler.toModel(updatedPurchaseOrder));
    }

    //Método DELETE para eliminar una orden de compra por id
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar una orden de compra", description = "Elimina una orden de compra existente en la bd mediante su ID")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable Long id){
        purchaseOrderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
