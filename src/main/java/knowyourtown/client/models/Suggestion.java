package knowyourtown.client.models;

/**
 * Created by matteo on 16/06/17.
 */
public class Suggestion {

    public String title;
    public String location;
    public String description;
    public String date;
    public String evaluation;

    public Suggestion() {

    }

    public Suggestion(String title, String location, String description, String date, String evaluation) {
        this.title = title;
        this.location = location;
        this.description = description;
        this.date = date;
        this.evaluation = evaluation;
    }

    public String formattedText() {
        return this.title + "\n" + this.description + "\n" + this.evaluation + "\n";
    }

}
