package levelUp.levelUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import levelUp.levelUp.Model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    
    //Hereda todos los metodos para el CRUD de la tabla purchase_order
    
}
