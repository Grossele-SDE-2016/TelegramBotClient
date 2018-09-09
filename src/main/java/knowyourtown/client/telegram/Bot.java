package knowyourtown.client.telegram;

import com.google.gson.Gson;
import com.vdurmont.emoji.EmojiParser;
import knowyourtown.client.models.Suggestion;
import knowyourtown.client.models.User;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Created by matteo on 09/05/17.
 */
public class Bot extends TelegramLongPollingBot implements Tags {

    private static final String BOT_NAME = "GrosseleIntroSDEBOT";
    private static final String BOT_TOKEN = "628512902:AAF5KzSvGOzCzhjXPMphbM9-GxGHGXKTDbM";

    // Services URI
    private static final String URI_PROCESS_CENTRIC = "http://localhost:5701";
    //private static final String URI_PROCESS_CENTRIC = "https://introsde-knowyourtown-p-centric.herokuapp.com";

    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        if ((update.hasMessage() && update.getMessage().hasText()) || update.hasCallbackQuery()) {

            //take important stuffs from the user request

            Message message;
            String reqMessage;
            if (update.hasMessage()) {
                message = update.getMessage();
                reqMessage = message.getText(); // message from the user
            } else {
                message = update.getCallbackQuery().getMessage();
                reqMessage = update.getCallbackQuery().getData();
            }

            Long chatId = message.getChatId(); // id of the chat

            // gen my profile
            User contact = new User(chatId,
                    message.getFrom().getFirstName(),
                    message.getFrom().getLastName(),
                    "",
                    "",
                    "",
                    "");

            String replyMessage = null;

            if (message.isReply()) {
                // Selection of the reply
                replyMessage = message.getReplyToMessage().getText();
            }

            // Manage the request, and prepare the response
            SendMessage response = prepareResponse(chatId, contact, reqMessage, replyMessage);

            try {
                if (response != null)
                    sendMessage(response); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private SendMessage prepareResponse(Long chatId, User contact, String command, String replyMessage) {
        // TODO manage the response
        SendMessage response;
        BotBusiness botBusiness = new BotBusiness(URI_PROCESS_CENTRIC, BOT_NAME);

        // log and instantiate response
        System.out.println(command);

        response = new SendMessage().setChatId(chatId);
        String res = "";

        if (command.equals(TAG_START)) {
            res = botBusiness.onStart(contact);

            // if the user is not registered
            if (!res.equals(botBusiness.genNotRegisteredResponse(contact)))
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
            // if something go wrong send default keyboard
            if (res.equals(botBusiness.genErrorMessage(TAG_START)))
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

            response.setText(res);
        }
        else if (command.equals(TAG_REGISTRATION)) {
            res = botBusiness.genRegSurname();
            response.setText(TAG_REGISTRATION + "\n" + res);
            response.setReplyMarkup(CustomKeyboards.getForceReply());
        }
        else if (command.equals(TAG_SEEPROFILE)) {
            res = botBusiness.seeProfile(contact);

            // if the user is not registered
            if (res.equals(botBusiness.genNotRegisteredResponse(contact)))
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

            response.setText(res);
        } else if (command.equals(TAG_PLACES)) {
            res = botBusiness.addPlace(contact);

            // if the user is not registered
            if (res.equals(botBusiness.genNotRegisteredResponse(contact)))
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
            else {
                String[] buttonslbls = {LBL_SHOWPLACES_VISITED, LBL_SHOWPLACES_TO_VISIT,
                        LBL_SHOWPLACES_DREAM };
                String[] buttonsmsgs = {TAG_SHOWPLACES_VISITED, TAG_SHOWPLACES_TO_VISIT,
                        TAG_SHOWPLACES_DREAM };
                response.setReplyMarkup(CustomKeyboards.getInlineKeyboard(buttonslbls, buttonsmsgs));
            }

            response.setText(res);
        } else if (command.equals(TAG_SHOWPLACES_VISITED)) {
            res = botBusiness.showPlaces(contact, "visited");

            response.setReplyMarkup(CustomKeyboards.getNewRowKeyboard3(TAG_UPDATEVISITED, TAG_RESETVISITED, TAG_BACK));

            response.setText(res);
        } else if (command.equals(TAG_SHOWPLACES_TO_VISIT)) {
            res = botBusiness.showPlaces(contact, "to visit");

            response.setReplyMarkup(CustomKeyboards.getNewRowKeyboard3(TAG_UPDATETO_VISIT, TAG_RESETTO_VISIT, TAG_BACK));

            response.setText(res);
        } else if (command.equals(TAG_SHOWPLACES_DREAM)) {
            res = botBusiness.showPlaces(contact, "dream");

            response.setReplyMarkup(CustomKeyboards.getNewRowKeyboard3(TAG_UPDATEDREAM, TAG_RESETDREAM, TAG_BACK));

            response.setText(res);
        } else if (command.equals(TAG_UPDATEVISITED)) {
            //res = botBusiness.updatePlace(contact, "weight");
            res = botBusiness.genUpdatePlace("visited");

            response.setReplyMarkup(CustomKeyboards.getForceReply());

            response.setText(res);
        } else if (command.equals(TAG_UPDATETO_VISIT)) {
            res = botBusiness.genUpdatePlace("to visit");

            response.setReplyMarkup(CustomKeyboards.getForceReply());

            response.setText(res);
        } else if (command.equals(TAG_UPDATEDREAM)) {
            res = botBusiness.genUpdatePlace("dream");

            response.setReplyMarkup(CustomKeyboards.getForceReply());

            response.setText(res);
        }else if (command.equals(TAG_RESETVISITED)) {
            //res = botBusiness.updatePlace(contact, "weight");
            res = botBusiness.deletePlacesbyType(contact,"visited");

            response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

            response.setText(res);
        } else if (command.equals(TAG_RESETTO_VISIT)) {
            res = botBusiness.deletePlacesbyType(contact,"to visit");

            response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

            response.setText(res);
        } else if (command.equals(TAG_RESETDREAM)) {
            res = botBusiness.deletePlacesbyType(contact,"dream");

            response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

            response.setText(res);
        }
        /* else if (command.equals(TAG_UPDATEWAIST)) {
            res = botBusiness.genUpdatePlace("waist");

            response.setReplyMarkup(CustomKeyboards.getForceReply());

            response.setText(res);
        } else if (command.equals(TAG_UPDATEHIP)) {
            res = botBusiness.genUpdatePlace("hip");

            response.setReplyMarkup(CustomKeyboards.getForceReply());

            response.setText(res);
        } */else if (command.equals(TAG_SHOWSUGGESTIONS)) {
            res = botBusiness.getSuggestions(contact);

            Gson gson = new Gson();
            Suggestion vSuggestions[];
            vSuggestions = gson.fromJson(res, Suggestion[].class);

            String[] suggestions;
            String[] deleteSuggestions;
            if (vSuggestions.length == 0) {
                suggestions = new String[2];
                deleteSuggestions = new String[2];
                suggestions[0] = TAG_SUGGESTIONS_NEW;
                suggestions[1] = TAG_BACK;
                deleteSuggestions[0] = null;
                deleteSuggestions[1] = null;
                res = "There aren't suggestions!";
            } else {
                suggestions = new String[vSuggestions.length + 2];
                deleteSuggestions = new String[vSuggestions.length + 2];

                suggestions[0] = TAG_SUGGESTIONS_NEW;
                deleteSuggestions[0] = null;

                res = "Suggestions : \n\n";
                int i;
                for (i = 0; i < vSuggestions.length; i++) {
                    suggestions[i + 1] = TAG_SUGGESTIONS_UPDATE + " " + vSuggestions[i].title;
                    deleteSuggestions[i + 1] = TAG_SUGGESTIONS_DELETE + " " + vSuggestions[i].title;
                    res += vSuggestions[i].formattedText() + "\n";
                }
                suggestions[i + 1] = TAG_BACK;
            }

            //response.setReplyMarkup(CustomKeyboards.getNewColumnKeyboard(suggestions));
            response.setReplyMarkup(CustomKeyboards.getNewGridKeyboard(suggestions, deleteSuggestions));
            response.setText(EmojiParser.parseToUnicode(res));
        } else if (command.equals(TAG_INFO)) {
            res = botBusiness.genInfoMessage();

            response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
            response.setText(res);

        } else if (command.equals(TAG_BACK)) {

            res = botBusiness.back(contact);

            // if the user is not registered
            if (!res.equals(botBusiness.genNotRegisteredResponse(contact)))
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

            response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

            response.setText(res);
        } else if (command.equals(TAG_SUGGESTIONS_NEW)) {

            response.setText(TAG_SUGGESTIONS_NEW + "\n" + "New Title : ");
            response.setReplyMarkup(CustomKeyboards.getForceReply());

            
        }else if (command.split(" ")[0].equals(TAG_SUGGESTIONS_UPDATE)) {
            response.setText(command + "\n" + "New evaluation : ");
            response.setReplyMarkup(CustomKeyboards.getForceReply());

        }
         else if (command.split(" ")[0].equals(TAG_SUGGESTIONS_DELETE)) {

            // get suggestion title
            String title = "";
            String[] vCommand = command.split(" ");
            for (int i = 1; i < vCommand.length; i++) {
                title += vCommand[i];
                if (i < vCommand.length - 1)
                    title += " ";
            }

            res = botBusiness.deleteSuggestion(contact, title);

            if (res.equals(botBusiness.genErrorMessage("deleteSuggestion")))
                res = botBusiness.genErrorMessage(TAG_SUGGESTIONS_DELETE);

            response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

            response.setText(res);
        }
        // REPLIES
        else if (replyMessage != null) {
            //String[] vReplies = replyMessage.split(" : ");

            if (replyMessage.startsWith(TAG_REGISTRATION)) {

                res = replyMessage;
                String[] regString = res.split("\n");

                response.setReplyMarkup(CustomKeyboards.getForceReply());

                if (regString.length == 2) {
                    res += botBusiness.genRegSex(command);
                } else if (regString.length == 3) {
                    res += botBusiness.genRegBirthDate(command);
                } else if (regString.length == 4) {
                    res += botBusiness.genRegNationality(command);
                } else if (regString.length == 5) {
                    res += botBusiness.genRegBirthplace(command);
                } else if (regString.length == 6) {
                    res = botBusiness.registration(contact, res + command);
                    response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());

                } else {
                    System.err.println("Error on registration");
                }

                response.setText(res);

            } else if (replyMessage.equals(botBusiness.genUpdatePlace("visited"))) {
                res = botBusiness.updatePlace(contact, command, "visited");
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
                // TODO AGGIUNGERE CONTROLLI
                response.setText(res);
            } else if (replyMessage.equals(botBusiness.genUpdatePlace("to visit"))) {
                res = botBusiness.updatePlace(contact, command, "to visit");
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
                // TODO AGGIUNGERE CONTROLLI
                response.setText(res);
            } else if (replyMessage.equals(botBusiness.genUpdatePlace("dream"))) {
                res = botBusiness.updatePlace(contact, command, "dream");
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
                // TODO AGGIUNGERE CONTROLLI
                response.setText(res);
            } /*else if (replyMessage.equals(botBusiness.genUpdatePlace("waist"))) {
                res = botBusiness.updatePlace(contact, command, "waist");
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
                // TODO AGGIUNGERE CONTROLLI
                response.setText(res);
            } else if (replyMessage.equals(botBusiness.genUpdatePlace("hip"))) {
                res = botBusiness.updatePlace(contact, command, "hip");
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
                // TODO AGGIUNGERE CONTROLLI
                response.setText(res);
            } */else if (replyMessage.startsWith(TAG_SUGGESTIONS_NEW)) {

                res = replyMessage;

                res = botBusiness.createSuggestion(contact, command);

                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());


                response.setText(res);
            } else if (replyMessage.split(" ")[0].equals(TAG_SUGGESTIONS_UPDATE)) {

                res = replyMessage + command;

                String[] rows = res.split("\n");


                res = botBusiness.updateSuggestion(contact, rows);
                response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());


                response.setText(res);
            }
        } else {// TODO measures , suggestions , adaptor
            //response = null; //TODO
            response.setReplyMarkup(CustomKeyboards.getDefaultKeyboard());
            res = botBusiness.back(contact);
            response.setText(" What? " + res);
        }

        return response;
    }

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

    public void onClosing() {
        //TODO
    }
}