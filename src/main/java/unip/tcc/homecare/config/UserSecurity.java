package unip.tcc.homecare.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import unip.tcc.homecare.model.User;

@Component("userSecurity")
public class UserSecurity {
    public boolean hasUserId(Authentication authentication, Long userId) {
        User user = (User)authentication.getPrincipal();
        return user.getId().equals(userId);
    }
}
