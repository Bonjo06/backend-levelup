package levelUp.levelUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import levelUp.levelUp.Model.Cart;
import levelUp.levelUp.Model.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    //Hereda todos los metodos para el CRUD de la tabla cart
    Cart findByUser(User user);
    
}
