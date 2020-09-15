package ru.trofimov.Bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.trofimov.controller.ControllerImpl;
import ru.trofimov.controller.Controller;
import ru.trofimov.parameters.BotParameters;

public class Bot extends TelegramLongPollingBot {

    private boolean isAnswerPrepared = false;

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

        Controller controller = new ControllerImpl();

        if(update.hasMessage()){
            if(update.getMessage().hasText()) {
                controller.setChatId(update.getMessage().getChatId());
                controller.setTextMessage(update.getMessage().getText());
                isAnswerPrepared = true;
            }
        }
        else if(update.hasCallbackQuery()){
            controller.setChatId(update.getCallbackQuery().getMessage().getChatId());
            controller.setTextMessage(update.getCallbackQuery().getData());
            isAnswerPrepared = true;
        }

        if (isAnswerPrepared){
            try {
                execute(controller.getSendMessage());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }





//        if(update.hasMessage()){
//            if(update.getMessage().hasText()){
//                ProcessingRequest request = new SimpleProcessingRequest();
//                request.setMessage(update.getMessage());
//
//                try {
//                    execute(request.getSendMassage());
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }else if(update.hasCallbackQuery()){
//            System.out.println("update.getCallbackQuery().getData() = " + update.getCallbackQuery().getData());
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
//            sendMessage.setText("123!!!");
//
//            switch (update.getCallbackQuery().getData()){
//                case "/recipe":
//                    System.out.println("Recipe!!!");
//                    break;
//                default:
////                    System.out.println("update.getCallbackQuery() = " + update.getCallbackQuery().getInlineMessageId());
//                    System.out.println("update.getCallbackQuery().getMessage().getChatId() = " + update.getCallbackQuery().getMessage().getChatId());
//                    sendMessage.setText("Доступны следующие команды:\n" +
//                            "\n/help - помощь" +
//                            "\n/recipes - рецепты(Не работает)");
//                    sendMessage.setReplyMarkup(MyInlineKeyboard.simpleButton());
//
//
//
//            }


//            try {
//                execute(new SendMessage().setText(
//                        update.getCallbackQuery().getData())
//                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
////                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
        }

//    }

    public String getBotUsername() {
        return BotParameters.getBotUsername();
    }

    public String getBotToken() {
        return BotParameters.getBotToken();
    }


}
