package ir.maktab.service;

import ir.maktab.entity.Duty;
import ir.maktab.exceptions.DutyExistException;
import ir.maktab.exceptions.DutyNotFoundException;
import ir.maktab.repository.DutyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DutyService {

    private final DutyRepository dutyRepository;

    public DutyService(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }

    public Duty save(Duty duty) {
        Duty foundedDuty = dutyRepository.findByName(duty.getName());
        if (foundedDuty == null) {
            return dutyRepository.save(duty);
        } else throw new DutyExistException("a duty already exists with this name.");
    }

    public List<Duty> findAll() {
        return dutyRepository.findAll();
    }

    public Duty findById(long id) {
        return dutyRepository.findById(id).orElseThrow(() -> new DutyNotFoundException("no duty found with this ID."));
    }
}
