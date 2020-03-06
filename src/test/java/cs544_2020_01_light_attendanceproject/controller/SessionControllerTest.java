package cs544_2020_01_light_attendanceproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs544_2020_01_light_attendanceproject.domain.*;
import cs544_2020_01_light_attendanceproject.service.SessionService;
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
@WebMvcTest(controllers = SessionController.class)
class SessionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    public static Date toDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay()
                .atZone(ZoneId.of("America/Chicago")).toInstant();
        return toDate(instant);
    }

    public static Date toDate(LocalTime localTime) {
        Instant instant = localTime.atDate(LocalDate.ofEpochDay(0))
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

    static final List<Location> listOfMockedLocations = Arrays.asList(
            new Location(1L, "Location A"),
            new Location(2L, "Location B"),
            new Location(3L, "Location C")
    );


    static final List<CourseOffering> listOfCourseOffering = Arrays.asList(
            new CourseOffering(1L, listOfMockedCourses.get(0), toDate(LocalDate.of(2020, 5, 1)), toDate(LocalDate.of(2020, 6, 1)), listOfMockedLocations.get(0), new ArrayList<>()),
            new CourseOffering(2L, listOfMockedCourses.get(1), toDate(LocalDate.of(2020, 6, 1)), toDate(LocalDate.of(2020, 6, 1)), listOfMockedLocations.get(0), new ArrayList<>())
    );

    static final List<Timeslot> listOfTimeslots = Arrays.asList(
            new Timeslot("AM", "Morning", toDate(LocalTime.of(0, 0,0)), toDate(LocalTime.of(11,59, 59)), new ArrayList<>()),
            new Timeslot("PM", "Afternoon", toDate(LocalTime.of(12, 0,0)), toDate(LocalTime.of(23,59, 59)), new ArrayList<>())
    );

    static final List<Session> listOfSessions = Arrays.asList(
            new Session(1L, listOfCourseOffering.get(0), listOfTimeslots.get(0), toDate(LocalDate.of(2020, 5, 10))),
            new Session(2L, listOfCourseOffering.get(0), listOfTimeslots.get(0), toDate(LocalDate.of(2020, 5, 11)))
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
    void addSession() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/session")
                .content(asJsonString(listOfSessions.get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/session/1"));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void testFindAllSessions() throws Exception {
        when(sessionService.findAllSessions()).thenReturn(listOfSessions);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        List<Session> returnedListOfSessions = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Session>>() {});

        assertThat(returnedListOfSessions).isEqualTo(listOfSessions);
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void testFindSession() throws Exception {
        Optional<Session> receivedSession = Optional.of(listOfSessions.get(0));
        when(sessionService.findSessionById(1L)).thenReturn(receivedSession);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/session/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Session returnedSession = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Session.class);

        assertThat(returnedSession).isEqualTo(listOfSessions.get(0));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void deleteSession() throws Exception {
        when(sessionService.findSessionById(1L)).thenReturn(Optional.of(listOfSessions.get(0)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/session/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Session returnedSession = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Session.class);

        assertThat(returnedSession).isInstanceOf(Session.class);
        assertThat(returnedSession).isNotNull();
        assertThat(returnedSession).isEqualTo(listOfSessions.get(0));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void update() throws Exception {
        Session oldSession = new Session(1L, listOfCourseOffering.get(0), listOfTimeslots.get(0), toDate(LocalDate.of(2020, 5, 10)));
        Session newSession = new Session(1L, listOfCourseOffering.get(0), listOfTimeslots.get(0), toDate(LocalDate.of(2020, 5, 11)));

        when(sessionService.findSessionById(oldSession.getId())).thenReturn(Optional.of(oldSession));
        when(sessionService.updateSession(any(Session.class))).thenReturn(null);
        when(sessionService.updateSession(newSession)).thenReturn(newSession);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/session/" + oldSession.getId())
                .content(asJsonString(newSession))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Session returnedSession = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Session.class);

        assertThat(returnedSession).isInstanceOf(Session.class);
        assertThat(returnedSession).isNotNull();
        assertThat(returnedSession).isEqualTo(newSession);
    }
}