package ru.cootrip.api.railwaystation.entity;

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
@Entity(name = "railway_stations")
public class RailwayStation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @Column(length = 12, unique = true, nullable = false)
    private String railCode;

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

    private RailwayStation(String railCode, String name, City city) {
        setRailCode(railCode);
        setName(name);
        setCity(city);
    }

    public static RailwayStation create(String railCode, String name, City city) {
        return new RailwayStation(railCode, name, city);
    }

}
