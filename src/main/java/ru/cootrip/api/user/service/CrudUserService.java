package ru.cootrip.api.user.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.authority.entity.Authority;
import ru.cootrip.api.user.entity.User;
import ru.cootrip.api.user.exception.UserAlreadyExistsException;
import ru.cootrip.api.user.exception.UserNotFoundException;
import ru.cootrip.api.user.repository.CrudUserRepository;

import java.util.Collection;
import java.util.UUID;

@Service
public class CrudUserService {

    private final CrudUserRepository crudUserRepository;

    public CrudUserService(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @Transactional
    public User getUserById(UUID userId) throws UserNotFoundException {
        return crudUserRepository.findById(userId).orElseThrow(
                () -> getUserNotFoundException("User with ID '%s' not found", userId)
        );
    }

    @Transactional
    public User createUser(
            String username,
            Collection<Authority> authorities,
            boolean isBlocked,
            boolean isEnabled
    ) throws UserAlreadyExistsException {
        if (existsUserByUsername(username)) {
            throwUserAlreadyExistsException("User with username '%s' already exists", username);
        }

        final User user = User.create(username, authorities, isBlocked, isEnabled);

        return crudUserRepository.save(user);
    }

    @Transactional
    public User updateUser(
            UUID userId,
            String username,
            Collection<Authority> authorities,
            boolean isBlocked,
            boolean isEnabled
    ) throws UserNotFoundException, UserAlreadyExistsException {
        final User user = getUserById(userId);

        if (existsOtherUserByUsername(username, user.getId())) {
            throwUserAlreadyExistsException("User with username '%s' already exists", username);
        }

        user.setUsername(username);
        user.setAuthorities(authorities);
        user.setIsBlocked(isBlocked);
        user.setIsEnabled(isEnabled);

        return crudUserRepository.save(user);
    }

    @Transactional
    public void deleteUserById(UUID userId) throws UserNotFoundException {
        final User user = getUserById(userId);
        crudUserRepository.delete(user);
    }

    private boolean existsUserByUsername(String username) {
        return crudUserRepository.existsByUsername(username);
    }

    private boolean existsOtherUserByUsername(String username, UUID ignoreUserId) {
        return crudUserRepository.existsByUsernameAndIdNot(username, ignoreUserId);
    }

    private UserNotFoundException getUserNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return UserNotFoundException.create(message);
    }

    private void throwUserNotFoundException(String template, Object... args) throws UserNotFoundException {
        throw getUserNotFoundException(template, args);
    }

    private UserAlreadyExistsException getUserAlreadyExistsException(String template, Object... args) {
        final String message = String.format(template, args);
        return UserAlreadyExistsException.create(message);
    }

    private void throwUserAlreadyExistsException(String template, Object... args) throws UserAlreadyExistsException {
        throw getUserAlreadyExistsException(template, args);
    }

}
