package levelUp.levelUp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import levelUp.levelUp.Model.ContactMessage;
import levelUp.levelUp.Service.ContactMessageService;
import levelUp.levelUp.Assembler.ContactMessageModelAssembler;

@RestController
@RequestMapping("/api/contact-messages")
public class ContactMessageController {

    @Autowired
    private ContactMessageService contactMessageService;

    @Autowired
    private ContactMessageModelAssembler assembler;

    //Método GET para obtener todos los mensajes de contacto
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los mensajes de contacto", description = "Obtiene todos los mensajes de contacto registrados en la bd")
    public CollectionModel<EntityModel<ContactMessage>> getAllContactMessages(){
        List<EntityModel<ContactMessage>> contactMessages = contactMessageService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(contactMessages,
            linkTo(methodOn(ContactMessageController.class).getAllContactMessages()).withSelfRel()
        );
    }

    //Método GET para obtener mensaje de contacto por id
    @GetMapping(value="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary= "Obtener mensaje de contacto por ID", description = "Obtiene un mensaje de contacto registrado en la bd mediante su ID")
    public EntityModel<ContactMessage> getContactMessageById(@PathVariable Long id){
        ContactMessage contactMessage = contactMessageService.findById(id);
        return assembler.toModel(contactMessage);
    }

    //Método POST para crear un nuevo mensaje de contacto
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo mensaje de contacto", description = "Crea un nuevo mensaje de contacto en la bd")
    public ResponseEntity<EntityModel<ContactMessage>> createContactMessage(@RequestBody ContactMessage contactMessage){
        ContactMessage newContactMessage =  contactMessageService.save(contactMessage);
        return ResponseEntity
               .created(linkTo(methodOn(ContactMessageController.class).getContactMessageById(newContactMessage.getId())).toUri())
               .body(assembler.toModel(newContactMessage));
    }

    //Método PUT para actualizar un mensaje de contacto existente
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un mensaje de contacto", description = "Actualiza los datos de un mensaje de contacto existente en la bd")
    public ResponseEntity<EntityModel<ContactMessage>> updateContactMessage(@PathVariable Long id, @RequestBody ContactMessage contactMessage){
        contactMessage.setId(id);
        ContactMessage updatedContactMessage = contactMessageService.save(contactMessage);
        return ResponseEntity
               .ok(assembler.toModel(updatedContactMessage));
    }

    //Método DELETE para eliminar un mensaje de contacto por id
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un mensaje de contacto", description = "Elimina un mensaje de contacto existente en la bd mediante su ID")
    public ResponseEntity<?> deleteContactMessage(@PathVariable Long id){
        contactMessageService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
