package ie.ranjitjoshi.knowledgedecay.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateKnowledgeItemRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Review frequency is required")
    @Min(value = 1, message = "Review frequency must be at least 1 day")
    private int reviewFrequencyDays;
}
