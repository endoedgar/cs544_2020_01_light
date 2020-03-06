package cs544_2020_01_light_attendanceproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs544_2020_01_light_attendanceproject.domain.Location;
import cs544_2020_01_light_attendanceproject.domain.Timeslot;
import cs544_2020_01_light_attendanceproject.service.LocationService;
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
import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = TimeSlotController.class)
class TimeSlotControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TimeSlotService timeSlotService;

    @Autowired
    private ObjectMapper objectMapper;

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

    static final List<Timeslot> listOfTimeslots = Arrays.asList(
            new Timeslot("AM", "Morning", toDate(LocalTime.of(0, 0,0)), toDate(LocalTime.of(11,59, 59)), new ArrayList<>()),
            new Timeslot("PM", "Afternoon", toDate(LocalTime.of(12, 0,0)), toDate(LocalTime.of(23,59, 59)), new ArrayList<>())
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
    void newTimeSlot() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/timeslots")
                .content(asJsonString(listOfTimeslots.get(0)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/timeslots/AM"));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void fetchAllTs() throws Exception {
        when(timeSlotService.getAll()).thenReturn(listOfTimeslots);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/timeslots")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        List<Timeslot> returnedListOfLocations = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<Timeslot>>() {});

        assertThat(returnedListOfLocations).isEqualTo(listOfTimeslots);
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void fetchTs() throws Exception {
        Optional<Timeslot> receivedTimeslot = Optional.of(listOfTimeslots.get(1));
        when(timeSlotService.get("PM")).thenReturn(receivedTimeslot);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/timeslots/PM")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Timeslot returnedTimeslot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Timeslot.class);

        assertThat(returnedTimeslot).isEqualTo(listOfTimeslots.get(1));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void deleteTs() throws Exception {
        when(timeSlotService.get("AM")).thenReturn(Optional.of(listOfTimeslots.get(0)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .delete("/timeslots/AM")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Timeslot returnedTimeslot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Timeslot.class);

        assertThat(returnedTimeslot).isInstanceOf(Timeslot.class);
        assertThat(returnedTimeslot).isNotNull();
        assertThat(returnedTimeslot).isEqualTo(listOfTimeslots.get(0));
    }

    @Test
    @WithMockUser(value = "admin",roles={"ADMIN"})
    void updateTs() throws Exception {
        Timeslot oldTimeslot = new Timeslot("AM", "Morning", toDate(LocalTime.of(0, 0, 0)), toDate(LocalTime.of(11,59, 59)),new ArrayList<>());
        Timeslot newTimeslot = new Timeslot("AM", "Morning new", toDate(LocalTime.of(0, 0,0)), toDate(LocalTime.of(11,59, 59)),new ArrayList<>());

        when(timeSlotService.get(oldTimeslot.getAbbreviation())).thenReturn(Optional.of(oldTimeslot));
        when(timeSlotService.update(any(Timeslot.class))).thenReturn(null);
        when(timeSlotService.update(newTimeslot)).thenReturn(newTimeslot);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/timeslots/" + oldTimeslot.getAbbreviation())
                .content(asJsonString(newTimeslot))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Timeslot returnedTimeslot = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Timeslot.class);

        assertThat(returnedTimeslot).isInstanceOf(Timeslot.class);
        assertThat(returnedTimeslot).isNotNull();
        assertThat(returnedTimeslot).isEqualTo(newTimeslot);
    }
}