package ie.ranjitjoshi.knowledgedecay.domain.entity;

import ie.ranjitjoshi.knowledgedecay.domain.enums.KnowledgeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "knowledge_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String ownerEmail;

    @Column(nullable = false)
    private LocalDate lastReviewedAt;
    @Column(nullable = false)
    private int reviewFrequencyDays;  // e.g., 30, 90, 180

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KnowledgeStatus status = KnowledgeStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;
}
