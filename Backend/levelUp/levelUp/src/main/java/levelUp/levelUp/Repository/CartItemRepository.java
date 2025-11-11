package levelUp.levelUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import levelUp.levelUp.Model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    //Hereda todos los metodos para el CRUD de la tabla cart_item
    
}
