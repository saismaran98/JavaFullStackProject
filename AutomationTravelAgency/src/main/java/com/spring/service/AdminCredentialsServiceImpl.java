
package com.spring.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.DriverEntity;
import com.spring.entity.ReservationEntity;
import com.spring.entity.RouteEntity;
import com.spring.entity.UserProfileEntity;
import com.spring.entity.VehicleEntity;
import com.spring.json.Driver;
import com.spring.json.Route;
import com.spring.json.UserProfile;
import com.spring.json.Vehicle;
import com.spring.rest.repository.DriverRepository;
import com.spring.rest.repository.ReservationRepository;
import com.spring.rest.repository.RouteRepository;
import com.spring.rest.repository.UserProfileRepository;
import com.spring.rest.repository.VehicleRepository;
import com.spring.utils.DriverUtils;
import com.spring.utils.RouteUtils;
import com.spring.utils.UserProfileUtils;
import com.spring.utils.VehicleUtils;

@Service
public class AdminCredentialsServiceImpl implements AdminCredentialsService {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private RouteRepository routeRepository;

	@Autowired
	private DriverRepository driverRepository;
	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;

	@Override
	public Object saveVehicleDetails(String authtoken,Vehicle vehicle) {
		UserProfileEntity userEntity=this.getUserUsingSessionId(authtoken);
		if(userEntity!=null)
		{
			if(userEntity.getUserType().equals("A"))
			{
				 
				VehicleEntity vehicleEntity=vehicleRepository.save(VehicleUtils.convertVehicleToVehicleEntity(vehicle));
				return vehicleEntity;
			}
			else
			{
				return "Not a Admin";
			}
		}
		else
		{
			return "Error : Wrong sessionId";
		}
	}

	public Object saveRouteDetails(String authtoken,Route route) {
		UserProfileEntity userEntity=this.getUserUsingSessionId(authtoken);
		if(userEntity!=null)
		{
			if(userEntity.getUserType().equals("A"))
			{
				 
				RouteEntity routeEntity=routeRepository.save(RouteUtils.convertRouteToRouteEntity(route));
				return routeEntity;
			}
			else
			{
				return "Not a Admin";
			}
		}
		else
		{
			return "Error : Wrong sessionId";
		}
	}
	public Object saveDriverDetails(String authtoken,Driver driver) {
		UserProfileEntity userEntity=this.getUserUsingSessionId(authtoken);
		if(userEntity!=null)
		{
			if(userEntity.getUserType().equals("A"))
			{
				 
				DriverEntity driverEntity=driverRepository.save(DriverUtils.convertDriverToDriverEntity(driver));
				return driverEntity;
			}
			else
			{
				return "Not a Admin";
			}
		}
		else
		{
			return "Error : Wrong sessionId";
		}
	}

	@Override
	public String deleteByVehicleid(String authtoken,long vehicleid) 
	{
		
		UserProfileEntity checklogin=userProfileRepository.findBySessionId(authtoken).get(0);
		
	if(checklogin!=null  || authtoken!=null )
		{	
			if(checklogin.getUserType().equals("A"))
			{
			VehicleEntity vehicletodelete=vehicleRepository.findById(vehicleid).get();
				if (vehicletodelete!=null)
						
					{
						vehicleRepository.deleteById(vehicleid);
						return "Vehicle Deleted";
					}
				else 
					
					{
						return "Vehicleid is invalid";
					}
				}
			else
			{
				return "You are Not a Admin";
			}
		}
	
	else {
			return "invalid authtoken / login to perform the function";
		}
	}
	

	public String deleteByRouteid(String authtoken, long routeid) {

		UserProfileEntity checkLogin = userProfileRepository.findBySessionId(authtoken).get(0);

		if (checkLogin != null) 
		{
			if(checkLogin.getUserType().equals("A"))
			{

			RouteEntity routeToDelete = routeRepository.findById(routeid).get();

			if (routeToDelete != null) {
				routeRepository.deleteById(routeid);
				return "Route delete successfully";
			}

			else {
				return " Invalid routeid";
			}
			}
			else
			{
				return "You are Not a Admin";
			}

		}

		else {
			return "invalid authtoken / login to perform the function";

		}
	}

