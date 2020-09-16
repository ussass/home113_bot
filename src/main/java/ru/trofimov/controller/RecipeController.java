package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trofimov.entity.Recipe;
import ru.trofimov.model.WorkWithDB;

import java.util.ArrayList;
import java.util.List;

class RecipeController {

    private String[] textMessage;
    private String responseText;
    private InlineKeyboardMarkup responseKeyboard;

    RecipeController(String textMessage) {
        this.textMessage = textMessage.split(" ");
        prepareAnswer();
    }

    String getText() {
        return responseText;
    }

    InlineKeyboardMarkup getMarkup() {
        return responseKeyboard;
    }

    private void prepareAnswer() {
        if (textMessage.length == 1) {
            responseText = "Выберите категорию";
            responseKeyboard = getCategoryMarkup();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Доступны следующие рецепты:\n\n");

        List<Recipe> list = WorkWithDB.findAll(Integer.parseInt(textMessage[2]));
        for (Recipe x : list)
            System.out.println(x.getRecipeName());




        stringBuilder.append("Всего рецептов в данной категории: ").append(list.size());


        responseText = stringBuilder.toString();
    }

    private InlineKeyboardMarkup getCategoryMarkup() {

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Завтраки")
                .setCallbackData("/recipes category 1"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Супы")
                .setCallbackData("/recipes category 2"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Основные блюда")
                .setCallbackData("/recipes category 3"));
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Выпечка и десерты")
                .setCallbackData("/recipes category 4"));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Сэндвичи")
                .setCallbackData("/recipes category 5"));
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("Паста и пицца")
                .setCallbackData("/recipes category 6"));
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow3.add(new InlineKeyboardButton().setText("Салаты")
                .setCallbackData("/recipes category 7"));
        keyboardButtonsRow3.add(new InlineKeyboardButton().setText("Соусы")
                .setCallbackData("/recipes category 8"));
        keyboardButtonsRow3.add(new InlineKeyboardButton().setText("Напитки")
                .setCallbackData("/recipes category 9"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        markup.setKeyboard(rowList);

        return markup;
    }

}
