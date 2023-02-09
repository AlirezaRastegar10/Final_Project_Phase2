package ir.maktab.repository;

import ir.maktab.entity.Offers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OffersRepository extends JpaRepository<Offers, Long> {

    @Query("select offer from Offers offer inner join offer.ordersSet order where order.id= ?1 " +
            "order by offer.proposedPrice desc, offer.expert.score desc")
    List<Offers> findAllByOrderId(Long orderId);

    @Query("select offer from Offers offer inner join offer.ordersSet order where order.id= ?1 and offer.id= ?2")
    Offers findByOrderId(Long orderId, Long offerId);
}
