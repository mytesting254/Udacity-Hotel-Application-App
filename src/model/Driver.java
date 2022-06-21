package model;

import api.AdminMenu;
import api.MainMenu;
import com.sun.tools.javac.Main;
import service.CustomerService;
import service.ReservationService;

import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        boolean keepRunning = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (keepRunning) {
                try {
                    MainMenu.mainMenu();
                    String userInput = scanner.nextLine();
                    switch (userInput) {
                        case "1" ->
                            // find and reserve a room
                                MainMenu.reserveARoom();
                        case "2" ->
                            // see reservation
                                MainMenu.printUserReservation(MainMenu.email());
                        case "3" ->
                            // create an account
                                MainMenu.createAccount();
                        case "4" -> {
                            // display admin menu
                            AdminMenu.adminMenu();
                            boolean runAdmin = true;
                            while (runAdmin) {
                                try {
                                    userInput = scanner.nextLine();
                                    switch (userInput) {
                                        case "1" -> {
                                            // see all customers
                                            AdminMenu.showAllCustomer();
                                            AdminMenu.adminMenu();
                                        }
                                        case "2" -> {
                                            // see all rooms
                                            AdminMenu.showAllRooms();
                                            AdminMenu.adminMenu();
                                        }
                                        case "3" -> {
                                            // see all reservations
                                            AdminMenu.seeAllReservation();
                                            AdminMenu.adminMenu();
                                        }
                                        case "4" -> {
                                            // add a room
                                            AdminMenu.createRoom();
                                            AdminMenu.adminMenu();
                                        }
                                        case "5" ->
                                            // back to main menu
                                                runAdmin = false;
                                        default -> System.out.println("Invalid entry. Try again!");
                                    }
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        case "5" ->
                            // exit the entire menu
                                keepRunning = false;
                        default -> System.out.println("Invalid entry. Try again!");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}