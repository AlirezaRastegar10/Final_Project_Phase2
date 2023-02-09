package ir.maktab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Customer extends BaseEntity<Long> {

    Long validity;

    @OneToOne()
    User user;

    @OneToMany(mappedBy = "customer")
    List<Orders> orders;

    @OneToMany(mappedBy = "customer")
    List<Comments> comments;

    public Customer(Long validity) {
        this.validity = validity;
    }
}
