package ru.cootrip.api.railwaystation.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.city.entity.City;
import ru.cootrip.api.railwaystation.entity.RailwayStation;
import ru.cootrip.api.railwaystation.exception.RailwayStationAlreadyExistsException;
import ru.cootrip.api.railwaystation.exception.RailwayStationNotFoundException;
import ru.cootrip.api.railwaystation.repository.CrudRailwayStationRepository;

import java.util.UUID;

@Service
public class CrudRailwayStationService {

    private final CrudRailwayStationRepository crudRailwayStationRepository;

    public CrudRailwayStationService(CrudRailwayStationRepository crudRailwayStationRepository) {
        this.crudRailwayStationRepository = crudRailwayStationRepository;
    }

    @Transactional
    public RailwayStation getRailwayStationById(UUID railwayStationId) throws RailwayStationNotFoundException {
        return crudRailwayStationRepository.findById(railwayStationId).orElseThrow(
                () -> getRailwayStationNotFoundException("Railway station with ID '%s' not found", railwayStationId)
        );
    }

    @Transactional
    public RailwayStation createRailwayStation(
            String railCode,
            String name,
            City city
    ) throws RailwayStationAlreadyExistsException {
        if (existsRailwayStationByRailCode(railCode)) {
            throwRailwayStationAlreadyExistsException("Railway station with RAIL code '%s' already exists", railCode);
        }

        final RailwayStation railwayStation = RailwayStation.create(railCode, name, city);

        return crudRailwayStationRepository.save(railwayStation);
    }

    @Transactional
    public RailwayStation updateRailwayStation(
            UUID railwayStationId,
            String railCode,
            String name,
            City city
    ) throws RailwayStationNotFoundException, RailwayStationAlreadyExistsException {
        final RailwayStation railwayStation = getRailwayStationById(railwayStationId);

        if (existsOtherRailwayStationByRailCode(railCode, railwayStation.getId())) {
            throwRailwayStationAlreadyExistsException("Railway station with RAIL code '%s' already exists", railCode);
        }

        railwayStation.setRailCode(railCode);
        railwayStation.setName(name);
        railwayStation.setCity(city);

        return crudRailwayStationRepository.save(railwayStation);
    }

    @Transactional
    public void deleteRailwayStationById(UUID railwayStationId) throws RailwayStationNotFoundException {
        final RailwayStation railwayStation = getRailwayStationById(railwayStationId);
        crudRailwayStationRepository.delete(railwayStation);
    }

    private boolean existsRailwayStationByRailCode(String railCode) {
        return crudRailwayStationRepository.existsByRailCode(railCode);
    }

    private boolean existsOtherRailwayStationByRailCode(String railCode, UUID ignoreRailwayStationId) {
        return crudRailwayStationRepository.existsByRailCodeAndIdNot(railCode, ignoreRailwayStationId);
    }

    private RailwayStationNotFoundException getRailwayStationNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return RailwayStationNotFoundException.create(message);
    }

    private void throwRailwayStationNotFoundException(
            String template,
            Object... args
    ) throws RailwayStationNotFoundException {
        throw getRailwayStationNotFoundException(template, args);
    }

    private RailwayStationAlreadyExistsException getRailwayStationAlreadyExistsException(
            String template,
            Object... args
    ) {
        final String message = String.format(template, args);
        return RailwayStationAlreadyExistsException.create(message);
    }

    private void throwRailwayStationAlreadyExistsException(
            String template,
            Object... args
    ) throws RailwayStationAlreadyExistsException {
        throw getRailwayStationAlreadyExistsException(template, args);
    }

}
