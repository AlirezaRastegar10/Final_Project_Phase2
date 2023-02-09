package ir.maktab.service;

import ir.maktab.entity.User;
import ir.maktab.exceptions.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static ir.maktab.entity.enumeration.UserType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    @Autowired
    private UserService userService;
    private User customerUser;
    private User expertUser;

    @BeforeEach
    void setUp() {
        customerUser = User.builder()
                .firstname("alireza")
                .lastname("rastegar")
                .email("alireza@gmail.com")
                .password("alireza1")
                .userType(CUSTOMER)
                .build();
        expertUser = User.builder()
                .firstname("omid")
                .lastname("omidi")
                .email("omid@gmail.com")
                .password("omid1234")
                .userType(EXPERT)
                .build();
    }

    @DisplayName("JUnit test for saveUser method")
    @Test
    @Order(1)
    void save_new_customer_and_expert_user() {
        User savedCustomerUser = userService.register(customerUser);
        User savedExpertUser = userService.register(expertUser);

        assertThat(savedCustomerUser).isNotNull();
        assertThat(savedExpertUser).isNotNull();
    }

    @DisplayName("JUnit test for saveUser method with exist user")
    @Test
    @Order(2)
    void save_new_customer_and_expert_user_with_exist_email() {
        assertThatThrownBy(() -> userService.register(customerUser))
                .isInstanceOf(UserExistException.class)
                .hasMessageContaining("user with given email :-- " + customerUser.getEmail() + " -- has already registered, please login.");

        assertThatThrownBy(() -> userService.register(expertUser))
                .isInstanceOf(UserExistException.class)
                .hasMessageContaining("user with given email :-- " + expertUser.getEmail() + " -- has already registered, please login.");
    }

    @DisplayName("JUnit test for loginUser method")
    @Test
    @Order(3)
    void login() {
        boolean loggedInCustomerUser = userService.login(customerUser.getEmail(), customerUser.getPassword());
        boolean loggedInExpertUser = userService.login(expertUser.getEmail(), expertUser.getPassword());

        assertThat(loggedInCustomerUser).isTrue();
        assertThat(loggedInExpertUser).isTrue();
    }

    @DisplayName("JUnit test for loginUser method with not found user")
    @Test
    @Order(4)
    void login_not_found_user() {
        assertThatThrownBy(() -> userService.login("sina@gmail.com", customerUser.getPassword()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("user with given email :-- sina@gmail.com -- not found.");

        assertThatThrownBy(() -> userService.login("hasan@gmail.com", expertUser.getPassword()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("user with given email :-- hasan@gmail.com -- not found.");
    }

    @DisplayName("JUnit test for loginUser method with wrong password")
    @Test
    @Order(5)
    void login_with_wrong_password() {
        assertThatThrownBy(() -> userService.login(customerUser.getEmail(), "a1234567"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("password is wrong.");

        assertThatThrownBy(() -> userService.login(expertUser.getEmail(), "o1234567"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("password is wrong.");
    }

    @DisplayName("JUnit test for findAll method")
    @Test
    @Order(6)
    void findAll() {
        List<User> users = userService.findAll();

        assertThat(users).isNotEmpty();
    }

    @DisplayName("JUnit test for findById method")
    @Test
    @Order(7)
    void findById() {
        User user = userService.findById(1L);

        assertThat(user).isNotNull();

        assertThatThrownBy(() -> userService.findById(3L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("no user found with this ID.");
    }

    @DisplayName("JUnit test for update method")
    @Test
    @Order(8)
    void update() {
        User customer = userService.findById(1);
        customer.setFirstname("mohammad");
        User expert = userService.findById(2);
        expert.setLastname("hosseini");

        User newCustomer = userService.update(customer);
        User newExpert = userService.update(expert);

        assertThat(newCustomer.getFirstname()).isEqualTo("mohammad");
        assertThat(newExpert.getLastname()).isEqualTo("hosseini");
    }

    @DisplayName("JUnit test for changePassword method")
    @Test
    @Order(9)
    void changePassword() {
        User newCustomer = userService.changePassword(1L, customerUser.getEmail(), "ali12345", "ali12345");
        User newExpert = userService.changePassword(2L, expertUser.getEmail(), "omid0000", "omid0000");

        assertThat(newCustomer.getPassword()).isEqualTo("ali12345");
        assertThat(newExpert.getPassword()).isEqualTo("omid0000");
    }

    @DisplayName("JUnit test for changePassword method with password not match")
    @Test
    @Order(10)
    void changePassword_with_not_match() {
        assertThatThrownBy(() -> userService.changePassword(1L, customerUser.getEmail(), "alireza1", "ali12345"))
                .isInstanceOf(PasswordNotMatchException.class)
                .hasMessageContaining("the password and its repetition are not the same.");

        assertThatThrownBy(() -> userService.changePassword(2L, expertUser.getEmail(), "omid1234", "omid0000"))
                .isInstanceOf(PasswordNotMatchException.class)
                .hasMessageContaining("the password and its repetition are not the same.");
    }

    @DisplayName("JUnit test for changePassword method with wrong user email")
    @Test
    @Order(11)
    void changePassword_with_wrong_user_email() {
        assertThatThrownBy(() -> userService.changePassword(1L, "sina@gmail.com", "alireza1", "alireza1"))
                .isInstanceOf(WrongUserEmailException.class)
                .hasMessageContaining("the email entered does not belong to you.");

        assertThatThrownBy(() -> userService.changePassword(2L, "hasan@gmail.com", "omid1234", "omid1234"))
                .isInstanceOf(WrongUserEmailException.class)
                .hasMessageContaining("the email entered does not belong to you.");
    }
}