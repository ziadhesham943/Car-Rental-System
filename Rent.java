package oop.project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Rent {

    private String renter_name;
    private int national_ID;
    private Car rented_car;
    private String start_date;
    private String end_date;
    private double total_price;

    public Rent(String renter_name, int national_ID, Car rented_car, String start_date, String end_date) {
        this.renter_name = renter_name;
        this.national_ID = national_ID;
        this.rented_car = rented_car;
        this.start_date = start_date;
        this.end_date = end_date;
        addTotal_price();
    }

    public String getRenter_name() {
        return renter_name;
    }

    public int getNational_ID() {
        return national_ID;
    }

    public Car getRented_car() {
        return rented_car;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public void addTotal_price() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate start_date = LocalDate.parse(getStart_date(), formatter);
        LocalDate end_date = LocalDate.parse(getEnd_date(), formatter);
        double price = getRented_car().getPrice();
        long days = ChronoUnit.DAYS.between(start_date, end_date);
        double total_price = days * price;
        setTotal_price(total_price);
    }

    public void extend_rental_time(String newEndDate) {
        setEnd_date(newEndDate);
        addTotal_price();
    }
}
