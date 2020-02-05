package cs544_2020_01_light_attendanceproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import cs544_2020_01_light_attendanceproject.dao.LocationRepository;
import cs544_2020_01_light_attendanceproject.domain.Location;

//@RunWith(SpringRunner.class)
class LocationServiceImplTest {

	@MockBean
	private LocationRepository mockRepository;

	@Test
	void testFindLocationById() {
		// fail("Not yet implemented");TODO
	}

	@Test
	void testFindLocationByDescription() {
		// fail("Not yet implemented");TODO
	}

	@Test
	void testFindAllLocations() {
		// fail("Not yet implemented");TODO
	}

	@Test
	void testCreateLocation() {
		// fail("Not yet implemented");TODO
	}

	@Test
	void testUpdateLocation() {
		// fail("Not yet implemented");TODO
	}

	@Test
	void testDeleteLocation() {
		// fail("Not yet implemented");TODO
	}

}
