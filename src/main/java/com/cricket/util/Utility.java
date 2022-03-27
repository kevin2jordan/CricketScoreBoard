package com.cricket.util;

import lombok.NonNull;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Utility {

    public static int acceptInt(@NonNull String message) {
        Scanner sc = new Scanner(System.in);
        System.out.println(message + ": ");
        return sc.nextInt();
    }

    public static String acceptString() {
        Scanner sc= new Scanner(System.in);
        return sc.nextLine();
    }

    public static boolean isValidInput(@NonNull String str) {

        Set<String> acceptableInputs = new HashSet<>();
        acceptableInputs.add("wd");
        acceptableInputs.add("nb");
        acceptableInputs.add("w");
        acceptableInputs.add("0");
        acceptableInputs.add("1");
        acceptableInputs.add("2");
        acceptableInputs.add("3");
        acceptableInputs.add("4");
        acceptableInputs.add("5");
        acceptableInputs.add("6");

        return acceptableInputs.contains(str.toLowerCase());
    }

    public static int getIntegerValue(@NonNull String ball) {
        try {
            return Integer.parseInt(ball);
        } catch (Exception e) {
            return 0;
        }
    }

}
