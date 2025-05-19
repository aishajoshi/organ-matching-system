package com.pascal.ptm.examples;

public class AmountServiceExample {
    public static void main(String[] args) {
        long totalTimeSec = 60 * 60 * 2 + 60 * 45;
        System.out.println("Total time (min): " + totalTimeSec / 60.0 + " Total amount= " + calculateAmount(totalTimeSec));

    }


    public static double calculateAmount(long totalTime) {
        System.out.println("Calculate Total amount example");
        double fiveMinuteRate = 0.0;
        double halfHourRate = 15.0;
        double hourlyRate = 25.0;


        int hrs = (int) (totalTime / 3600);
        System.out.println("Hours: " + hrs);
        double totalCost = hrs * hourlyRate;
        long remainingTimeInSec = totalTime - hrs * 3600L;

        if (remainingTimeInSec <= 300) { // 5 minutes (300 seconds)
            totalCost += fiveMinuteRate;
        } else if (remainingTimeInSec <= 1800) { // 30 minutes (1800 seconds)
            totalCost += halfHourRate;
        } else if (remainingTimeInSec <= 3600) { // 1 hour (3600 seconds)
            totalCost += hourlyRate;
        } else { // More than 1 hour
            System.out.println("Remaining time error: " + remainingTimeInSec);
        }
        return totalCost;
    }

    public static double calculateAmountRecursive(long totalTime) {
        System.out.println("Calculate Total amount example");
        double fiveMinuteRate = 0.0;
        double halfHourRate = 15.0;
        double hourlyRate = 25.0;

        double totalCost = 0.0;

        if (totalTime <= 300) { // 5 minutes (300 seconds)
            totalCost = fiveMinuteRate;
        } else if (totalTime <= 1800) { // 30 minutes (1800 seconds)
            totalCost = halfHourRate;
        } else if (totalTime <= 3600) { // 1 hour (3600 seconds)
            totalCost = hourlyRate;
        } else { // More than 1 hour
            long remainingTime = totalTime - 3600; // Subtract 1 hour
            totalCost = 25 + calculateAmountRecursive(remainingTime);
        }
        return totalCost;
    }

}

