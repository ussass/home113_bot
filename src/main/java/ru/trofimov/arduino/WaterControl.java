package ru.trofimov.arduino;

import arduino.Arduino;
import ru.trofimov.model.Water;
import ru.trofimov.model.WaterReading;
import ru.trofimov.service.WaterService;
import ru.trofimov.service.WaterServiceImp;

import java.util.Date;
import java.util.Random;

public class WaterControl implements Runnable {

    private int hotWater;
    private int coldWater;
    private boolean isConnect;
    private Arduino arduino;

    public WaterControl(String port) {
//        arduino = new Arduino(port, 9600);
//        isConnect = arduino.openConnection();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void toReadMeter() {
//        arduino.serialWrite('1');
//        String waterResult = arduino.serialRead();

        Random random = new Random();
        String waterResult = (random.nextInt(10) + 1) + "\n" + (random.nextInt(10) + 1);

        hotWater = Integer.parseInt(waterResult.split("\n")[0]);
        coldWater = Integer.parseInt(waterResult.split("\n")[1]);
        System.out.println("hotWater = " + hotWater);
        System.out.println("coldWater = " + coldWater);
    }

    @Override
    public void run() {
        int hour = -1;
        int myDate;
        WaterService service = new WaterServiceImp();
        while (true) {
            Date date = new Date();
            if (date.getHours() != hour) {
                myDate = date.getYear() % 100;
                myDate = myDate * 100 + date.getMonth() + 1;
                myDate = myDate * 100 + date.getDate();
                hour = date.getHours();
                toReadMeter();
                int lastDay = service.getLastDate();

                Water water;

                if (lastDay != myDate) {
                    water = new Water(myDate);
                    water.addReading(new WaterReading(true, true, water));
                    water.addReading(new WaterReading(true, false, water));
                    water.addReading(new WaterReading(false, true, water));
                    water.addReading(new WaterReading(false, false, water));

                    water.setValue(hour, hotWater, coldWater);

                    service.save(water);
                } else {
                    water = service.getWaterByDate(myDate);

                    water.setValue(hour, hotWater, coldWater);
                    service.merge(water);
                }
                System.out.println("Добавленно в час: " + hour);
            }
        }


    }
}