	@Override
	public String deleteByDriverid(String authtoken, long driverId) {

		UserProfileEntity checkLogin = userProfileRepository.findBySessionId(authtoken).get(0);

		if (checkLogin != null || authtoken != null)
		{
			if(checkLogin.getUserType().equals("A"))
			{

			DriverEntity driverToDelete = driverRepository.findById(driverId).get();
			if (driverToDelete != null) {
				driverRepository.deleteById(driverId);
				return "Driver details delete successfully";
			}

			else 
			{
				return "driverId is invalid";
			}
			}
			else
			{
				return "You are Not a Admin";
			}
			
			
		}

		else {
			return "invalid authtoken / login to perform the function";
		}
	}
	@Override
	public Object updateByVehicleId(String authtoken,Vehicle vehicle, long vehicleId)
	{
		//UserProfileEntity checklogin=userProfileRepository.findBySessionId(authtoken).get(0);
		UserProfileEntity checklogin=this.getUserUsingSessionId(authtoken);
		if(checklogin!=null ) 
		{	
			if(checklogin.getUserType().equals("A"))
			{

			VehicleEntity vehicletoupdate=vehicleRepository.findByVehicleId(vehicleId).get(0);
				if(vehicletoupdate!=null)
				{
					vehicletoupdate.setName(vehicle.getName()!=null?(vehicle.getName()):(vehicletoupdate.getName()));
					vehicletoupdate.setType(vehicle.getType()!=null?(vehicle.getType()):(vehicletoupdate.getType()));
					vehicletoupdate.setRegistrationNumber(vehicle.getRegistrationNumber()!=null?(vehicle.getRegistrationNumber()):(vehicletoupdate.getRegistrationNumber()));
					vehicletoupdate.setSeatingCapacity(vehicle.getSeatingCapacity()!=0?(vehicle.getSeatingCapacity()):(vehicletoupdate.getSeatingCapacity()));
					vehicletoupdate.setFarePerKm(vehicle.getFarePerKm()!=0?(vehicle.getFarePerKm()):(vehicletoupdate.getFarePerKm()));
					vehicletoupdate = vehicleRepository.save(vehicletoupdate);
					return VehicleUtils.convertVehicleEntityToVehicle(vehicletoupdate);
				}
				else
				{
					return "Wrong Vehicle Id";
				}
			}
			else
			{
				return "You are Not a Admin";
			}
		}
		return "Wrong Session -Id";
			
		}
			
	@Override
	public Object updateByRouteId(String authtoken,Route route, long routeId)
	{
		//UserProfileEntity checklogin=userProfileRepository.findBySessionId(authtoken).get(0);
		UserProfileEntity checklogin=this.getUserUsingSessionId(authtoken);
		if(checklogin!=null ) 
		{	
			if(checklogin.getUserType().equals("A"))
			{

		
			RouteEntity routetoupdate=routeRepository.findByRouteId(routeId);
			if(routetoupdate!=null)
			{
				routetoupdate.setSource(route.getSource()!=null?(route.getSource()):(routetoupdate.getSource()));
				routetoupdate.setDestination(route.getDestination()!=null?(route.getDestination()):(routetoupdate.getDestination()));
				routetoupdate.setDistance(route.getDistance()!=0?(route.getDistance()):(routetoupdate.getDistance()));
				routetoupdate.setTravelDuration(route.getTravelDuration()!=0?(route.getTravelDuration()):(routetoupdate.getTravelDuration()));
				routetoupdate = routeRepository.save(routetoupdate);
				return RouteUtils.convertRouteEntityToRoute(routetoupdate);
			}
			else
			{
			return "Wrong Route Id";
			}
			}
			else
			{
				return "You are Not a Admin";
			}
		}
		else { 
			return "Wrong Session-Id";
			
		}
	}
	@Override
	public Object updateByDriverId(String authtoken,Driver driver, long driverId)
	{
			//UserProfileEntity checklogin=userProfileRepository.findBySessionId(authtoken).get(0);
			UserProfileEntity checklogin=this.getUserUsingSessionId(authtoken);
			if(checklogin!=null ) 
			{	
				if(checklogin.getUserType().equals("A"))
				{
		
			DriverEntity drivertoupdate=driverRepository.
					findByDriverId(driverId).get(0);
					if(drivertoupdate!=null)
					{
						drivertoupdate.setName(driver.getName()!=null?(driver.getName()):(drivertoupdate.getName()));
						drivertoupdate.setStreet(driver.getStreet()!=null?(driver.getStreet()):(drivertoupdate.getStreet()));
						drivertoupdate.setLocation(driver.getLocation()!=null?(driver.getLocation()):(drivertoupdate.getLocation()));
						drivertoupdate.setCity(driver.getCity()!=null?(driver.getCity()):(drivertoupdate.getCity()));
						drivertoupdate.setState(driver.getState()!=null?(driver.getState()):(drivertoupdate.getState()));
						drivertoupdate.setPincode(driver.getPincode()!=null?(driver.getPincode()):(drivertoupdate.getPincode()));
						drivertoupdate.setMobileNo(driver.getMobileNo()!=null?(driver.getMobileNo()):(drivertoupdate.getMobileNo()));
						drivertoupdate.setLicenseNumber(driver.getLicenseNumber()!=null?(driver.getLicenseNumber()):(drivertoupdate.getLicenseNumber()));
						drivertoupdate = driverRepository.save(drivertoupdate);
						return DriverUtils.convertDriverEntityToDriver(drivertoupdate);
					}
					else
					{
						return "DriverId Not Found";
					}
				}
				else
				{
					return "You are Not a Admin";
				}
		}
		else 
		{ 
			return "Invalid SessionID"; 
		}
	}



