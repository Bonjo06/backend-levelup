package levelUp.levelUp.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
	private User user;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;

    private String subject;

    @Column(nullable=false, columnDefinition="TEXT")
    private String message;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
