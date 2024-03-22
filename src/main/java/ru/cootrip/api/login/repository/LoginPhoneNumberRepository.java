package ru.cootrip.api.login.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.phonenumber.entity.PhoneNumber;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginPhoneNumberRepository extends CrudRepository<PhoneNumber, UUID> {

    Optional<PhoneNumber> getByPhone(String phone);

}
