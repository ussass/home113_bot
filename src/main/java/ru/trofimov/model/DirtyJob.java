package ru.trofimov.model;

import java.util.List;

public class DirtyJob {
    public static String ListGraph(List<Integer> list) {
        StringBuilder builder = new StringBuilder();
        int maxValue = 0;
        for (Integer integer : list) {
            if (maxValue < integer) maxValue = integer;
        }
//        String empty = "   ";
        String empty = " ";

        for (int i = maxValue; i > 0; i--){
            builder.append(i).append(" ");
            for (int j = 0; j < list.size(); j++){
                if (list.get(j) >= i) builder.append("#");
                else builder.append(empty);
                builder.append(" ");
            }
            builder.append("\n");
        }

        System.out.println(builder.toString());

        return "";
    }
}
