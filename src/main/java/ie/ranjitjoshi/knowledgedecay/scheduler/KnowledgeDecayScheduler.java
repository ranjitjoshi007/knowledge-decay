package ie.ranjitjoshi.knowledgedecay.scheduler;

import ie.ranjitjoshi.knowledgedecay.service.KnowledgeItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KnowledgeDecayScheduler {
    private final KnowledgeItemService service;

    // Run every day at 2 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void runDecayJob() {
        service.applyDecayLogic();
    }
}
