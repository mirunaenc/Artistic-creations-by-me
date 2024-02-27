public class Artwork {
    private int id_artwork;
    private String title;
    private String size;
    private int year_of_creation;
    private String name_artist;
    boolean active;


    public Artwork(int id, String title, String size,int year_of_creation,String name_artist,boolean active) {
        this.id_artwork = id;
        this.title = title;
        this.size = size;
        this.year_of_creation = year_of_creation;
        this.name_artist = name_artist;
        this.active = active;
    }

    // Getterii
    public int getId_artwork() {
        return id_artwork;
    }

    public String getTitle() {
        return title;
    }

    public String getSize() {
        return size;
    }

    public int getYear_of_creation() {
        return year_of_creation;
    }

    public String getName_artist() {
        return name_artist;
    }
    public boolean isActive(){
        return this.active;
    }

    // Setterii
    public void setId_artwork(int id_artwork) {
        this.id_artwork = id_artwork;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setYear_of_creation(int year_of_creation) {
        this.year_of_creation = year_of_creation;
    }

    public void setName_artist(String name_artist) {
        this.name_artist = name_artist;
    }
    public void setActive(boolean active)
    {
        this.active = active;
    }

}
