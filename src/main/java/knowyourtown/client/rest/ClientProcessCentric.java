package knowyourtown.client.rest;


import com.google.gson.Gson;
import knowyourtown.client.models.Success;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

public class ClientProcessCentric extends RestClient {

    public static final String NEW_USER = "/user/new";
    public static final String USER_EXIST = "/user/exist";
    public static final String USER_PROFILE = "/user/profile";
    public static final String NEW_PLACE = "/place/new";
    public static final String SHOW_PLACES = "/place/show";
    public static final String DELETE_PLACES = "/place/delete";
    public static final String SHOW_SUGGESTIONS = "/suggestion/show";
    public static final String UPDATE_SUGGESTION = "/suggestion/new";
    public static final String CREATE_SUGGESTION = "/suggestion/newSugg";
    public static final String DELETE_SUGGESTION = "/suggestion/delete";

    private String serviceURI = "";

    public ClientProcessCentric(String serviceURI) {
        super(serviceURI);
        this.serviceURI = serviceURI;
    }

    /*public String getMeasures() {

        return getApi(USER_PROFILE+"/"+uid);

        Response response;
        int status;
        String res = "";

        response = service.path(SHOW_PLACES)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();

        status = response.getStatus();

        if (status == 200) {
            res = response.readEntity(String.class);
        }

        return res;
    }*/

    public boolean newPlace(String place) {
        return newApi(NEW_PLACE, place);
    }

    public boolean newNameSurname(String user) {
        return newApi(NEW_USER, user);
    }


    public String getSuggestions(Long uid) {
        return getApi(SHOW_SUGGESTIONS + "/" + uid);
    }


    public String seeProfile(Long uid) {
        return getApi(USER_PROFILE + "/" + uid);
    }

    public String getPlaces(Long uid, String type) {
        return getApi(SHOW_PLACES + "/" + uid + "/" + type);
    }

    public String deletePlacesbyType(Long uid, String type) {
        return getApi(DELETE_PLACES + "/" + uid +"/"+ type);
    }

    public boolean userExist(Long uid) {

        // This call return a json like this {success : False}
        String getRes = getApi(USER_EXIST + "/" + uid);
        System.out.println(getRes);
        if (getRes != "") {
            Gson gson = new Gson();
            HashMap<String, Boolean> res = new HashMap<String, Boolean>();
            res = (HashMap<String, Boolean>) gson.fromJson(getRes, res.getClass());
            return res.get("success");
        } else
            return false; //TODO se non c'è connessione torna che è falso, che è come dire "l'utente non esiste"
    }

    public boolean newApi(String path, String parameter) {

        Response response;
        int status;
        boolean res = false;

        // TODO CECK OF THE RESPONSE

        try {
            response = service.path(path)
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Entity.json(parameter));

            status = response.getStatus();

            System.out.println(":: POST -> status \"" + this.serviceURI + path + "\" : " + status);

            if (status == 202 || status == 200 || status == 201) {
                res = true;
            }
        } catch (javax.ws.rs.ProcessingException e) {
            System.err.println("Error : Connection refused in \'" + path + "\'");
        }

        return res;
    }

    public String getApi(String path) {
        Response response;
        int status;
        String res = "";

        try {
            response = service.path(path)
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .get();

            status = response.getStatus();

            System.out.println(":: GET -> status \"" + this.serviceURI + path + "\" : " + status);

            if (status == 200) {
                res = response.readEntity(String.class);
            }
        } catch (javax.ws.rs.ProcessingException e) {
            System.err.println("Error : Connection refused in \'" + path + "\'");
        }

        return res;
    }

    public Boolean updateSuggestion(Long uid, String oldTitle, String evaluation) {
        return newApi(UPDATE_SUGGESTION + "/" + uid + "/" + oldTitle, evaluation);
    }

    public Boolean createSuggestion(Long uid, String title) {
        return newApi(CREATE_SUGGESTION + "/" + uid , title);
    }

    public boolean updatePerson(String user) {
        return newUserRegistration(user);
    }


    public boolean newUserRegistration(String user) {
        return newApi(NEW_USER, user);
    }

    public boolean deleteSuggestion(Long uid, String title) {

        Gson gson = new Gson();
        //if (gson.fromJson(getApi(DELETE_SUGGESTION+"/"+uid+"/"+title.replace(" ","_")), Success.class).success)
        //    return true;

        String resjson = getApi(DELETE_SUGGESTION + "/" + uid + "/" + title.replace(" ", "_"));
        return gson.fromJson(resjson, Success.class).success;
    }

}
