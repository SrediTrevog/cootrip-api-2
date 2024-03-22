package ru.cootrip.api.city.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.cootrip.api.railwaystation.entity.RailwayStation;
import ru.cootrip.api.region.entity.Region;
import ru.cootrip.api.seaport.entity.Seaport;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @ManyToOne(optional = false)
    private Region region;

    @Setter
    @NonNull
    @Column(length = 64, nullable = false)
    private String name;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Seaport> seaports;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<RailwayStation> railwayStations;

    private City(Region region, String name) {
        setRegion(region);
        setName(name);
    }

    public static City create(Region region, String name) {
        return new City(region, name);
    }

}
