package ru.trofimov.arduino;

import java.util.List;

public class WaterPerDay {
    private int date;
    private List<Integer> hotWater;
    private List<Integer> coldWater;

    public WaterPerDay(int date, List<Integer> hotWater, List<Integer> coldWater) {
        this.date = date;
        this.hotWater = hotWater;
        this.coldWater = coldWater;
    }

    public List<Integer> getHotWater() {
        return hotWater;
    }

    public List<Integer> getColdWater() {
        return coldWater;
    }

    public int getDate() {
        return date;
    }

    public int getHotWaterTotal() {
        return getTotal(hotWater);
    }

    public int getColdWaterTotal() {
        return getTotal(coldWater);
    }

    private int getTotal(List<Integer> list){
        int result = 0;
        for (Integer integer : list) {
            result += integer;
        }
        return result;
    }

}
