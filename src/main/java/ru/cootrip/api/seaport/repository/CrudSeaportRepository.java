package ru.cootrip.api.seaport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.seaport.entity.Seaport;

import java.util.UUID;

@Repository
public interface CrudSeaportRepository extends CrudRepository<Seaport, UUID> {

    boolean existsByImoCode(String imoCode);

    boolean existsByImoCodeAndIdNot(String imoCode, UUID ignoreId);

}
