package by.teplohova.tunnel;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class Train implements Runnable {
    public static final Logger LOGGER= LogManager.getLogger();

    private int trainId;
    private String direction;

    public Train(int trainId, String direction) {
        this.trainId = trainId;
        this.direction = direction;
        new Thread(this, String.valueOf(this.trainId));
    }

    @Override
    public void run() {
        Tunnel.RailRoad railRoad = Tunnel.getTunnel().getRailRoad(direction);
        comeInTunnel(railRoad);
        goThrough(railRoad);
        goOut(railRoad);
    }

    private void comeInTunnel(Tunnel.RailRoad railRoad) {
        System.out.println("Train " + this.trainId + " come in "+ railRoad.getName());

    }

    private void goThrough(Tunnel.RailRoad railRoad) {

        System.out.println("Train " + this.trainId + " go through " + railRoad.getName() + railRoad.getDirection());
        try {
            TimeUnit.MILLISECONDS.sleep(railRoad.getTimeGo());
        } catch (InterruptedException e) {
           LOGGER.log(Level.ERROR,"");
        }

    }

    private void goOut(Tunnel.RailRoad railRoad) {

        System.out.println("Train " + this.trainId + " go out");
        if (railRoad != null) {
            Tunnel.getTunnel().realiseRailRoad(railRoad);
        }
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainId=" + trainId +
                ", direction='" + direction + '\'' +
                '}';
    }
}
