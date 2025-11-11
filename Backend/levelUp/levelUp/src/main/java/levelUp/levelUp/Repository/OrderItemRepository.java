package levelUp.levelUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import levelUp.levelUp.Model.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    //Hereda todos los metodos para el CRUD de la tabla order_item
}
