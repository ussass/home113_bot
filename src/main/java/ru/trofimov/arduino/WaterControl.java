package ru.trofimov.arduino;

import arduino.Arduino;

public class WaterControl implements Runnable {

    private int hotWater;
    private int coldWater;
    private boolean isConnect;
    private Arduino arduino;

    public WaterControl() {
        arduino = new Arduino("COM3", 9600);
        isConnect = arduino.openConnection();
    }

    public WaterControl(String port) {
        arduino = new Arduino(port, 9600);
        isConnect = arduino.openConnection();
    }

    public boolean isConnect() {
        if (!isConnect) return false;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arduino.serialWrite('1');
        String waterResult = arduino.serialRead();

        hotWater = Integer.parseInt(waterResult.split("\n")[0]);
        coldWater = Integer.parseInt(waterResult.split("\n")[1]);

        System.out.print("hotWater = " + hotWater);
        System.out.println(", coldWater = " + coldWater);

        return true;
    }

    @Override
    public void run() {
        while (true)isConnect();
    }
}
