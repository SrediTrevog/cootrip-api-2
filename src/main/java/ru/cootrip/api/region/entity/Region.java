package ru.cootrip.api.region.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.cootrip.api.city.entity.City;
import ru.cootrip.api.country.entity.Country;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @ManyToOne(optional = false)
    private Country country;

    @Setter
    @NonNull
    @Column(length = 64, nullable = false)
    private String name;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<City> cities;

    private Region(Country country, String name) {
        setCountry(country);
        setName(name);
    }

    public static Region create(Country country, String name) {
        return new Region(country, name);
    }

}
