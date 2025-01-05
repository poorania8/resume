package busReservationSystem;
import java.util.*;
import java.util.Scanner;
import java.util.Random;

//import training.Class;
public class BusSystem {
	Scanner sc = new Scanner(System.in);
	
	ArrayList<City> cities = new ArrayList<City>();
	
	void printCities() {
		System.out.println("All cities");
		for(City city : cities) {
			System.out.println(city.name);
		}
	}
// 
	void addCity() {
		
		Random rand = new Random();
		City newCity = new City();
		System.out.println("Enter the name of the city you would like to add.");
		newCity.name = sc.nextLine();
		boolean cityExists = false;
		for(City city : cities) {
			if (city.name.equals(newCity.name)) {
				System.out.println("This city already exists.");
				cityExists = true;
				break;
			}
		}
		
		if(cityExists == false) {
			newCity.id = rand.nextInt(100);
			cities.add(newCity);
			System.out.println("The id of this city is: " + newCity.id);
			System.out.println("City has sucessfully been added.");
		}
	}
	
	City findCity(String cityName) {
		for(City city : cities) {
			if (cityName.equals(city.name)) {
				return city;
			}
		}
		return null;
	}
	
	City findCity(int id) {
		for(City city : cities) {
			if (id == city.id) {
				return city;
			}
		}
		return null;
	}
	
// -------------------------------------------------------------
	ArrayList<Bus> busses = new ArrayList<Bus>();
	
	void addBus() {
		Random rand = new Random();
		Bus newBus = new Bus();
		System.out.println("Enter the name of the bus.");
		newBus.name = sc.nextLine();
		
		printCities();
		System.out.println("Enter the name of the start city.");
		String cityName = sc.nextLine();
		newBus.startCity = findCity(cityName);
		
		printCities();
		System.out.println("Enter the name of the destination city.");
		cityName = sc.nextLine();
		newBus.destination = findCity(cityName);
		
		printCities();
		System.out.println("Enter the cities that are a part of the route. Enter the word STOP when you are done.");
		while(true) {
			System.out.println("Enter the name of the city you would like to add.");
			cityName = sc.nextLine();
			
			if(cityName.equals("STOP") || cityName.equals("Stop") || cityName.equals("stop")) {
				System.out.println("Stops sucessfully added.");
				break;
			}
			newBus.stops.add(findCity(cityName));
		}
		
		System.out.println("Enter the start time of the route. (eg. 10:00)");
		newBus.startTime = sc.nextLine();
		System.out.println("Enter the start date. (eg. 9/10/2024)");
		newBus.startDate = sc.nextLine();
		
		System.out.println("Enter the finish time of the route. (eg. 10:00)");
		newBus.finishTime = sc.nextLine();
		System.out.println("Enter the finish date. (eg. 9/10/2024)");
		newBus.finishDate = sc.nextLine();
		
		System.out.println("Enter the number of seats on the bus.");
		newBus.numSeats = sc.nextInt();
		newBus.availSeats = newBus.numSeats; // initial num of available seats are the same
		
		System.out.println("Enter the fee of the bus.");
		newBus.fee = sc.nextDouble();
		
		newBus.id = rand.nextInt(100);
		
		busses.add(newBus);
		System.out.println("The id of this bus is: " + newBus.id);
		System.out.println("Bus successfully added.");
		
	}
	
	Bus returnBus(int id) {
		for(Bus bus : busses) {
			if(id == bus.id) {
				return bus;
			}
		}
		return null;
	}
	
	void availableBusses(String startDate, City startCity, City destination) {
		for(Bus bus : busses) {
			if(bus.startDate.equals(startDate)) {
				if(bus.startCity.equals(startCity) && bus.destination.equals(destination)) {
					System.out.println("This bus starts at the starting city and ends at the destination");
					System.out.println("Bus Name     | Bus ID  |Num Available Seats");
					System.out.println(bus.name + "     |" + bus.id + "  |" + bus.availSeats + "|");
					bus.printRoute();
				}
				else if(bus.startCity.equals(startCity) && bus.containsStop(destination)) {
					System.out.println("This bus starts at the starting city and the destination city is one of the stops");
					System.out.println("Bus Name     | Bus ID  |Num Available Seats");
					System.out.println(bus.name + "     |" + bus.id + "  |" + bus.availSeats + "|");
					bus.printRoute();
				}
				else if(bus.containsStop(startCity) == true && bus.containsStop(destination) == true && (bus.stops.indexOf(destination) > bus.stops.indexOf(startCity))) {
					// both cities are on the way and the starting city is before the destination
					System.out.println("This bus stops at the cities during its route.");
					System.out.println("Bus Name     | Bus ID  |Num Available Seats");
					System.out.println(bus.name + "     |" + bus.id + "  |" + bus.availSeats + "|");
					bus.printRoute();
				}
				else if(bus.containsStop(startCity) == true && bus.destination.equals(destination)) {
					// the start city is a stop and the destination is the destination
					System.out.println("This bus stops at the start city and goes to the destination");
					System.out.println("Bus Name     | Bus ID  |Num Available Seats");
					System.out.println(bus.name + "     |" + bus.id + "  |" + bus.availSeats + "|");
					bus.printRoute();
				}
			}
		}
	}
// --------------------------------------------------------------
	
	
}
