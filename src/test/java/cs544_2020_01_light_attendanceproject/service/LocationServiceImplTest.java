package cs544_2020_01_light_attendanceproject.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cs544_2020_01_light_attendanceproject.dao.LocationRepository;
import cs544_2020_01_light_attendanceproject.domain.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest
public class LocationServiceImplTest {

	@Autowired
	private LocationService locationService;
	@Autowired
	private LocationRepository locationRepository;

	@Test
	public void testFindLocationById() {
		Location location1 = locationService.findLocationById(1L).get();
		assertEquals(location1.getId(), Long.valueOf(1L));
	}

	@Test
	public void testFindLocationByDescription() {
		Location location1 = locationService.findLocationById(1L).get();
		Location location2 = locationService.findLocationByDescription(location1.getDescription()).get();
		assertEquals(location1, location2);
	}

	@Test
	public void testFindAllLocations() {
		List<Location> list = (List<Location>) locationService.findAllLocations();
		long dbSize = list.size();
		long actualDBsize = locationRepository.count();
		assertEquals(actualDBsize, dbSize);
	}

	@Test
	public void testCreateLocation() {
		Long dbSize = locationRepository.count();
		Location location = new Location();
		location.setDescription("Dalby Hall");
		locationService.createLocation(location);
		Long expectedSize = locationRepository.count();
		assertEquals(++dbSize, expectedSize);
	}

	@Test
	public void testUpdateLocation() {
		Location location = locationService.findLocationById(1L).get();
		location.setDescription("Some Description");
		Location updated = locationService.updateLocation(location);
		assertEquals(updated.getDescription(), "Some Description");
	}

	@Test
	public void testDeleteLocation() {
		List<Location> list = (List<Location>) locationService.findAllLocations();
		long dbSize = list.size() - 1;
		Location location = new Location();
		location.setDescription("Dalby Hall");
		locationService.deleteLocation(list.get(list.size() - 1));
		long actualDBsize = locationRepository.count();
		assertEquals(dbSize, actualDBsize);
	}

}
