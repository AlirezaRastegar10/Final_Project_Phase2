package ir.maktab.repository;


import ir.maktab.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select o from Orders o where o.underDuty.id= ?1 and " +
            "(o.orderStatus='WAITING_FOR_THE_SUGGESTION_OF_EXPERTS' or o.orderStatus='WAITING_FOR_SPECIALIST_SELECTION')")
    List<Orders> findAllByUnderDutyIdAndOrderStatus(Long id);

    @Query("select o from Orders o where o.underDuty.id= ?1 and o.id= ?2 and " +
            "(o.orderStatus='WAITING_FOR_THE_SUGGESTION_OF_EXPERTS' or o.orderStatus='WAITING_FOR_SPECIALIST_SELECTION')")
    Orders findByUnderDutyIdAndOrderStatus(Long underDutyId, Long orderId);
}
