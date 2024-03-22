package ru.cootrip.api.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.user.entity.User;

import java.util.UUID;

@Repository
public interface CrudUserRepository extends CrudRepository<User, UUID> {

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, UUID ignoreId);

}
