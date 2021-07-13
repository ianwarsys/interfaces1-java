package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalServices;

public class Program {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
		try (Scanner sc = new Scanner(System.in)) {
		
		System.out.println("Enter rental data");				
		System.out.print("Car model: ");
		String carModel = sc.nextLine();		
		System.out.print("Pickup (dd/MM/yyyy HH:mm): ");
        Date start = sdf.parse(sc.nextLine());     
        System.out.print("Rerturn (dd/MM/yyyy HH:mm): ");
        Date finish = sdf.parse(sc.nextLine());      
        
        CarRental carRental = new CarRental(start, finish, new Vehicle(carModel));
        
        System.out.print("Enter price per hour: ");
        double pricePerHour = sc.nextDouble();    
        System.out.print("Enter price per day: ");
        double pricePerDay = sc.nextDouble();
		
        RentalServices rentalService = new RentalServices(pricePerDay, pricePerHour, new BrazilTaxService());
        
        rentalService.processInvoice(carRental);
        
        System.out.println("INVOICE:");
        System.out.printf("Basic payment: %.2f \n", carRental.getInvoice().getBasicPayment());
        System.out.printf("Tax: %.2f \n", carRental.getInvoice().getTax());
        System.out.printf("Total payment: %.2f \n", carRental.getInvoice().getTotalPayment());
        
		} 
		catch(ParseException e) {
			System.out.println(e.getMessage());
		}
	    catch(InputMismatchException e) {
	    	System.out.println("Incorrect data!");
	    }
		
	}
}
