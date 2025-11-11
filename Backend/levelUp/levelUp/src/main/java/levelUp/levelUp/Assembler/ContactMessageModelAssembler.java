package levelUp.levelUp.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import levelUp.levelUp.Controller.ContactMessageController;
import levelUp.levelUp.Model.ContactMessage;

@Component
public class ContactMessageModelAssembler implements RepresentationModelAssembler<ContactMessage, EntityModel<ContactMessage>>{
    
    @Override
    public EntityModel<ContactMessage> toModel(ContactMessage contactMessage){

        return EntityModel.of(contactMessage,
        linkTo(methodOn(ContactMessageController.class).getContactMessageById(contactMessage.getId())).withSelfRel(),
        linkTo(methodOn(ContactMessageController.class).getAllContactMessages()).withRel("contactMessages")
        );
    }

}
