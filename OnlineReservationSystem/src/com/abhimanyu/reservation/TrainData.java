package com.abhimanyu.reservation;

import java.util.HashMap;
import java.util.Map;

public class TrainData {

    private static final Map<Integer, String> trains = new HashMap<>();

    static {

        trains.put(12627, "Karnataka Express");
        trains.put(12628, "Karnataka Express");

        trains.put(17307, "Basava Express");
        trains.put(16591, "Hampi Express");

        trains.put(12629, "Sampark Kranti Express");
        trains.put(12777, "Hubballi Express");

        trains.put(11013, "Coimbatore Express");
        trains.put(12649, "Kongu Express");

    }

    public static String getTrainName(int trainNumber) {

        return trains.get(trainNumber);

    }

}