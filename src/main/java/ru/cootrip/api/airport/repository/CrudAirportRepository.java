package ru.cootrip.api.airport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.airport.entity.Airport;

import java.util.UUID;

@Repository
public interface CrudAirportRepository extends CrudRepository<Airport, UUID> {

    boolean existsByIataCode(String iataCode);

    boolean existsByIataCodeAndIdNot(String iataCode, UUID ignoreId);

}
