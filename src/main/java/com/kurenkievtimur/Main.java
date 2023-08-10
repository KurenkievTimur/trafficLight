package com.kurenkievtimur;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TrafficLight trafficLight = new TrafficLight();
            trafficLight.execute(scanner);
        }
    }
}