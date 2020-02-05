package cs544_2020_01_light_attendanceproject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import cs544_2020_01_light_attendanceproject.controller.UserController;
import cs544_2020_01_light_attendanceproject.dao.UserRepository;
import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.exceptions.AdminsCannotDeleteThemselvesException;
import cs544_2020_01_light_attendanceproject.exceptions.UserNotFoundException;
import cs544_2020_01_light_attendanceproject.service.UserService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    static List<User> listOfMockUsers;

    @BeforeAll
    public static void beforeAll() {
        listOfMockUsers = new ArrayList<>();

        User user = new User();
        user.setUsername("mockuser");
        user.setFirstName("Mock user firstname");
        user.setLastName("Mock user lastname");
        user.setPassword("12345");
        user.setEnabled(true);
        user.setEmail("mockuser@email.com");
        user.setBarCodeId("000-XX-YYYY");

        listOfMockUsers.add(user);

        User user2 = new User();
        user2.setUsername("mockuser2");
        user2.setFirstName("Mock user 2 firstname");
        user2.setLastName("Mock user 2 lastname");
        user2.setPassword("123452");
        user2.setEnabled(true);
        user2.setEmail("mockuser2@email.com");
        user2.setBarCodeId("001-XX-YYYY");

        listOfMockUsers.add(user2);
    }

    @Test
    public void addUser()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Object> responseEntity = userController.newUser(listOfMockUsers.get(0));

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/mockuser");
    }

    @Test
    public void updateUser() {
        when(userService.replaceUser(any(User.class), any(String.class))).thenReturn(null);
        when(userService.replaceUser(any(User.class), eq("mockuser"))).thenReturn(listOfMockUsers.get(0));

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User user = userController.replaceUser(listOfMockUsers.get(0), "mockuser");

        assertThat(user).isInstanceOf(User.class);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("mockuser");

        User nonExistingUser = userController.replaceUser(listOfMockUsers.get(0), "nonExistingUser");
        assertThat(nonExistingUser).isNull();
    }

    @Test
    public void deleteNormalUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getName()).thenReturn("loggedInUsername");
        userController.deleteUser("mockuser");
    }

    @Test
    public void deleteYourself() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(authentication.getName()).thenReturn("loggedInUsername");

        Assertions.assertThrows(AdminsCannotDeleteThemselvesException.class, () -> {
            userController.deleteUser("loggedInUsername");
        });
    }

    @Test
    public void testFindAll() {
        when(userService.listUsers()).thenReturn(listOfMockUsers);

        Iterable<User> result = userController.all();

        Iterator<User> iterator = result.iterator();

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next().getFirstName()).isEqualTo("Mock user firstname");
        assertThat(iterator.next().getFirstName()).isEqualTo("Mock user 2 firstname");
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void testFindExistingUser() {
        when(userService.findOneUser("mockuser2")).thenReturn(Optional.of(listOfMockUsers.get(1)));

        User user = userController.one("mockuser2");

        assertThat(user.getUsername()).isEqualTo("mockuser2");
    }

    @Test
    public void testFindNonExistingUser() {
        when(userService.findOneUser(any(String.class))).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(UserNotFoundException.class, () -> {
            userController.one("nonExistingUser");
        });
    }
}
