package ie.ranjitjoshi.knowledgedecay.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    public boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isOwner(Authentication auth, String ownerEmail) {
        return auth.getName().equals(ownerEmail);
    }

    public void checkOwnerOrAdmin(Authentication auth, String ownerEmail) {
        if (!isOwner(auth, ownerEmail) && !isAdmin(auth)) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
