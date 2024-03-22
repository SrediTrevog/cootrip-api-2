package ru.cootrip.api.device.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.cootrip.api.device.entity.Device;

import java.util.UUID;

@Repository
public interface CrudDeviceRepository extends CrudRepository<Device, UUID> {}
