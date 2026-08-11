package com.abhimanyu.reservation;

import java.util.Random;

public class PNRGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int PNR_LENGTH = 6;

    public static String generatePNR() {

        Random random = new Random();
        StringBuilder pnr = new StringBuilder();

        for (int i = 0; i < PNR_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            pnr.append(CHARACTERS.charAt(index));
        }

        return pnr.toString();
    }
}