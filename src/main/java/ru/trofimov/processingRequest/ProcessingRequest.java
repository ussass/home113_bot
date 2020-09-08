package ru.trofimov.processingRequest;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ProcessingRequest {
    SendMessage getMassage();
    void setMessage(Message message);
}
