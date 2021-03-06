import by.teplohova.tunnel.creator.TrainCreator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(6);
//        executor.execute(new Train(1, "tuda"));
//        executor.execute(new Train(2, "ob"));
//        executor.execute(new Train(3, "ob"));
//        executor.execute(new Train(4, "tuda"));
//        executor.execute(new Train(5, "tuda"));
//        executor.execute(new Train(6, "ob"));
//        executor.execute(new Train(7, "ob"));
//        executor.execute(new Train(8, "ob"));
//        executor.execute(new Train(9, "ob"));
//        executor.shutdown();
        //System.out.println(new TunnelConfigSAXBuilder().buildTunnelConfig("data/configtunnel.xml"));
         TrainCreator.createTrains(15).forEach(train -> executor.execute(train));
        executor.shutdown();
    }
}
