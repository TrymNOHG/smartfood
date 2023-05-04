package edu.ntnu.idatt2106_2023_06.backend.service.users;

import edu.ntnu.idatt2106_2023_06.backend.exception.not_found.UserNotFoundException;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import edu.ntnu.idatt2106_2023_06.backend.repo.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService implements IUserInfoService {

    private final UserRepository userRepository;

    @Override
    public String getAuthenticatedUserEmail() {
        return getAuthenticatedUserObject().getEmail();
    }

    @Override
    public String getAuthenticatedUserFirstName() {
        return getAuthenticatedUserObject().getFirstName();
    }

    @Override
    public String getAuthenticatedUserLastName() {
        return getAuthenticatedUserObject().getLastName();
    }

    @Override
    public String getAuthenticatedUserUsername() {
        return getAuthenticatedUserObject().getUsername();
    }

    @Override
    public User getAuthenticatedUserObject() {
        return userRepository.findById(getAuthenticatedUserId()).orElseThrow(
                () -> new UserNotFoundException(getAuthenticatedUserId())
        );
    }

    @Override
    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((User) authentication.getPrincipal()).getUserId();
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated();
    }
}
