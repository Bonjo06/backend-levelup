package levelUp.levelUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import levelUp.levelUp.Model.Inventario;

public interface InventarioRepository  extends JpaRepository<Inventario, Long> {
    
    //Hereda todos los metodos para el CRUD de la tabla inventario

}
