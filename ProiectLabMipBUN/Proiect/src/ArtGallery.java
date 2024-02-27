public class ArtGallery {
    private int id_gallery;
    private String location;
    private String phone;
    private String email;
    private String name;

    private boolean active;

    public ArtGallery(int id, String location, String phone, String email, String name,boolean active)
    {
        this.id_gallery = id;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.active = active;
    }


    // Getterii
    public int getId_gallery() {
        return id_gallery;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    // Setterii
    public void setId_gallery(int id_gallery) {
        this.id_gallery = id_gallery;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
