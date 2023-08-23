package com.kurenkievtimur;

import java.util.Objects;

public class Road {
    private String name;
    private int seconds;
    private boolean open;

    public Road(String name, int seconds, boolean open) {
        this.name = name;
        this.seconds = seconds;
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Road road = (Road) o;
        return seconds == road.seconds && open == road.open && Objects.equals(name, road.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, seconds, open);
    }
}
