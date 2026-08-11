package com.abhimanyu.reservation;

import java.util.HashMap;
import java.util.Map;

public final class SeatAllocator {

    private static final int SEATS_PER_COACH = 72;

    private static final Map<String, Integer> seatCounters = new HashMap<>();

    static {
        seatCounters.put("Sleeper", 1);
        seatCounters.put("3AC", 1);
        seatCounters.put("2AC", 1);
        seatCounters.put("1AC", 1);
    }

    private SeatAllocator() {
        // Utility class
    }

    public static synchronized String getNextSeat(String classType) {

        int currentSeat = seatCounters.getOrDefault(classType, 1);

        int coachNumber = ((currentSeat - 1) / SEATS_PER_COACH) + 1;
        int seatNumber = ((currentSeat - 1) % SEATS_PER_COACH) + 1;

        seatCounters.put(classType, currentSeat + 1);

        String coachPrefix = getCoachPrefix(classType);

        return String.format(
                "%s%d-%02d",
                coachPrefix,
                coachNumber,
                seatNumber
        );
    }

    private static String getCoachPrefix(String classType) {

        return switch (classType) {
            case "Sleeper" -> "S";
            case "3AC" -> "B";
            case "2AC" -> "A";
            case "1AC" -> "H";
            default -> "C";
        };
    }
}