package busReservationSystem;
import java.util.*;

public class Bus {
	String name;
	City startCity;
	City destination;
	ArrayList<City> stops = new ArrayList<City>();
	
	String startTime;
	String startDate;
	String finishTime;
	String finishDate;
	int numSeats;
	double fee;
	int id;
	
	int availSeats;
	
	boolean containsStop(City stop) {
		for(City city : stops) {
			if(city.equals(stop)) {
				return true;
			}
		}
		return false;
	}
	
	boolean containsRoute(City start, City finish) {
		if(startCity.equals(start) && destination.equals(finish)) {
			return true;
		}
		else if(startCity.equals(start) && containsStop(finish)) {
			return true;
		}
		else if(containsStop(start) == true && containsStop(finish) == true && (stops.indexOf(finish) > stops.indexOf(start))) {
			// both cities are on the way and the starting city is before the destination
			return true;
		}
		else if(containsStop(start) == true && destination.equals(finish)) {
			// the start city is a stop and the destination is the destination
			return true;
		}
		else {
			return false;
		}
	}
	
	void printRoute() {
		System.out.println("Start City: " + startCity.name);
		for(City city : stops) {
			System.out.println("Stop: " + city.name);
		}
		System.out.println("Destination: " + destination.name);
	}
	
	ArrayList<Ticket> tickets = new ArrayList<Ticket>();
}
