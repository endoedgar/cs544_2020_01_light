package cs544_2020_01_light_attendanceproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.Location;
import cs544_2020_01_light_attendanceproject.service.CourseService;
import cs544_2020_01_light_attendanceproject.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CourseController.class)
class CourseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    static final List<Course> listOfMockedCourses = Arrays.asList(
            new Course(1L, "EA", "Enterprise Architecture"),
            new Course(2L, "MPP", "Modern Programming Practices"),
            new Course(3L, "Algorithms", "Damn Algorithms")
    );

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void addNewCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/course")
                .content(asJsonString(listOfMockedCourses.get(1)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/course/MPP"));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void all() throws Exception {
        when(courseService.listCourses()).thenReturn(listOfMockedCourses);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        List<Course> returnedListOfLocations = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Course>>() {});

        assertThat(returnedListOfLocations).isEqualTo(listOfMockedCourses);
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void deleteCourse() throws Exception {
        when(courseService.findCourseByName("EA")).thenReturn(Optional.of(listOfMockedCourses.get(0)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/course/EA")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Course returnedCourse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Course.class);

        assertThat(returnedCourse).isInstanceOf(Course.class);
        assertThat(returnedCourse).isNotNull();
        assertThat(returnedCourse).isEqualTo(listOfMockedCourses.get(0));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void updateCourse() throws Exception {
        when(courseService.findCourseByName("MPP")).thenReturn(Optional.of(listOfMockedCourses.get(1)));
        when(courseService.updateCourse(any(Course.class))).thenReturn(null);
        when(courseService.updateCourse(listOfMockedCourses.get(1))).thenReturn(listOfMockedCourses.get(2));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/course/MPP")
                .content(asJsonString(listOfMockedCourses.get(1)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Course returnedCourse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Course.class);

        assertThat(returnedCourse).isInstanceOf(Course.class);
        assertThat(returnedCourse).isNotNull();
        assertThat(returnedCourse).isEqualTo(new Course(3L, "Algorithms", "Damn Algorithms"));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void findCourseByName() throws Exception {
        when(courseService.findCourseByName("Algorithms")).thenReturn(Optional.of(listOfMockedCourses.get(2)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/course/Algorithms")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Course returnedLocation = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Course.class);

        assertThat(returnedLocation).isEqualTo(listOfMockedCourses.get(2));
    }
}