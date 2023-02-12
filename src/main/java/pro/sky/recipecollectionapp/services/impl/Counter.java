package pro.sky.recipecollectionapp.services.impl;

public class Counter {
    private static int idCounter = 0;

    public static int getId() {
        idCounter += 1;
        return idCounter;
    }
}
