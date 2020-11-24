package ru.trofimov.Bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;

public class LoopBot implements Runnable{

    private static boolean loop = false;
    private Bot bot;
    private static Update updateLoop;

    public static void setUpdateLoop(Update updateLoop) {
        LoopBot.updateLoop = updateLoop;
    }

    private void loop(){
        Update update = new Update();
    }

    public LoopBot() {
        bot = new Bot();
    }

    public static void setLoop(boolean loop) {
        LoopBot.loop = loop;
    }

    @Override
    public void run() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(updateLoop.getCallbackQuery().getMessage().getChatId());
        sendMessage.setText("avava");
        Date date;
        LoopBotFlag loopBotFlag = LoopBotFlag.getInstance();
        boolean flag = true;
        try {
            while (loop){
                date = new Date();
                if (flag && loopBotFlag.isFlagTrueOneTime() && date.getSeconds() % 5 == 0){
                    bot.execute(sendMessage);
                    flag = false;
                }
                if (date.getSeconds() % 5 == 2){
                    flag = true;
                    loopBotFlag.setFlag(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
