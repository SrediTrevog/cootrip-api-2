package ru.cootrip.api.phonenumber.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.phonenumber.entity.PhoneNumber;

import java.util.UUID;

@Repository
public interface CrudPhoneNumberRepository extends CrudRepository<PhoneNumber, UUID> {

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdNot(String phone, UUID ignoreId);

}
