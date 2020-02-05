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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

    static final List<Timeslot> listOfTimeslots = Arrays.asList(
            new Timeslot("AM", "Morning", LocalTime.of(0, 0), LocalTime.of(11,59, 59)),
            new Timeslot("PM", "Afternoon", LocalTime.of(12, 0), LocalTime.of(23,59, 59))
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
        String asjs = asJsonString(listOfTimeslots.get(0));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/timeslots")
                .content(asjs)
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
        when(timeSlotService.get("PM")).thenReturn(Optional.of(listOfTimeslots.get(1)));

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
    void deleteTs() {
    }

    @Test
    void updateTs() {
    }
}