package ru.cootrip.api.authority.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.authority.entity.Authority;

import java.util.UUID;

@Repository
public interface CrudAuthorityRepository extends CrudRepository<Authority, UUID> {

    boolean existsByKey(String key);

    boolean existsByKeyAndIdNot(String key, UUID ignoreId);

}
