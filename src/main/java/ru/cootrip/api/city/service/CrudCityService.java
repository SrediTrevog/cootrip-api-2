package ru.cootrip.api.city.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.city.entity.City;
import ru.cootrip.api.city.exception.CityNotFoundException;
import ru.cootrip.api.city.repository.CrudCityRepository;
import ru.cootrip.api.region.entity.Region;

import java.util.UUID;

@Service
public class CrudCityService {

    private final CrudCityRepository crudCityRepository;

    public CrudCityService(CrudCityRepository crudCityRepository) {
        this.crudCityRepository = crudCityRepository;
    }

    @Transactional
    public City getCityById(UUID cityId) throws CityNotFoundException {
        return crudCityRepository.findById(cityId).orElseThrow(
                () -> getCityNotFoundException("City with ID '%s' not found", cityId)
        );
    }

    @Transactional
    public City createCity(Region region, String name) {
        final City city = City.create(region, name);
        return crudCityRepository.save(city);
    }

    @Transactional
    public City updateCity(UUID cityId, Region region, String name) throws CityNotFoundException {
        final City city = getCityById(cityId);

        city.setRegion(region);
        city.setName(name);

        return crudCityRepository.save(city);
    }

    @Transactional
    public void deleteCityById(UUID cityId) throws CityNotFoundException {
        final City city = getCityById(cityId);
        crudCityRepository.delete(city);
    }

    private CityNotFoundException getCityNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return CityNotFoundException.create(message);
    }

    private void throwCityNotFoundException(String template, Object... args) {
        throw getCityNotFoundException(template, args);
    }

}