	@Override
	public Vehicle getVehicleByVechicleid(long vehicleId) {
		return VehicleUtils.convertVehicleEntityToVehicle(vehicleRepository.findByVehicleId(vehicleId).get(0));
	}

	@Override
	public Route getRouteByRouteid(long routeId) {
		return RouteUtils.convertRouteEntityToRoute(routeRepository.findByRouteId(routeId));
	}

	@Override
	public List<Vehicle> getAllVehicles() {
		return VehicleUtils.convertVehicleEntityListToVehicleList(vehicleRepository.findAll());
	}

	@Override
	public List<Route> getAllRoutes() {
		return RouteUtils.convertRouteEntityListToRouteList(routeRepository.findAll());
	}
	public  UserProfileEntity getUserUsingSessionId(String authtoken)
	{
		if(authtoken==null||authtoken.equals(""))
		{
			return null;
		}
		UserProfileEntity userprofileentity=userProfileRepository.findBySessionId(authtoken).get(0);
		if(userprofileentity==null)
		{
			return null;
		}
		else
		{
			return userprofileentity;
		}
	}

	@Override
	public List<UserProfile> getUserProfilesByRoute(long routeId) {
		RouteEntity routeEntity = routeRepository.getByRouteId(routeId);
		if(routeEntity!=null) {
			List<ReservationEntity> reservationEntityList= reservationRepository.findByRouteEntity(routeEntity);
			List<UserProfileEntity> userProfileEntityList= reservationEntityList.stream().map((ele)->ele.getUserProfileEntity()).collect(Collectors.toList());
			List<UserProfile> userProfileList=UserProfileUtils.convertUserProfileEntityListToUserProfileList(userProfileEntityList);
			return userProfileList;
			
		}
		else {
			return null;
		}
	}

	@Override
	public List<UserProfile> getUserProfileOnDate(LocalDate date) 
	{
		List<ReservationEntity> reservationEntityList	= reservationRepository.findByBookingDate(date);
		List<UserProfileEntity> userProfileEntityList =reservationEntityList.stream().map((reservation)->reservation.getUserProfileEntity()).collect(Collectors.toList());
		List<UserProfile> userProfileList=UserProfileUtils.convertUserProfileEntityListToUserProfileList(userProfileEntityList);
		
		return userProfileList;
		
	}

	@Override
	public List<UserProfile> getUserProfileAfterDate(LocalDate date) {
		List<ReservationEntity> reservationEntityList	= reservationRepository.findByBookingDateGreaterThan(date);
		List<UserProfileEntity> userProfileEntityList =reservationEntityList.stream().map((reservation)->reservation.getUserProfileEntity()).collect(Collectors.toList());
		List<UserProfile> userProfileList=UserProfileUtils.convertUserProfileEntityListToUserProfileList(userProfileEntityList);
		
		return userProfileList;
	}

	@Override
	public List<UserProfile> getUserProfileBeforeDate(LocalDate date) {
		List<ReservationEntity> reservationEntityList	= reservationRepository.findByBookingDateLessThan(date);
		List<UserProfileEntity> userProfileEntityList =reservationEntityList.stream().map((reservation)->reservation.getUserProfileEntity()).collect(Collectors.toList());
		List<UserProfile> userProfileList=UserProfileUtils.convertUserProfileEntityListToUserProfileList(userProfileEntityList);
		
		return userProfileList;
	}

	@Override
	public Object allotDriver(String reservationId) 
	{
		ReservationEntity reservationEntity= reservationRepository.findById(Long.parseLong(reservationId)).get();
		if(reservationEntity!=null)
		{
			reservationEntity.setBookingStatus("Booked");
			reservationEntity.setBookingDate(LocalDate.now());
			return "Driver Alotted";
		}
		else
		{
			return "Error..";
		}
	}
}
