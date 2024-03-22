package ru.cootrip.api.photo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.photo.entity.Photo;

import java.util.UUID;

@Repository
public interface CrudPhotoRepository extends CrudRepository<Photo, UUID> {

    boolean existsByUrl(String url);

    boolean existsByUrlAndIdNot(String url, UUID ignoreId);

}
