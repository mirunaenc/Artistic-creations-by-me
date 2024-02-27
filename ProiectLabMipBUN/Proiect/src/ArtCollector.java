public class ArtCollector {
    private int id_art_collector;
    private String name;
    private String address;
    private String email;
    private String cnp;
    private String phone;
    private double collection_value;
    boolean active;


    public ArtCollector(int id, String name, String address, String email,String cnp, String phone, double collection_value, boolean active
)
    {
        this.id_art_collector = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.cnp = cnp;
        this.phone = phone;
        this.collection_value = collection_value;
        this.active=active;
    }

    // Getterii
    public int getId_art_collector() {
        return id_art_collector;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
    public boolean isActive(){
        return this.active;
    }

    public String getEmail() {
        return email;
    }

    public String getCnp() {
        return cnp;
    }

    public String getPhone() {
        return phone;
    }

    public double getCollection_value() {
        return collection_value;
    }

    // Setterii
    public void setId_art_collector(int id_art_collector) {
        this.id_art_collector = id_art_collector;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCollection_value(double collection_value) {
        this.collection_value = collection_value;
    }
    public void setActive(boolean active)
    {
        this.active = active;
    }

}
