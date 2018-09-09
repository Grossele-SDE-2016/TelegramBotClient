package knowyourtown.client.telegram;

import com.google.gson.Gson;

import knowyourtown.client.models.Suggestion;
import knowyourtown.client.models.Place;
import knowyourtown.client.models.User;
import knowyourtown.client.rest.ClientProcessCentric;

import java.util.HashMap;

/**
 * Created by matteo on 15/06/17.
 */
public class BotBusiness implements Tags {

    private String serviceUri;
    private String botName;

    public BotBusiness(String serviceUri, String botName) {
        this.serviceUri = serviceUri;
        this.botName = botName;
    }

    public String onStart(User contact) {
        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        // is the userid already registered?
        if (!cp.userExist(contact.uid))
            return genNotRegisteredResponse(contact);

        return this.genOnStartResponse(contact);
    }

    public String back(User contact) {

        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        // is the userid already registered?
        if (!cp.userExist(contact.uid))
            return genNotRegisteredResponse(contact);

        return new String("Back to main menù");
    }

    public String seeProfile(User contact) {

        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        // is the userid already registered?
        if (!cp.userExist(contact.uid))
            return genNotRegisteredResponse(contact);

        String res = cp.seeProfile(contact.uid);

        if (res.equals(""))
            return this.genErrorMessage("seeProfile");

        return this.genProfile(res);
    }

    public String showPlaces(User contact, String type) {

        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        // is the userid already registered?
        if (!cp.userExist(contact.uid))
            return genNotRegisteredResponse(contact);

        String res = "";
        res = this.genPlacesList(cp.getPlaces(contact.uid, type));
        

        if (res.equals(""))
            return this.genErrorMessage("showPlaces");

        return res;
    }

    public String updatePlace(User contact, String parameter, String type) {

        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        // is the userid already registered?
        if (!cp.userExist(contact.uid))
            return genNotRegisteredResponse(contact);

        // Replace , with . for avoid errors
        parameter = parameter.replace(",", ".");

        // generating JSON
        Gson gson = new Gson();

        String[] rows = parameter.split("\n");
        
        String place = gson.toJson(new Place(contact.uid, type, rows[0], rows[1], ""));
        // is all good? if not report the error
        if (!cp.newPlace(place))
            return this.genErrorMessage("updatePlace");
        return this.genUpdatePlaceSucess(type);
       
    }

   /* public boolean updateSuggestionCheck_newTitle(String title) {
        return !title.contains("\n");
    }

    public boolean updateSuggestionCheck_newDescription(String description) {
        return !description.contains("\n");
    }

    public boolean updateSuggestionCheck_newCondition(String condition) {
        return condition.equals("<") || condition.equals("<=") || condition.equals(">") || condition.equals(">=");
    }

    public boolean updateSuggestionCheck_type(String type) {

        return type.toLowerCase().equals("weight") || type.toLowerCase().equals("step") ||
                type.toLowerCase().equals("height");
    }

    public boolean updateSuggestionCheck_number(String number) {
        try {
            return !Double.valueOf(number).isNaN();
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
    }*/

    public String updateSuggestion(User contact, String[] rows) {

        // old title
        // new title
        // description
        // higher or lower
        // Quantity

        ClientProcessCentric cp = new ClientProcessCentric(serviceUri);

        // is the userid already registered?
        if (!cp.userExist(contact.uid))
            return genNotRegisteredResponse(contact);


       

        System.out.println("=============");
        System.out.println(rows[0]);
        String[] vOldTitle;
        String oldTitle = "";
        vOldTitle = rows[0].split(" ");
        for (int i = 1; i < vOldTitle.length; i++) {
            oldTitle += vOldTitle[i];
            if (i < vOldTitle.length - 1) {
                oldTitle += " ";
            }
        }

        String evaluation = rows[1].split(":")[1];
        
        cp.updateSuggestion(contact.uid, oldTitle, evaluation);

        return genUpdateSuggestionSuccess(oldTitle);

    }

    private String genUpdateSuggestionSuccess(String oldTitle) {
        return new String("Suggestion " + oldTitle + " updated");
    }

    public String getSuggestions(User contact) {
        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        String res = cp.getSuggestions(contact.uid);

        if (res.equals(""))
            return this.genErrorMessage("getSuggestions");

        return res;

    }

    private String genUpdatePlaceSucess(String type) {
        return new String("Place of " + type + " updated");
    }

    private String genPlacesList(String json) {

        Gson gson = new Gson();

        Place[] places;
        places = gson.fromJson(json, Place[].class);

        String res = "Places : \n\n";

        for (Place m : places) {
            res += m.placeType +
                    " | " + m.name +
                    " | " + m.location +
                    " | " + m.date + "\n";
        }

        return res;

    }

