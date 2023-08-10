package com.kurenkievtimur;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import static com.kurenkievtimur.TrafficLightState.*;

public class TrafficLight {
    private int roads;
    private int interval;
    private TrafficLightState state = NOT_STARTED;
    private ArrayBlockingQueue<String> queue;

    private void setup(Scanner scanner) {
        System.out.println("Welcome to the traffic management system!");
        inputRoads(scanner);
        queue = new ArrayBlockingQueue<>(roads);

        inputInterval(scanner);

        Timer timer = new Timer(this);
        timer.start();
        this.state = MENU;

        clearConsole();
    }

    public void execute(Scanner scanner) {
        while (!state.equals(EXIT)) {
            switch (state) {
                case NOT_STARTED -> setup(scanner);
                case MENU -> choiceMenu(scanner);
            }
        }
        System.out.println("Bye!");
    }

    private void inputRoads(Scanner scanner) {
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
            else
                this.roads = roads;
        }
    }

    private void inputInterval(Scanner scanner) {
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
            else
                this.interval = interval;
        }
    }

    private void choiceMenu(Scanner scanner) {
        System.out.println("Menu:");
        System.out.println("1. Add");
        System.out.println("2. Delete");
        System.out.println("3. System");
        System.out.println("0. Quit");

        switch (scanner.nextLine()) {
            case "1" -> addRoad(scanner);
            case "2" -> deleteRoad(scanner);
            case "3" -> {
                state = SYSTEM;
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                    state = MENU;
                }
            }
            case "0" -> state = EXIT;
            default -> {
                System.out.println("Incorrect option");
                scanner.nextLine();
                clearConsole();
            }
        }

        clearConsole();
    }

    private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void clearConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) {}
    }

    private void addRoad(Scanner scanner) {
        System.out.print("Input road name: ");
        String input = scanner.nextLine();

        if (queue.offer(input))
            System.out.printf("%s added!\n", input);
        else
            System.out.println("Queue is full");

        if (scanner.hasNextLine())
            scanner.nextLine();
    }

    private void deleteRoad(Scanner scanner) {
        String element = queue.poll();
        if (element != null)
            System.out.printf("%s deleted!\n", element);
        else
            System.out.println("Queue is empty");

        if (scanner.hasNextLine())
            scanner.nextLine();
    }

    public int getRoads() {
        return roads;
    }

    public int getInterval() {
        return interval;
    }

    public TrafficLightState getState() {
        return state;
    }

    public ArrayBlockingQueue<String> getQueue() {
        return queue;
    }
}