package ru.trofimov.Bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.trofimov.parameters.BotParameters;
import ru.trofimov.processingRequest.ProcessingRequest;
import ru.trofimov.processingRequest.SimpleProcessingRequest;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    public static boolean startBot(){
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

        if(update.hasMessage()){
            if(update.getMessage().hasText()){
                ProcessingRequest request = new SimpleProcessingRequest();
                request.setMessage(update.getMessage());

                try {
                    execute(request.getMassage());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        }else if(update.hasCallbackQuery()){
//            System.out.println(update.getCallbackQuery().getMessage().getText());
            System.out.println("update.getCallbackQuery().getData() = " + update.getCallbackQuery().getData());
//            try {
//                execute(new SendMessage().setText(
//                        update.getCallbackQuery().getData())
//                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
        }
    }

    public String getBotUsername() {
        return BotParameters.getBotUsername();
    }

    public String getBotToken() {
        return BotParameters.getBotToken();
    }



}
