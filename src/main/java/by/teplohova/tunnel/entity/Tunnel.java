package by.teplohova.tunnel.entity;

import by.teplohova.tunnel.parser.TunnelConfigSAXBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel {

    private final static Logger LOGGER = LogManager.getLogger();
    private static Tunnel tunnel;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isExistTunnel = new AtomicBoolean(false);
    private ArrayList<RailRoad> listRoads;
    private Semaphore semaphore;

    private Tunnel() {
        TunnelConfig config = new TunnelConfigSAXBuilder().buildTunnelConfig("data/configtunnel.xml");
        semaphore = new Semaphore(config.getCountTrain(), true);
        listRoads = new ArrayList<>();
        listRoads.add(new RailRoad(config.getRailRoadNames().get(0), config.getTimeGoInRailRoad(), config.getCountTrainInDirection()));
        listRoads.add(new RailRoad(config.getRailRoadNames().get(1), config.getTimeGoInRailRoad(), config.getCountTrainInDirection()));

    }

    public static Tunnel getTunnel() {

        if (!isExistTunnel.get()) {
            lock.lock();
            try {
                if (tunnel == null) {
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
                for (RailRoad road : listRoads) {

                    if (road.getDirection() == null) {
                        if (road.getSemaphoreRoad().tryAcquire(1)) {
                            railRoad = road;
                            road.setDirection(direction);
                        }
                    } else {
                        if (direction.equals(road.getDirection())) {
                            if (road.getSemaphoreRoad().tryAcquire(1)) {
                                railRoad = road;
                            }
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
           LOGGER.log(Level.ERROR,"Thread "+ Thread.currentThread()+" is interrupted" );
        }
        return railRoad;
    }

    public void realiseRailRoad(RailRoad road) {

        road.getSemaphoreRoad().release(1);
        road.setDirection(null);
        semaphore.release(1);

    }


}
