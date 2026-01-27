package ie.ranjitjoshi.knowledgedecay.service;

import ie.ranjitjoshi.knowledgedecay.domain.entity.KnowledgeItem;
import ie.ranjitjoshi.knowledgedecay.domain.enums.KnowledgeStatus;
import ie.ranjitjoshi.knowledgedecay.repository.KnowledgeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KnowledgeItemService {
    private final KnowledgeItemRepository repository;

    public List<KnowledgeItem> getAllItems() {
        return repository.findAll();
    }

    public KnowledgeItem saveItem(KnowledgeItem item) {
        item.setStatus(KnowledgeStatus.ACTIVE); // default
        return repository.save(item);
    }
    public Optional<KnowledgeItem> getItemById(Long id) {
        return repository.findById(id);
    }
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }
    public KnowledgeItem create(KnowledgeItem item) {
        item.setStatus(KnowledgeStatus.ACTIVE);

        // Auto-set lastReviewedAt if not provided
        if(item.getLastReviewedAt() == null){
            item.setLastReviewedAt(LocalDate.now());
        }

        return repository.save(item);
    }
    public void applyDecayLogic() {
        List<KnowledgeItem> items = repository.findAll();
        LocalDate today = LocalDate.now();

        for (KnowledgeItem item : items) {
            if (item.getStatus() == KnowledgeStatus.ARCHIVED) continue;

            LocalDate expiryDate = item.getLastReviewedAt().plusDays(item.getReviewFrequencyDays());

            if (today.isAfter(expiryDate)) {
                item.setStatus(KnowledgeStatus.STALE);
                repository.save(item);
                // Optional: add audit logging here
            } else {
                item.setStatus(KnowledgeStatus.ACTIVE);
                repository.save(item);
            }
        }
    }
}
