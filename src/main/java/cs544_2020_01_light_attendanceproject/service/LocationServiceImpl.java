package cs544_2020_01_light_attendanceproject.service;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cs544_2020_01_light_attendanceproject.dao.LocationRepository;
import cs544_2020_01_light_attendanceproject.domain.Location;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationDAO;

	@Override
	public Location findLocationById(long id) throws ObjectNotFoundException {
		return locationDAO.findById(id).get();
	}

	@Override
	public Location findLocationByDescription(String description) throws ObjectNotFoundException {
		return locationDAO.findByDescription(description).get();
	}

	@Override
	public Iterable<Location> findAllLocations() {
		return locationDAO.findAll();
	}

	@Override
	public Location createLocation(Location location) {
		return locationDAO.save(location);
	}

	@Override
	public Location updateLocation(Location location) {
		return locationDAO.save(location);
	}

	@Override
	public void deleteLocation(Location location) {
		locationDAO.delete(location);
	}

}
