package ir.maktab.repository;


import ir.maktab.entity.UnderDuty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UnderDutyRepository extends JpaRepository<UnderDuty, Long> {

    UnderDuty findByNameAndDuty_Id(String name, Long dutyId);

    List<UnderDuty> findAllByDuty_Id(Long dutyId);
}
