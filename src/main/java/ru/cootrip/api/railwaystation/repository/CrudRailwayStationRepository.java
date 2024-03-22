package ru.cootrip.api.railwaystation.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.railwaystation.entity.RailwayStation;

import java.util.UUID;

@Repository
public interface CrudRailwayStationRepository extends CrudRepository<RailwayStation, UUID> {

    boolean existsByRailCode(String railCode);

    boolean existsByRailCodeAndIdNot(String railCode, UUID ignoreId);

}
