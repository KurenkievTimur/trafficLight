package com.kurenkievtimur;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.kurenkievtimur.Console.clearConsole;
import static com.kurenkievtimur.TrafficLightState.*;

public class TrafficLight {
    private int size;
    private int interval;
    private TrafficLightState state = NOT_STARTED;
    private BlockingQueue<Road> roads;
    private BlockingQueue<Road> activeRoads;

    private void setup(Scanner scanner) {
        System.out.println("Welcome to the traffic management system!");
        inputRoads(scanner);
        roads = new ArrayBlockingQueue<>(size);
        activeRoads = new ArrayBlockingQueue<>(size);

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
                this.size = roads;
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
            case "1" -> {
                state = ADD_ROAD;
                addRoad(scanner);
                state = MENU;
            }
            case "2" -> {
                state = DELETE_ROAD;
                deleteRoad(scanner);
                state = MENU;
            }
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

    private void addRoad(Scanner scanner) {
        System.out.print("Input road name: ");
        String input = scanner.nextLine();

        try {
            Road activeRoad = activeRoads.peek();

            Road road;
            if (activeRoads.size() == 0) {
                road = new Road(input, interval, true);
            } else {
                int seconds = activeRoads.size() * interval - Math.abs(activeRoad.getSeconds() - interval);
                road = new Road(input, seconds, false);
            }

            if (roads.add(road)) {
                activeRoads.add(road);
                System.out.printf("%s added!\n", input);
            }
        } catch (IllegalStateException ignored) {
            System.out.println("Queue is full");
        }

        if (scanner.hasNextLine())
            scanner.nextLine();
    }

    private void deleteRoad(Scanner scanner) {
        try {
            Road road = roads.remove();

            Iterator<Road> iterator = activeRoads.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Road next = iterator.next();

                if (index >= 2 && road.isOpen())
                    next.setSeconds(next.getSeconds() - 3);

                index++;
            }

            if (activeRoads.remove(road))
                System.out.printf("%s deleted!\n", road.getName());
        } catch (NoSuchElementException ignored) {
            System.out.println("Queue is empty");
        }

        if (scanner.hasNextLine())
            scanner.nextLine();
    }

    public int getSize() {
        return size;
    }

    public int getInterval() {
        return interval;
    }

    public TrafficLightState getState() {
        return state;
    }

    public BlockingQueue<Road> getRoads() {
        return roads;
    }

    public BlockingQueue<Road> getActiveRoads() {
        return activeRoads;
    }
}