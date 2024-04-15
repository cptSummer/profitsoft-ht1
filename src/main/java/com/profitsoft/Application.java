package com.profitsoft;

import com.profitsoft.service.PhotoService;

public class Application {

    public static void main(String[] args) {
        String path = "";
        String attribute = "";
        if (args.length == 0) {
            System.out.println("Please, enter directory path");
            return;
        } else if (args.length == 1) {
            path = args[0];
            attribute = "";
        } else if (args.length == 2) {
            path = args[0];
            attribute = args[1];
        }
        PhotoService photoService = new PhotoService();
        long startTime = System.nanoTime();
        photoService.getCountOfAttribute(path, attribute);
        long endTime = System.nanoTime();
        long durationInNanoseconds = endTime - startTime;
        double durationInSeconds = (double) durationInNanoseconds / 1_000_000_000;

        System.out.printf("Execution time: %.5f seconds%n", durationInSeconds);
    }
}
