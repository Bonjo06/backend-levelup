package levelUp.levelUp.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import levelUp.levelUp.Model.ContactMessage;
import levelUp.levelUp.Repository.ContactMessageRepository;

@Service
@Transactional
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    //Método para obtener todos los mensajes de contacto
    public List<ContactMessage> findAll() {
        return contactMessageRepository.findAll();
    }

    //Metodo para buscar mensaje de contacto por id en la bd
    public ContactMessage findById(Long id){
        return contactMessageRepository.findById(id).get();
    }

    //Método para guardar/sobreescribir un mensaje de contacto en la bd
    public ContactMessage save(ContactMessage contactMessage){
        return contactMessageRepository.save(contactMessage);
    }
    
    //Método para eliminar un mensaje de contacto por id 
    public void delete(Long id){
        contactMessageRepository.deleteById(id);
    }
}
