package ru.cootrip.api.otp.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.otp.entity.OTP;
import ru.cootrip.api.otp.exception.OTPAlreadyExistsException;
import ru.cootrip.api.otp.exception.OTPNotFoundException;
import ru.cootrip.api.otp.repository.CrudOtpRepository;
import ru.cootrip.api.phonenumber.entity.PhoneNumber;

import java.util.UUID;

@Service
public class CrudOtpService {

    private final CrudOtpRepository crudOtpRepository;

    public CrudOtpService(CrudOtpRepository crudOtpRepository) {
        this.crudOtpRepository = crudOtpRepository;
    }

    @Transactional
    public OTP getOtpById(UUID otpId) throws OTPNotFoundException {
        return crudOtpRepository.findById(otpId).orElseThrow(
                () -> getOtpNotFoundException("OTP with ID '%s' not found", otpId)
        );
    }

    @Transactional
    public OTP createOtp(PhoneNumber phoneNumber, String password) throws OTPAlreadyExistsException {
        if (existsOtpByPhoneNumberAndPassword(phoneNumber, password)) {
            throwOtpAlreadyExistsException("OTP already exists");
        }

        final OTP otp = OTP.create(phoneNumber, password);
        return crudOtpRepository.save(otp);
    }

    @Transactional
    public OTP updateOtp(
            UUID otpId,
            PhoneNumber phoneNumber,
            String password
    ) throws OTPNotFoundException, OTPAlreadyExistsException {
        final OTP otp = getOtpById(otpId);

        if (existsOtherOtpByPhoneNumberAndPassword(phoneNumber, password, otpId)) {
            throwOtpAlreadyExistsException("OTP already exists");
        }

        otp.setPhoneNumber(phoneNumber);
        otp.setPassword(password);

        return crudOtpRepository.save(otp);
    }

    @Transactional
    public void deleteOtpById(UUID otpId) throws OTPNotFoundException {
        final OTP otp = getOtpById(otpId);
        crudOtpRepository.delete(otp);
    }

    private boolean existsOtpByPhoneNumberAndPassword(PhoneNumber phoneNumber, String password) {
        return crudOtpRepository.existsByPhoneNumberAndPassword(phoneNumber, password);
    }

    private boolean existsOtherOtpByPhoneNumberAndPassword(PhoneNumber phoneNumber, String password, UUID ignoreOtpId) {
        return crudOtpRepository.existsByPhoneNumberAndPasswordAndIdNot(phoneNumber, password, ignoreOtpId);
    }

    private OTPNotFoundException getOtpNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return OTPNotFoundException.create(message);
    }

    private void throwOtpNotFoundException(String template, Object... args) throws OTPNotFoundException {
        throw getOtpNotFoundException(template, args);
    }

    private OTPAlreadyExistsException getOtpAlreadyExistsException(String template, Object... args) {
        final String message = String.format(template, args);
        return OTPAlreadyExistsException.create(message);
    }

    private void throwOtpAlreadyExistsException(String template, Object... args) throws OTPAlreadyExistsException {
        throw getOtpAlreadyExistsException(template, args);
    }

}
