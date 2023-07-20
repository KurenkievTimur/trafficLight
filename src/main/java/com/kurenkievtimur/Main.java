package com.kurenkievtimur;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        printMenu();
    }

    public static void printMenu() {
        Scanner scanner = new Scanner(System.in);

        inputTrafficLight(scanner);
        choiceMenu(scanner);

        System.out.println("Bye!");
    }

    public static void inputTrafficLight(Scanner scanner) {
        System.out.println("Welcome to the traffic management system!");

        System.out.print("Input the number of roads: ");
        int roads;

        boolean isValidRoads = false;
        while (!isValidRoads) {
            String input = scanner.nextLine();
            if (!isNumber(input)) {
                System.out.print("Error! Incorrect Input. Try again: ");
                continue;
            }

            roads = Integer.parseInt(input);

            isValidRoads = roads > 0;
            if (!isValidRoads)
                System.out.print("Error! Incorrect Input. Try again: ");
        }

        System.out.print("Input the interval: ");
        int interval;

        boolean isValidInterval = false;
        while (!isValidInterval) {
            String input = scanner.nextLine();
            if (!isNumber(input)) {
                System.out.print("Error! Incorrect Input. Try again: ");
                continue;
            }

            interval = Integer.parseInt(input);

            isValidInterval = interval > 0;
            if (!isValidInterval)
                System.out.print("Error! Incorrect Input. Try again: ");
        }

        clearConsole();
    }

    public static boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static void choiceMenu(Scanner scanner) {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("Menu:");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. System");
            System.out.println("0. Quit");

            switch (scanner.nextLine()) {
                case "1" -> System.out.println("Road added");
                case "2" -> System.out.println("Road deleted");
                case "3" -> System.out.println("System opened");
                case "0" -> isExit = true;
                default -> {
                    System.out.println("Incorrect option");
                    scanner.nextLine();
                    clearConsole();
                    continue;
                }
            }

            if (!isExit) {
                scanner.nextLine();
                clearConsole();
            }
        }
    }

    public static void clearConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) {}
    }
}