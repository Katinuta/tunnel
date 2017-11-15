package by.teplohova.tunnel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Train implements Runnable {
    private int trainId;
    private String direction;

    public Train(int trainId,String direction) {
        this.trainId = trainId;
    this.direction=direction;
        new Thread(this, String.valueOf(this.trainId));
    }

    @Override
    public void run() {
        //comeInTunnel();
        goThrough();
        goOut();
    }

    private void comeInTunnel() {
        System.out.println("Train " + this.trainId + " come in ");
    }

    private void goThrough() {
        Tunnel.RailRoad railRoad = null;
        try {
            Tunnel tunnel=Tunnel.getTunnel();

            railRoad = Tunnel.getTunnel().getRailRoad(direction);

            System.out.println("Train " + this.trainId + " go through " + railRoad.getName()+ railRoad.getDirection());
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {

            if (railRoad != null) {
                Tunnel.getTunnel().realiseRailRoad(railRoad);
            }


        }

    }

    private void goOut() {

    //    System.out.println("Train " + this.trainId + " go out");
    }

}
