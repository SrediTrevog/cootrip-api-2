package ru.cootrip.api.country.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.country.entity.Country;

import java.util.UUID;

@Repository
public interface CrudCountryRepository extends CrudRepository<Country, UUID> {

    boolean existsByIsoCode(String isoCode);

    boolean existsByIsoCodeAndIdNot(String isoCode, UUID ignoreId);

}
