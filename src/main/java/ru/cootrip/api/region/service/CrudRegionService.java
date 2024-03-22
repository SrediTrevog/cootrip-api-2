package ru.cootrip.api.region.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.country.entity.Country;
import ru.cootrip.api.region.entity.Region;
import ru.cootrip.api.region.exception.RegionNotFoundException;
import ru.cootrip.api.region.repository.CrudRegionRepository;

import java.util.UUID;

@Service
public class CrudRegionService {

    private final CrudRegionRepository crudRegionRepository;

    public CrudRegionService(CrudRegionRepository crudRegionRepository) {
        this.crudRegionRepository = crudRegionRepository;
    }

    @Transactional
    public Region getRegionById(UUID regionId) throws RegionNotFoundException {
        return crudRegionRepository.findById(regionId).orElseThrow(
                () -> getRegionNotFoundException("Region with ID '%s' not found", regionId)
        );
    }

    @Transactional
    public Region createRegion(Country country, String name) {
        final Region region = Region.create(country, name);
        return crudRegionRepository.save(region);
    }

    @Transactional
    public Region updateRegion(UUID regionId, Country country, String name) throws RegionNotFoundException {
        final Region region = getRegionById(regionId);

        region.setCountry(country);
        region.setName(name);

        return crudRegionRepository.save(region);
    }

    @Transactional
    public void deleteRegionById(UUID regionId) throws RegionNotFoundException {
        final Region region = getRegionById(regionId);
        crudRegionRepository.delete(region);
    }

    private RegionNotFoundException getRegionNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return RegionNotFoundException.create(message);
    }

    private void throwRegionNotFoundException(String template, Object... args) throws RegionNotFoundException {
        throw getRegionNotFoundException(template, args);
    }

}
