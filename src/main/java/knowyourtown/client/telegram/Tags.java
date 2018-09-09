package knowyourtown.client.telegram;

public interface Tags {

    // COMMAND TAGS FOR THE TELEGRAM BOT
    //private static final String TAG_MEASURES = "/measures"; // show tag measures
   // String TAG_MEASURES_UPDATE = "/newmeasure";
  //  String TAG_REPLY_MEASURES_UPDATE1 = "1. Insert measure type";
   // String TAG_REPLY_MEASURES_UPDATE2 = "2. Now insert the value for the type";
  //  String TAG_REPLY_MEASURES_UPDATE3 = "3. Measure saved";
    //private static final String TAG_GOALS = "/goals";
    String TAG_SUGGESTIONS_UPDATE = "/evaluate";
    String TAG_SUGGESTIONS_DELETE = "/delete";
    String TAG_SUGGESTIONS_NEW = "/new suggestion";
    //String TAG_REPLY_GOALS_UPDATE1 = "1. Insert goal title";
    //String TAG_REPLY_GOALS_UPDATE2 = "2. Now insert the description for the goal ";
   // String TAG_REPLY_GOALS_UPDATE3 = "3. Goal saved";
    String TAG_BACK = "/back";
    String TAG_INFO = "/info";

    // COMMAND TAGS FOR THE TELEGRAM BOT
    String TAG_START = "/start";
    String TAG_REGISTRATION = "/registration";
    //String TAG_REGHEIGHT = "/regheight";
    //String TAG_REGWEIGHT = "/regweight";
    //String TAG_REGSEX = "/regsex";
   // String TAG_REGBIRTHDATE = "/regbirthdate";
   // String TAG_REGWAIST = "/regweist";
    //String TAG_REGHIP = "/reghip";
    String TAG_SEEPROFILE = "/seeprofile";
    String TAG_PLACES = "/places";
    String LBL_SHOWPLACES_VISITED = "visited";
    String TAG_SHOWPLACES_VISITED = "/showvisited";
    String LBL_SHOWPLACES_TO_VISIT = "to visit";
    String TAG_SHOWPLACES_TO_VISIT = "/showtovisit";
    String LBL_SHOWPLACES_DREAM = "dream";
    String TAG_SHOWPLACES_DREAM = "/showdream";


    String TAG_SHOWSUGGESTIONS = "/sugestions";
    // String TAG_GETBMI = "/bmi";
    //String TAG_ADDMEASURE = "/update";

    //String TAG_MEASURETYPE = "/measuretype";
    //String TAG_UPDATEMEASURE = "/update";
    String TAG_UPDATEVISITED = "/update visited";
    String TAG_UPDATETO_VISIT = "/update tovisit";
    String TAG_UPDATEDREAM = "/update dream";
   // String TAG_UPDATEWAIST = "/update waist";
    //String TAG_UPDATEHIP = "/update hip";


    //String TAG_GOALSUPDATE = "/goalsupdate";
    //String TAG_GOALTYPE = "/goaltype";
}
