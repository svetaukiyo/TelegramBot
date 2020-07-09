package com.svetaukiyo.TelegramBot;

import com.svetaukiyo.TelegramBot.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.NoSuchElementException;

public class TelegramBot extends TelegramWebhookBot {

    private String webHookPath;
    private String botUserName;
    private String botToken;
    private CountryService countryService;

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public TelegramBot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.getMessage().getText().equals("/start")) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("Hello! \n" +
                    "I am a robot for sights in cities around the world.\n" +
                    "To start communication with me please enter the name of the city.");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                sendMessage(update);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void setWebHookPath(String webHookPath) {
        this.webHookPath = webHookPath;
    }

    public void setBotUserName(String botUserName) {
        this.botUserName = botUserName;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void sendMessage (Update update) throws TelegramApiException {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText(countryService.findByName(update.getMessage().getText()));
            execute(sendMessage);
        } catch (NoSuchElementException e) {
            SendMessage exception = new SendMessage();
            exception.setChatId(update.getMessage().getChatId());
            exception.setText("This city doesn't exist");
            execute(exception);
        }
    }
}
