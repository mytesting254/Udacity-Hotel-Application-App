package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static final AdminResource service = new AdminResource();

    private final CustomerService customerService = CustomerService.getInstance();

    private final ReservationService reservationService = ReservationService.getInstance();
    private AdminResource() {}

    public static AdminResource getInstance() {
        return service;
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(List < IRoom > rooms) {
        rooms.forEach(reservationService::addRoom);
    }

    public Collection < IRoom > getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection < Customer > getAllCustomers() {
        return customerService.getAllCustomer();
    }

    public void displayALlReservations() {
        reservationService.printAllReservation();
    }
}