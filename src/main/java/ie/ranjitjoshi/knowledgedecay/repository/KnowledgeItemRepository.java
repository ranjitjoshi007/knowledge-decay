package ie.ranjitjoshi.knowledgedecay.repository;
import ie.ranjitjoshi.knowledgedecay.domain.entity.KnowledgeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface KnowledgeItemRepository extends JpaRepository<KnowledgeItem, Long> {

    // Find all active knowledge items
    List<KnowledgeItem> findByStatus(String status);

    // Find items that were last reviewed before a certain date
    List<KnowledgeItem> findByLastReviewedAtBefore(LocalDate date);

    // Find user-specific knowledge
    List<KnowledgeItem> findByOwnerEmail(String ownerEmail);
}