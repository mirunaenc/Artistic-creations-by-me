import java.util.Date;

public class ArtExhibition
{
    private int id_art_exhibition;
    private String name;
    private Date date;
    private String name_artgallery;
    private int nr_of_artworks;
    private boolean active;

    public ArtExhibition(int id_art_exhibition, String name, Date date, int nr_of_artworks,String name_artgallery,boolean active){
        this.id_art_exhibition = id_art_exhibition;
        this.name = name;
        this.date = date;
        this.name_artgallery = name_artgallery;
        this.nr_of_artworks = nr_of_artworks;
        this.active  = active;
    }

    // Getterii
    public int getId_art_exhibition() {
        return id_art_exhibition;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getName_artgallery() {
        return name_artgallery;
    }

    public int getNr_of_artworks() {
        return nr_of_artworks;
    }

    public boolean isActive() {
        return active;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNr_of_artworks(int nr_of_artworks) {
        this.nr_of_artworks = nr_of_artworks;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setName_artgallery(String name_artgallery) {
        this.name_artgallery = name_artgallery;
    }
}
