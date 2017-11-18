package by.teplohova.tunnel;

import by.teplohova.tunnel.parser.TunnelConfigSAXBuilder;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel {

    private RailRoad first;
    private RailRoad second;
    private static Tunnel tunnel;
    private Semaphore semaphore;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isExistTunnel = new AtomicBoolean(false);

    private Tunnel() {
        TunnelConfig config=new TunnelConfigSAXBuilder().buildTunnelConfig("data/configtunnel.xml");
        semaphore = new Semaphore(config.getCountTrain(), true);
        first = new RailRoad(config.getRailRoadNames().get(0),config.getTimeGoInRailRoad(),config.getCountTrainInDirection());
        second = new RailRoad(config.getRailRoadNames().get(1),config.getTimeGoInRailRoad(),config.getCountTrainInDirection());
    }

    public static Tunnel getTunnel() {

        if (!isExistTunnel.get()) {
            lock.lock();
            try {
                if (tunnel==null) {
                    tunnel = new Tunnel();
                    isExistTunnel.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return tunnel;
    }

    public RailRoad getRailRoad(String direction) {
        RailRoad railRoad = null;
        try {

            semaphore.acquire(1);
            while (railRoad == null) {

                if (first.getDirection() == null) {
                    if (first.getSemaphoreRoad().tryAcquire(1)) {
                        railRoad = first;
                        first.setDirection(direction);
                    }
                } else {
                    if (direction.equals(first.direction)) {
                        if (first.getSemaphoreRoad().tryAcquire(1)) {
                            railRoad = first;

                        }
                    }
                    if (second.getDirection() == null) {
                        if (second.getSemaphoreRoad().tryAcquire(1)) {
                            railRoad = second;
                            second.setDirection(direction);
                        }

                    } else {
                        if (direction.equals(second.direction)) {
                            if (second.getSemaphoreRoad().tryAcquire(1)) {
                                railRoad = second;

                            }
                        }
                    }

                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return railRoad;
    }

    public void realiseRailRoad(RailRoad road) {

        road.getSemaphoreRoad().release(1);
        road.direction = null;
        semaphore.release(1);

    }

    public class RailRoad {
        private String name;
        private String direction;
        private int timeGo;
        private Semaphore semaphoreRoad;

        public RailRoad() {
        }

        public RailRoad(String name,int timeGo, int countInDirection) {
            this.name = name;
            this.timeGo=timeGo;
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
}
