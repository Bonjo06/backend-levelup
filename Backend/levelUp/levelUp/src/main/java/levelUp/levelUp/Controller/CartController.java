package levelUp.levelUp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.HashMap;
import java.util.Map;


import levelUp.levelUp.Model.Cart;
import levelUp.levelUp.Service.CartService;
import levelUp.levelUp.Assembler.CartModelAssembler;
import levelUp.levelUp.Dto.AddToCartRequest;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartModelAssembler assembler;

    //Método GET para obtener carrito del usuario
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener carrito del usuario", description = "Obtiene el carrito de un usuario por su email")
    public ResponseEntity<?> getCartByUser(Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            Cart cart = cartService.getOrCreateCartForUser(userEmail);
            return ResponseEntity.ok(assembler.toModel(cart));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al obtener carrito: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    //Método POST para agregar item al carrito
    @PostMapping(value = "/add", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Agregar item al carrito", description = "Agrega un producto al carrito del usuario")
    public ResponseEntity<?> addItemToCart(
            Authentication authentication,
            @RequestBody AddToCartRequest request) {
        try {
            String userEmail = authentication.getName();
            Cart cart = cartService.addItemToCart(userEmail, request.getProductId(), request.getQuantity());
            return ResponseEntity.ok(assembler.toModel(cart));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al agregar item: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    //Método PATCH para actualizar cantidad de item
    @PatchMapping(value = "/update", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar cantidad de item", description = "Actualiza la cantidad de un producto en el carrito")
    public ResponseEntity<?> updateItemQuantity(
            Authentication authentication,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        try {
            String userEmail = authentication.getName();
            Cart cart = cartService.updateItemQuantity(userEmail, productId, quantity);
            return ResponseEntity.ok(assembler.toModel(cart));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al actualizar cantidad: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    //Método DELETE para remover item del carrito
    @DeleteMapping(value = "/remove", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Remover item del carrito", description = "Elimina un producto del carrito")
    public ResponseEntity<?> removeItemFromCart(
            Authentication authentication,
            @RequestParam Long productId) {
        try {
            String userEmail = authentication.getName();
            Cart cart = cartService.removeItemFromCart(userEmail, productId);
            return ResponseEntity.ok(assembler.toModel(cart));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al remover item: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    //Método DELETE para vaciar el carrito
    @DeleteMapping(value = "/clear", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Vaciar carrito", description = "Elimina todos los items del carrito del usuario")
    public ResponseEntity<?> clearCart(Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            Cart cart = cartService.clearCart(userEmail);
            return ResponseEntity.ok(assembler.toModel(cart));
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error al vaciar carrito: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
