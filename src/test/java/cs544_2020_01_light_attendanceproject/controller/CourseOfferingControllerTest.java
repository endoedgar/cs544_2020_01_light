package cs544_2020_01_light_attendanceproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs544_2020_01_light_attendanceproject.domain.Course;
import cs544_2020_01_light_attendanceproject.domain.CourseOffering;
import cs544_2020_01_light_attendanceproject.domain.Session;
import cs544_2020_01_light_attendanceproject.domain.Timeslot;
import cs544_2020_01_light_attendanceproject.service.CourseOfferingService;
import cs544_2020_01_light_attendanceproject.service.TimeSlotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = CourseOfferingController.class)
class CourseOfferingControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CourseOfferingService courseOfferingService;

    @Autowired
    private ObjectMapper objectMapper;

    public static Date toDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.of("America/Chicago")).toInstant();
        return toDate(instant);
    }

    public static Date toDate(Instant instant) {
        BigInteger milis = BigInteger.valueOf(instant.getEpochSecond()).multiply(
                BigInteger.valueOf(1000));
        milis = milis.add(BigInteger.valueOf(instant.getNano()).divide(
                BigInteger.valueOf(1_000_000)));
        return new Date(milis.longValue());
    }

    static final List<Course> listOfMockedCourses = Arrays.asList(
            new Course(1L, "EA", "Enterprise Architecture"),
            new Course(2L, "MPP", "Modern Programming Practices"),
            new Course(3L, "Algorithms", "Damn Algorithms")
    );

    static final List<CourseOffering> listOfCourseOffering = Arrays.asList(
            new CourseOffering(1L, listOfMockedCourses.get(0), toDate(LocalDate.of(2020, 5, 1)), toDate(LocalDate.of(2020, 6, 1)), new ArrayList<>()),
            new CourseOffering(2L, listOfMockedCourses.get(1), toDate(LocalDate.of(2020, 6, 1)), toDate(LocalDate.of(2020, 6, 1)), new ArrayList<>())
    );

    public String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void createCourseOffering() throws Exception  {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/courseOffering")
                .content(asJsonString(listOfCourseOffering.get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/courseOffering/1"));
    }

    @Test
    void viewAllOfferingCourse() {
    }

    @Test
    void fetchCourseOffering() {
    }

    @Test
    void deleteCourseOffering() {
    }

    @Test
    void updateCourseOffering() {
    }
}