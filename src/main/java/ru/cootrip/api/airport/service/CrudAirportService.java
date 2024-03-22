package ru.cootrip.api.airport.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.airport.entity.Airport;
import ru.cootrip.api.airport.exception.AirportAlreadyExistsException;
import ru.cootrip.api.airport.exception.AirportNotFoundException;
import ru.cootrip.api.airport.repository.CrudAirportRepository;
import ru.cootrip.api.city.entity.City;

import java.util.UUID;

@Service
public class CrudAirportService {

    private final CrudAirportRepository crudAirportRepository;

    public CrudAirportService(CrudAirportRepository crudAirportRepository) {
        this.crudAirportRepository = crudAirportRepository;
    }

    @Transactional
    public Airport getAirportById(UUID airportId) throws AirportNotFoundException {
        return crudAirportRepository.findById(airportId).orElseThrow(
                () -> getAirportNotFoundException("Airport with ID '%s' not found", airportId)
        );
    }

    @Transactional
    public Airport createAirport(String iataCode, String name, City city) throws AirportAlreadyExistsException {
        if (existsAirportByIataCode(iataCode)) {
            throwAirportAlreadyExistsException("Airport with IATA code '%s' already exists", iataCode);
        }

        final Airport airport = Airport.create(iataCode, name, city);

        return crudAirportRepository.save(airport);
    }

    @Transactional
    public Airport updateAirport(
            UUID airportId,
            String iataCode,
            String name,
            City city
    ) throws AirportNotFoundException, AirportAlreadyExistsException {
        final Airport airport = getAirportById(airportId);

        if (existsOtherAirportByIataCode(iataCode, airport.getId())) {
            throwAirportAlreadyExistsException("Airport with IATA code '%s' already exists", iataCode);
        }

        airport.setIataCode(iataCode);
        airport.setName(name);
        airport.setCity(city);

        return crudAirportRepository.save(airport);
    }

    @Transactional
    public void deleteAirportById(UUID airportId) throws AirportNotFoundException {
        final Airport airport = getAirportById(airportId);
        crudAirportRepository.delete(airport);
    }

    private boolean existsAirportByIataCode(String iataCode) {
        return crudAirportRepository.existsByIataCode(iataCode);
    }

    private boolean existsOtherAirportByIataCode(String iataCode, UUID ignoreAirportId) {
        return crudAirportRepository.existsByIataCodeAndIdNot(iataCode, ignoreAirportId);
    }

    private AirportNotFoundException getAirportNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return AirportNotFoundException.create(message);
    }

    private void throwAirportNotFoundException(String template, Object... args) throws AirportNotFoundException {
        throw getAirportNotFoundException(template, args);
    }

    private AirportAlreadyExistsException getAirportAlreadyExistsException(String template, Object... args) {
        final String message = String.format(template, args);
        return AirportAlreadyExistsException.create(message);
    }

    private void throwAirportAlreadyExistsException(
            String template,
            Object... args
    ) throws AirportAlreadyExistsException {
        throw getAirportAlreadyExistsException(template, args);
    }

}
