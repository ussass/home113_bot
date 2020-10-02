package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.model.DirtyJob;
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
                    if (month != x.getYearWithMonth() && x.getYearWithMonth() / 100 == Integer.parseInt(textMessage[2])){
                        month = x.getYearWithMonth();
                        monthList.add(x.getYearWithMonth());

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
                    if (day != x.getDay() && x.getDay() / 100 == Integer.parseInt(textMessage[2])){
                        day = x.getDay();
                        dayList.add(x.getDay());
                    }
                }
                for (int x : dayList)
                    System.out.println("x = " + x);

                responseText = "На следующие даты доступен график потребления воды:";
                responseKeyboard = getTimeKeyboard(dayList, "day");
                break;
            case "day":

                WaterPerDay water = WorkWithDB.getWaterByDate(Integer.parseInt(textMessage[2]));

                StringBuilder dayBuilder = new StringBuilder();
                dayBuilder.append(DirtyJob.ListGraph(water.getHotWater(),true));
                responseText = dayBuilder.toString();
//                responseKeyboard = getCategoryMarkup();
                break;
            default:
                responseText = "Выберите категорию";
                responseKeyboard = getCategoryMarkup();
        }
    }

    private InlineKeyboardMarkup getTimeKeyboard(List<Integer> list, String time){

        String text = "";
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (time.equals("year"))
                text = "20" + list.get(i);
            else if (time.equals("month")) {
                switch (list.get(i) % 100){
                    case 1: text = "январь";break;
                    case 2: text = "февраль";break;
                    case 3: text = "март";break;
                    case 4: text = "апрель";break;
                    case 5: text = "май";break;
                    case 6: text = "июнь";break;
                    case 7: text = "июль";break;
                    case 8: text = "август";break;
                    case 9: text = "сентябрь";break;
                    case 10: text = "октябрь";break;
                    case 11: text = "ноябрь";break;
                    case 12: text = "декабрь";break;
                }
            } else text = "" + list.get(i) % 100;
            if (i < 7)
                keyboardButtonsRow1.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("/status " + time + " " + list.get(i)));
            if ( i>= 7 && i < 14)
                keyboardButtonsRow2.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("/status " + time + " " + list.get(i)));
            if ( i>= 14 && i < 21)
                keyboardButtonsRow3.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("/status " + time + " " + list.get(i)));
            if ( i>= 21 && i < 28)
                keyboardButtonsRow4.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("/status " + time + " " + list.get(i)));
            if ( i>= 28 && i < 35)
                keyboardButtonsRow5.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("/status " + time + " " + list.get(i)));

        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);

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












