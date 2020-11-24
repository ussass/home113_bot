package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.trofimov.Bot.Bot;
import ru.trofimov.Bot.LoopBot;
import ru.trofimov.keyboard.MyInlineKeyboard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControllerImpl implements Controller {

    private String[] textMessage;
    private long chatId;

    @Override
    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage.split(" ");
    }

    @Override
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public SendMessage getSendMessage() {
        if (textMessage.equals("/keyboard")) return getCustomKeyBord(chatId);

        System.out.println(textMessage);

//        if (textMessage.equals("stop")) Bot.setLoop(false);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        switch (textMessage[0]){
            case "/recipes":
                RecipeController recipeController = new RecipeController(textMessage);
                sendMessage.setText(recipeController.getText());
                sendMessage.setReplyMarkup(recipeController.getMarkup());
                break;
            case "/home":
                SecondController homeController = new HomeController(textMessage);
                sendMessage.setText(homeController.getText());
                sendMessage.setReplyMarkup(homeController.getResponseKeyboard());
                break;
            default:
//                if (LoopBot.startBot()) {
//                    System.out.println("runing");
//                } else {
//                    System.out.println("error");
//                }
                sendMessage.setText(sendText());
                sendMessage.setReplyMarkup(MyInlineKeyboard.simpleButton());
        }

        Date date = new Date();

        String sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : "" + date.getSeconds();
        String min = date.getMinutes() < 10 ? "0" + date.getMinutes() : "" + date.getMinutes();

        StringBuilder builder = new StringBuilder(String.valueOf("[" +
                date.getHours()) + ':' +
                min + ':' +
                sec + "] " +
                chatId + ": ");
        for (String x : textMessage)
            builder.append(x).append(" ");
        System.out.println(builder);

        return sendMessage;
    }

    private String sendText(){
        StringBuilder builder = new StringBuilder();
        String footer = "\n/help - помощь\n" +
                "/recipes - рецепты";
        switch (textMessage[0]){
            case "/start":
                builder.append("Добро пожаловать в Home113\n" +
                        "Пока тут только представлена домашняя база рецептов\n");
                builder.append(footer);
                break;
            case "/help":
                builder.append("Доступны следующие команды:\n");
                builder.append(footer);
                break;

            default:
                builder.append("Не понимаю.\n" +
                        "Доступны следующие команды:\n");
                builder.append(footer);

        }
        return builder.toString();
    }

    private static SendMessage getCustomKeyBord(long chatId){
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText("Here is your keyboard");
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add("Hello");
        row.add("/start");
        row.add("/help");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
