package levelUp.levelUp.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import levelUp.levelUp.Model.User;
import levelUp.levelUp.Repository.UserRepository;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Método para obtener todos los usuarios
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //Metodo para buscar usuario por id en la bd
    public User findById(Long id){
        return userRepository.findById(id).get();
    }

    //Método para guardar/sobreescribir un usuario en la bd
    public User save(User user){
        return userRepository.save(user);
    }
    
    //Método para eliminar un usuario por id 
    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
