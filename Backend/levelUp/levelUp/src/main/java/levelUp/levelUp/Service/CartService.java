package levelUp.levelUp.Service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import levelUp.levelUp.Model.Cart;
import levelUp.levelUp.Model.CartItem;
import levelUp.levelUp.Model.Inventario;
import levelUp.levelUp.Model.User;
import levelUp.levelUp.Repository.CartRepository;
import levelUp.levelUp.Repository.InventarioRepository;
import levelUp.levelUp.Repository.UserRepository;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    //Método para obtener todos los carritos
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    //Metodo para buscar carrito por id en la bd
    public Cart findById(Long id){
        return cartRepository.findById(id).orElse(null);
    }

    //Método para obtener o crear carrito de un usuario por email
    public Cart getOrCreateCartForUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail);
    
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado: " + userEmail);
        }

        Cart existingCart = cartRepository.findByUser(user);
        
        if (existingCart != null) {
            return existingCart;
        }

        Cart newCart = new Cart();
        newCart.setUser(user);
        return cartRepository.save(newCart);
    }

    //Método para agregar item al carrito
    public Cart addItemToCart(String userEmail, Long productId, int quantity) {
        Cart cart = getOrCreateCartForUser(userEmail);
        Inventario product = inventarioRepository.findById(productId).orElse(null);
        
        if (product == null) {
            throw new RuntimeException("Producto no encontrado: " + productId);
        }

        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setProductTitle(product.getItemTitle());
            newItem.setUnitPrice(BigDecimal.valueOf(product.getItemPrice()));
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    //Método para actualizar cantidad de un item
    public Cart updateItemQuantity(String userEmail, Long productId, int newQuantity) {
        Cart cart = getOrCreateCartForUser(userEmail);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        
        if (item == null) {
            throw new RuntimeException("Item no encontrado en el carrito");
        }

        if (newQuantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(newQuantity);
        }

        return cartRepository.save(cart);
    }

    //Método para remover item del carrito
    public Cart removeItemFromCart(String userEmail, Long productId) {
        Cart cart = getOrCreateCartForUser(userEmail);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        return cartRepository.save(cart);
    }

    //Método para vaciar el carrito
    public Cart clearCart(String userEmail) {
        Cart cart = getOrCreateCartForUser(userEmail);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }

    //Método para guardar/sobreescribir un carrito en la bd
    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }
    
    //Método para eliminar un carrito por id 
    public void delete(Long id){
        cartRepository.deleteById(id);
    }
}
