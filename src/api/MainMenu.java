package api;

import model.IRoom;
import model.Reservation;
import service.ReservationService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final ReservationService reservation = ReservationService.getInstance();
    private static final String dateRegex = "^(1[0-2]|0[1-9])/(3[01]|[12]\\d|0[1-9])/\\d{4}$";
    private static final Pattern datePattern = Pattern.compile(dateRegex);
    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    private static final String emailRegex = "^(.+)@(.+).com$";
    private static final Pattern pattern = Pattern.compile(emailRegex);
    private static final Scanner scnr = new Scanner(System.in);
    public static void mainMenu() {
        System.out.println("________________________________________");
        System.out.println("             Main Menu                  ");
        System.out.println("________________________________________");
        System.out.println("1. Find and reserve a room.");
        System.out.println("2. See my reservations.");
        System.out.println("3. Create an account.");
        System.out.println("4. Admin.");
        System.out.println("5. Exit.");
        System.out.println("________________________________________");
        System.out.println("Enter your selection: ");
    }
    public static void createAccount() {
        try {
            System.out.println("Enter Email format: name@domain.com");
            final String email = scnr.nextLine();

            System.out.println("First Name:");
            final String firstName = scnr.nextLine();

            System.out.println("Last Name:");
            final String lastName = scnr.nextLine();

            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");

        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
    public static void reserveARoom() throws ParseException {
       Date userCheckIn = format.parse(checkIn());
       Date userCheckOut = format.parse(checkOut());

        while (true){
            if (userCheckOut.before(userCheckIn)) {
                System.out.println("Check-Out cannot be before Check-IN");
                reserveARoom();
            }else {
                break;
            }
        }

       AdminMenu.showAllRooms();

        if(!Objects.equals(userBooking(), "n")) {
            if (!Objects.equals(userAccount(), "n")){
                String userEmail = email();
                IRoom userRoom = userRoom();
                hotelResource.bookARoom(userEmail, userRoom, userCheckIn, userCheckOut);
                MainMenu.printUserReservation(userEmail);
            }
        }
    }
    public static String userBooking() {
        System.out.println("Would you like to book a room? (y/n) ");
        String userInput = scnr.nextLine();
        while (true) {
            if (Objects.equals(userInput, "n")) {
                break;
            } else if (Objects.equals(userInput, "y")) {
                break;
            } else {
                System.out.println("Invalid input. Enter (y/n) ");
            }
            userInput = scnr.nextLine();
        }
        return userInput;
    }
    public static String userAccount() {
        System.out.println("Do you have an account with us? (y/n) ");
        String account = scnr.nextLine();
        while (true) {
            if (Objects.equals(account, "n")) {
                System.out.println("Please create and account first: ");
                break;
            } else if (Objects.equals(account, "y")) {
                break;
            } else {
                System.out.println("Invalid input. Enter (y/n) ");
            }
            account = scnr.nextLine();
        }
        return account;
    }
    public static String email() {
        System.out.println("Enter Email format: name@domain.com ");
        String userEmail = scnr.nextLine();
        while(true) {
            if (!pattern.matcher(userEmail).matches()) {
                System.out.println("Invalid email address! Email format: name@domain.com ");
                userEmail = scnr.nextLine();
            }
            else {
                break;
            }
        }
        return userEmail;
    }
    public static String roomValidation(){
        System.out.println("What room would you like to reserve? ");
        String userRoom = scnr.nextLine();

        Collection<Reservation> tempReservations = reservation.getTotalReservations();
        for(Reservation reservation: tempReservations){
            while (true) {

                if (userRoom.equals(reservation.getRoom().getRoomNumber())) {
                    System.out.println("Room is already book. Try another");
                    userRoom = scnr.nextLine();
                }
                else {
                    break;
                }
            }
        }
        return userRoom;
    }
    public static IRoom userRoom() {
        return hotelResource.getRoom(roomValidation());
    }
    public static String checkIn(){
        System.out.println("Enter Check-In Date mm/dd/yyyy: ");
        String checkIn = scnr.nextLine();
        while(true) {
            if (!datePattern.matcher(checkIn).matches()) {
                System.out.println("Invalid date format. Enter mm/dd/yyyy: ");
                checkIn = scnr.nextLine();
            }
            else {
                break;
            }
        }
        return checkIn;
    }
    public static String checkOut(){
        System.out.println("Enter Check-Out Date mm/dd/yyyy: ");
        String checkOut = scnr.nextLine();
        while(true) {
            if (!datePattern.matcher(checkOut).matches()) {
                System.out.println("Invalid date format. Enter mm/dd/yyyy: ");
                checkOut = scnr.nextLine();
            }
            else {
                break;
            }
        }
        return checkOut;
    }
    public static void printUserReservation(String email){
        Collection<Reservation> tempReserve = hotelResource.getCustomersReservations(email);
        if (tempReserve.isEmpty()){
            System.out.println("Sorry No rooms are available under this email.");
        }
        else for (Reservation reserve : tempReserve){
            System.out.println("____________________________________________");
            System.out.println("Customer: " + reserve.getCustomer());
            System.out.println("Check-In: " + reserve.getCheckInDate());
            System.out.println("Check-Out: " + reserve.getCheckOutDate());
            System.out.println(reserve.getRoom());
            System.out.println("____________________________________________");
        }
    }
}