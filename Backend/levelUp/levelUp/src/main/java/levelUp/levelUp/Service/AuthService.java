package levelUp.levelUp.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import levelUp.levelUp.Dto.LoginRequest;
import levelUp.levelUp.Dto.PasswordResetRequest;
import levelUp.levelUp.Dto.RegisterRequest;
import levelUp.levelUp.Model.User;
import levelUp.levelUp.Repository.UserRepository;
import levelUp.levelUp.Security.JwtUtil;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * LOGICA PARA LOGIN
    */

    public Map<String, Object> login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Verificar contraseña - soporta tanto BCrypt como texto plano (migración)
        boolean passwordMatches;
        if (user.getPassword().startsWith("$2a$") || user.getPassword().startsWith("$2b$")) {
            // Contraseña encriptada con BCrypt
            passwordMatches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        } else {
            // Contraseña en texto plano (sistema antiguo)
            passwordMatches = user.getPassword().equals(loginRequest.getPassword());
            
            // Actualizar a BCrypt en el siguiente login
            if (passwordMatches) {
                user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
                userRepository.save(user);
            }
        }

        if (!passwordMatches) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar token JWT
        String token = jwtUtil.generateToken(user.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Inicio de sesión exitoso");
        response.put("token", token);
        response.put("user", Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "name", user.getName(),
            "role", user.getRole()
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
        // Encriptar contraseña con BCrypt
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        
        // Asignar rol automáticamente
        // Si es el email del administrador, asignar rol ADMIN, sino USER
        if ("admin@levelup.com".equals(registerRequest.getEmail())) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }

        User savedUser = userRepository.save(user);

        // Generar token JWT para el nuevo usuario
        String token = jwtUtil.generateToken(savedUser.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Registro exitoso");
        response.put("token", token);
        response.put("user", Map.of(
            "id", savedUser.getId(),
            "email", savedUser.getEmail(),
            "name", savedUser.getName(),
            "role", savedUser.getRole()
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

        // Encriptar la nueva contraseña con BCrypt
        user.setPassword(passwordEncoder.encode(passwordReset.getNewPassword()));
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Contraseña actualizada exitosamente");
        return response;
     }

}
