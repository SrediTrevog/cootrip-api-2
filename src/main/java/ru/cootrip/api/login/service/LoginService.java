package ru.cootrip.api.login.service;

import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.cootrip.api.common.jwt.component.JWTComponent;
import ru.cootrip.api.common.jwt.entity.AccessToken;
import ru.cootrip.api.common.jwt.entity.RefreshToken;
import ru.cootrip.api.common.jwt.exception.InvalidRefreshTokenException;
import ru.cootrip.api.device.entity.Device;
import ru.cootrip.api.device.exception.DeviceNotFoundException;
import ru.cootrip.api.device.service.CrudDeviceService;
import ru.cootrip.api.login.exception.InvalidPasswordException;
import ru.cootrip.api.login.repository.LoginPhoneNumberRepository;
import ru.cootrip.api.otp.entity.OTP;
import ru.cootrip.api.otp.exception.OTPNotFoundException;
import ru.cootrip.api.otp.service.CrudOtpService;
import ru.cootrip.api.phonenumber.entity.PhoneNumber;
import ru.cootrip.api.phonenumber.service.CrudPhoneNumberService;
import ru.cootrip.api.user.entity.User;
import ru.cootrip.api.user.service.CrudUserService;

import java.util.Collections;
import java.util.Random;
import java.util.UUID;

@Service
public class LoginService {

    private final LoginPhoneNumberRepository loginPhoneNumberRepository;

    private final CrudUserService crudUserService;

    private final CrudPhoneNumberService crudPhoneNumberService;

    private final CrudOtpService crudOtpService;

    private final CrudDeviceService crudDeviceService;

    private final JWTComponent jwtComponent;

    private final boolean isDevelopmentMode;

    public LoginService(
            LoginPhoneNumberRepository loginPhoneNumberRepository,
            CrudUserService crudUserService,
            CrudPhoneNumberService crudPhoneNumberService,
            CrudOtpService crudOtpService,
            CrudDeviceService crudDeviceService,
            JWTComponent jwtComponent,
            @Value("${spring.is-development-mode:false}") boolean isDevelopmentMode
    ) {
        this.loginPhoneNumberRepository = loginPhoneNumberRepository;
        this.crudUserService = crudUserService;
        this.crudPhoneNumberService = crudPhoneNumberService;
        this.crudOtpService = crudOtpService;
        this.crudDeviceService = crudDeviceService;
        this.jwtComponent = jwtComponent;
        this.isDevelopmentMode = isDevelopmentMode;
    }

    @Transactional
    public OTP login(String phone) {
        final PhoneNumber phoneNumber = loginPhoneNumberRepository.getByPhone(phone).orElseGet(
                () -> {
                    final String username = generateUsername();
                    final User user = crudUserService.createUser(
                            username,
                            Collections.emptyList(),
                            false,
                            true
                    );

                    return crudPhoneNumberService.createPhoneNumber(user, phone);
                }
        );

        final String password = generatePassword();

        return crudOtpService.createOtp(phoneNumber, password);
    }

    @Transactional
    public Pair<AccessToken, RefreshToken> authenticate(
            UUID otpId,
            String password,
            String deviceName,
            String deviceInfo,
            Device.Platform devicePlatform
    ) throws OTPNotFoundException, InvalidPasswordException {
        final OTP otp = crudOtpService.getOtpById(otpId);

        if (otp.getPassword().equals(password) || (isDevelopmentMode && password.equals("TEST11"))) {
            final Device device = crudDeviceService.createDevice(
                    otp.getPhoneNumber().getUser(),
                    deviceName,
                    deviceInfo,
                    devicePlatform
            );

            crudOtpService.deleteOtpById(otp.getId());

            final AccessToken accessToken = jwtComponent.generateAccessToken(device);
            final RefreshToken refreshToken = jwtComponent.generateRefreshToken(device);

            return new Pair<>(accessToken, refreshToken);
        } else {
            throwInvalidPasswordException("Invalid one-time password");
        }

        return null;
    }

    @Transactional
    public AccessToken refreshToken(String refreshToken) throws InvalidRefreshTokenException, DeviceNotFoundException {
        final UUID deviceId = jwtComponent.getRefreshTokenDeviceId(refreshToken);
        final Device device = crudDeviceService.getDeviceById(deviceId);

        return jwtComponent.generateAccessToken(device);
    }

    private String generateUsername() {
        return generateRandomString(16);
    }

    private String generatePassword() {
        return generateRandomString(6);
    }

    private String generateRandomString(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final StringBuilder sb = new StringBuilder();
        final Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    private InvalidPasswordException getInvalidPasswordException(String template, Object... args) {
        final String message = String.format(template, args);
        return InvalidPasswordException.create(message);
    }

    private void throwInvalidPasswordException(String template, Object... args) throws InvalidPasswordException {
        throw getInvalidPasswordException(template, args);
    }

}
