package ir.maktab.entity;


import ir.maktab.entity.enumeration.UserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "Users")
public class User extends BaseEntity<Long> {

    String firstname;
    String lastname;
    String email;
    String password;

    @CreationTimestamp
    LocalDateTime registerDate;

    @Enumerated(EnumType.STRING)
    UserType userType;
}
