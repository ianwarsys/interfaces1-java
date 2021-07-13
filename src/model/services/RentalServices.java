package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalServices {

	private Double pricePerDay;
	private Double pricePerHour;

	private BrazilTaxService taxService;

	public RentalServices(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}

	public void processInvoice(CarRental carRental) {
		long time1 = carRental.getStart().getTime();
		long time2 = carRental.getFinish().getTime();

		double hours = (double) (time2 - time1) / 1000 / 60 / 60;

		double basicPayment;
		if (hours < 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;

		} else {
			double days = hours / 24;
			basicPayment = Math.ceil(days) * pricePerDay;
			
		}

		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
}
