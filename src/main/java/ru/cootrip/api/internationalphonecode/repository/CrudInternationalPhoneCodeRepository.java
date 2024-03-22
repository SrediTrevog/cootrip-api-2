package ru.cootrip.api.internationalphonecode.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.country.entity.Country;
import ru.cootrip.api.internationalphonecode.entity.InternationalPhoneCode;

import java.util.UUID;

@Repository
public interface CrudInternationalPhoneCodeRepository extends CrudRepository<InternationalPhoneCode, UUID> {

    boolean existsByCodeAndCountry(String code, Country country);

    boolean existsByCodeAndCountryAndIdNot(String code, Country country, UUID ignoreId);

}
