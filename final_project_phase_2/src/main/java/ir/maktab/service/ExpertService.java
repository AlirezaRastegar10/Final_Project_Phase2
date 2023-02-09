package ir.maktab.service;

import ir.maktab.entity.Expert;
import ir.maktab.entity.UnderDuty;
import ir.maktab.entity.enumeration.ExpertStatus;
import ir.maktab.exceptions.ExpertNotFoundException;
import ir.maktab.repository.ExpertRepository;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
public class ExpertService {

    private final ExpertRepository expertRepository;

    public ExpertService(ExpertRepository expertRepository) {
        this.expertRepository = expertRepository;
    }

    public Expert save(Expert expert) {
        return expertRepository.save(expert);
    }

    public List<Expert> findAllByStatus(ExpertStatus status) {
        return expertRepository.findAllByStatus(status);
    }

    public Expert findById(long id) {
        return expertRepository.findById(id).orElseThrow(() -> new ExpertNotFoundException("no expert found with this ID."));
    }

    public void saveExpertImage(String email) throws IOException {
        Expert expert = expertRepository.findByUserEmail(email);
        if (expert != null) {
            byte[] data = expert.getImageUrl();
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage image = ImageIO.read(bis);
            ImageIO.write(image, "jpg", new File("H:\\maktab83\\final_project_phase2\\final_project_phase_2\\src\\main\\java\\ir\\maktab\\images\\" + expert.getUser().getLastname() + ".jpg"));
        }
    }

    public Expert update(Expert expert) {
        return expertRepository.save(expert);
    }

    public void addExpertToUnderDuty(Expert expert, UnderDuty underDuty) {
        expert.addUnderDuties(underDuty);
    }

    public void removeExpertFromUnderDuty(Expert expert, UnderDuty underDuty) {
        expert.removeUnderDuties(underDuty);
    }
}
