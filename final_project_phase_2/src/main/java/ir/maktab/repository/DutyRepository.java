package ir.maktab.repository;

import ir.maktab.entity.Duty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {

    Duty findByName(String name);
}
