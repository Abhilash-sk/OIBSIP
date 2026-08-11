package com.abhimanyu.reservation;

public class FareCalculator {

    public static int calculateFare(String classType) {

        switch (classType) {

            case "Sleeper":
                return 450;

            case "3AC":
                return 900;

            case "2AC":
                return 1500;

            case "1AC":
                return 2500;

            default:
                return 0;
        }
    }
}