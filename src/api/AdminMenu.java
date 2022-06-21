package api;

import model.*;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class AdminMenu {

    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservation = ReservationService.getInstance();
    private static final AdminResource adminResource = AdminResource.getInstance();

    public static void adminMenu() {
        System.out.println("________________________________________");
        System.out.println("               Admin Menu               ");
        System.out.println("________________________________________");
        System.out.println("1. See all Customers.");
        System.out.println("2. See all Rooms.");
        System.out.println("3. See all Reservations.");
        System.out.println("4. Add a Room.");
        System.out.println("5. Back to Main Menu.");
        System.out.println("________________________________________");

    }
    public static void showAllCustomer() {
        Collection < Customer > customers = customerService.getAllCustomer();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            customerService.getAllCustomer().forEach(System.out::println);
        }
    }

    public static void showAllRooms() {
        Collection < IRoom > rooms = reservation.getAllRooms();

        if (rooms.isEmpty()) {
            System.out.println("No empty rooms found.");
        } else {
            reservation.getAllRooms().forEach(System.out::println);
        }
    }
    public static void createRoom() {
        Scanner scnr = new Scanner(System.in);
        System.out.println("Enter room number: ");
        String roomNumber = scnr.nextLine();

        Collection < IRoom > tempReservations = reservation.getAllRooms();
        for (IRoom reservation: tempReservations) {
            while (true) {
                if (reservation.getRoomNumber().equals(roomNumber)) {
                    System.out.println("Room number exists. Use another number.");
                    roomNumber = scnr.nextLine();
                } else {
                    break;
                }
            }
        }

        System.out.println("Enter nightly rate: ");
        Double roomCost = scnr.nextDouble();

        System.out.println("Enter room type: 1.SINGLE or 2.DOUBLE ");
        String tempType = scnr.next();

        RoomType roomType = roomChoice(tempType);

        Room room = new Room(roomNumber, roomCost, roomType);

        reservation.addRoom(room);

        System.out.println("Would you like to add another room? y/n ");
        String userInput = scnr.next();
        addNewRoom(userInput);
    }
    public static RoomType roomChoice(String tempType) {
        boolean keepRunning = true;
        RoomType roomType = null;
        Scanner scnr = new Scanner(System.in);
        while (keepRunning) {
            switch (tempType) {
                case "1":
                    roomType = RoomType.SINGLE;
                    keepRunning = false;
                    break;
                case "2":
                    roomType = RoomType.DOUBLE;
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid entry. Try again");
                    tempType = scnr.nextLine();
                    keepRunning = true;
            }
        }
        return roomType;
    }
    public static void addNewRoom(String selectionInput) {
        Scanner scnr = new Scanner(System.in);
        while (true) {
            if (Objects.equals(selectionInput, "y")) {
                AdminMenu.createRoom();
                break;
            } else if (Objects.equals(selectionInput, "n")) {
                break;
            } else {
                System.out.println("Enter (y/n): ");
            }
            selectionInput = scnr.next();
        }
    }
    public static void seeAllReservation() {
        adminResource.displayALlReservations();
    }

}