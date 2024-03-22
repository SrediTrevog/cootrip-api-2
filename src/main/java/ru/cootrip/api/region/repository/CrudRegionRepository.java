package ru.cootrip.api.region.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.region.entity.Region;

import java.util.UUID;

@Repository
public interface CrudRegionRepository extends CrudRepository<Region, UUID> {}
