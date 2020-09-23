package ru.trofimov.Bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.trofimov.controller.ControllerImpl;
import ru.trofimov.controller.Controller;
import ru.trofimov.parameters.BotParameters;

public class Bot extends TelegramLongPollingBot {

    private boolean isAnswerPrepared = false;

    public static boolean startBot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
            return true;
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onUpdateReceived(Update update) {

        Controller controller = new ControllerImpl();

        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                controller.setChatId(update.getMessage().getChatId());
                controller.setTextMessage(update.getMessage().getText());
                isAnswerPrepared = true;
            }
        } else if (update.hasCallbackQuery()) {
            controller.setChatId(update.getCallbackQuery().getMessage().getChatId());
            controller.setTextMessage(update.getCallbackQuery().getData());
            isAnswerPrepared = true;
        }

        if (isAnswerPrepared) {
            try {
                execute(controller.getSendMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return BotParameters.getBotUsername();
    }

    public String getBotToken() {
        return BotParameters.getBotToken();
    }

}
