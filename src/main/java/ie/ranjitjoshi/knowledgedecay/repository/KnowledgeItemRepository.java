package ie.ranjitjoshi.knowledgedecay.repository;
import ie.ranjitjoshi.knowledgedecay.domain.entity.KnowledgeItem;
import ie.ranjitjoshi.knowledgedecay.domain.enums.KnowledgeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface KnowledgeItemRepository extends JpaRepository<KnowledgeItem, Long> {

    // Find all active knowledge items
    Page<KnowledgeItem> findByStatus(KnowledgeStatus status, Pageable pageable);

    // Find items that were last reviewed before a certain date
    Page<KnowledgeItem> findByLastReviewedAtBefore(LocalDate date, Pageable pageable);

    // Find user-specific knowledge
    Page<KnowledgeItem> findByOwnerEmail(String ownerEmail, Pageable pageable);

    Page<KnowledgeItem> findByUserEmail(String email, Pageable pageable);

    Optional<KnowledgeItem> findByIdAndUserEmail(Long id, String email);
    Page<KnowledgeItem> findByOwnerEmailAndStatus(
            String ownerEmail,
            KnowledgeStatus status,
            Pageable pageable
    );
}