package ru.trofimov.controller;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface SecondController {

    String getText();

    InlineKeyboardMarkup getResponseKeyboard();
}
