import java.util.Date;

public class Artist {
    private int id_artist;
    private Date birthDate;
    private String nationality;
    boolean active;
    private String name;

    public Artist(int id_artist, Date birthDate, String nationality, String name,boolean active) {
        this.id_artist = id_artist;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.name = name;
        this.active = active;
    }


    public int getId() {
        return this.id_artist;
    }

    public String getName() {
        return this.name;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getNationality() {
        return this.nationality;
    }
    public boolean isActive(){
        return this.active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }


}
