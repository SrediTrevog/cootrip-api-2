package ru.cootrip.api.seaport.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.city.entity.City;
import ru.cootrip.api.seaport.entity.Seaport;
import ru.cootrip.api.seaport.exception.SeaportAlreadyExistsException;
import ru.cootrip.api.seaport.exception.SeaportNotFoundException;
import ru.cootrip.api.seaport.repository.CrudSeaportRepository;

import java.util.UUID;

@Service
public class CrudSeaportService {

    private final CrudSeaportRepository crudSeaportRepository;

    public CrudSeaportService(CrudSeaportRepository crudSeaportRepository) {
        this.crudSeaportRepository = crudSeaportRepository;
    }

    @Transactional
    public Seaport getSeaportById(UUID seaportId) throws SeaportNotFoundException {
        return crudSeaportRepository.findById(seaportId).orElseThrow(
                () -> getSeaportNotFoundException("Seaport with ID '%s' not found", seaportId)
        );
    }

    @Transactional
    public Seaport createSeaport(String imoCode, String name, City city) throws SeaportAlreadyExistsException {
        if (existsSeaportByImoCode(imoCode)) {
            throwSeaportAlreadyExistsException("Seaport with IMO code '%s' already exists", imoCode);
        }

        final Seaport seaport = Seaport.create(imoCode, name, city);

        return crudSeaportRepository.save(seaport);
    }

    @Transactional
    public Seaport updateSeaport(
            UUID seaportId,
            String imoCode,
            String name,
            City city
    ) throws SeaportNotFoundException, SeaportAlreadyExistsException {
        final Seaport seaport = getSeaportById(seaportId);

        if (existsOtherSeaportByImoCode(imoCode, seaport.getId())) {
            throwSeaportAlreadyExistsException("Seaport with IMO code '%s' already exists", imoCode);
        }

        seaport.setImoCode(imoCode);
        seaport.setName(name);
        seaport.setCity(city);

        return crudSeaportRepository.save(seaport);
    }

    @Transactional
    public void deleteSeaportById(UUID seaportId) throws SeaportNotFoundException {
        final Seaport seaport = getSeaportById(seaportId);
        crudSeaportRepository.delete(seaport);
    }

    private boolean existsSeaportByImoCode(String imoCode) {
        return crudSeaportRepository.existsByImoCode(imoCode);
    }

    private boolean existsOtherSeaportByImoCode(String imoCode, UUID ignoreSeaportId) {
        return crudSeaportRepository.existsByImoCodeAndIdNot(imoCode, ignoreSeaportId);
    }

    private SeaportNotFoundException getSeaportNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return SeaportNotFoundException.create(message);
    }

    private void throwSeaportNotFoundException(String template, Object... args) throws SeaportNotFoundException {
        throw getSeaportNotFoundException(template, args);
    }

    private SeaportAlreadyExistsException getSeaportAlreadyExistsException(String template, Object... args) {
        final String message = String.format(template, args);
        return SeaportAlreadyExistsException.create(message);
    }

    private void throwSeaportAlreadyExistsException(
            String template,
            Object... args
    ) throws SeaportAlreadyExistsException {
        throw getSeaportAlreadyExistsException(template, args);
    }

}
