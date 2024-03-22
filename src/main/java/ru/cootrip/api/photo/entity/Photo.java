package ru.cootrip.api.photo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.cootrip.api.user.entity.User;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @ManyToOne
    private User user;

    @Setter
    @NonNull
    @Column(length = 2048, unique = true, nullable = false)
    private String url;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private Photo(User user, String url) {
        setUser(user);
        setUrl(url);
    }

    public static Photo create(User user, String url) {
        return new Photo(user, url);
    }

}
