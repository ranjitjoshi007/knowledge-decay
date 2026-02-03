package ie.ranjitjoshi.knowledgedecay.domain.entity;

import ie.ranjitjoshi.knowledgedecay.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)//, columnDefinition = "VARCHAR(20) DEFAULT 'USER'")
    private Role role;
}
