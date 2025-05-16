package com.github.bytewizard3.polydipsia.data;

public class ClientHeatData{
    public static float heat=0;
    public static int tickCount=0;

    public static void set(float heat) {
        ClientHeatData.heat = heat;
    }

    public static float getPlayerHeat() {
        return heat;
    }
}
