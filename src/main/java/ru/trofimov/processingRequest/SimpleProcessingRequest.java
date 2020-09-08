package ru.trofimov.processingRequest;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SimpleProcessingRequest implements ProcessingRequest {

    private Message message;

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public SendMessage getMassage() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(sendText());

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
        inlineKeyboardRow2Button1.setText("1");
        inlineKeyboardRow2Button1.setCallbackData("1");
        inlineKeyboardRow2Button2.setText("2");
        inlineKeyboardRow2Button2.setCallbackData("2");

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(inlineKeyboardRow2Button1);
        keyboardButtonsRow2.add(inlineKeyboardRow2Button2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);

        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }

    private String sendText(){
        StringBuilder builder = new StringBuilder();
        String footer = "\n/help - помощь\n" +
                "/recipes - рецепты(Не работает)";
        switch (message.getText()){
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
}
