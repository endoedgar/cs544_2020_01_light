package cs544_2020_01_light_attendanceproject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cs544_2020_01_light_attendanceproject.controller.UserController;
import cs544_2020_01_light_attendanceproject.dao.UserRepository;
import cs544_2020_01_light_attendanceproject.domain.User;
import cs544_2020_01_light_attendanceproject.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

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
        user2.setFirstName("Mock user 2  firstname");
        user2.setLastName("Mock user 2 lastname");
        user2.setPassword("123452");
        user2.setEnabled(true);
        user2.setEmail("mockuser2@email.com");
        user2.setBarCodeId("001-XX-YYYY");

        listOfMockUsers.add(user2);
    }

    @Test
    public void testAddUser()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Object> responseEntity = userController.newUser(listOfMockUsers.get(0));

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/mockuser");
    }

    @Test
    public void testFindAll() {
        when(userService.listUsers()).thenReturn(listOfMockUsers);

        Iterable<User> result = userController.all();

        assertThat(result.iterator().hasNext()).isTrue();
    }
}
