package App;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Bot extends TelegramLongPollingBot {
    private final String BOT_NAME = "Kujjik_bot";
    private final String BOT_TOKEN = "5366888519:AAHEuucDVygZqREq3mPUx4blhp4NaDZk1TU";

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param text   текст ответа
     */
    private void setAnswer(Long chatId, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        ModelWeather model = new ModelWeather();
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
            switch (msg.getText()) {
                case "/start":
                    setAnswer(chatId, "Конечно тут должно было быть куча функционала, " +
                            "но этот перень не сильно разобрался с многопотоком \uD83D\uDE2B");
                    break;
                case "/exchange":
                    setAnswer(chatId, "И не то что бы он не старался, минус 2 дня, короткие сроки " +
                            "(не, нуууу объективно он тут сам виноват)");
                    break;
                case "/weather":
                    setAnswer(msg.getChatId(), "Сделаем вид, что сейчас я прошу вас ввести город в котором " +
                            "вам интересно узнать погоду (но на деле это все работает не так \uD83D\uDE05), но вы " +
                            "попробуйте, введите интересующий вас город: ");
                    break;
                default:
                    try {
                        setAnswer(chatId, SimpleHTTPGet.getJson(msg.getText(), model));
                    } catch (IOException e) {
                        setAnswer(chatId, "Ага, а может еще и погоду в Атлантиде показать, да ладно " +
                                "будьте реалистом! \uD83D\uDE15");
                    }

        }
    }


    public void initKeyboard() {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        //Добавляем одну кнопку с текстом "Просвяти" наш ряд
        keyboardRow.add(new KeyboardButton("/help"));
        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}
