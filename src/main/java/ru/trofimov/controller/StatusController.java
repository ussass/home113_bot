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

                responseText = "На следующие даты доступен график потребления воды:";
                responseKeyboard = getTimeKeyboard(dayList, "day");
                break;
            case "day":

                int date = Integer.parseInt(textMessage[2]);
                WaterPerDay water = WorkWithDB.getWaterByDate(date);
                int[] neighboringDays = WorkWithDB.getWaterByDatePreviousAndNext(date);
                boolean isAm = true;
                if (textMessage.length > 3) isAm = false;


                StringBuilder dayBuilder = new StringBuilder();
                dayBuilder.append("Показания на ").append(date % 100).append(" ");
                dayBuilder.append(dateIntToString(date / 100 % 100, false)).append(" ").append(date / 10000 + 2000);
                dayBuilder.append(".\nГорячей воды");
                if (isAm) dayBuilder.append(" первой");
                else  dayBuilder.append(" второй");
                dayBuilder.append(" половины дня по часам:\n\n");
                dayBuilder.append(DirtyJob.ListGraph(water.getHotWater(), isAm));
                dayBuilder.append("\n\n");
                dayBuilder.append("Холодной воды");
                if (isAm) dayBuilder.append(" первой");
                else  dayBuilder.append(" второй");
                dayBuilder.append(" половины дня по часам:\n\n");
                dayBuilder.append(DirtyJob.ListGraph(water.getColdWater(), isAm));



                responseText = dayBuilder.toString();
                responseKeyboard = getGraphMarkup(neighboringDays, date, isAm);
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
                text = dateIntToString(list.get(i), true);
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
    private InlineKeyboardMarkup getGraphMarkup(int[] neighboringDays, int today, boolean isAm) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        if (neighboringDays[0] != 0)
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText(dateRemake(neighboringDays[0]))
                    .setCallbackData("/status day " + neighboringDays[0]));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText((isAm ? "вторая" : "первая") + " половина дня")
//                .setCallbackData(callback));
                .setCallbackData("/status day " + today + (isAm ? " 1" : "")));
        if (neighboringDays[1] != 0)
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText(dateRemake(neighboringDays[1]))
                    .setCallbackData("/status day " + neighboringDays[1]));


        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("На главную")
                .setCallbackData("/help"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

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

    private String dateRemake (int date){
        String month = date /100 % 100 < 10 ? "0" + date /100 % 100 : " " + date / 100 % 100;
        return date % 100 + "." + month + "." + (date / 10000 + 2000);
    }

    private String dateIntToString (int date, boolean isNominative){
        switch (date % 100){
            case 1: return isNominative ? "январь" : "января";
            case 2: return isNominative ? "февраль" : "февраля";
            case 3: return isNominative ? "март" : "марта";
            case 4: return isNominative ? "апрель" : "апреля";
            case 5: return isNominative ? "май" : "мая";
            case 6: return isNominative ? "июнь" : "июня";
            case 7: return isNominative ? "июль" : "июля";
            case 8: return isNominative ? "август" : "августа";
            case 9: return isNominative ? "сентябрь" : "сентября";
            case 10: return isNominative ? "октябрь" : "октября";
            case 11: return isNominative ? "ноябрь" : "ноября";
            case 12: return isNominative ? "декабрь" : "декабря";
            default: return "";
        }
    }
}












