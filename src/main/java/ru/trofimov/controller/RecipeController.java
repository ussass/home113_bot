package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.trofimov.entity.Recipe;
import ru.trofimov.model.PreparationOfTheRecipe;
import ru.trofimov.model.WorkWithDB;

import java.util.ArrayList;
import java.util.List;

class RecipeController {

    private String[] textMessage;
    private String responseText;
    private InlineKeyboardMarkup responseKeyboard;

    RecipeController(String textMessage) {
        this.textMessage = textMessage.split(" ");
        responseKeyboard = new InlineKeyboardMarkup();
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
        List<String> callbackData = new ArrayList<>();
//        for (String x : textMessage)
//            System.out.print(x + " ");
//        System.out.println();

        switch (textMessage[1]) {
            case "category":
                stringBuilder.append("Доступны следующие рецепты:\n\n");
                List<Recipe> list = WorkWithDB.findAll(Integer.parseInt(textMessage[2]));

                int count = textMessage.length == 4 ? Integer.parseInt(textMessage[3]) : 0;

                for (int i = count; i < list.size(); i++) {
                    stringBuilder.append(i + 1);
                    stringBuilder.append(". ").append(list.get(i).getRecipeName()).append("\n\n");
                    callbackData.add("/recipes show " + list.get(i).getId());
                    if (i == count + 7) break;
                }
                int last = 0;
                while (last < list.size() - 8)
                    last += 8;

                prepareKeyboard(callbackData, true, count, 24, count > 7, list.size() - count > 8);

                stringBuilder.append("Всего рецептов в данной категории: ").append(list.size());

                responseText = stringBuilder.toString();
                break;
            case "show":
                int id = Integer.parseInt(textMessage[2]);
                if (textMessage.length == 4)
                    responseText = PreparationOfTheRecipe.getRecipe(id, Integer.parseInt(textMessage[3]));
                else
                    responseText = PreparationOfTheRecipe.getRecipe(id);
                for (int i = 0; i < 8; i++) {
                    callbackData.add("/recipes show " + textMessage[2] + " " + (i + 1));
                }

                prepareKeyboard(callbackData, false, 0, 0, false, false);

                break;
            default:
                responseText = "Выберите категорию";
                responseKeyboard = getCategoryMarkup();
        }
    }

    private void prepareKeyboard(List<String> callbackData, boolean isCategory,
                                 int count, int last, boolean beginning, boolean ending) {
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        for (int i = 0; i < callbackData.size(); i++) {
            keyboardButtonsRow1.add(new InlineKeyboardButton().setText(" " + (i + 1 + count))
                    .setCallbackData(callbackData.get(i)));
        }
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        if (isCategory && beginning) {
            keyboardButtonsRow2.add(new InlineKeyboardButton().setText("<<")
                    .setCallbackData("/recipes category " + textMessage[2]));
            keyboardButtonsRow2.add(new InlineKeyboardButton().setText("<")
                    .setCallbackData("/recipes category " + textMessage[2] + " " + (count - 8)));
        }
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("В начало")
                .setCallbackData("/help"));
        if (isCategory && ending) {
            keyboardButtonsRow2.add(new InlineKeyboardButton().setText(">")
                    .setCallbackData("/recipes category " + textMessage[2] + " " + (count + 8)));
            keyboardButtonsRow2.add(new InlineKeyboardButton().setText(">>")
                    .setCallbackData("/recipes category " + textMessage[2] + " " + last));
        }

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        responseKeyboard.setKeyboard(rowList);

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
