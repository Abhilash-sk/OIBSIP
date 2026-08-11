package com.abhimanyu.reservation;

public class SeatInventory {

    private int trainNumber;
    private String classType;
    private int totalSeats;
    private int availableSeats;

    public SeatInventory() {
    }

    public SeatInventory(
            int trainNumber,
            String classType,
            int totalSeats,
            int availableSeats
    ) {

        this.trainNumber = trainNumber;
        this.classType = classType;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;

    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}