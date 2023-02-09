package ir.maktab.repository;

import ir.maktab.entity.Expert;
import ir.maktab.entity.enumeration.ExpertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {

    List<Expert> findAllByStatus(ExpertStatus status);

    Expert findByUserEmail(String email);
}
