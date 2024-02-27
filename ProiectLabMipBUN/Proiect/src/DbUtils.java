import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbUtils {

    public static Connection getConnection()
    {

        Connection conn;

        String connectionUrl;
        connectionUrl = "jdbc:sqlserver://localhost:1433;database=LabMip;user=sa;password=1234;encrypt=true;trustServerCertificate=true";

        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(connectionUrl);

            if(conn==null)
            {
                System.out.println("Failed - conn is null");
                return null;
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
        return conn;
    }

    public static List<Artist> getAllActiveArtists()
    {
        Connection conn = getConnection();

        if(conn == null)
        {
            return null;
        }

        List<Artist> artists = new ArrayList<Artist>();

        try
        {
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("spArtistSelectAllActive");

            while (rs.next())
            {
                int id_artist =rs.getInt("id_artist") ;
                Date birthDate = rs.getDate("data_nasterii");
                String nationality = rs.getString("nationalitate");
                String name = rs.getString("nume");
                Artist artist = new Artist(id_artist,birthDate,nationality,name,true);
                artists.add(artist);

            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }

        return artists;
    }


    public static List<Artwork> getAllActiveArtworks() {
        Connection conn = getConnection();

        if(conn == null)
        {
            return null;
        }

        List<Artwork> artworks = new ArrayList<Artwork>();

        try
        {
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("spOperaDeArtaSelectAllActive");

            while (rs.next())
            {
                int id_artwork =rs.getInt("id_opera") ;
                String title = rs.getString("titlu");
                String size = rs.getString("dimensiuni");
                int year_of_creation = rs.getInt("anul_creatiei");
                String name_artist = rs.getString("nume_artist");
                Artwork artwork = new Artwork(id_artwork, title, size, year_of_creation, name_artist,true);

                artworks.add(artwork);

            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }

        return artworks;
    }

    public static List<ArtGallery> getAllActiveArtGalleries() {
        Connection conn = getConnection();

        if(conn == null)
        {
            return null;
        }

        List<ArtGallery> artGalleries = new ArrayList<ArtGallery>();

        try
        {
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("spGalerieDeArtaSelectAllActive");

            while (rs.next())
            {
                int id_artGallery =rs.getInt("id_galerie") ;
                String location = rs.getString("locatie");
                String phone = rs.getString("telefon");
                String email = rs.getString("email");
                String name = rs.getString("nume");

                ArtGallery artGallery = new ArtGallery(id_artGallery,location,phone,email,name,true);

                artGalleries.add(artGallery);

            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }

        return artGalleries;
    }

    public static List<ArtCollector> getAllActiveArtCollectors() {
        Connection conn = getConnection();

        if(conn == null)
        {
            return null;
        }

        List<ArtCollector> artCollectors = new ArrayList<ArtCollector>();

        try
        {
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("spColectionarDeArtaSelectAllActive");

            while (rs.next())
            {
                int id_artCollector =rs.getInt("id_colectionar");
                String name = rs.getString("nume");
                String address = rs.getString("adresa");
                String email = rs.getString("email");
                String cnp = rs.getString("cnp");
                String phone = rs.getString("telefon");
                double collection_value = Double.parseDouble(rs.getString("valoarea_colectiei"));

                ArtCollector artCollector = new ArtCollector(id_artCollector,name,address,email,cnp,phone,collection_value,true);

                artCollectors.add(artCollector);

            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }

        return artCollectors;
    }
    public static List<ArtExhibition> getAllActiveArtExhibitions() {
        Connection conn = getConnection();

        if(conn == null)
        {
            return null;
        }

        List<ArtExhibition> artExhibitions = new ArrayList<ArtExhibition>();

        try {
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery("spExpozitieSelectAllActive");

            while (rs.next()) {
                int id_artExhibition = rs.getInt("id_expozitie");
                String name = rs.getString("nume");
                Date data = rs.getDate("data");
                int nr_of_artworks = rs.getInt("numar_opere_expuse");
                boolean active = rs.getBoolean("active");
                String name_gallery = rs.getString("nume_galerie");

                ArtExhibition artExhibition = new ArtExhibition(id_artExhibition, name, data, nr_of_artworks, name_gallery,true);

                artExhibitions.add(artExhibition);
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }


        return artExhibitions;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void deleteArtist(Artist artist) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spDeactivateArtist(?)}");
            cs.setInt(1, artist.getId());
            cs.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void deleteArtwork(Artwork artwork) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spDeactivateOperaDeArta(?)}");
            cs.setInt(1, artwork.getId_artwork());
            cs.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void deleteArtCollector(ArtCollector artCollector) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spDeactivateColectionarDeArta(?)}");
            cs.setInt(1, artCollector.getId_art_collector());
            cs.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void deleteArtExhibition(ArtExhibition artExhibition) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spDeactivateExpozitie(?)}");
            cs.setInt(1, artExhibition.getId_art_exhibition());
            cs.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteArtGallery(ArtGallery artGallery) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spDeactivateGalerieDeArta(?)}");
            cs.setInt(1, artGallery.getId_gallery());
            cs.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void updateArtist(Artist artist) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spArtistUpdate(?, ?, ?, ?)}");
            cs.setInt(1, artist.getId());
            cs.setDate(2, new java.sql.Date(artist.getBirthDate().getTime()));
            cs.setString(3, artist.getNationality());
            cs.setString(4, artist.getName());
            cs.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static int getGalleryId(String name_artgallery) {
        Connection conn = getConnection();
        if (conn == null) {
            return -1;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spGalerieDeArtaGetId(?, ?)}");
            cs.setString(1, name_artgallery);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            return cs.getInt(2);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1;
    }

    public static void updateArtExhibition(ArtExhibition artExhibition) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            int id_galerie = getGalleryId(artExhibition.getName_artgallery());
            if (id_galerie == -1) {
                throw new RuntimeException("Galeria de artă " + artExhibition.getName_artgallery() + " nu există sau nu mai activează în prezent.");
            }

            Date currentDate = new Date();
            if (artExhibition.getDate().before(currentDate)) {
                artExhibition.setActive(false);
            }

            CallableStatement cs = conn.prepareCall("{call spExpozitieUpdate(?, ?, ?, ?, ?)}");
            cs.setInt(1, artExhibition.getId_art_exhibition());
            cs.setString(2, artExhibition.getName());
            cs.setDate(3, new java.sql.Date(artExhibition.getDate().getTime()));
            cs.setInt(4, artExhibition.getNr_of_artworks());
            cs.setInt(5, id_galerie);
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static int getArtistId(String name_artist) {
        Connection conn = getConnection();
        if (conn == null) {
            return -1;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spArtistGetId(?, ?)}");
            cs.setString(1, name_artist);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.execute();
            return cs.getInt(2);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1;
    }


    public static void updateArtwork(Artwork artwork) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            int id_artist = getArtistId(artwork.getName_artist());
            if (id_artist == -1) {
                throw new RuntimeException("Artistul " + artwork.getName_artist() + " nu există.");
            }

            CallableStatement cs = conn.prepareCall("{call spOperaDeArtaUpdate(?, ?, ?, ?, ?)}");
            cs.setInt(1, artwork.getId_artwork());
            cs.setString(2, artwork.getTitle());
            cs.setString(3, artwork.getSize());
            cs.setInt(4, artwork.getYear_of_creation());
            cs.setInt(5, id_artist);
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void updateArtCollector(ArtCollector artCollector) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spColectionarDeArtaUpdate(?, ?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, artCollector.getId_art_collector());
            cs.setString(2, artCollector.getName());
            cs.setString(3, artCollector.getAddress());
            cs.setString(4, artCollector.getEmail());
            cs.setString(5, artCollector.getCnp());
            cs.setString(6, artCollector.getPhone());
            cs.setDouble(7, artCollector.getCollection_value());
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void updateArtGallery(ArtGallery artGallery) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spGalerieDeArtaUpdate(?, ?, ?, ?, ?)}");
            cs.setInt(1, artGallery.getId_gallery());
            cs.setString(2, artGallery.getLocation());
            cs.setString(3, artGallery.getPhone());
            cs.setString(4, artGallery.getEmail());
            cs.setString(5, artGallery.getName());
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void addArtist(Artist artist) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spArtistInsert(?, ?, ?, ?)}");
            cs.setInt(1, artist.getId());
            cs.setDate(2, new java.sql.Date(artist.getBirthDate().getTime()));
            cs.setString(3, artist.getNationality());
            cs.setString(4, artist.getName());
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addArtwork(Artwork artwork) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            int id_artist = getArtistId(artwork.getName_artist());
            if (id_artist == -1) {
                throw new RuntimeException("Artistul " + artwork.getName_artist() + " nu există.");
            }
            CallableStatement cs = conn.prepareCall("{call spOperaDeArtaInsert(?, ?, ?, ?, ?)}");
            cs.setInt(1, artwork.getId_artwork());
            cs.setString(2, artwork.getTitle());
            cs.setString(3, artwork.getSize());
            cs.setInt(4, artwork.getYear_of_creation());
            cs.setInt(5, id_artist);
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void addArtExhibition(ArtExhibition artExhibition) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            int id_galerie = getGalleryId(artExhibition.getName_artgallery());
            if (id_galerie == -1) {
                throw new RuntimeException("Galeria de artă " + artExhibition.getName_artgallery() + " nu există sau nu mai activează în prezent.");
            }

            CallableStatement cs = conn.prepareCall("{call spExpozitieInsert(?, ?, ?, ?, ?)}");
            cs.setInt(1, artExhibition.getId_art_exhibition());
            cs.setString(2, artExhibition.getName());
            cs.setDate(3, new java.sql.Date(artExhibition.getDate().getTime()));
            cs.setInt(4, id_galerie);
            cs.setInt(5, artExhibition.getNr_of_artworks());
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addArtCollector(ArtCollector collector) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spColectionarDeArtaInsert(?, ?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, collector.getId_art_collector());
            cs.setString(2, collector.getName());
            cs.setString(3, collector.getAddress());
            cs.setString(4, collector.getEmail());
            cs.setString(5, collector.getCnp());
            cs.setString(6, collector.getPhone());
            cs.setDouble(7, collector.getCollection_value());
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addArtGallery(ArtGallery gallery) {
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spGalerieDeArtaInsert(?, ?, ?, ?, ?)}");
            cs.setInt(1, gallery.getId_gallery());
            cs.setString(2, gallery.getLocation());
            cs.setString(3, gallery.getPhone());
            cs.setString(4, gallery.getEmail());
            cs.setString(5, gallery.getName());
            cs.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    ////////////////////////////////////////////////////////////

    public static Artist getArtist(int id) {
        Artist artist = null;
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spArtistGetActiveById(?, ?)}");
            cs.setInt(1, id);
            cs.setInt(2, 1); // active = 1
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                int id_artist = rs.getInt("id_artist");
                Date birthDate = rs.getDate("data_nasterii");
                String nationality = rs.getString("nationalitate");
                String name = rs.getString("nume");
                artist = new Artist(id_artist, birthDate, nationality, name, true);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return artist;
    }

    public static int getMaxIdArtist() {
        int maxId = 0;
        Connection conn = getConnection();
        if (conn == null) {
            return maxId;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call spArtistGetMaxId(?)}");
            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();
            maxId = cs.getInt(1);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return maxId;
    }

    public static Artwork getArtwork(int id) {
        Artwork artwork = null;
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call GetOperaDeArtaById(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                int id_artwork = rs.getInt("id_opera");
                String title = rs.getString("titlu");
                String size = rs.getString("dimensiuni");
                Integer year = rs.getInt("anul_creatiei");
                String name_artist = rs.getString("nume_artist");
                artwork = new Artwork(id_artwork, title, size, year, name_artist, true);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return artwork;
    }

    public static int getMaxIdOpera() {
        int maxId = 0;
        Connection conn = getConnection();
        if (conn == null) {
            return maxId;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call GetMaxIdOperaDeArta()}");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return maxId;
    }

    public static ArtCollector getArtCollector(int id) {
        ArtCollector artCollector = null;
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call GetColectionarDeArtaById(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                int id_artCollector = rs.getInt("id_colectionar");
                String name = rs.getString("nume");
                String address = rs.getString("adresa");
                String email = rs.getString("email");
                String cnp = rs.getString("cnp");
                String phone = rs.getString("telefon");
                Double collection_value = rs.getDouble("valoarea_colectiei");
                artCollector = new ArtCollector(id_artCollector, name, address, email, cnp, phone, collection_value, true);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return artCollector;
    }
    public static int getMaxIdArtCollector() {
        int maxId = 0;
        Connection conn = getConnection();
        if (conn == null) {
            return maxId;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call GetMaxIdColectionarDeArta()}");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return maxId;
    }

    public static ArtExhibition getArtExhibition(int id) {
        ArtExhibition artExhibition = null;
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call GetExpozitieById(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                int id_artExhibition = rs.getInt("id_expozitie");
                String name = rs.getString("nume");
                Date data = rs.getDate("data");
                int nr_of_artworks = rs.getInt("numar_opere_expuse");
                boolean active = rs.getBoolean("active");
                String name_gallery = rs.getString("nume_galerie");

                artExhibition = new ArtExhibition(id_artExhibition, name, data, nr_of_artworks, name_gallery, active);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return artExhibition;
    }

    public static int getMaxIdArtExhibition() {
        int maxId = 0;
        Connection conn = getConnection();
        if (conn == null) {
            return maxId;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call GetMaxIdExpozitie()}");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return maxId;
    }

    public static ArtGallery getArtGallery(int id) {
        ArtGallery artGallery = null;
        Connection conn = getConnection();
        if (conn == null) {
            return null;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call GetGalerieDeArtaById(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                int id_artGallery = rs.getInt("id_galerie");
                String location = rs.getString("locatie");
                String phone = rs.getString("telefon");
                String email = rs.getString("email");
                String name = rs.getString("nume");
                boolean active = rs.getBoolean("active");

                artGallery = new ArtGallery(id_artGallery, location, phone, email, name, active);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return artGallery;
    }

    public static int getMaxIdArtGallery() {
        int maxId = 0;
        Connection conn = getConnection();
        if (conn == null) {
            return maxId;
        }

        try {
            CallableStatement cs = conn.prepareCall("{call GetMaxIdGalerieDeArta()}");
            ResultSet rs = cs.executeQuery();

            if (rs.next()) {
                maxId = rs.getInt("max_id");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return maxId;
    }
}