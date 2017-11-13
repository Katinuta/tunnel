import by.teplohova.tunnel.Train;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String [] args){
        ExecutorService executor= Executors.newFixedThreadPool(3);
        executor.execute(new Train(1));
      executor.execute(new Train(2));
      executor.execute(new Train(3));
//       executor.execute(new Train(4));
//      executor.execute(new Train(5));
executor.shutdown();
    }
}
