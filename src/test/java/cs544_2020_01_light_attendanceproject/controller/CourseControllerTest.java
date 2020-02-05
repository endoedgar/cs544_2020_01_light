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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CourseController.class)
class CourseControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseService locationService;

    @Autowired
    private ObjectMapper objectMapper;

    static final List<Course> listOfMockedCourses = Arrays.asList(
            new Course(1L, "EA"),
            new Course(2L, "MPP"),
            new Course(3L, "Algorithms")
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
    void addNewCourse() {
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void all() throws Exception {
        when(locationService.listCourses()).thenReturn(listOfMockedCourses);

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
    void deleteCourse() {
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void updateCourse() {
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void findCourseByName() throws Exception {

    }
}