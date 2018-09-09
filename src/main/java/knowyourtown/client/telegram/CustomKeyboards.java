package knowyourtown.client.telegram;

import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 09/05/17.
 */
public class CustomKeyboards implements Tags {

    // DEFAULT keyboard
    public static ReplyKeyboardMarkup getDefaultKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();
        row.add(TAG_SEEPROFILE);
        row.add(TAG_PLACES);
        keyboard.add(row);
        row = new KeyboardRow();
        row.add(TAG_SHOWSUGGESTIONS);
        row.add(TAG_INFO);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup getNewRowKeyboard(String command1, String command2) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList();
        KeyboardRow row = new KeyboardRow();

        row.add(command1);
        row.add(command2);
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup getNewColumnKeyboard(String[] commands) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        
        List<KeyboardRow> keyboard = new ArrayList();

        for (int i = 0; i < commands.length; i++) {
            KeyboardRow row = new KeyboardRow();
            row.add(commands[i]);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static ForceReplyKeyboard getForceReply() {

        ForceReplyKeyboard keyboardMarkup = new ForceReplyKeyboard();
        keyboardMarkup.setSelective(true);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup getInlineKeyboard(String[] lbls, String[] msgs) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        ArrayList keyboard = new ArrayList();
        ArrayList row = new ArrayList();

        /*
        InlineKeyboardButton btn1 = new InlineKeyboardButton();
        btn1.setText(lbl1);
        btn1.setCallbackData(msg1);
        InlineKeyboardButton btn2 = new InlineKeyboardButton();
        btn2.setText(lbl2);
        btn2.setCallbackData(msg2);*/

        for (int i = 0; i < lbls.length; i++) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText(lbls[i]);
            btn.setCallbackData(msgs[i]);
            row.add(btn);
        }

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }


    public static InlineKeyboardMarkup getInlineKeyboard(String lbl1, String msg1, String lbl2, String msg2) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        ArrayList keyboard = new ArrayList();
        ArrayList row = new ArrayList();

        InlineKeyboardButton btn1 = new InlineKeyboardButton();
        btn1.setText(lbl1);
        btn1.setCallbackData(msg1);
        InlineKeyboardButton btn2 = new InlineKeyboardButton();
        btn2.setText(lbl2);
        btn2.setCallbackData(msg2);

        row.add(btn1);
        row.add(btn2);

        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static ReplyKeyboard getInlineVerticalKeyboard(String[] goals) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        ArrayList keyboard = new ArrayList();
        ArrayList row = new ArrayList();

        for (int i = 0; i < goals.length; i++) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setText(goals[i]);
            btn.setCallbackData(goals[i]);

            row = new ArrayList();
            row.add(btn);

            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static void getRowForceKeyboard(String command1, String command2) {

        // TODO: 16/06/17
    }

    // Commands1.length must be equal to commands2.length
    public static ReplyKeyboard getNewGridKeyboard(String[] commands1, String[] commands2) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList();

        for (int i = 0; i < commands1.length; i++) {
            KeyboardRow row = new KeyboardRow();
            if (commands1[i] != null)
                row.add(commands1[i]);
            if (commands2[i] != null)
                row.add(commands2[i]);
            keyboard.add(row);
        }

        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;

    }
}
