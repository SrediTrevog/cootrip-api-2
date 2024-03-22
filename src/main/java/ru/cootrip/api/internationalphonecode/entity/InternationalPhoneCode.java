package ru.cootrip.api.internationalphonecode.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.cootrip.api.country.entity.Country;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "international_phone_codes")
public class InternationalPhoneCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @Column(length = 15, unique = true, nullable = false)
    private String code;

    @Setter
    @NonNull
    @ManyToOne(optional = false)
    private Country country;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private InternationalPhoneCode(String code, Country country) {
        setCode(code);
        setCountry(country);
    }

    public static InternationalPhoneCode create(String code, Country country) {
        return new InternationalPhoneCode(code, country);
    }

}
