package ir.maktab.entity;


import ir.maktab.entity.enumeration.ExpertStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Expert extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    ExpertStatus status;

    byte[] imageUrl;
    Long validity;
    Integer score;

    @OneToOne()
    User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "expert_underduty",
            joinColumns = @JoinColumn(name = "expert_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "under_duty_id", referencedColumnName = "id"))
    Set<UnderDuty> underDutySet = new HashSet<>();

    @OneToMany(mappedBy = "expert")
    List<Offers> offers;

    @OneToMany(mappedBy = "expert")
    List<Comments> comments;

    public void addUnderDuties(UnderDuty underDuty) {
        underDutySet.add(underDuty);
        underDuty.getExpertSet().add(this);
    }

    public void removeUnderDuties(UnderDuty underDuty) {
        underDutySet.remove(underDuty);
        underDuty.getExpertSet().remove(this);
    }
}
