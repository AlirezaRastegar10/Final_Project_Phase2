package ir.maktab.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UnderDuty extends BaseEntity<Long> {

    String name;
    Long basePrice;
    String explanation;

    @ManyToMany(mappedBy = "underDutySet", fetch = FetchType.EAGER)
    Set<Expert> expertSet = new HashSet<>();

    @ManyToOne
    Duty duty;
}
