package levelUp.levelUp.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import levelUp.levelUp.Model.Cart;
import levelUp.levelUp.Repository.CartRepository;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    //Método para obtener todos los carritos
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    //Metodo para buscar carrito por id en la bd
    public Cart findById(Long id){
        return cartRepository.findById(id).get();
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
