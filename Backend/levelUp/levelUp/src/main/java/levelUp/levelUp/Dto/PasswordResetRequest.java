package levelUp.levelUp.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {

    private String email;
    private String newPassword;
    
}
