package ie.ranjitjoshi.knowledgedecay.controller;

import ie.ranjitjoshi.knowledgedecay.domain.entity.KnowledgeItem;
import ie.ranjitjoshi.knowledgedecay.domain.enums.KnowledgeStatus;
import ie.ranjitjoshi.knowledgedecay.dto.request.CreateKnowledgeItemRequest;
import ie.ranjitjoshi.knowledgedecay.dto.response.KnowledgeItemResponse;
import ie.ranjitjoshi.knowledgedecay.mapper.KnowledgeItemMapper;
import ie.ranjitjoshi.knowledgedecay.service.KnowledgeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
@RequiredArgsConstructor
public class KnowledgeItemController {
    private final KnowledgeItemService service;

    // Get all knowledge items
    @GetMapping
    public ResponseEntity<List<KnowledgeItem>> getAllItems() {
        return ResponseEntity.ok(service.getAllItems());
    }

    @PostMapping
    public ResponseEntity<KnowledgeItemResponse> createItem(@RequestBody CreateKnowledgeItemRequest request, Authentication auth) {
        KnowledgeItem item =
                KnowledgeItemMapper.toEntity(request, auth.getName());

//        item.setOwnerEmail(auth.getName());
//        item.setLastReviewedAt(java.time.LocalDate.now());
//        item.setStatus(KnowledgeStatus.ACTIVE);
        KnowledgeItem saved = service.saveItem(item);
        return ResponseEntity.ok(KnowledgeItemMapper.toResponse(saved));
    }
    // Optional: mark as reviewed (updates lastReviewedAt)
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeItem> updateItem(@PathVariable Long id,@RequestBody KnowledgeItem updatedItem,                                                    Authentication auth) {
        KnowledgeItem item = service.getItemById(id);


        if (!item.getOwnerEmail().equals(auth.getName())) {
            throw new RuntimeException("You cannot update this item");
        }

        item.setTitle(updatedItem.getTitle());
        item.setContent(updatedItem.getContent());
        item.setReviewFrequencyDays(updatedItem.getReviewFrequencyDays());
        item.setLastReviewedAt(java.time.LocalDate.now()); // optional: reset review date
        item.setStatus(KnowledgeStatus.ACTIVE);

        service.saveItem(item);
        return ResponseEntity.ok(item);
    }
    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeItemResponse> getItem(@PathVariable Long id) {
        KnowledgeItem item = service.getItemById(id);

        return ResponseEntity.ok(KnowledgeItemMapper.toResponse(item));
    }
    @DeleteMapping("/{id}")
    public boolean deleteItem(@PathVariable Long id) {
        KnowledgeItem item = service.getItemById(id);
        service.deleteItem(id);
        return true;

    }
    @PutMapping("/{id}/review")
    public ResponseEntity<KnowledgeItem> markAsReviewed(@PathVariable Long id, Authentication auth) {
        KnowledgeItem item = service.getItemById(id);

        if (!item.getOwnerEmail().equals(auth.getName())) {
            throw new RuntimeException("You cannot update this item");
        }

        item.setLastReviewedAt(LocalDate.now());
        item.setStatus(KnowledgeStatus.ACTIVE);

        service.saveItem(item);
        return ResponseEntity.ok(item);
    }
}
