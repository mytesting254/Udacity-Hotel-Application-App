package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final ReservationService service = new ReservationService();

    private static final int adjustDate = 7;
    private final Map < String, IRoom > rooms = new HashMap < > ();

    private final Map < String, Collection < Reservation >> reservations = new HashMap < > ();
    private ReservationService() {}

    public static ReservationService getInstance() {
        return service;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);

        Collection < Reservation > userReservation = getCustomerReservation(customer);

        if (userReservation == null) {
            userReservation = new LinkedList < > ();
        }

        userReservation.add(reservation);
        reservations.put(customer.getEmail(), userReservation);

        return reservation;
    }
    public Collection < IRoom > getAllRooms() {
        return rooms.values();
    }
    public Collection < IRoom > findRooms(Date checkInDate, Date checkOutDate) {
        //TODO:
        return null;
    }
    public Collection < Reservation > getCustomerReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }
    public void printAllReservation() {
        final Collection < Reservation > reservations = getTotalReservations();

        if (reservations.isEmpty()) {
            System.out.println("Sorry :( No rooms are available on reserve.");
        } else
            for (Reservation reservation: reservations) {
                System.out.println("____________________________________________");
                System.out.println("Your Current Reservation is: ");
                System.out.println("Customer: " + reservation.getCustomer());
                System.out.println("Check-In: " + reservation.getCheckInDate());
                System.out.println("Check-Out: " + reservation.getCheckOutDate());
                System.out.println(reservation.getRoom());
                System.out.println("____________________________________________");
            }
    }

    public Collection < Reservation > getTotalReservations() {
        final Collection < Reservation > totalReservation = new LinkedList < > ();

        for (Collection < Reservation > reservations: reservations.values()) {
            totalReservation.addAll(reservations);
        }
        return totalReservation;
    }

    public Date addDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, adjustDate);
        return calendar.getTime();
    }

    private boolean overlapsDays(Reservation reservation, Date checkInDate, Date checkOutDate) {
        return checkInDate.before(reservation.getCheckOutDate()) &&
                checkOutDate.after(reservation.getCheckInDate());
    }

    private Collection < IRoom > availableRooms(Date checkInDate, Date checkOutDate) {
        return null;
    }
    public Collection < IRoom > backUpRoom(Date checkInDate, Date checkOutDate) {
        return availableRooms(addDays(checkInDate), addDays(checkOutDate));
    }

}