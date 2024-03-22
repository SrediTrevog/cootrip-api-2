package ru.cootrip.api.airport.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.cootrip.api.city.entity.City;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "airport")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @Column(length = 3, unique = true, nullable = false)
    private String iataCode;

    @Setter
    @NonNull
    @Column(length = 64, nullable = false)
    private String name;

    @Setter
    @NonNull
    @ManyToOne(optional = false)
    private City city;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private Airport(String iataCode, String name, City city) {
        setIataCode(iataCode);
        setName(name);
        setCity(city);
    }

    public static Airport create(String iataCode, String name, City city) {
        return new Airport(iataCode, name, city);
    }

}
