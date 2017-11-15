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
            semaphore=new Semaphore(5,true);
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
        return tunnel;
    }
    public RailRoad getRailRoad(String direction){
        RailRoad railRoad=null;
 try {

          semaphore.acquire(1);
     System.out.println(Thread.currentThread().getName()+"get permission");
              while(railRoad==null){
                  System.out.println(direction.equals(first.direction));
                  if(direction.equals(first.direction)){
                     if(first.getSemaphoreRoad().tryAcquire(1)){
                          railRoad=first;

                      }
                  }else  if(direction.equals(second.direction)){
                      if(second.getSemaphoreRoad().tryAcquire(1)){
                          railRoad=second;

                      }
                  }else

                    if(second.semaphoreRoad.tryAcquire(1)) {
                        railRoad = second;
                        second.direction=direction;


                    }else              if(first.getSemaphoreRoad().tryAcquire(1)){
                        railRoad=first;
                        first.direction=direction;
                    }
                }


        } catch (InterruptedException e) {
            e.printStackTrace();
     //   }
//        finally {
//     if(semaphore.availablePermits()==0){
//         System.out.println(Thread.currentThread().getName()+"stop");
//         semaphore.release();
//     }
    }
      //  semaphore.release(1);
        return railRoad;
    }
    public void realiseRailRoad(RailRoad road){
        //road.setFree(true);

       road.getSemaphoreRoad().release(1);
     //  System.out.println(semaphore.availablePermits());
      System.out.println(Thread.currentThread().getName()+"ggive ");
      road.direction=null;
        semaphore.release(1);

    }
   public class RailRoad
    {
        private String name;
        private boolean free;
        private TimeUnit timeGo;
        private String direction;

        private Semaphore semaphoreRoad;

        public RailRoad() {
        }

        public RailRoad(String name, boolean free) {
            this.name = name;
            this.free = free;
           semaphoreRoad=new Semaphore(2,true);
         //  timeGo=TimeUnit.MILLISECONDS.sleep(10);
        }

        public String getName() {
            return name;
        }

        public boolean isFree() {
            return free;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public void setFree(boolean free) {
            this.free = free;
        }

        public Semaphore getSemaphoreRoad() {
            return semaphoreRoad;
        }

        public void setSemaphoreRoad(Semaphore semaphoreRoad) {
            this.semaphoreRoad = semaphoreRoad;
        }

        @Override
        public String toString() {
            return "RailRoad{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
