package ru.trofimov.model;

import java.util.ArrayList;
import java.util.List;

public class DirtyJob {
    public static String ListGraph(List<Integer> list, boolean isAm) {
        int start = isAm ? 1: 13;
        list.add(list.get(0));
        list.remove(0);
        StringBuilder builder = new StringBuilder();
        int maxValue = 0;
        for (Integer integer : list) {
            if (maxValue < integer) maxValue = integer;
        }
        String empty = "\u25FD";
        String full = "\u25FE";


        for (int i = maxValue; i > 0; i--){
            if (i > 9) {
                builder.append(i);
            } else {
                builder.append("0").append(i);
            }
            builder.append(" ");

            for (int j = start - 1; j < list.size() - 13 + start; j++){
                if (list.get(j) >= i) builder.append(full);
                else builder.append(empty);
            }
            builder.append("\n");
        }
        builder.append("L\u2191").append("      ");
        for (int i = start; i < start + 12; i++){
            if (i % 2 != 0) continue;
            if (i > 9) {
                builder.append(i);
            } else {
                builder.append("0").append(i);
            }
            builder.append("     ");
        }

        return builder.toString();
    }

    public static String ListGraph(List<WaterReading> waterReadings, boolean isHot, boolean isAm) {
        List<Integer> list = new ArrayList<>();
//        for (WaterReading waterReading : waterReadings)
        for (WaterReading waterReading : waterReadings) {
            if (waterReading.isHot() == isHot && waterReading.isMorning() == isAm) {
                list = waterReading.getWaterList();
            }
        }

        list.add(list.get(0));
        list.remove(0);
        StringBuilder builder = new StringBuilder();
        int maxValue = 0;
        for (Integer integer : list) {
            if (maxValue < integer) maxValue = integer;
        }
        String empty = "\u25FD";
        String full = "\u25FE";

        for (int i = maxValue; i > 0; i--){
            if (i > 9) {
                builder.append(i);
            } else {
                builder.append("0").append(i);
            }
            builder.append(" ");

            for (int j = 1; j < list.size(); j++){
                if (list.get(j) >= i) builder.append(full);
                else builder.append(empty);
            }
            builder.append("\n");
        }
        builder.append("L\u2191").append("      ");
        for (int i = 1; i < 13; i++){
            if (i % 2 != 0) continue;
            if (i > 9) {
                builder.append(i);
            } else {
                builder.append("0").append(i);
            }
            builder.append("     ");
        }

        return builder.toString();
    }
}
