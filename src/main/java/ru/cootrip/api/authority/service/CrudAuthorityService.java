package ru.cootrip.api.authority.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.authority.entity.Authority;
import ru.cootrip.api.authority.exception.AuthorityAlreadyExistsException;
import ru.cootrip.api.authority.exception.AuthorityNotFoundException;
import ru.cootrip.api.authority.repository.CrudAuthorityRepository;

import java.util.UUID;

@Service
public class CrudAuthorityService {

    private final CrudAuthorityRepository crudAuthorityRepository;

    public CrudAuthorityService(CrudAuthorityRepository crudAuthorityRepository) {
        this.crudAuthorityRepository = crudAuthorityRepository;
    }

    @Transactional
    public Authority getAuthorityById(UUID authorityId) throws AuthorityNotFoundException {
        return crudAuthorityRepository.findById(authorityId).orElseThrow(
                () -> getAuthorityNotFoundException("Authority with ID '%s' not found", authorityId)
        );
    }

    @Transactional
    public Authority createAuthority(String key, String name) throws AuthorityAlreadyExistsException {
        if (existsAuthorityByKey(key)) {
            throwAuthorityAlreadyExistsException("Authority with key '%s' already exists", key);
        }

        final Authority authority = Authority.create(key, name);

        return crudAuthorityRepository.save(authority);
    }

    @Transactional
    public Authority updateAuthority(
            UUID authorityId,
            String key,
            String name
    ) throws AuthorityNotFoundException, AuthorityAlreadyExistsException {
        final Authority authority = getAuthorityById(authorityId);

        if (existsOtherAuthorityByKey(key, authority.getId())) {
            throwAuthorityAlreadyExistsException("Authority with key '%s' already exists", key);
        }

        authority.setKey(key);
        authority.setName(name);

        return crudAuthorityRepository.save(authority);
    }

    @Transactional
    public void deleteAuthorityById(UUID authorityId) throws AuthorityNotFoundException {
        final Authority authority = getAuthorityById(authorityId);
        crudAuthorityRepository.delete(authority);
    }

    private boolean existsAuthorityByKey(String key) {
        return crudAuthorityRepository.existsByKey(key);
    }

    private boolean existsOtherAuthorityByKey(String key, UUID ignoreAuthorityId) {
        return crudAuthorityRepository.existsByKeyAndIdNot(key, ignoreAuthorityId);
    }

    private AuthorityNotFoundException getAuthorityNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return AuthorityNotFoundException.create(message);
    }

    private void throwAuthorityNotFoundException(String template, Object... args) throws AuthorityNotFoundException {
        throw getAuthorityNotFoundException(template, args);
    }

    private AuthorityAlreadyExistsException getAuthorityAlreadyExistsException(String template, Object... args) {
        final String message = String.format(template, args);
        return AuthorityAlreadyExistsException.create(message);
    }

    private void throwAuthorityAlreadyExistsException(
            String template,
            Object... args
    ) throws AuthorityAlreadyExistsException {
        throw getAuthorityAlreadyExistsException(template, args);
    }

}
