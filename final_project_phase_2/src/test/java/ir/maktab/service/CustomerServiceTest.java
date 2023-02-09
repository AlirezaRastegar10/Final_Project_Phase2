package ir.maktab.service;

import ir.maktab.entity.Customer;
import ir.maktab.entity.User;
import ir.maktab.exceptions.UserNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        User user = userService.findById(1L);
        customer = Customer.builder()
                .validity(1_000_000L)
                .user(user).build();
    }

    @DisplayName("JUnit test for saveCustomer method")
    @Test
    @Order(1)
    void save_customer() {
        Customer newCustomer = customerService.save(customer);

        assertThat(newCustomer).isNotNull();
    }

    @DisplayName("JUnit test for findAll method")
    @Test
    @Order(2)
    void findAll() {
        List<Customer> customers = customerService.findAll();

        assertThat(customers).isNotEmpty();
    }

    @DisplayName("JUnit test for findById method")
    @Test
    @Order(3)
    void findById() {
        Customer foundedCustomer = customerService.findById(1L);

        assertThat(foundedCustomer).isNotNull();

        assertThatThrownBy(() -> customerService.findById(213L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("no customer found with this ID");
    }
}