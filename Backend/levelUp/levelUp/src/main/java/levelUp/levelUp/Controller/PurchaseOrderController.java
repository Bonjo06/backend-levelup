package levelUp.levelUp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import levelUp.levelUp.Model.OrderStatus;
import levelUp.levelUp.Model.PurchaseOrder;
import levelUp.levelUp.Service.PurchaseOrderService;
import levelUp.levelUp.Assembler.PurchaseOrderModelAssembler;
import levelUp.levelUp.Dto.CreateOrderRequest;

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

    //Método POST para crear orden desde el carrito (NUEVO)
    @PostMapping("/create-from-cart")
    @Operation(summary = "Crear orden desde carrito", description = "Crea una nueva orden desde los items del carrito")
    public ResponseEntity<?> createOrderFromCart(@RequestBody CreateOrderRequest request) {
        try {
            PurchaseOrder order = purchaseOrderService.createOrderFromCart(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Orden creada exitosamente");
            response.put("orderId", order.getId());
            response.put("orderNumber", "ORD-" + order.getId());
            response.put("total", order.getTotal());
            response.put("status", order.getStatus().toString());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    
    //Método PATCH para actualizar estado de orden (NUEVO)
    @PatchMapping("/{id}/status")
    @Operation(summary = "Actualizar estado de orden", description = "Actualiza el estado de una orden existente")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        try {
            PurchaseOrder updatedOrder = purchaseOrderService.updateOrderStatus(id, status);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Estado actualizado exitosamente");
            response.put("orderId", updatedOrder.getId());
            response.put("status", updatedOrder.getStatus().toString());
            
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
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
