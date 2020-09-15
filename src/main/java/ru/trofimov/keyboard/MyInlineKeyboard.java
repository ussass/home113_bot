package ru.trofimov.keyboard;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MyInlineKeyboard {

    public static InlineKeyboardMarkup simpleButton(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardRow1Button1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardRow1Button2 = new InlineKeyboardButton();
        inlineKeyboardRow1Button1.setText("Помощь");
        inlineKeyboardRow1Button1.setCallbackData("/help");
        inlineKeyboardRow1Button2.setText("Клавиатура");
        inlineKeyboardRow1Button2.setCallbackData("/keyboard");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardRow1Button1);
        keyboardButtonsRow1.add(inlineKeyboardRow1Button2);

        InlineKeyboardButton inlineKeyboardRow2Button1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardRow2Button2 = new InlineKeyboardButton();
        inlineKeyboardRow2Button1.setText("Рецепты");
        inlineKeyboardRow2Button1.setCallbackData("/recipes");
        inlineKeyboardRow2Button2.setText("2");
        inlineKeyboardRow2Button2.setCallbackData("2");

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardRow2Button1);
        keyboardButtonsRow2.add(inlineKeyboardRow2Button2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}
