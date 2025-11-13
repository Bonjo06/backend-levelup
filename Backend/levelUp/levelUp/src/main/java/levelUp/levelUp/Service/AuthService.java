package levelUp.levelUp.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import levelUp.levelUp.Dto.LoginRequest;
import levelUp.levelUp.Dto.PasswordResetRequest;
import levelUp.levelUp.Dto.RegisterRequest;
import levelUp.levelUp.Model.User;
import levelUp.levelUp.Repository.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    /*
     * LOGICA PARA LOGIN
    */

    public Map<String, Object> login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Inicio de sesión exitoso");
        response.put("user", Map.of(
            "email", user.getEmail(),
            "name", user.getName()
        ));
        return response;
    }

    /*
     * LOGICA PARA REGISTRAR USUARIO
    */
    public Map<String, Object> register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new RuntimeException("El email ya se encuentra registrado");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());

        User savedUser = userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Registro exitoso");
        response.put("user", Map.of(
            "email", savedUser.getEmail(),
            "name", savedUser.getName()
        ));
        return response;
    }

    /*
     * LOGICA PARA CAMBIAR CONTRASEÑA
     */

     public Map<String, Object> resetPassword(PasswordResetRequest passwordReset) {
        User user = userRepository.findByEmail(passwordReset.getEmail());

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        user.setPassword(passwordReset.getNewPassword());
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Contraseña actualizada exitosamente");
        return response;
     }

}
