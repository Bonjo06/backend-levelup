package levelUp.levelUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import levelUp.levelUp.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    //Hereda todos los metodos para el CRUD de la tabla user
    User findByEmail(String email);
    
}
