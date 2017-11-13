package by.teplohova.tunnel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Train implements Runnable {
    private int trainId;
    private ReentrantLock lock;

    public Train(int trainId) {
        this.trainId = trainId;
        lock=new ReentrantLock();

        new Thread(this,String.valueOf(this.trainId));
    }

    @Override
    public void run() {
comeInTunnel();
goThrough();
goOut();
    }

    private void comeInTunnel(){
    System.out.println("Train "+this.trainId+ " come in ");
    }
    private void goThrough(){
        Tunnel.RailRoad railRoad=null;
       try{
         //  lock.lock();
           railRoad=Tunnel.getTunnel().getRailRoad();
          // railRoad.getLockRoad().lock();
           System.out.println("Train "+this.trainId+ " go through " +railRoad.getName());
           try {
               TimeUnit.MILLISECONDS.sleep(10);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       } finally {
           Tunnel.getTunnel().realiseRailRoad(railRoad);
           //railRoad.getLockRoad().unlock();
          // lock.unlock();
       }

    }

    private void goOut(){

        System.out.println("Train "+this.trainId+ " go out");
    }

}
