package knowyourtown.client.telegram;

public interface Tags {

    // COMMAND TAGS FOR THE TELEGRAM BOT

    String TAG_SUGGESTIONS_UPDATE = "/evaluate";
    String TAG_SUGGESTIONS_DELETE = "/delete";
    String TAG_SUGGESTIONS_NEW = "/newsuggestion";

    String TAG_BACK = "/back";
    String TAG_INFO = "/info";

    // COMMAND TAGS FOR THE TELEGRAM BOT
    String TAG_START = "/start";
    String TAG_REGISTRATION = "/registration";

    String TAG_SEEPROFILE = "/seeprofile";
    String TAG_PLACES = "/places";
    String LBL_SHOWPLACES_VISITED = "visited";
    String TAG_SHOWPLACES_VISITED = "/showvisited";
    String LBL_SHOWPLACES_TO_VISIT = "to visit";
    String TAG_SHOWPLACES_TO_VISIT = "/showtovisit";
    String LBL_SHOWPLACES_DREAM = "dream";
    String TAG_SHOWPLACES_DREAM = "/showdream";


    String TAG_SHOWSUGGESTIONS = "/suggestions";


    String TAG_UPDATEVISITED = "/create visited";
    String TAG_UPDATETO_VISIT = "/create tovisit";
    String TAG_UPDATEDREAM = "/create dream";
    String TAG_RESETVISITED = "/reset visited";
    String TAG_RESETTO_VISIT = "/reset tovisit";
    String TAG_RESETDREAM = "/reset dream";
    
}
