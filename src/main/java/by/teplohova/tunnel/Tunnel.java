package by.teplohova.tunnel;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel {

    private RailRoad first;
    private RailRoad second;
    private static Tunnel tunnel;
    //private int poolSize;
    private Semaphore semaphore;
    private static ReentrantLock lock=new ReentrantLock();
    private static AtomicBoolean isExistTunnel=new AtomicBoolean(false);

    private Tunnel(){
            semaphore=new Semaphore(3,true);
            first=new RailRoad("first",true);
            second=new RailRoad("second",true);
    }

    public static Tunnel getTunnel(){

      if(isExistTunnel.get()==false){
          lock.lock();
          try{
              if(isExistTunnel.get()==false) {
                  tunnel = new Tunnel();
                  isExistTunnel.set(true);
              }
          }finally {
              lock.unlock();
          }
      }
        System.out.println(isExistTunnel);
        return tunnel;
    }
    public RailRoad getRailRoad(){
        RailRoad railRoad=null;
        try {
                semaphore.acquire(1);
                if(second.free){
                    second.setFree(false);
                    railRoad=second;
               //     railRoad.getLockRoad().lock();
                    System.out.println(railRoad+" " +Thread.currentThread().getName());
                }else               if(first.isFree()){
                    first.setFree(false);
                    railRoad=first;
                    //railRoad.getLockRoad().lock();
                    System.out.println(railRoad+" " +Thread.currentThread().getName());
                }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        railRoad.getLockRoad().lock();
        return railRoad;
    }
    public void realiseRailRoad(RailRoad road){
        road.setFree(true);
     road.getLockRoad().unlock();
        semaphore.release(1);

    }
   public class RailRoad
    {
        private String name;
        private boolean free;
//        private TimeUnit timeGo;
        private ReentrantLock lockRoad;

        public RailRoad() {
        }

        public RailRoad(String name, boolean free) {
            this.name = name;
            this.free = free;
            lockRoad=new ReentrantLock();
        }

        public String getName() {
            return name;
        }

        public boolean isFree() {
            return free;
        }

        public void setFree(boolean free) {
            this.free = free;
        }

        public ReentrantLock getLockRoad() {
            return lockRoad;
        }

        public void setLockRoad(ReentrantLock lockRoad) {
            this.lockRoad = lockRoad;
        }

        @Override
        public String toString() {
            return "RailRoad{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
