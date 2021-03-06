package ru.trofimov.processingRequest;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.trofimov.keyboard.MyInlineKeyboard;

import java.util.ArrayList;
import java.util.List;

public class SimpleProcessingRequest implements ProcessingRequest {

    private Message message;

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public SendMessage getSendMassage() {

        if (message.getText().equals("/keyboard")) return getCustomKeyBord(message.getChatId());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(sendText());

        sendMessage.setReplyMarkup(MyInlineKeyboard.simpleButton());

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

    private static SendMessage getCustomKeyBord(long chatId){
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chatId)
                .setText("Here is your keyboard");
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Hello");
        row.add("/start");
        row.add("/help");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        row.add("Row 2 Button 1");
        row.add("Row 2 Button 2");
        row.add("Row 2 Button 3");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
