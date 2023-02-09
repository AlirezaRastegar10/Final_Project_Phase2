package ir.maktab.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Admin extends BaseEntity<Long> {

    String username;
    Integer age;

    @OneToOne(cascade = CascadeType.ALL)
    User user;
}
