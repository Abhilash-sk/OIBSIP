package com.abhimanyu.reservation;

public class PNRTest {

    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {
            System.out.println(PNRGenerator.generatePNR());
        }

    }
}