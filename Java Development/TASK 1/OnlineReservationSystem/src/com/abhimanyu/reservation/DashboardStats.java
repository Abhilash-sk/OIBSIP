package com.abhimanyu.reservation;

public record DashboardStats(
        int totalBookings,
        int totalPassengers,
        double totalRevenue
) {
}