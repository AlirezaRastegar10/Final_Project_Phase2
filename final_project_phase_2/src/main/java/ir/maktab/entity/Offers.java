package ir.maktab.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Offers extends BaseEntity<Long> {

    @ManyToOne
    Expert expert;

    @CreationTimestamp
    LocalDateTime registerDateAndTime;

    Long proposedPrice;
    LocalDateTime suggestedTime;
    String durationOfWork;

    @ManyToMany(mappedBy = "offersSet", fetch = FetchType.EAGER)
    Set<Orders> ordersSet = new HashSet<>();
}
