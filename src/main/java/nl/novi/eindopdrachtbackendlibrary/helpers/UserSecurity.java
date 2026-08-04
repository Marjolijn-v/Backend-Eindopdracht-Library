package nl.novi.eindopdrachtbackendlibrary.helpers;

import nl.novi.eindopdrachtbackendlibrary.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    private final UserRepository userRepository;

    public UserSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isSelf(Long pathUserId, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return false;
        }

        String email = jwt.getClaimAsString("email");
        if (email == null || email.isBlank()) {
            return false;
        }

        return userRepository.findById(pathUserId)
                .map(user -> email.equalsIgnoreCase(user.getEmail()))
                .orElse(false);
    }
}
