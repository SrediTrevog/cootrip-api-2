package ru.cootrip.api.seaport.entity;

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
@Entity(name = "seaports")
public class Seaport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @Column(length = 7, unique = true, nullable = false)
    private String imoCode;

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

    private Seaport(String imoCode, String name, City city) {
        setImoCode(imoCode);
        setName(name);
        setCity(city);
    }

    public static Seaport create(String imoCode, String name, City city) {
        return new Seaport(imoCode, name, city);
    }

}
