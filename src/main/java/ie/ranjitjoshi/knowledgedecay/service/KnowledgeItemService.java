package ie.ranjitjoshi.knowledgedecay.service;

import ie.ranjitjoshi.knowledgedecay.domain.entity.KnowledgeItem;
import ie.ranjitjoshi.knowledgedecay.domain.enums.KnowledgeStatus;
import ie.ranjitjoshi.knowledgedecay.exception.ResourceNotFoundException;
import ie.ranjitjoshi.knowledgedecay.repository.KnowledgeItemRepository;
import ie.ranjitjoshi.knowledgedecay.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class KnowledgeItemService {
    private final KnowledgeItemRepository repository;
    private final SecurityService securityService;

    public List<KnowledgeItem> getAllItems() {
        return repository.findAll();
    }
    public Page<KnowledgeItem> listForUser(Authentication auth, KnowledgeStatus status, Pageable pageable) {
        boolean isAdmin = securityService.isAdmin(auth);
        if (isAdmin && status != null) {
            return repository.findByStatus(status, pageable);
        }

        if (isAdmin) {
            return repository.findAll(pageable);
        }

        if (status != null) {
            return repository.findByOwnerEmailAndStatus(auth.getName(), status, pageable);
        }

        return repository.findByOwnerEmail(auth.getName(), pageable);
    }
    public KnowledgeItem saveItem(KnowledgeItem item) {
        item.setStatus(KnowledgeStatus.ACTIVE); // default
        return repository.save(item);
    }
    public KnowledgeItem getItemById(Long id) {
        try {
            repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Knowledge item not found"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Knowledge item not found"));
    }
    public void deleteItem(Long id) {
        repository.deleteById(id);
    }
    public KnowledgeItem create(KnowledgeItem item) {
        item.setStatus(KnowledgeStatus.ACTIVE);
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
