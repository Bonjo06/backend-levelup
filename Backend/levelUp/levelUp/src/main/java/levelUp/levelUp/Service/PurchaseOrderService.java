package levelUp.levelUp.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import levelUp.levelUp.Dto.CreateOrderRequest;
import levelUp.levelUp.Dto.OrderItemDto;
import levelUp.levelUp.Model.Inventario;
import levelUp.levelUp.Model.OrderItem;
import levelUp.levelUp.Model.OrderStatus;
import levelUp.levelUp.Model.PurchaseOrder;
import levelUp.levelUp.Model.User;
import levelUp.levelUp.Repository.InventarioRepository;
import levelUp.levelUp.Repository.PurchaseOrderRepository;
import levelUp.levelUp.Repository.UserRepository;

@Service
@Transactional
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private InventarioRepository inventarioRepository;

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

    //Método para crear una orden desde el carrito (NUEVO)
    public PurchaseOrder createOrderFromCart(CreateOrderRequest request) {
        // Buscar el usuario por email
        User user = userRepository.findByEmail(request.getUserEmail());
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado con email: " + request.getUserEmail());
        }
        
        // Crear la orden principal
        PurchaseOrder order = new PurchaseOrder();
        order.setUser(user);
        order.setTotal(request.getTotal());
        order.setStatus(OrderStatus.PENDING);
        
        // Crear los items de la orden
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto itemDto : request.getItems()) {
            // Buscar el producto en el inventario
            Inventario product = inventarioRepository.findById(itemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + itemDto.getProductId()));
            
            // Verificar stock disponible
            if (product.getItemQuantity() < itemDto.getQuantity()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + itemDto.getProductTitle());
            }
            
            // Crear el OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setProductTitle(itemDto.getProductTitle());
            orderItem.setUnitPrice(itemDto.getUnitPrice());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPurchaseOrder(order);
            
            orderItems.add(orderItem);
            
            // Reducir el stock del producto
            product.setItemQuantity(product.getItemQuantity() - itemDto.getQuantity());
            inventarioRepository.save(product);
        }
        
        order.setItems(orderItems);
        
        // Guardar la orden completa
        return purchaseOrderRepository.save(order);
    }
    
    //Método para actualizar el estado de una orden (NUEVO)
    public PurchaseOrder updateOrderStatus(Long orderId, OrderStatus newStatus) {
        PurchaseOrder order = findById(orderId);
        order.setStatus(newStatus);
        return purchaseOrderRepository.save(order);
    }
}
