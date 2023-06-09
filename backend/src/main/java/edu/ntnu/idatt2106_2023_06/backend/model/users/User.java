package edu.ntnu.idatt2106_2023_06.backend.model.users;

import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeMember;
import edu.ntnu.idatt2106_2023_06.backend.model.recipe.RecipeSuggestion;
import edu.ntnu.idatt2106_2023_06.backend.model.notification.Notification;
import edu.ntnu.idatt2106_2023_06.backend.model.stats.Statistics;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 User class represents a user of the e-commerce application with their personal information,
 authentication details, and activities on the platform.

 @author Trym Hamer Gudvangen
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    /**
     * The unique identifier for the user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /**
     * The username of the user, must be unique and not null
     */
    @Column(name = "username", length = 64, nullable = false, unique = true)
    @NonNull
    private String username;

    /**
     * The first name of the user, not null
     */
    @Column(name="first_name", nullable = false)
    @NonNull
    private String firstName;

    /**
     * The last name of the user, not null
     */
    @Column(name="last_name", nullable = false)
    @NonNull
    private String lastName;

    /**
     * The password of the user, not null
     */
    @Column(name = "password", nullable = false)
    @NonNull
    private String password;

    /**
     * The email address of the user, not null
     */
    @Column(name = "email", nullable = false, unique = true)
    @NonNull
    private String email;

    /**
     * The memberships to fridges.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Set<FridgeMember> memberships = new HashSet<>();

    /**
     * The statistics of the fridge.
     */
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Statistics> stats = new HashSet<>();

    /**
     * The tokens of the user.
     */
    @OneToMany(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Set<Token> tokens = new HashSet<>();

    /**
     * The recipe suggestions of the fridge.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<RecipeSuggestion> recipeSuggestion = new ArrayList<>();

    /**
     * The notifications of the user.
     */
    @OneToMany(mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Set<Notification> notifications = new HashSet<>();

    @PreRemove
    private void removeStats() {
        for (Statistics s : stats) {
            s.setUser(null);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ADMIN"));
    }

    @NotNull
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}