package ru.cootrip.api.phonenumber.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.cootrip.api.otp.entity.OTP;
import ru.cootrip.api.user.entity.User;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "phone_numbers")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @ManyToOne(optional = false)
    private User user;

    @Setter
    @NonNull
    @Column(length = 15, unique = true, nullable = false)
    private String phone;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<OTP> otps;

    private PhoneNumber(User user, String phone) {
        setUser(user);
        setPhone(phone);
    }

    public static PhoneNumber create(User user, String phone) {
        return new PhoneNumber(user, phone);
    }

}
