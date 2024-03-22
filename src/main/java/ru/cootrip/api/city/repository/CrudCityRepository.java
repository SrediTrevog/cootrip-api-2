package ru.cootrip.api.city.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.city.entity.City;

import java.util.UUID;

@Repository
public interface CrudCityRepository extends CrudRepository<City, UUID> {}
