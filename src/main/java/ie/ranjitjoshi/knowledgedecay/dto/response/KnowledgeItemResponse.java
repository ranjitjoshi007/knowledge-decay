package ie.ranjitjoshi.knowledgedecay.dto.response;
import ie.ranjitjoshi.knowledgedecay.domain.enums.KnowledgeStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class KnowledgeItemResponse {

    private Long id;
    private String title;
    private String content;
    private KnowledgeStatus status;
    private LocalDate lastReviewedAt;
    private int reviewFrequencyDays;
}
