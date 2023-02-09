package ir.maktab.service;

import ir.maktab.entity.Expert;
import ir.maktab.entity.Offers;
import ir.maktab.entity.Orders;
import ir.maktab.exceptions.DateAndTimeException;
import ir.maktab.exceptions.LessProposedPriceException;
import ir.maktab.exceptions.OffersNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OffersServiceTest {

    @Autowired
    private OffersService offersService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private OrdersService ordersService;

    private Offers offers;

    private Offers offerWithLessProposedPrice;

    private Offers offerWithLessTimeNow;

    private Offers offerWithLessSuggestedTime;

    private Orders order;

    @BeforeAll
    static void setUp(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("UpdateOrderStatus.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        Expert expert = expertService.findById(1L);
        order = ordersService.findOrderInOrdersList(1L, 1L);
        offers = Offers.builder()
                .expert(expert)
                .proposedPrice(850_000L)
                .suggestedTime(LocalDateTime.of(2023,2,21,11,45))
                .durationOfWork("3 day")
                .build();
        offerWithLessProposedPrice = Offers.builder()
                .expert(expert)
                .proposedPrice(500_000L)
                .suggestedTime(LocalDateTime.of(2023,2,21,11,45))
                .durationOfWork("3 day")
                .build();
        offerWithLessTimeNow = Offers.builder()
                .expert(expert)
                .proposedPrice(850_000L)
                .suggestedTime(LocalDateTime.of(2020,2,21,11,45))
                .durationOfWork("3 day")
                .build();
        offerWithLessSuggestedTime = Offers.builder()
                .expert(expert)
                .proposedPrice(850_000L)
                .suggestedTime(LocalDateTime.of(2023,2,19,11,45))
                .durationOfWork("3 day")
                .build();
    }

    @DisplayName("JUnit test for saveOffer method")
    @Test
    @Order(1)
    void save_offer() {
        Offers foundedOffer = offersService.save(offers, order);

        assertThat(foundedOffer.getId()).isEqualTo(4);
    }

    @DisplayName("JUnit test for saveOffer with less proposed price method")
    @Test
    @Order(2)
    void save_offer_with_less_proposed_price() {
        assertThatThrownBy(() -> offersService.save(offerWithLessProposedPrice, order))
                .isInstanceOf(LessProposedPriceException.class)
                .hasMessageContaining("your bid price is lower than the base price.");
    }

    @DisplayName("JUnit test for saveOffer with less time now method")
    @Test
    @Order(3)
    void save_offers_with_less_time_now() {
        assertThatThrownBy(() -> offersService.save(offerWithLessTimeNow, order))
                .isInstanceOf(DateAndTimeException.class)
                .hasMessageContaining("the entered date is less than today's date.");
    }

    @DisplayName("JUnit test for saveOffer with less suggested time method")
    @Test
    @Order(4)
    void save_offers_with_less_suggested_time() {
        assertThatThrownBy(() -> offersService.save(offerWithLessSuggestedTime, order))
                .isInstanceOf(DateAndTimeException.class)
                .hasMessageContaining("The suggested time to start the work is less than the time entered by the customer.");
    }

    @DisplayName("JUnit test for find all by order id method (orderBy)")
    @Test
    @Order(5)
    void findAllByOrderId() {
        List<Offers> offers = offersService.findAllByOrderId(1L);

        assertThat(offers.get(0).getProposedPrice()).isEqualTo(800_000);
    }

    @DisplayName("JUnit test for find by Id method")
    @Test
    @Order(6)
    void findById() {
        Offers offer = offersService.findById(4L);

        assertThat(offer).isNotNull();

        assertThatThrownBy(() -> offersService.findById(234L))
                .isInstanceOf(OffersNotFoundException.class)
                .hasMessageContaining("no offer found with this ID.");
    }

    @DisplayName("JUnit test for get offer from offer list method")
    @Test
    @Order(7)
    void getOffer_from_offer_list() {
        Offers offer = offersService.getOffer(1L, 2L);
        Offers nullOffer = offersService.getOffer(1L, 23L);

        assertThat(offer).isNotNull();
        assertThat(nullOffer).isNull();
    }


}