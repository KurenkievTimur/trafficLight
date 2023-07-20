package com.kurenkievtimur;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        printMenu();
    }

    public static void printMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the traffic management system!");

        System.out.print("Input the number of roads: ");
        int roads = scanner.nextInt();

        System.out.print("Input the interval: ");
        int interval = scanner.nextInt();

        boolean isExit = false;
        while (!isExit) {
            System.out.println("Menu:");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. System");
            System.out.println("0. Quit");

            switch (scanner.nextInt()) {
                case 1 -> System.out.println("Road added");
                case 2 -> System.out.println("Road deleted");
                case 3 -> System.out.println("System opened");
                case 0 -> isExit = true;
            }
        }

        System.out.println("Bye!");
    }
}
