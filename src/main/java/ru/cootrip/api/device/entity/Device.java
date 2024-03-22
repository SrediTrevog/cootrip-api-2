package ru.cootrip.api.device.entity;

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
@Entity(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @ManyToOne(optional = false)
    private User user;

    @Setter
    @NonNull
    @Column(length = 32, nullable = false)
    private String name;

    @Setter
    @NonNull
    @Column(length = 64, nullable = false)
    private String info;

    @Setter
    @NonNull
    @Enumerated
    @Column(nullable = false)
    private Platform platform;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private Device(User user, String name, String info, Platform platform) {
        setUser(user);
        setName(name);
        setInfo(info);
        setPlatform(platform);
    }

    public static Device create(User user, String name, String info, Platform platform) {
        return new Device(user, name, info, platform);
    }

    public enum Platform {

        ANDROID,

        IOS,

        WEB

    }

}
