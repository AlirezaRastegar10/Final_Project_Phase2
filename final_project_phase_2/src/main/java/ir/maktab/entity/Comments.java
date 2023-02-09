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
public class Comments extends BaseEntity<Long> {

    @ManyToOne
    Customer customer;

    @ManyToOne
    Expert expert;

    Integer score;
    String content;
}
