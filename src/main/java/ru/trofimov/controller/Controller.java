package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Controller {
    void setTextMessage(String textMessage);
    void setChatId(long chatId);
    SendMessage getSendMessage();
}
