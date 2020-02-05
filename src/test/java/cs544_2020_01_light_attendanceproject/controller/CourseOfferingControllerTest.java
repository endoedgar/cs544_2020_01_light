package cs544_2020_01_light_attendanceproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs544_2020_01_light_attendanceproject.domain.*;
import cs544_2020_01_light_attendanceproject.service.CourseOfferingService;
import cs544_2020_01_light_attendanceproject.service.TimeSlotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    static final List<Location> listOfMockedLocations = Arrays.asList(
            new Location(1L, "Location A"),
            new Location(2L, "Location B"),
            new Location(3L, "Location C")
    );

    static final List<Course> listOfMockedCourses = Arrays.asList(
            new Course(1L, "EA", "Enterprise Architecture"),
            new Course(2L, "MPP", "Modern Programming Practices"),
            new Course(3L, "Algorithms", "Damn Algorithms")
    );

    static final List<CourseOffering> listOfCourseOffering = Arrays.asList(
            new CourseOffering(1L, listOfMockedCourses.get(0), toDate(LocalDate.of(2020, 5, 1)), toDate(LocalDate.of(2020, 6, 1)), listOfMockedLocations.get(0), new ArrayList<>()),
            new CourseOffering(2L, listOfMockedCourses.get(1), toDate(LocalDate.of(2020, 6, 1)), toDate(LocalDate.of(2020, 6, 1)), listOfMockedLocations.get(0), new ArrayList<>())
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
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void viewAllOfferingCourse() throws Exception {
        when(courseOfferingService.getAllCourseOffering()).thenReturn(listOfCourseOffering);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/courseOffering")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        List<CourseOffering> returnedListOfLocations = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<CourseOffering>>() {});

        assertThat(returnedListOfLocations).isEqualTo(listOfCourseOffering);
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void fetchCourseOffering() throws Exception {
        Optional<CourseOffering> receivedCourseOffering = Optional.of(listOfCourseOffering.get(1));
        when(courseOfferingService.getCourseOffering(2L)).thenReturn(receivedCourseOffering);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/courseOffering/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CourseOffering returnedTimeslot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CourseOffering.class);

        assertThat(returnedTimeslot).isEqualTo(listOfCourseOffering.get(1));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void deleteCourseOffering() throws Exception {
        when(courseOfferingService.getCourseOffering(1L)).thenReturn(Optional.of(listOfCourseOffering.get(0)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/courseOffering/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CourseOffering returnedTimeslot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CourseOffering.class);

        assertThat(returnedTimeslot).isInstanceOf(CourseOffering.class);
        assertThat(returnedTimeslot).isNotNull();
        assertThat(returnedTimeslot).isEqualTo(listOfCourseOffering.get(0));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void updateCourseOffering() throws Exception {
        CourseOffering oldCourseOffering = new CourseOffering(1L, listOfMockedCourses.get(0), toDate(LocalDate.of(2020, 5, 1)), toDate(LocalDate.of(2020, 6, 1)), listOfMockedLocations.get(0), new ArrayList<>());
        CourseOffering newCourseOffering = new CourseOffering(1L, listOfMockedCourses.get(2), toDate(LocalDate.of(2021, 6, 1)), toDate(LocalDate.of(2021, 6, 1)), listOfMockedLocations.get(0), new ArrayList<>());

        when(courseOfferingService.getCourseOffering(oldCourseOffering.getId())).thenReturn(Optional.of(oldCourseOffering));
        when(courseOfferingService.updateCourseOffering(any(CourseOffering.class))).thenReturn(null);
        when(courseOfferingService.updateCourseOffering(newCourseOffering)).thenReturn(newCourseOffering);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/courseOffering/" + oldCourseOffering.getId())
                .content(asJsonString(newCourseOffering))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        CourseOffering returnedCourseOffering = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CourseOffering.class);

        assertThat(returnedCourseOffering).isInstanceOf(CourseOffering.class);
        assertThat(returnedCourseOffering).isNotNull();
        assertThat(returnedCourseOffering).isEqualTo(newCourseOffering);
    }
    @Test
    @WithMockUser(value = "student",roles={"STUDENT"})
    void viewAllOfferingSessions() throws Exception {
        Optional<List<Session>> mockedList = Mockito.mock(Optional.class);

        when(courseOfferingService.getCourseSessions(1L)).thenReturn(mockedList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/getSessions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}