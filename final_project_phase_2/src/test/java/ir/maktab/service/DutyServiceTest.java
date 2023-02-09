package ir.maktab.service;

import ir.maktab.entity.Duty;
import ir.maktab.exceptions.DutyExistException;
import ir.maktab.exceptions.DutyNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DutyServiceTest {

    @Autowired
    private DutyService dutyService;

    private Duty buildingDecoration;

    private Duty homeAppliances;

    private Duty cleaningAndHygiene;

    @BeforeEach
    void setUp() {
        buildingDecoration = Duty.builder().name("Building Decoration").build();
        homeAppliances = Duty.builder().name("Home Appliances").build();
        cleaningAndHygiene = Duty.builder().name("Cleaning And Hygiene").build();
    }

    @DisplayName("JUnit test for saveDuty method")
    @Test
    @Order(1)
    void save_duty() {
        Duty savedBuildingDecoration = dutyService.save(buildingDecoration);
        Duty savedHomeAppliances = dutyService.save(homeAppliances);
        Duty savedCleaningAndHygiene = dutyService.save(cleaningAndHygiene);

        assertThat(savedBuildingDecoration).isNotNull();
        assertThat(savedHomeAppliances).isNotNull();
        assertThat(savedCleaningAndHygiene).isNotNull();
    }

    @DisplayName("JUnit test for saveDuty method with exist name")
    @Test
    @Order(2)
    void save_duty_with_exist_name() {
        assertThatThrownBy(() -> dutyService.save(buildingDecoration))
                .isInstanceOf(DutyExistException.class)
                .hasMessageContaining("a duty already exists with this name.");
        assertThatThrownBy(() -> dutyService.save(homeAppliances))
                .isInstanceOf(DutyExistException.class)
                .hasMessageContaining("a duty already exists with this name.");
    }

    @DisplayName("JUnit test for findAll method")
    @Test
    @Order(3)
    void findAll() {
        List<Duty> duties = dutyService.findAll();

        assertThat(duties).isNotEmpty();
    }

    @DisplayName("JUnit test for findById method")
    @Test
    @Order(4)
    void findById() {
        Duty duty = dutyService.findById(1);

        assertThat(duty).isNotNull();

        assertThatThrownBy(() -> dutyService.findById(56L))
                .isInstanceOf(DutyNotFoundException.class)
                .hasMessageContaining("no duty found with this ID.");
    }
}