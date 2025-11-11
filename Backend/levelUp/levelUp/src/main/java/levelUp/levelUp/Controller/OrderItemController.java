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

import levelUp.levelUp.Model.OrderItem;
import levelUp.levelUp.Service.OrderItemService;
import levelUp.levelUp.Assembler.OrderItemModelAssembler;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemModelAssembler assembler;

    //Método GET para obtener todos los items de orden
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los items de orden", description = "Obtiene todos los items de orden registrados en la bd")
    public CollectionModel<EntityModel<OrderItem>> getAllOrderItems(){
        List<EntityModel<OrderItem>> orderItems = orderItemService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(orderItems,
            linkTo(methodOn(OrderItemController.class).getAllOrderItems()).withSelfRel()
        );
    }

    //Método GET para obtener item de orden por id
    @GetMapping(value="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary= "Obtener item de orden por ID", description = "Obtiene un item de orden registrado en la bd mediante su ID")
    public EntityModel<OrderItem> getOrderItemById(@PathVariable Long id){
        OrderItem orderItem = orderItemService.findById(id);
        return assembler.toModel(orderItem);
    }

    //Método POST para crear un nuevo item de orden
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo item de orden", description = "Crea un nuevo item de orden en la bd")
    public ResponseEntity<EntityModel<OrderItem>> createOrderItem(@RequestBody OrderItem orderItem){
        OrderItem newOrderItem =  orderItemService.save(orderItem);
        return ResponseEntity
               .created(linkTo(methodOn(OrderItemController.class).getOrderItemById(newOrderItem.getId())).toUri())
               .body(assembler.toModel(newOrderItem));
    }

    //Método PUT para actualizar un item de orden existente
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un item de orden", description = "Actualiza los datos de un item de orden existente en la bd")
    public ResponseEntity<EntityModel<OrderItem>> updateOrderItem(@PathVariable Long id, @RequestBody OrderItem orderItem){
        orderItem.setId(id);
        OrderItem updatedOrderItem = orderItemService.save(orderItem);
        return ResponseEntity
               .ok(assembler.toModel(updatedOrderItem));
    }

    //Método DELETE para eliminar un item de orden por id
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un item de orden", description = "Elimina un item de orden existente en la bd mediante su ID")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Long id){
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
