package ru.trofimov.processingRequest;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ProcessingRequest {
    SendMessage getSendMassage();
    void setMessage(Message message);
}
