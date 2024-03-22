package ru.cootrip.api.otp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.cootrip.api.phonenumber.entity.PhoneNumber;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "otp")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @ManyToOne(optional = false)
    private PhoneNumber phoneNumber;

    @Setter
    @NonNull
    @Column(length = 6, nullable = false)
    private String password;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private OTP(PhoneNumber phoneNumber, String password) {
        setPhoneNumber(phoneNumber);
        setPassword(password);
    }

    public static OTP create(PhoneNumber phoneNumber, String password) {
        return new OTP(phoneNumber, password);
    }

}
