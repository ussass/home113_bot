package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trofimov.arduino.WaterPerDay;
import ru.trofimov.model.WorkWithDB;

import java.util.ArrayList;
import java.util.List;

class StatusController {

    private String[] textMessage;
    private String responseText;
    private InlineKeyboardMarkup responseKeyboard;

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

        List<WaterPerDay> list = WorkWithDB.findAllWater();
        WaterPerDay water = list.get(list.size()-1);

        StringBuilder builder = new StringBuilder();
        builder.append("Литры ");
        for (int x : water.getColdWater())
            builder.append(x).append(" ");
        builder.append("\n");
        builder.append("1    ");
        for (int i = 0; i < 24; i++){
            builder.append(i + 1).append(" ");
        }
        responseText = builder.toString();
        responseKeyboard = getCategoryMarkup();


    }

    private InlineKeyboardMarkup getCategoryMarkup() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Показания воды")
                .setCallbackData("/status water"));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        markup.setKeyboard(rowList);

        return markup;
    }
}
