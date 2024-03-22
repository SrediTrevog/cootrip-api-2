package ru.cootrip.api.authority.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "authorities")
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @Column(name = "authority_key", length = 32, unique = true, nullable = false)
    private String key;

    @Setter
    @NonNull
    @Column(length = 64, nullable = false)
    private String name;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private Authority(String key, String name) {
        setKey(key);
        setName(name);
    }

    @Override
    public String getAuthority() {
        return getKey();
    }

    public static Authority create(String key, String name) {
        return new Authority(key, name);
    }

}
