package ru.cootrip.api.photo.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.photo.entity.Photo;
import ru.cootrip.api.photo.exception.PhotoAlreadyExistsException;
import ru.cootrip.api.photo.exception.PhotoNotFoundException;
import ru.cootrip.api.photo.repository.CrudPhotoRepository;
import ru.cootrip.api.user.entity.User;

import java.util.UUID;

@Service
public class CrudPhotoService {

    private final CrudPhotoRepository crudPhotoRepository;

    public CrudPhotoService(CrudPhotoRepository crudPhotoRepository) {
        this.crudPhotoRepository = crudPhotoRepository;
    }

    @Transactional
    public Photo getPhotoById(UUID photoId) throws PhotoNotFoundException {
        return crudPhotoRepository.findById(photoId).orElseThrow(
                () -> getPhotoNotFoundException("Photo with ID '%s' not found", photoId)
        );
    }

    @Transactional
    public Photo createPhoto(User user, String url) throws PhotoAlreadyExistsException {
        if (existsPhotoByUrl(url)) {
            throwPhotoAlreadyExistsException("Photo with URL '%s' already exists", url);
        }

        final Photo photo = Photo.create(user, url);

        return crudPhotoRepository.save(photo);
    }

    @Transactional
    public Photo updatePhoto(
            UUID photoId,
            User user,
            String url
    ) throws PhotoNotFoundException, PhotoAlreadyExistsException {
        final Photo photo = getPhotoById(photoId);

        if (existsOtherPhotoByUrl(url, photo.getId())) {
            throwPhotoAlreadyExistsException("Photo with URL '%s' already exists", url);
        }

        photo.setUser(user);
        photo.setUrl(url);

        return crudPhotoRepository.save(photo);
    }

    @Transactional
    public void deletePhotoById(UUID photoId) throws PhotoNotFoundException {
        final Photo photo = getPhotoById(photoId);
        crudPhotoRepository.delete(photo);
    }

    private boolean existsPhotoByUrl(String url) {
        return crudPhotoRepository.existsByUrl(url);
    }

    private boolean existsOtherPhotoByUrl(String url, UUID ignorePhotoId) {
        return crudPhotoRepository.existsByUrlAndIdNot(url, ignorePhotoId);
    }

    private PhotoNotFoundException getPhotoNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return PhotoNotFoundException.create(message);
    }

    private void throwPhotoNotFoundException(String template, Object... args) throws PhotoNotFoundException {
        throw getPhotoNotFoundException(template, args);
    }

    private PhotoAlreadyExistsException getPhotoAlreadyExistsException(String template, Object... args) {
        final String message = String.format(template, args);
        return PhotoAlreadyExistsException.create(message);
    }

    private void throwPhotoAlreadyExistsException(String template, Object... args) throws PhotoAlreadyExistsException {
        throw getPhotoAlreadyExistsException(template, args);
    }

}
