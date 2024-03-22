package ru.cootrip.api.country.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.country.entity.Country;
import ru.cootrip.api.country.exception.CountryAlreadyExistsException;
import ru.cootrip.api.country.exception.CountryNotFoundException;
import ru.cootrip.api.country.repository.CrudCountryRepository;

import java.util.UUID;

@Service
public class CrudCountryService {

    private final CrudCountryRepository crudCountryRepository;

    public CrudCountryService(CrudCountryRepository crudCountryRepository) {
        this.crudCountryRepository = crudCountryRepository;
    }

    @Transactional
    public Country getCountryById(UUID countryId) throws CountryNotFoundException {
        return crudCountryRepository.findById(countryId).orElseThrow(
                () -> getCountryNotFoundException("Country with ID '%s' not found", countryId)
        );
    }

    @Transactional
    public Country createCountry(String isoCode, String name) throws CountryAlreadyExistsException {
        if (existsCountryByIsoCode(isoCode)) {
            throwCountryAlreadyExistsException("Country with ISO code '%s' already exists", isoCode);
        }

        final Country country = Country.create(isoCode, name);

        return crudCountryRepository.save(country);
    }

    @Transactional
    public Country updateCountry(
            UUID countryId,
            String isoCode,
            String name
    ) throws CountryNotFoundException, CountryAlreadyExistsException {
        final Country country = getCountryById(countryId);

        if (existsOtherCountryByIsoCode(isoCode, country.getId())) {
            throwCountryAlreadyExistsException("Country with ISO code '%s' already exists", isoCode);
        }

        country.setIsoCode(isoCode);
        country.setName(name);

        return crudCountryRepository.save(country);
    }

    @Transactional
    public void deleteCountryById(UUID countryId) throws CountryNotFoundException {
        final Country country = getCountryById(countryId);
        crudCountryRepository.delete(country);
    }

    private boolean existsCountryByIsoCode(String isoCode) {
        return crudCountryRepository.existsByIsoCode(isoCode);
    }

    private boolean existsOtherCountryByIsoCode(String isoCode, UUID ignoreCountryId) {
        return crudCountryRepository.existsByIsoCodeAndIdNot(isoCode, ignoreCountryId);
    }

    private CountryNotFoundException getCountryNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return CountryNotFoundException.create(message);
    }

    private void throwCountryNotFoundException(String template, Object... args) throws CountryNotFoundException {
        throw getCountryNotFoundException(template, args);
    }

    private CountryAlreadyExistsException getCountryAlreadyExistsException(String template, Object... args) {
        final String message = String.format(template, args);
        return CountryAlreadyExistsException.create(message);
    }

    public void throwCountryAlreadyExistsException(
            String template,
            Object... args
    ) throws CountryAlreadyExistsException {
        throw getCountryAlreadyExistsException(template, args);
    }

}
