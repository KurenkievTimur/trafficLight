package com.kurenkievtimur;

import java.util.concurrent.BlockingQueue;

import static com.kurenkievtimur.Console.clearConsole;
import static com.kurenkievtimur.TrafficLightState.*;

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
                Thread.sleep(1000);
                seconds++;

                if (trafficLight.getState().equals(SYSTEM))
                    clearConsole();

                if (trafficLight.getState().equals(SYSTEM))
                    printSystemInfo();

                if (!trafficLight.getState().equals(ADD_ROAD) && !trafficLight.getState().equals(DELETE_ROAD))
                    openRoad();
            } catch (InterruptedException ignored) {}
        }
    }

    private void printSystemInfo() {
        System.out.printf("! %ds. have passed since system startup !\n", seconds);
        System.out.printf("! Number of roads: %d !\n", trafficLight.getSize());
        System.out.printf("! Interval: %d !\n\n", trafficLight.getInterval());

        for (Road road : trafficLight.getRoads()) {
            System.out.printf("%s will be %s for %ds.\u001B[0m\n", road.getName(), road.isOpen() ? "\u001B[32mopened" :
                    "\u001B[31mclosed", road.getSeconds());
        }

        System.out.println("\nPress \"Enter\" to open menu!");
    }

    private void openRoad() {
        BlockingQueue<Road> roads = trafficLight.getActiveRoads();
        int interval = trafficLight.getInterval();

        for (Road road : roads) {
            if (road.getSeconds() == 1) {
                roads.poll();
                road.setOpen(false);
                road.setSeconds(roads.size() <= 1 ? interval + 1 : interval * roads.size() + 1);
                roads.add(road);

                Road open = roads.peek();
                open.setOpen(true);
                open.setSeconds(interval + 1);

                break;
            }
        }

        for (Road road : roads) {
            int seconds = road.getSeconds();
            road.setSeconds(seconds == 1 ? seconds : seconds - 1);
        }
    }
}