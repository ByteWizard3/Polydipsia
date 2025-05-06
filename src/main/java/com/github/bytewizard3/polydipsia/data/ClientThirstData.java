package com.github.bytewizard3.polydipsia.data;

public class ClientThirstData {
    public static double playerThirst=0;
    public static int tickCount=0;

    public static void set(double thirst) {
        ClientThirstData.playerThirst = thirst;
    }

    public static double getPlayerThirst() {
        return playerThirst;
    }
}
