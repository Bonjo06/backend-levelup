package levelUp.levelUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import levelUp.levelUp.Model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    
    //Hereda todos los metodos para el CRUD de la tabla order_status
    
}
