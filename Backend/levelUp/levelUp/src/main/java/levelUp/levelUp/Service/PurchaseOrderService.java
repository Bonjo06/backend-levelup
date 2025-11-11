package levelUp.levelUp.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import levelUp.levelUp.Model.PurchaseOrder;
import levelUp.levelUp.Repository.PurchaseOrderRepository;

@Service
@Transactional
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    //Método para obtener todas las órdenes de compra
    public List<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    //Metodo para buscar orden de compra por id en la bd
    public PurchaseOrder findById(Long id){
        return purchaseOrderRepository.findById(id).get();
    }

    //Método para guardar/sobreescribir una orden de compra en la bd
    public PurchaseOrder save(PurchaseOrder purchaseOrder){
        return purchaseOrderRepository.save(purchaseOrder);
    }
    
    //Método para eliminar una orden de compra por id 
    public void delete(Long id){
        purchaseOrderRepository.deleteById(id);
    }
}
