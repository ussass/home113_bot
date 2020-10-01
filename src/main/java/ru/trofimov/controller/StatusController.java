package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.model.WorkWithDB;

import java.util.ArrayList;
import java.util.List;

class StatusController {

    private String[] textMessage;
    private String responseText;
    private InlineKeyboardMarkup responseKeyboard;
    private static int baseHot;
    private static int baseCold;

    StatusController(String textMessage) {
        this.textMessage = textMessage.split(" ");
        responseKeyboard = new InlineKeyboardMarkup();
        prepareAnswer();
    }

    String getText() {
        return responseText;
    }

    InlineKeyboardMarkup getResponseKeyboard() {
        return responseKeyboard;
    }

    private void prepareAnswer(){
        if (textMessage.length == 1) {
            responseText = "Выберите категорию";
            responseKeyboard = getCategoryMarkup();
            return;
        }

        StringBuilder builder = new StringBuilder();


        switch (textMessage[1]) {
            case "water":
                List<WaterPerDay> list = WorkWithDB.findAllWater();
                int resultHot = baseHot;
                int resultCold = baseCold;
                for (WaterPerDay x : list){
                    resultHot += x.getHotWaterTotal();
                    resultCold += x.getColdWaterTotal();
                }
                builder.append("Показание горячей воды: \uD83D\uDD25");
                builder.append(resultHot/100).append(",").append(resultHot%100*10);
                builder.append(" m\u00B3").append("\n");
                builder.append("Показание холодной воды: \u2744");
                builder.append(resultCold/100).append(",").append(resultCold%100*10);
                builder.append(" m\u00B3");


                responseText = builder.toString();
                responseKeyboard = getCategoryMarkup();
                break;
            case "set":
                Bot.setPrefix("/status hot ");
                responseText = "Введите показание горячей воды";
                break;
            case "hot":
                Bot.setPrefix("/status cold ");
                responseText = "Введите показание холодной воды";
                try {
                    baseHot = Integer.parseInt(textMessage[2]) / 10;
                } catch (NumberFormatException e) {
                    responseText = "Введите число";
                }
                break;
            case "cold":
                Bot.setPrefix("");
                responseText = "Данные сохранены";
                responseKeyboard = getCategoryMarkup();
                try {
                    baseCold = Integer.parseInt(textMessage[2]) / 10;
                } catch (NumberFormatException e) {
                    responseText = "Введите число";
                }
                break;
            case "graph":

                List<WaterPerDay> waterPerDayListForYears = WorkWithDB.findAllWater();
                List<Integer> yearsList = new ArrayList<>();
                int year = 0;
                for (WaterPerDay x : waterPerDayListForYears){
                    if (year != x.getYear()){
                        year = x.getYear();
                        yearsList.add(x.getYear());
                    }
                }
                responseText = "Выберите год";
                responseKeyboard = getTimeKeyboard(yearsList, "year");
                break;
            case "year":

                List<WaterPerDay> waterPerDayListForMonths = WorkWithDB.findAllWater();
                List<Integer> monthList = new ArrayList<>();
                int month = 0;
                for (WaterPerDay x : waterPerDayListForMonths){
                    if (month != x.getMonth()){
                        month = x.getMonth();
                        monthList.add(x.getMonth());
                    }
                }
                responseText = "Выберите месяц";
                responseKeyboard = getTimeKeyboard(monthList, "month");

                break;
            case "month":
                List<WaterPerDay> waterPerDayListForDay = WorkWithDB.findAllWater();
                List<Integer> dayList = new ArrayList<>();
                int day = 0;
                for (WaterPerDay x : waterPerDayListForDay){
                    if (day != x.getDay()){
                        day = x.getDay();
                        dayList.add(x.getDay());
                    }
                }
                responseText = "Выберите месяц";
                responseKeyboard = getTimeKeyboard(dayList, "day");
            default:
                responseText = "Выберите категорию";
                responseKeyboard = getCategoryMarkup();
        }

//        List<WaterPerDay> list = WorkWithDB.findAllWater();
//        WaterPerDay water = list.get(list.size()-1);
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("Литры ");
//        for (int x : water.getColdWater())
//            builder.append(x).append(" ");
//        builder.append("\n");
//        builder.append("1    ");
//        for (int i = 0; i < 24; i++){
//            builder.append(i + 1).append(" ");
//        }
//        responseText = builder.toString();
    }

    private InlineKeyboardMarkup getTimeKeyboard(List<Integer> list, String time){

        String text = "";
        if (time.equals("year"))
            text = "20";

        System.out.println("list.size() = " + list.size());
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        for (int x : list)
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText(text + x)
                    .setCallbackData("/status " + time + " " + x));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        markup.setKeyboard(rowList);

        return markup;
    }

    private InlineKeyboardMarkup getCategoryMarkup() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Показания воды")
                .setCallbackData("/status water"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("График потребления")
                .setCallbackData("/status graph"));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Установить начальное значение")
                .setCallbackData("/status set"));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        markup.setKeyboard(rowList);

        return markup;
    }
}
