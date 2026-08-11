package com.abhimanyu.reservation;

public class Passenger {

    private int passengerId;
    private String pnr;
    private String passengerName;
    private int age;
    private String gender;
    private String coach;
    private String seatNumber;

    // Default Constructor
    public Passenger() {
    }

    // Parameterized Constructor
    public Passenger(String pnr,
                     String passengerName,
                     int age,
                     String gender,
                     String coach,
                     String seatNumber) {

        this.pnr = pnr;
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
        this.coach = coach;
        this.seatNumber = seatNumber;
    }

    // Getters & Setters

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}