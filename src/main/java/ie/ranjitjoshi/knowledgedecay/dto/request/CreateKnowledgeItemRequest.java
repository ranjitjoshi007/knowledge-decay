package ie.ranjitjoshi.knowledgedecay.dto.request;

import lombok.Data;

@Data
public class CreateKnowledgeItemRequest {
    private String title;
    private String content;
    private int reviewFrequencyDays;
}
