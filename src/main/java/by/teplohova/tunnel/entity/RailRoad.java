package by.teplohova.tunnel.entity;

import java.util.concurrent.Semaphore;

public class RailRoad {
    private String name;
    private String direction;
    private int timeGo;
    private Semaphore semaphoreRoad;

    public RailRoad() {
    }

    public RailRoad(String name, int timeGo, int countInDirection) {
        this.name = name;
        this.timeGo = timeGo;
        semaphoreRoad = new Semaphore(countInDirection, true);
    }

    public String getName() {
        return name;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    public Semaphore getSemaphoreRoad() {
        return semaphoreRoad;
    }

    public int getTimeGo() {
        return timeGo;
    }


    @Override
    public String toString() {
        return "RailRoad{" +
                "name='" + name + '\'' +
                '}';
    }
}