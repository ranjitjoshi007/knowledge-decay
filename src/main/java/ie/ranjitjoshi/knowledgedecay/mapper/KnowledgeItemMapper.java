package ie.ranjitjoshi.knowledgedecay.mapper;
import ie.ranjitjoshi.knowledgedecay.domain.entity.KnowledgeItem;
import ie.ranjitjoshi.knowledgedecay.dto.request.CreateKnowledgeItemRequest;
import ie.ranjitjoshi.knowledgedecay.dto.response.KnowledgeItemResponse;

import java.time.LocalDate;
public class KnowledgeItemMapper {

    public static KnowledgeItem toEntity(CreateKnowledgeItemRequest dto, String ownerEmail) {
        return KnowledgeItem.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .reviewFrequencyDays(dto.getReviewFrequencyDays())
                .ownerEmail(ownerEmail)
                .lastReviewedAt(LocalDate.now())
                .build();
    }
    public static KnowledgeItemResponse toResponse(KnowledgeItem item) {
        return KnowledgeItemResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .content(item.getContent())
                .status(item.getStatus())
                .lastReviewedAt(item.getLastReviewedAt())
                .reviewFrequencyDays(item.getReviewFrequencyDays())
                .build();
    }
}
