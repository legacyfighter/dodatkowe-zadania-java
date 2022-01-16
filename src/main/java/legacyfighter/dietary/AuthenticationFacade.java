package legacyfighter.dietary;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}

@Component
class AuthenticationContextFacade implements AuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

