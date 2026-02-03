package ie.ranjitjoshi.knowledgedecay.security;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OwnerOrAdminAspect {

    private final SecurityService securityService;

    @Before("@annotation(ie.ranjitjoshi.knowledgedecay.security.OwnerOrAdmin)")
    public void checkAccess(JoinPoint joinPoint) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Object[] args = joinPoint.getArgs();

        // Convention: ownerEmail must be one of the method parameters
        for (Object arg : args) {
            if (arg instanceof String ownerEmail) {
                securityService.checkOwnerOrAdmin(auth, ownerEmail);
                return;
            }
        }

        throw new AccessDeniedException("Owner email not found in method arguments");
    }
}
