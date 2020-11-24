package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class HomeController implements SecondController{
    private String[] textMessage;
    private String responseText;
    private InlineKeyboardMarkup responseKeyboard;

    HomeController(String[] textMessage) {
        this.textMessage = textMessage;
        responseKeyboard = new InlineKeyboardMarkup();
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
        if (textMessage.length == 1) {
            responseText = "Выберите категорию";
            responseKeyboard = getCategoryMarkup();
            return;
        }

        switch (textMessage[1]) {
            case "water":
                SecondController waterController = new WaterController(textMessage);
                responseText = waterController.getText();
                responseKeyboard = waterController.getResponseKeyboard();
                break;
            default:
                responseText = "Такой команды нету!";
        }
    }

    private InlineKeyboardMarkup getCategoryMarkup() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Показания воды")
                .setCallbackData("/home water"));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        markup.setKeyboard(rowList);

        return markup;
    }
}
