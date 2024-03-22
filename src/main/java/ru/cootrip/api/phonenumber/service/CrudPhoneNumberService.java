package ru.cootrip.api.phonenumber.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.phonenumber.entity.PhoneNumber;
import ru.cootrip.api.phonenumber.exception.PhoneNumberAlreadyExistsException;
import ru.cootrip.api.phonenumber.exception.PhoneNumberNotFoundException;
import ru.cootrip.api.phonenumber.repository.CrudPhoneNumberRepository;
import ru.cootrip.api.user.entity.User;

import java.util.UUID;

@Service
public class CrudPhoneNumberService {

    private final CrudPhoneNumberRepository crudPhoneNumberRepository;

    public CrudPhoneNumberService(CrudPhoneNumberRepository crudPhoneNumberRepository) {
        this.crudPhoneNumberRepository = crudPhoneNumberRepository;
    }

    private PhoneNumberNotFoundException getPhoneNumberNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return PhoneNumberNotFoundException.create(message);
    }

    @Transactional
    public PhoneNumber getPhoneNumber(UUID phoneNumberId) throws PhoneNumberNotFoundException {
        return crudPhoneNumberRepository.findById(phoneNumberId).orElseThrow(
                () -> getPhoneNumberNotFoundException("Phone number with '%s' not found", phoneNumberId)
        );
    }

    @Transactional
    public PhoneNumber createPhoneNumber(User user, String phone) throws PhoneNumberAlreadyExistsException {
        if (existsPhoneNumberByPhone(phone)) {
            throwPhoneNumberAlreadyExistsException("Phone number '%s' already exists", phone);
        }

        final PhoneNumber phoneNumber = PhoneNumber.create(user, phone);

        return crudPhoneNumberRepository.save(phoneNumber);
    }

    @Transactional
    public PhoneNumber updatePhoneNumber(
            UUID phoneNumberId,
            User user,
            String phone
    ) throws PhoneNumberNotFoundException, PhoneNumberAlreadyExistsException {
        final PhoneNumber phoneNumber = getPhoneNumber(phoneNumberId);

        if (existsOtherPhoneNumberByPhone(phone, phoneNumber.getId())) {
            throwPhoneNumberAlreadyExistsException("Phone number '%s' already exists", phone);
        }

        phoneNumber.setUser(user);
        phoneNumber.setPhone(phone);

        return crudPhoneNumberRepository.save(phoneNumber);
    }

    @Transactional
    public void deletePhoneNumberById(UUID phoneNumberId) throws PhoneNumberNotFoundException {
        final PhoneNumber phoneNumber = getPhoneNumber(phoneNumberId);
        crudPhoneNumberRepository.delete(phoneNumber);
    }

    private boolean existsPhoneNumberByPhone(String phone) {
        return crudPhoneNumberRepository.existsByPhone(phone);
    }

    private boolean existsOtherPhoneNumberByPhone(String phone, UUID ignorePhoneNumberId) {
        return crudPhoneNumberRepository.existsByPhoneAndIdNot(phone, ignorePhoneNumberId);
    }

    private void throwPhoneNumberNotFoundException(
            String template,
            Object... args
    ) throws PhoneNumberNotFoundException {
        throw getPhoneNumberNotFoundException(template, args);
    }

    private PhoneNumberAlreadyExistsException getPhoneNumberAlreadyExistsException(String template, Object... args) {
        final String message = String.format(template, args);
        return PhoneNumberAlreadyExistsException.create(message);
    }

    private void throwPhoneNumberAlreadyExistsException(
            String template,
            Object... args
    ) throws PhoneNumberAlreadyExistsException {
        throw getPhoneNumberAlreadyExistsException(template, args);
    }

}
