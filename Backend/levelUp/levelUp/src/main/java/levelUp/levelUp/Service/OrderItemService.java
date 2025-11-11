package levelUp.levelUp.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import levelUp.levelUp.Model.OrderItem;
import levelUp.levelUp.Repository.OrderItemRepository;

@Service
@Transactional
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    //Método para obtener todos los items de orden
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    //Metodo para buscar item de orden por id en la bd
    public OrderItem findById(Long id){
        return orderItemRepository.findById(id).get();
    }

    //Método para guardar/sobreescribir un item de orden en la bd
    public OrderItem save(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }
    
    //Método para eliminar un item de orden por id 
    public void delete(Long id){
        orderItemRepository.deleteById(id);
    }
}
