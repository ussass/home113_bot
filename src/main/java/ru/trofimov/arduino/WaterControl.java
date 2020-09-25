package ru.trofimov.arduino;

import arduino.Arduino;
import ru.trofimov.model.WorkWithDB;

import java.util.Date;

public class WaterControl implements Runnable {

    private int hotWater;
    private int coldWater;
    private boolean isConnect;
    private Arduino arduino;

    public WaterControl(String port) {
        arduino = new Arduino(port, 9600);
        isConnect = arduino.openConnection();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void toReadMeter() {
        arduino.serialWrite('1');
        String waterResult = arduino.serialRead();

        hotWater = Integer.parseInt(waterResult.split("\n")[0]);
        coldWater = Integer.parseInt(waterResult.split("\n")[1]);
    }

    @Override
    public void run() {
        int hour = -1;
        int myDate;
//        boolean isTodayAdded  = false;
        while (true){
            Date date = new Date();
            if (date.getHours() != hour){
//                if (date.getDate() == myDate % 100)
//                    isTodayAdded = false;
                myDate = date.getYear() % 100;
                myDate = myDate * 100 + date.getMonth() + 1;
                myDate = myDate * 100 + date.getDate();
                hour = date.getHours();
                toReadMeter();
                int lastDay = WorkWithDB.lastDateWater();

                if (lastDay != myDate){
                    WorkWithDB.saveWater(hotWater, coldWater, myDate, date.getHours());
                }
                else {
                    WorkWithDB.updateWater(lastDay, hotWater, coldWater, date.getHours());
                }
                System.out.println("Добавленно в час: " + hour);
            }
        }


    }
}
