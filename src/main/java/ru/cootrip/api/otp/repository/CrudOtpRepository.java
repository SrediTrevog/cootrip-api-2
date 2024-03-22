package ru.cootrip.api.otp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.otp.entity.OTP;
import ru.cootrip.api.phonenumber.entity.PhoneNumber;

import java.util.UUID;

@Repository
public interface CrudOtpRepository extends CrudRepository<OTP, UUID> {

    boolean existsByPhoneNumberAndPassword(PhoneNumber phoneNumber, String password);

    boolean existsByPhoneNumberAndPasswordAndIdNot(PhoneNumber phoneNumber, String password, UUID ignoreId);

}
