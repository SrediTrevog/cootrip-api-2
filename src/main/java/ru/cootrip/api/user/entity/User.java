package ru.cootrip.api.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.cootrip.api.authority.entity.Authority;
import ru.cootrip.api.device.entity.Device;
import ru.cootrip.api.phonenumber.entity.PhoneNumber;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @NonNull
    @Column(length = 16, unique = true, nullable = false)
    private String username;

    @Setter
    @NonNull
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Authority> authorities;

    @Setter
    @NonNull
    @Column(nullable = false)
    private Boolean isBlocked;

    @Setter
    @NonNull
    @Column(nullable = false)
    private Boolean isEnabled;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Device> devices;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<PhoneNumber> phoneNumbers;

    private User(String username, Collection<Authority> authorities, boolean isBlocked, boolean isEnabled) {
        setUsername(username);
        setAuthorities(authorities);
        setIsBlocked(isBlocked);
        setIsEnabled(isEnabled);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !getIsBlocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getIsEnabled();
    }

    public static User create(
            String username,
            Collection<Authority> authorities,
            boolean isBlocked,
            boolean isEnabled
    ) {
        return new User(username, authorities, isBlocked, isEnabled);
    }

}
