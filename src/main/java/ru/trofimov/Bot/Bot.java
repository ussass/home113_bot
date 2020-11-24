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
    private static String prefix = "";
    private static LoopBot loopBot;

    public static void setPrefix(String prefix) {
        Bot.prefix = prefix;
    }

    public static boolean startBot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
            loopBot = new LoopBot();
            return true;
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            System.out.println("update.getMessage().getText() = " + update.getMessage().getText());
            if (update.getMessage().getText().equals("stop")) {
                LoopBot.setLoop(false);
                return;
            }
        }

        LoopBot.setLoop(false);
        LoopBot.setUpdateLoop(update);
        Thread thread = new Thread(loopBot);
        thread.start();

        Controller controller = new ControllerImpl();
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                controller.setChatId(update.getMessage().getChatId());
                controller.setTextMessage(prefix + update.getMessage().getText());
                isAnswerPrepared = true;
            }
        } else if (update.hasCallbackQuery()) {
            controller.setChatId(update.getCallbackQuery().getMessage().getChatId());
            controller.setTextMessage(update.getCallbackQuery().getData());
            isAnswerPrepared = true;
        }

        LoopBot.setLoop(true);

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
