package by.teplohova.tunnel.creator;

import by.teplohova.tunnel.DirectionEnum;
import by.teplohova.tunnel.Train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TrainCreator {
    public static ArrayList<Train> createTrains(int countTrain){
        ArrayList<Train> trainList =new ArrayList<>();
        Random random=new Random();
        for(int number=1;number<=countTrain;number++){
            trainList.add(new Train(random.nextInt(2000), DirectionEnum.values()[random.nextInt(2)].getDirection()));
        }
        return trainList;
    }
}
