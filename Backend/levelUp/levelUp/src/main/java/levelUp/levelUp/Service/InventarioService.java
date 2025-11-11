package levelUp.levelUp.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import levelUp.levelUp.Model.Inventario;
import levelUp.levelUp.Repository.InventarioRepository;

@Service
@Transactional
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    //Método para obtener todos los productos
    public List<Inventario> findAll() {
        return inventarioRepository.findAll();
    }

    //Metodo para buscar producto por id en la bd
    public Inventario findById(Long id){
        return inventarioRepository.findById(id).get();
    }

    //Método para guardar/sobreescribir un producto en la bd
    public Inventario save(Inventario inventario){
        return inventarioRepository.save(inventario);
    }
    
    //Método para eliminar un producto por id 
    public void delete(Long id){
        inventarioRepository.deleteById(id);
    }
}
