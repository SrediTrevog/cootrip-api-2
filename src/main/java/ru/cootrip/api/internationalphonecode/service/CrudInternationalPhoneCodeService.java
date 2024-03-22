package ru.cootrip.api.internationalphonecode.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.country.entity.Country;
import ru.cootrip.api.internationalphonecode.entity.InternationalPhoneCode;
import ru.cootrip.api.internationalphonecode.exception.InternationalPhoneCodeAlreadyExistsException;
import ru.cootrip.api.internationalphonecode.exception.InternationalPhoneCodeNotFoundException;
import ru.cootrip.api.internationalphonecode.repository.CrudInternationalPhoneCodeRepository;

import java.util.UUID;

@Service
public class CrudInternationalPhoneCodeService {

    private final CrudInternationalPhoneCodeRepository crudInternationalPhoneCodeRepository;

    public CrudInternationalPhoneCodeService(CrudInternationalPhoneCodeRepository crudInternationalPhoneCodeRepository) {
        this.crudInternationalPhoneCodeRepository = crudInternationalPhoneCodeRepository;
    }

    @Transactional
    public InternationalPhoneCode getInternationalPhoneCodeById(
            UUID internationalPhoneCodeId
    ) throws InternationalPhoneCodeNotFoundException {
        return crudInternationalPhoneCodeRepository.findById(internationalPhoneCodeId).orElseThrow(
                () -> getInternationalPhoneCodeNotFoundException("International phone code with ID '%s' not found", internationalPhoneCodeId)
        );
    }

    @Transactional
    public InternationalPhoneCode createInternationalPhoneCode(
            String code,
            Country country
    ) throws InternationalPhoneCodeAlreadyExistsException {
        if (existsInternationalPhoneCodeByCodeAndCountry(code, country)) {
            throwInternationalPhoneCodeAlreadyExistsException("International phone code '%s' already exists", code);
        }

        final InternationalPhoneCode internationalPhoneCode = InternationalPhoneCode.create(code, country);

        return crudInternationalPhoneCodeRepository.save(internationalPhoneCode);
    }

    @Transactional
    public InternationalPhoneCode updateInternationalPhoneCode(
            UUID internationalPhoneCodeId,
            String code,
            Country country
    ) throws InternationalPhoneCodeNotFoundException, InternationalPhoneCodeAlreadyExistsException {
        final InternationalPhoneCode internationalPhoneCode = getInternationalPhoneCodeById(internationalPhoneCodeId);

        if (existsOtherInternationalPhoneCodeByCodeAndCountry(code, country, internationalPhoneCode.getId())) {
            throwInternationalPhoneCodeAlreadyExistsException("International phone code '%s' already exists", code);
        }

        internationalPhoneCode.setCode(code);
        internationalPhoneCode.setCountry(country);

        return crudInternationalPhoneCodeRepository.save(internationalPhoneCode);
    }

    @Transactional
    public void deleteInternationalPhoneCode(
            UUID internationalPhoneCodeId
    ) throws InternationalPhoneCodeNotFoundException {
        final InternationalPhoneCode internationalPhoneCode = getInternationalPhoneCodeById(internationalPhoneCodeId);
        crudInternationalPhoneCodeRepository.delete(internationalPhoneCode);
    }

    private boolean existsInternationalPhoneCodeByCodeAndCountry(String code, Country country) {
        return crudInternationalPhoneCodeRepository.existsByCodeAndCountry(code, country);
    }

    private boolean existsOtherInternationalPhoneCodeByCodeAndCountry(
            String code,
            Country country,
            UUID ignoreInternationalPhoneCodeId
    ) {
        return crudInternationalPhoneCodeRepository.existsByCodeAndCountryAndIdNot(
                code,
                country, ignoreInternationalPhoneCodeId
        );
    }

    private InternationalPhoneCodeNotFoundException getInternationalPhoneCodeNotFoundException(
            String template,
            Object... args
    ) {
        final String message = String.format(template, args);
        return InternationalPhoneCodeNotFoundException.create(message);
    }

    private void throwInternationalPhoneCodeNotFoundException(
            String template,
            Object... args
    ) throws InternationalPhoneCodeNotFoundException {
        throw getInternationalPhoneCodeNotFoundException(template, args);
    }

    private InternationalPhoneCodeAlreadyExistsException getInternationalPhoneCodeAlreadyExistsException(
            String template,
            Object... args
    ) {
        final String message = String.format(template, args);
        return InternationalPhoneCodeAlreadyExistsException.create(message);
    }

    private void throwInternationalPhoneCodeAlreadyExistsException(
            String template,
            Object... args
    ) throws InternationalPhoneCodeAlreadyExistsException {
        throw getInternationalPhoneCodeAlreadyExistsException(template, args);
    }

}
