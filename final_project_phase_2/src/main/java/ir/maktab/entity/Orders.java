package ir.maktab.entity;

import ir.maktab.entity.enumeration.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


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
public class Orders extends BaseEntity<Long> {

    @ManyToOne
    Customer customer;

    Long proposedPrice;
    String description;
    LocalDateTime dateAndTime;
    String address;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @OneToOne()
    UnderDuty underDuty;

    @OneToOne()
    Expert expert;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "orders_offers",
            joinColumns = @JoinColumn(name = "orders_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "offers_id", referencedColumnName = "id"))
    Set<Offers> offersSet = new HashSet<>();

    public void addOffers(Offers offers) {
        offersSet.add(offers);
        offers.getOrdersSet().add(this);
    }

    public void removeOffers(Offers offers) {
        offersSet.remove(offers);
        offers.getOrdersSet().remove(this);
    }
}