    private String genProfile(String res) {

        // Format the output
        Gson gson = new Gson();
        HashMap<String, String> profile = new HashMap<String, String>();
        profile = (HashMap<String, String>) gson.fromJson(res, profile.getClass());

        String strOut = "Name : " + profile.get("name") +
                "\nSurname : " + profile.get("surname") +
                "\nHeight : " + profile.get("height") +
                "\nBirthdate : " + profile.get("birthdate") +
                "\nNationality : " + profile.get("nationality") +
                "\nBirthplace : " + profile.get("birthplace");

        return strOut;
    }

    private String genRegFinish(User contact) {
        return new String("Good! The registration operation is finished! \n") + genInfoMessage();
    }

    /*public String genRegWeight(String command) {
        //return new String("Wonderful! Just a bit of patience, this is the last step! Insert your actual weight! " +
        //        "(write " + TAG_REGWEIGHT + " followed by your weight)");
        return new String(command + "\nYour weight (kg) : ");
    }*/

    public String genInfoMessage() {

        return new String("Bot commands : \n\n" +
                "/start : starts a new session of the bot\n\n" +
                "/seeprofile : user can see his actual profile\n\n" +
                "/places : show the places of the user, and allows him to modify the places \n\n" +
                "/suggestions : show the user suggestions and allows the user to evaluate and delete them\n\n" +
                "/info : shows this message"
        );
    }

    public String genErrorMessage(String where) {
        return new String("Something went wrong... in " + where);
    }

    // Responses
    public String genNotRegisteredResponse(User contact) {
        return new String("Hi " + contact.name + "! Welcome to " + this.botName + ". For using our bot, you have" +
                " to insert a couple of informations about yourself! Proceed with the " + TAG_REGISTRATION);
    }

    /*public String genRegHeight(String command) {
        //return new String("Well done " + contact.name + ", now insert your height! (write " + TAG_REGHEIGHT + " " +
        //        "followed by your height)");
        return new String(command + "\nYour height (cm) :");
    }*/

    private String genOnStartResponse(User contact) {
        return new String("Hi again " + contact.name + "!");
    }

    public String addPlace(User contact) {
        return new String("Place types :"); //TODO
    }

    public String genUpdatePlace(String type) {
        return new String("Insert new " + type + " value:");

    }

   /* public String getBmi(User contact) {

        String res = "";


        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        // is the userid already registered?
        if (!cp.userExist(contact.uid))
            return genNotRegisteredResponse(contact);

        res = cp.getBmi(contact.uid);

        if (res.equals(""))
            return this.genErrorMessage("getBmi");

        return genBmi(res);
    }

    private String genBmi(String bmijson) {

        Gson gson = new Gson();
        Bmi bmi = gson.fromJson(bmijson, Bmi.class);

        String res = "";

        res += "\nYour bmi value is ";
        res += bmi.bmiValue + " and your ideal weight is ";
        res += bmi.idealWeight + "\n";

        res += "You are ";
        res += bmi.bmiStatus + "\n";

        res += "\nYou have a ";
        res += bmi.bmiRisk + "\n";

        res += "\nYour risk is ";
        res += bmi.whrStatus + "\n";

        res += "\nYou should eat ";
        res += bmi.bmrValue + " calories per day";

        return res; //TODO
    }*/

    public String genRegBirthDate(String command) {
        return new String(command + "\nBirthdate (YYYY/MM//DD) : ");
    }

    public String genRegSurname() {
        return new String("Surname : ");
    }

    public String genRegSex(String command) {
        return new String(command + "\nSex (m or f) : ");
    }

    public String genRegNationality(String command) {
        return new String(command + "\nYour nationality : ");
    }

    public String genRegBirthplace(String command) {
        return new String(command + "\nYour birthplace : ");
    }

    public String registration(User contact, String command) {

        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        if (cp.userExist(contact.uid))
            return this.genErrorMessage("registration - user");

        System.out.println(command);

        String[] rows = command.split("\n");

        // row 1 surname
        String surname = rows[1].split(":")[1];
        surname = surname.replace(" ", "");
        // row 2 sex
        String sex = rows[2].split(":")[1];
        sex = sex.replace(" ", "");
        // row 3 birthdate
        String birthdate = rows[3].split(":")[1];
        birthdate = birthdate.replace(" ", "");
        // row 4 nationality
        String nationality = rows[4].split(":")[1];
        nationality = nationality.replace(" ", "");
        // row 5 birthplace
        String birthplace = rows[5].split(":")[1];
        birthplace = birthplace.replace(" ", "");

        Gson gson = new Gson();

        /* Registration of the new User */
        // generating JSON
        String userJson = gson.toJson(new User(contact.uid, contact.name, surname, sex, birthdate, nationality, birthplace));


        if (!cp.newUserRegistration(userJson))
            return this.genErrorMessage("registration - user");

        return this.genRegFinish(contact);
    }

    public String deleteSuggestion(User contact, String title) {

        ClientProcessCentric cp = new ClientProcessCentric(this.serviceUri);

        if (!cp.deleteSuggestion(contact.uid, title))
            genErrorMessage("deleteSuggestion");
        return genDeleteSuggestionMessage(title);
    }

    private String genDeleteSuggestionMessage(String title) {
        return new String("Suggestion " + title + " successfully removed");
    }
}
