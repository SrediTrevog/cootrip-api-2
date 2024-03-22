package ru.cootrip.api.device.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.cootrip.api.device.entity.Device;
import ru.cootrip.api.device.exception.DeviceNotFoundException;
import ru.cootrip.api.device.repository.CrudDeviceRepository;
import ru.cootrip.api.user.entity.User;

import java.util.UUID;

@Service
public class CrudDeviceService {

    private final CrudDeviceRepository crudDeviceRepository;

    public CrudDeviceService(CrudDeviceRepository crudDeviceRepository) {
        this.crudDeviceRepository = crudDeviceRepository;
    }

    @Transactional
    public Device getDeviceById(UUID deviceId) throws DeviceNotFoundException {
        return crudDeviceRepository.findById(deviceId).orElseThrow(
                () -> getDeviceNotFoundException("Device with ID '%s' not found", deviceId)
        );
    }

    @Transactional
    public Device createDevice(User user, String name, String info, Device.Platform platform) {
        final Device device = Device.create(user, name, info, platform);
        return crudDeviceRepository.save(device);
    }

    @Transactional
    public Device updateDevice(
            UUID deviceId,
            User user,
            String name,
            String info,
            Device.Platform platform
    ) throws DeviceNotFoundException {
        final Device device = getDeviceById(deviceId);

        device.setUser(user);
        device.setName(name);
        device.setInfo(info);
        device.setPlatform(platform);

        return crudDeviceRepository.save(device);
    }

    @Transactional
    public void deleteDeviceById(UUID deviceId) throws DeviceNotFoundException {
        final Device device = getDeviceById(deviceId);
        crudDeviceRepository.delete(device);
    }

    private DeviceNotFoundException getDeviceNotFoundException(String template, Object... args) {
        final String message = String.format(template, args);
        return DeviceNotFoundException.create(message);
    }

    private void throwDeviceNotFoundException(String template, Object... args) throws DeviceNotFoundException {
        throw getDeviceNotFoundException(template, args);
    }

}
