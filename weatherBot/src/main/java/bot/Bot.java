package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Bot extends TelegramLongPollingBot {
    static Long chatId;
    private final Weather weather = new Weather();

    /*
    Создать и получить токен бота нужно в @BotFather в Telegram.
     */
    @Override
    public String getBotUsername() {
        return "PogodaByM"; // Метод, который возвращает username бота.
    }

    @Override
    public String getBotToken() {
        return ""; // Метод, который возвращает token бота (в return "токен бота выдает @BotFather в Telegram." )
    }

    // Метод, отвечающий за обработку полученного сообщения.
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String messageText = update.getMessage().getText(); // Получаем текст сообщения пользователя
            chatId = update.getMessage().getChatId(); // Получаем chatId пользователя
            switch (messageText) {
                case "/start" ->
                        sendMessage(
                                chatId,
                                "Привет! Я помогу узнать погоду, просто напиши название города."
                        );
                case "/help" ->
                        sendMessage(
                                chatId,
                                "Чтобы узнать погоду, отправь название города. Если такого города нет, я промолчу!"
                        );
                default ->
                        sendMessage(
                                chatId,
                                weather.getCityWeather(messageText)
                        );
            }
        }
    }

    // Метод для отправки сообщения пользователю, чей id мы получили
    public void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(messageText);
        sendMessage.setChatId(chatId);
        try {
            executeAsync(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    // Точка входа (метод main, в котором происходит создание и инициализация бота
    public static void main(String[] args) {
        try {
            Bot bot = new Bot();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}