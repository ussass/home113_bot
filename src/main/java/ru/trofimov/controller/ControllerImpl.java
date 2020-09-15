package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.trofimov.keyboard.MyInlineKeyboard;

public class ControllerImpl implements Controller {

    private String textMessage;
    private long chatId;

    @Override
    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    @Override
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    @Override
    public SendMessage getSendMessage() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        sendMessage.setReplyMarkup(MyInlineKeyboard.simpleButton());

        return sendMessage;
    }
}
