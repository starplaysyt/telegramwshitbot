import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public final class TGBot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME = "";
    private final String BOT_TOKEN= "";

    public long chatIDE = 0;

    public TGBot(String botName, String botToken) {
        super();
        /*Timer tm = new Timer();
        TimerTask ttask = new TimerTask() {
            @Override
            public void run() {
                setAnswer(chatIDE, "", "Хер");
            }
        };
        tm.scheduleAtFixedRate(ttask, 1000, 2000);*/
        System.out.println(55);
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        String answer = "";
        if(msg.getText().split(" ")[0].equals("/dosmth")) answer = "You issued a command.";
        System.out.println(msg.getText());
        setAnswer(chatId, userName, answer);
    }

    /**
     * Формирование имени пользователя
     * @param msg сообщение
     */
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        chatIDE = chatId;
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName
        }
    }
}
