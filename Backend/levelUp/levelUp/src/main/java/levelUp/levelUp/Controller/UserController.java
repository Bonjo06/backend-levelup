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

import levelUp.levelUp.Model.User;
import levelUp.levelUp.Service.UserService;
import levelUp.levelUp.Assembler.UserModelAssembler;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelAssembler assembler;

    //Método GET para obtener todos los usuarios
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los usuarios", description = "Obten todos los usuarios registrados en la bd")
    public CollectionModel<EntityModel<User>> getAllUsers(){
        List<EntityModel<User>> users = userService.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

        return CollectionModel.of(users,
            linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel()
        );
    }

    //Método GET para obtener usuario por id
    @GetMapping(value="/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary= "Obtener usuario por ID", description = "Obtiene un usuario registrado en la bd mediante su ID")
    public EntityModel<User> getUserbyId(@PathVariable Long id){
        User user = userService.findById(id);
        return assembler.toModel(user);
    }

    //Método POST para crear un nuevo usuario
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un nuevo usuario", description = "Crea un nuevo usuario en la bd")
    public ResponseEntity<EntityModel<User>> createUser(@RequestBody User user){
        User newUser =  userService.save(user);
        return ResponseEntity
               .created(linkTo(methodOn(UserController.class).getUserbyId(newUser.getId())).toUri())
               .body(assembler.toModel(newUser));
    }

    //Método PUT para actualizar un usuario existente
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente en la bd")
    public ResponseEntity<EntityModel<User>> updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        User updatedUser = userService.save(user);
        return ResponseEntity
               .ok(assembler.toModel(updatedUser));
    }

    //Método DELETE para eliminar un usuario por id
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario existente en la bd mediante su ID")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}