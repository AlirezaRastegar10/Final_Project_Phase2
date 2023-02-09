package ir.maktab.service;

import ir.maktab.entity.UnderDuty;
import ir.maktab.exceptions.UnderDutyExistException;
import ir.maktab.exceptions.UnderDutyNotFoundException;
import ir.maktab.repository.UnderDutyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UnderDutyService {

    private final UnderDutyRepository underDutyRepository;

    public UnderDutyService(UnderDutyRepository underDutyRepository) {
        this.underDutyRepository = underDutyRepository;
    }

    public UnderDuty save(UnderDuty underDuty) {
        UnderDuty foundedUnderDuty = underDutyRepository.findByNameAndDuty_Id(underDuty.getName(), underDuty.getDuty().getId());
        if (foundedUnderDuty == null) {
            return underDutyRepository.save(underDuty);
        } else throw new UnderDutyExistException("an under duty already exists with this name in this duty.");
    }

    public List<UnderDuty> findAllByDutyId(Long dutyId) {
        return underDutyRepository.findAllByDuty_Id(dutyId);
    }

    public UnderDuty findById(long id) {
        return underDutyRepository.findById(id).orElseThrow(() -> new UnderDutyNotFoundException("no under duty found with this ID."));
    }

    public UnderDuty update(UnderDuty underDuty) {
        return underDutyRepository.save(underDuty);
    }
}
