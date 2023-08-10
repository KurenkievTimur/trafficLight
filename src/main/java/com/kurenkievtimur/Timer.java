package com.kurenkievtimur;

import java.io.IOException;

import static com.kurenkievtimur.TrafficLightState.EXIT;
import static com.kurenkievtimur.TrafficLightState.SYSTEM;

public class Timer extends Thread {
    private int seconds = 0;
    private final TrafficLight trafficLight;

    public Timer(TrafficLight trafficLight) {
        super("QueueThread");
        setDaemon(true);
        this.trafficLight = trafficLight;
    }

    @Override
    public void run() {
        while (!trafficLight.getState().equals(EXIT)) {
            try {
                if (trafficLight.getState().equals(SYSTEM))
                    printSystemInfo();

                Thread.sleep(1000);
                seconds++;

                if (trafficLight.getState().equals(SYSTEM))
                    clearConsole();
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void printSystemInfo() {
        System.out.printf("! %ds. have passed since system startup !\n", seconds);
        System.out.printf("! Number of roads: %d !\n", trafficLight.getRoads());
        System.out.printf("! Interval: %d !\n", trafficLight.getInterval());

        for (String element : trafficLight.getQueue()) {
            System.out.println(element);
        }

        System.out.println("Press \"Enter\" to open menu!");
    }

    private void clearConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) {
        }
    }
}