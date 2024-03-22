package ru.cootrip.api.country.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.cootrip.api.internationalphonecode.entity.InternationalPhoneCode;
import ru.cootrip.api.region.entity.Region;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @Column(length = 3, unique = true, nullable = false)
    private String isoCode;

    @Setter
    @NonNull
    @Column(length = 64, nullable = false)
    private String name;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Region> regions;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<InternationalPhoneCode> internationalPhoneCodes;

    private Country(String isoCode, String name) {
        setIsoCode(isoCode);
        setName(name);
    }

    public static Country create(String isoCode, String name) {
        return new Country(isoCode, name);
    }

}
