package levelUp.levelUp.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import levelUp.levelUp.Model.CartItem;
import levelUp.levelUp.Repository.CartItemRepository;

@Service
@Transactional
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    //Método para obtener todos los items del carrito
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    //Metodo para buscar item del carrito por id en la bd
    public CartItem findById(Long id){
        return cartItemRepository.findById(id).get();
    }

    //Método para guardar/sobreescribir un item del carrito en la bd
    public CartItem save(CartItem cartItem){
        return cartItemRepository.save(cartItem);
    }
    
    //Método para eliminar un item del carrito por id 
    public void delete(Long id){
        cartItemRepository.deleteById(id);
    }
}
