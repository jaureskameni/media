package cm.klg.media.adapter.persistence.jpa.outbound;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentSpringRepository extends JpaRepository<DocumentJpa, UUID> {}
