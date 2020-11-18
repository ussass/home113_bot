package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trofimov.Bot.Bot;
import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.model.DirtyJob;
import ru.trofimov.model.Water;
import ru.trofimov.model.WaterReading;
import ru.trofimov.model.WorkWithDB;
import ru.trofimov.service.WaterService;
import ru.trofimov.service.WaterServiceImp;

import java.util.ArrayList;
import java.util.List;

public class WaterController implements SecondController {

    private String[] textMessage;
    private String responseText;
    private InlineKeyboardMarkup responseKeyboard;
    private static int baseHot;
    private static int baseCold;

    public WaterController(String[] textMessage) {
        this.textMessage = textMessage;
        responseText = "IT IS WATER CONTROLLER!!";
        responseKeyboard = getCategoryMarkup();
        prepareAnswer();
    }

    @Override
    public String getText() {
        return responseText;
    }

    @Override
    public InlineKeyboardMarkup getResponseKeyboard() {
        return responseKeyboard;
    }

    private void prepareAnswer() {
        if (textMessage.length == 2) {
            responseText = "Выберите категорию";
            responseKeyboard = getCategoryMarkup();
            return;
        }

        StringBuilder builder = new StringBuilder();
        WaterService service = new WaterServiceImp();

        switch (textMessage[2]) {
            case "readings":

//                List<WaterPerDay> list = WorkWithDB.findAllWater();
                List<Water> waters = service.findAll();
                int resultHot = baseHot;
                int resultCold = baseCold;
                for (Water x : waters) {
                    for (WaterReading reading : x.getWaterReadings()) {
                        if (reading.isHot()) resultHot += reading.sumAll();
                        else resultCold += reading.sumAll();
                    }
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
                Bot.setPrefix("/home water hot ");
                responseText = "Введите показание горячей воды";
                break;
            case "hot":
                Bot.setPrefix("/home water cold ");
                responseText = "Введите показание холодной воды";
                try {
                    baseHot = Integer.parseInt(textMessage[3]) / 10;
                } catch (NumberFormatException e) {
                    responseText = "Введите число";
                }
                break;
            case "cold":
                Bot.setPrefix("");
                responseText = "Данные сохранены";
                responseKeyboard = getCategoryMarkup();
                try {
                    baseCold = Integer.parseInt(textMessage[3]) / 10;
                } catch (NumberFormatException e) {
                    responseText = "Введите число";
                }
                break;
            case "graph":
                List<Water> waterYears = service.findAll();
                List<Integer> yearsList = new ArrayList<>();
                int year = 0;
                for (Water x : waterYears){
                    if (year != x.getYear()){
                        year = x.getYear();
                        yearsList.add(x.getYear());
                    }
                }
                responseText = "Выберите год";
                responseKeyboard = getTimeKeyboard(yearsList, "year");
                break;
            case "year":

                List<Water> waterMonth = service.findAll();
                List<Integer> monthList = new ArrayList<>();
                int month = 0;
                for (Water x : waterMonth){
                    if (month != x.getYearWithMonth() && x.getYearWithMonth() / 100 == Integer.parseInt(textMessage[3])){
                        month = x.getYearWithMonth();
                        monthList.add(x.getYearWithMonth());
                    }
                }
                responseText = "Выберите месяц";
                responseKeyboard = getTimeKeyboard(monthList, "month");
                break;
            case "month":
                List<Water> waterDays = service.findAll();
                List<Integer> dayList = new ArrayList<>();
                int day = 0;
                for (Water x : waterDays){
                    if (day != x.getDay() && x.getDay() / 100 == Integer.parseInt(textMessage[3])){
                        day = x.getDay();
                        dayList.add(x.getDay());
                    }
                }

                responseText = "На следующие даты доступен график потребления воды:";
                responseKeyboard = getTimeKeyboard(dayList, "day");
                break;
            case "day":

//                WaterPerDay water = WorkWithDB.getWaterByDate(date);
//                Water water = service.
//                int[] neighboringDays = WorkWithDB.getWaterByDatePreviousAndNext(date);
//                boolean isAm = true;
//                if (textMessage.length > 3) isAm = false;

                int date = Integer.parseInt(textMessage[3]);
                System.out.println(date);
                Water water = service.getWaterByDate(date);
                System.out.println(water.getId());
                int[] neighboringDays = service.getWaterByDatePreviousAndNext(date);
                boolean isAm = true;
                if (textMessage.length > 4) isAm = false;
                List<WaterReading> waterReadings = water.getWaterReadings();

                for (WaterReading x : waterReadings){
                    System.out.println("x.isHot() = " + x.isHot());
                    System.out.println("x.isMorning() = " + x.isMorning());
                }

//
                StringBuilder dayBuilder = new StringBuilder();
                dayBuilder.append("Показания на ").append(date % 100).append(" ");
                dayBuilder.append(dateIntToString(date / 100 % 100, false)).append(" ").append(date / 10000 + 2000);
                dayBuilder.append(".\nГорячей воды");
                if (isAm) dayBuilder.append(" первой");
                else  dayBuilder.append(" второй");
                dayBuilder.append(" половины дня по часам:\n\n");
                dayBuilder.append(DirtyJob.ListGraph(water.getWaterReadings(),true, isAm));
                dayBuilder.append("\n\n");
                dayBuilder.append("Холодной воды");
                if (isAm) dayBuilder.append(" первой");
                else  dayBuilder.append(" второй");
                dayBuilder.append(" половины дня по часам:\n\n");
                dayBuilder.append(DirtyJob.ListGraph(water.getWaterReadings(),false, isAm));
//
//
//
                responseText = dayBuilder.toString();
//                responseText = "День";
//                responseKeyboard = getGraphMarkup(neighboringDays, date, isAm);
                break;
            default:
                String text = "";
                for (String x : textMessage)
                    text += x + " ";
                System.out.println(text);
                responseText = text;
        }
    }

    private InlineKeyboardMarkup getCategoryMarkup() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Показания воды")
                .setCallbackData("/home water readings"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("График потребления")
                .setCallbackData("/home water graph"));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Установить начальное значение")
                .setCallbackData("/home water set"));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        markup.setKeyboard(rowList);

        return markup;
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
                        .setCallbackData("/home water " + time + " " + list.get(i)));
            if ( i>= 7 && i < 14)
                keyboardButtonsRow2.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("/home water " + time + " " + list.get(i)));
            if ( i>= 14 && i < 21)
                keyboardButtonsRow3.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("/home water " + time + " " + list.get(i)));
            if ( i>= 21 && i < 28)
                keyboardButtonsRow4.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("//home water " + time + " " + list.get(i)));
            if ( i>= 28 && i < 35)
                keyboardButtonsRow5.add(new InlineKeyboardButton().setText(text)
                        .setCallbackData("/home water " + time + " " + list.get(i)));

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
