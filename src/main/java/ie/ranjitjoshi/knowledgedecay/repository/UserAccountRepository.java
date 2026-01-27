package ie.ranjitjoshi.knowledgedecay.repository;

import ie.ranjitjoshi.knowledgedecay.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
}
