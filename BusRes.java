package busReservationSystem;

import java.util.Random;
import java.util.Scanner;

public class BusRes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		BusSystem busSystem = new BusSystem();
		
		int menuSelect = 0;
		
		while(menuSelect != 9) {
			String [] menu  = {"1. Add City",
								"2. Delete City",
								"3. Show Cities",
								"4. Add bus",
								"5. Delete Bus",
								"6. Show Available Busses",
								"7. Book Bus Tickets",
								"8. Show Ticket",
								"9. Quit Application"};
			

			for (int i = 0; i < menu.length; ++i) {
				System.out.println(menu[i]);
			}

			menuSelect = sc.nextInt();
		// 1. Add a city
			if(menuSelect == 1) {
				busSystem.addCity();
			}
		//2. Delete a city
			if(menuSelect == 2) {
				System.out.println("Enter the ID of the city you would like to remove.");
				int removeId = 0;
				removeId = sc.nextInt();
				boolean removed = false;
				if(busSystem.findCity(removeId) == null) {
					System.out.println("There are no cities matching this ID.");
				}
				else {
					busSystem.cities.remove(busSystem.findCity(removeId));
					System.out.println("City successfully removed.");
				}
			}
		//3. Show cities
			if(menuSelect == 3) {
				System.out.println("Name        |ID       ");
				for(City city : busSystem.cities) {
					System.out.println(city.name + "        |" + city.id);
				}
			}
		// 4. Create bus 
			if(menuSelect == 4) {
				busSystem.addBus();
			}
		// 5. Delete Bus
			if(menuSelect == 5) {
				System.out.println("Enter the id of the bus you would like to remove.");
				int findId = sc.nextInt();
				if(busSystem.returnBus(findId) != null) {
					busSystem.busses.remove(busSystem.returnBus(findId));
					System.out.println("Bus successfully removed.");
				}
				else {
					System.out.println("Bus not found.");
				}
				
				
			}
		//6. Show available busses
			if(menuSelect == 6) {
				System.out.println("Enter the start date.");
				String startDate = sc.nextLine();
				startDate = sc.nextLine();
				System.out.println("Enter the name of the starting city.");
				String cityName = sc.nextLine();
				City startCity = busSystem.findCity(cityName);
				System.out.println("Enter the name of the destination city.");
				cityName = sc.nextLine();
				City destination = busSystem.findCity(cityName);
				
				busSystem.availableBusses(startDate, startCity, destination);
			}
		// 7. Book bus tickets
			if(menuSelect == 7) {
				System.out.println("Enter the id of the bus you would like to book tickets for.");
				int findId = sc.nextInt();
				Bus bookBus = busSystem.returnBus(findId);
				System.out.println("Enter the number of passengers you would like to get tickets for.");
				int numPassengers = sc.nextInt();
				
				if(bookBus == null) {
					System.out.println("This bus does not exist.");
				}
				
				else { // bus exists
					bookBus.printRoute();
					System.out.println("Enter the name of the starting city.");
					String cityNameStart = sc.nextLine();
					cityNameStart = sc.nextLine();
					City start = busSystem.findCity(cityNameStart);
					
					System.out.println("Enter the name of the destination city.");
					String cityNameFin = sc.nextLine();
					City finish = busSystem.findCity(cityNameFin);
					
					if(numPassengers <= bookBus.availSeats) { // seats ARE available
						if(bookBus.containsRoute(start, finish) == true) { // route IS correct
							double totalFare =  bookBus.fee * numPassengers;
							System.out.println("The total fare for all passengers is: " + totalFare);
							
							for(int i = 0 ; i < numPassengers; ++i) {
								Ticket newTick = new Ticket();
								Random rand = new Random();
								newTick.id = rand.nextInt(100);
								newTick.bus = bookBus;
								newTick.fare = bookBus.fee;
								
								Passenger newPassenger = new Passenger();
								System.out.println("Enter the name of the new passenger.");
								newPassenger.name = sc.nextLine();
								System.out.println("Enter the age of the passenger.");
								newPassenger.age = sc.nextInt();
								System.out.println("Enter the gender of the passenger.");
								newPassenger.gender = sc.nextLine();
								newPassenger.gender = sc.nextLine();
								
								newTick.passenger = newPassenger;
								
								bookBus.tickets.add(newTick);
								System.out.println("The id of this ticket is: " + newTick.id);
								-- bookBus.availSeats;
							}
						}
						else { 
							System.out.println("The bus selected will not take the desired route.");
						}
					}
					else {
						System.out.println("There are no seats available on this bus.");
					}
				}				
				
			}
		// 8. Show ticket
			if(menuSelect == 8) {
				System.out.println("Enter the ID of the ticket you would like to find.");
				int findID = sc.nextInt();
				for(Bus bus : busSystem.busses) {
					for(Ticket tick : bus.tickets) {
						if(tick.id == findID) {
							System.out.println("Ticket ID: " + tick.id);
							System.out.println("Bus Information");
							System.out.println("----------------");
							System.out.println("Bus Name     | Bus ID  |Num Available Seats");
							System.out.println(bus.name + "     |" + bus.id + "  |" + bus.availSeats + "|");
							System.out.println("Route");
							System.out.println("----------------");
							bus.printRoute();
							System.out.println("Passenger Information");
							System.out.println("----------------------");
							System.out.println("Passenger Name    |Age   |Gender  |Fare   |");
							System.out.println(tick.passenger.name + "   |" + tick.passenger.age + "   |" + tick.passenger.gender+ "   |" + tick.fare);
						}
					}
				}
			}
		}
		
		

	}

}
