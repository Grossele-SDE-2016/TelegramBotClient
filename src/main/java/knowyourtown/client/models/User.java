package knowyourtown.client.models;

public class User {

    public Long uid;
    public String name;
    public String surname;
    public String sex;
    public String birthdate;
    public String nationality;
    public String birthplace;

    public User(Long uid, String name, String surname, String sex, String birthdate, String nationality, String birthplace) {
        this.uid = uid;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.birthplace = birthplace;
    }

}
