package levelUp.levelUp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import levelUp.levelUp.Model.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    // Hereda todos los métodos para el CRUD de la tabla contact_messages
}
