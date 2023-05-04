package edu.ntnu.idatt2106_2023_06.backend.model.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.ntnu.idatt2106_2023_06.backend.model.fridge.FridgeItems;
import edu.ntnu.idatt2106_2023_06.backend.model.users.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@NoArgsConstructor(force = true)
@Entity
@Table(name = "notifications")
public class Notification {

    /**
     * The unique identifier for this notification.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    @JsonIgnore
    private Long notificationId;

    /**
     * Boolean value to check if the notification is read or not.
     */
    @Column(name = "is_read", nullable = false)
    @NonNull
    private Boolean isRead;

    /**
     * The date the notification was created.
     */
    @Column(name = "created_date", nullable = false)
    @NonNull
    private LocalDateTime createdDate;

    /**
     * The user that the notification is connected to.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private User user;

    /**
     * The fridge that the user is a member of.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumns({
            @JoinColumn(name = "item_id", referencedColumnName = "item_id"),
            @JoinColumn(name = "fridge_id", referencedColumnName = "fridge_id")
    }
    )
    @NonNull
    @ToString.Exclude
    private FridgeItems fridgeItem;
}
