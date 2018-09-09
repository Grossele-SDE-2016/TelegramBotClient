package knowyourtown.client.models;

public class Place {

    public Long uid;
    public String placeType;
    public String location;
    public String name;
    public String date;

    public Place(Long uid, String placeType, String location, String name, String date) {
        this.uid = uid;
        this.placeType = placeType;
        this.location = location;
        this.name = name;
        this.date = date;
    }
}
