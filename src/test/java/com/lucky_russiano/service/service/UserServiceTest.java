package com.lucky_russiano.service.service;

import com.lucky_russiano.DAO.UserDao;
import com.lucky_russiano.entity.MyUser;
import com.lucky_russiano.service.UserService;
import com.lucky_russiano.service.extension.ConditionalExtension;
import com.lucky_russiano.service.extension.GlobalExtension;
import com.lucky_russiano.service.extension.ThrowableExtension;
import com.lucky_russiano.service.extension.UserServiceParamResolver;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("unit-tests") //command in terminal ./mvnw clean test -Dgroups=login
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD) //default //for each test will be created new Instance UserServiceTest
@ExtendWith({
        UserServiceParamResolver.class,
        GlobalExtension.class,
        ConditionalExtension.class,
        MockitoExtension.class
//        ThrowableExtension.class

})
//@Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
public class UserServiceTest {
    @Captor
    private ArgumentCaptor<Long> captor;
    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    private MyUser user1;
    private MyUser user;
    private MyUser user2;

    @BeforeEach
//    void setUp(UserService userService){ // from UserServiceParamResolver
    void setUp(){ // from UserServiceParamResolver
        user1 = MyUser.builder().id(1L).username("avraam112").password("123").name("Russo").lastName("Zaripov").salary(1000).build();
        user = MyUser.builder().id(2L).username("elena001").password("123").name("Elena").lastName("Sokolova").salary(2000).build();
        user2 = MyUser.builder().id(3L).username("marishel").password("123").name("marishel").lastName("Sokolova").salary(2000).build();
//        this.userDao = Mockito.mock(UserDao.class);
//        this.userDao = Mockito.spy(new UserDao());
//        this.userService = new UserService(userDao);
        userService.addUserToList(user, user1, user2);
        System.out.println("before each..." + this);
    }
    @BeforeAll
     static void beforeAll(){
        System.out.println("before all...");
    }
    @Test
    void deleteExistedUserByID(){
//        BDDMockito.given(userDao.deleteUserById(mockUserId)).willReturn(true);
//        Mockito.doReturn(true).when(userDao).deleteUserById(Mockito.anyLong());
//        Mockito.when(userDao.deleteUserById(Mockito.anyLong())).thenReturn(false);
        Long mockUserId = 1111L;
        BDDMockito.given(userDao.deleteUserById(mockUserId)).willAnswer(invocationOnMock -> {
            Arrays.stream(invocationOnMock.getArguments())
                    .forEach(arg -> System.out.println(arg));
            return true;
        });
        boolean result = userService.deleteById(mockUserId);
        assertThat(result).isTrue();
//        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
//        Mockito.verify(userDao, Mockito.times(1)).deleteUserById(mockUserId);
        Mockito.verify(userDao, Mockito.times(1)).deleteUserById(captor.capture()); // catch which argument we use
        assertThat(captor.getValue()).isEqualTo(mockUserId);
    }
    @Test
    void throwExceptionIfDataBaseIsNotAvailable(){
        Mockito.doThrow(RuntimeException.class).when(userDao).deleteUserById(Mockito.anyLong());
        Assertions.assertThrows(RuntimeException.class, () -> {
            userService.deleteById(Mockito.anyLong());
        });
    }
    @Test
    @Order(5)
    @DisplayName("Add users to list.")
    void addUsers_test(){
        assertThat( userService.getAllUsers()).hasSize(3);
    }

    @Test
    @Order(1)
    void addUserToHashMap_test(){
        Map<Long, MyUser> myUserMap = userService.convertUserToMap(user, user1, user2);
        assertAll(
                () ->  assertThat(myUserMap).containsKeys(user.getId(), user1.getId(), user2.getId()),
                () -> assertThat(myUserMap).containsValues(user, user1, user2)
        );
    }
    @Test
    @DisplayName("Login test will be successfully")
    @Tag("login") //command in terminal ./mvnw clean test -Dgroups=login
    @Order(2)
//    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    void oneMore_login_test_success() throws IOException {

//        if (true) {
//            throw new RuntimeException(); // test fail if will be IOException
//        }
        assertTimeout(Duration.ofMillis(200), () -> {
            Thread.sleep(150);
            userService.login("avraam112", "123");
        });
    }
    @AfterEach
    void afterEach(){
        System.out.println("After each..." + this);
    }
    @AfterAll
     static void afterAll(){
        System.out.println("After all...");
    }
    @Nested
    class LoginTests{
        @Test
        @DisplayName("Login test will be successfully")
        @Tag("login") //command in terminal ./mvnw clean test -Dgroups=login
        @Order(2)
        void login_test_success(){

            Optional<MyUser> avraam112 = userService.login("avraam112", "123");
            assertThat(avraam112).isPresent();
            assertThat(avraam112.get().getUsername()).isEqualTo("avraam112");
        }

        @Test
        @Tag("login")//command in terminal ./mvnw clean test -Dgroups=login
        @Order(3)
        @DisplayName("Login test will be failure")
        void login_test_fail(){
            try {
                userService.login(null, "123");
                Assertions.fail("Username should be null in this test...");
            }catch (IllegalArgumentException exception){
                assertThat(true).isTrue();
            }
        }
        @Test
        @Tag("login") //command in terminal ./mvnw clean test -Dgroups=login
        @Order(4)
        void oneMore_login_test_fail(){
            assertThrows(IllegalArgumentException.class, () -> userService.login(null, "123"));
        }
        @ParameterizedTest
        @MethodSource("com.lucky_russiano.service.service.UserServiceTest#getArgumentsForLoginTestMethod")
        void parametrizedMethodLoginTest(String username, String password){
            Optional<MyUser> savedUser = userService.login(username, password);
            assertThat(savedUser).isPresent();
            assertThat(savedUser.get().getName()).isEqualTo("Russo");
        }
        @ParameterizedTest
        @CsvFileSource(resources = "/login-test-data.csv", delimiter = ',', numLinesToSkip = 1)
        void oneMoreParametrizedMethodLoginTest(String username, String password){
            Optional<MyUser> savedUser = userService.login(username, password);
            assertThat(savedUser).isPresent();
            assertThat(savedUser.get().getName()).isEqualTo("Russo");
        }
    }
    static Stream<Arguments> getArgumentsForLoginTestMethod(){ // only static method can give arguments
        return Stream.of(
                Arguments.of("avraam112", "123")
        );
    }
}
