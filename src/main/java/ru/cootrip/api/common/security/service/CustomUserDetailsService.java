package ru.cootrip.api.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.cootrip.api.device.entity.Device;
import ru.cootrip.api.device.service.CrudDeviceService;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CrudDeviceService crudDeviceService;

    public CustomUserDetailsService(CrudDeviceService crudDeviceService) {
        this.crudDeviceService = crudDeviceService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UUID deviceId = UUID.fromString(username);
        final Device device = crudDeviceService.getDeviceById(deviceId);

        return device.getUser();
    }

}
