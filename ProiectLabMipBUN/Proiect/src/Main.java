import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class Main {
    private static void resetFrame(JFrame f) {
        f.getContentPane().removeAll();

        JLabel lbltitle = new JLabel("Gestiunea unui muzeu de arte plastice");
        lbltitle.setFont(new Font("Arial", Font.BOLD + Font.ITALIC, 30));
        lbltitle.setHorizontalAlignment(JLabel.CENTER);
        f.add(lbltitle, BorderLayout.NORTH);

        ImageIcon imageIcon = new ImageIcon("D:\\Facultate\\Anul II\\MIP\\ProiectLabMipBUN\\Proiect\\art.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newImage);
        JLabel label = new JLabel(newImageIcon);
        label.setBounds(250, 100, 700, 700);
        f.add(label);

        f.revalidate();
        f.repaint();
    }

    public static void main(String[] args) {

        JFrame f = new JFrame("Artist");
        f.setLocationRelativeTo(null);
        f.setLayout(new FlowLayout ());
        f.setSize(1000, 1000);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        resetFrame(f);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuHome = new JMenu("Acasă");
        menuHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetFrame(f);
            }
        });

        JMenu menuArtists = new JMenu("Artiști");
        JMenuItem menuShowDeleteArtists = new JMenuItem("Listă artiști");
        JMenuItem menuAddEditArtist = new JMenuItem("Adaugă artiști");
        menuArtists.add(menuShowDeleteArtists);
        menuArtists.add(menuAddEditArtist);

        JMenu menuArtworks = new JMenu("Opere de artă");
        JMenuItem menuShowDeleteArtworks = new JMenuItem("Listă opere de artă");
        JMenuItem menuAddEditArtwork= new JMenuItem("Adaugă opere de artă");
        menuArtworks.add(menuShowDeleteArtworks);
        menuArtworks.add(menuAddEditArtwork);

        JMenu menuArtCollectors = new JMenu("Colecționari de artă");
        JMenuItem menuShowDeleteArtCollectors = new JMenuItem("Listă colecționari de artă");
        JMenuItem menuAddEditArtCollector= new JMenuItem("Adaugă colecționari de artă");
        menuArtCollectors.add(menuShowDeleteArtCollectors);
        menuArtCollectors.add(menuAddEditArtCollector);

        JMenu menuArtExhibitions = new JMenu("Expoziții");
        JMenuItem menuShowDeleteArtExhibitions = new JMenuItem("Listă expoziții");
        JMenuItem menuAddEditArtExhibition = new JMenuItem("Adaugă expoziții");
        menuArtExhibitions.add(menuShowDeleteArtExhibitions);
        menuArtExhibitions.add(menuAddEditArtExhibition);

        JMenu menuArtGalleries = new JMenu("Galerii de artă");
        JMenuItem menuShowDeleteArtGalleries = new JMenuItem("Listă galerii de artă");
        JMenuItem menuAddEditArtGallery = new JMenuItem("Adaugă galerii de artă");
        menuArtGalleries.add(menuShowDeleteArtGalleries);
        menuArtGalleries.add(menuAddEditArtGallery);

        menuBar.add(menuHome);
        menuBar.add(menuArtists);
        menuBar.add(menuArtworks);
        menuBar.add(menuArtCollectors);
        menuBar.add(menuArtExhibitions);
        menuBar.add(menuArtGalleries);
        f.setJMenuBar(menuBar);



        menuAddEditArtist.addActionListener(e -> {
            f.getContentPane().removeAll(); // sterge toate componentele existente

            JPanel artistPanel = new JPanel();
            artistPanel.setLayout(new GridLayout(6, 2));

            JLabel lblId = new JLabel("ID: ");
            JTextField txtId = new JTextField();
            artistPanel.add(lblId);
            artistPanel.add(txtId);

            JLabel lblName = new JLabel("Nume: ");
            JTextField txtName = new JTextField();
            txtName.setPreferredSize(new Dimension(400, 50));
            artistPanel.add(lblName);
            artistPanel.add(txtName);

            JLabel lblBirthDate = new JLabel("Data nașterii: ");
            UtilDateModel model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            artistPanel.add(lblBirthDate);
            artistPanel.add(datePicker);

            JLabel lblNationality = new JLabel("Naționalitate: ");
            JTextField txtNationality = new JTextField();
            artistPanel.add(lblNationality);
            artistPanel.add(txtNationality);

            txtId.addActionListener(E -> {
                int id_artist = Integer.parseInt(txtId.getText());
                if (id_artist > 0) {
                    Artist artist = DbUtils.getArtist(id_artist);
                    if (artist != null) {
                        txtName.setText(artist.getName());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(artist.getBirthDate());
                        model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                        txtNationality.setText(artist.getNationality());
                    }
                } else {
                    txtName.setText("");
                    model.setValue(null);
                    txtNationality.setText("");
                }
            });


            JButton saveButton = new JButton("Salvează");
            saveButton.setPreferredSize(new Dimension(100, 30));

            JButton cancelButton = new JButton("Anulează");
            cancelButton.setPreferredSize(new Dimension(100, 30));
            cancelButton.addActionListener(E -> {
                resetFrame(f);
                f.revalidate();
                f.repaint();
            });

            saveButton.addActionListener(E -> {
                int id_artist = Integer.parseInt(txtId.getText());
                if (id_artist < 0) {
                    id_artist = DbUtils.getMaxIdArtist() + 1;
                }
                Date birthDate = (Date) datePicker.getModel().getValue();
                Artist newArtist = new Artist(id_artist, birthDate, txtNationality.getText(),
                        txtName.getText(), true);
                if (id_artist > DbUtils.getMaxIdArtist()) {
                    DbUtils.addArtist(newArtist);
                } else {
                    DbUtils.updateArtist(newArtist);
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);

            artistPanel.add(buttonPanel);

            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            JLabel title = new JLabel("Artist                     ");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(title);

            f.add(titlePanel, BorderLayout.NORTH);
            f.add(artistPanel, BorderLayout.CENTER);
            f.revalidate();
            f.repaint();
        });

        menuAddEditArtwork.addActionListener(e -> {
            f.getContentPane().removeAll();

            JPanel artworkPanel = new JPanel();
            artworkPanel.setLayout(new GridLayout(7, 2));

            JLabel lblId = new JLabel("ID: ");
            JTextField txtId = new JTextField();
            artworkPanel.add(lblId);
            artworkPanel.add(txtId);

            JLabel lblArtistName = new JLabel("Nume artist: ");
            JTextField txtArtistName = new JTextField();
            txtArtistName.setPreferredSize(new Dimension(400, 50));
            artworkPanel.add(lblArtistName);
            artworkPanel.add(txtArtistName);

            JLabel lblTitle = new JLabel("Titlu: ");
            JTextField txtTitle = new JTextField();
            artworkPanel.add(lblTitle);
            artworkPanel.add(txtTitle);

            JLabel lblSize = new JLabel("Dimensiuni: ");
            JTextField txtSize = new JTextField();
            artworkPanel.add(lblSize);
            artworkPanel.add(txtSize);

            JLabel lblYear = new JLabel("An creație: ");
            JTextField txtYear = new JTextField();
            artworkPanel.add(lblYear);
            artworkPanel.add(txtYear);

            txtId.addActionListener(E -> {
                int id_artwork = Integer.parseInt(txtId.getText());
                if (id_artwork > 0) {
                    Artwork artwork = DbUtils.getArtwork(id_artwork);
                    if (artwork != null) {
                        txtArtistName.setText(artwork.getName_artist());
                        txtTitle.setText(artwork.getTitle());
                        txtSize.setText(artwork.getSize());
                        txtYear.setText(String.valueOf(artwork.getYear_of_creation()));
                    }
                } else {
                    txtArtistName.setText("");
                    txtTitle.setText("");
                    txtSize.setText("");
                    txtYear.setText("");
                }
            });

            JButton saveButton = new JButton("Salvează");
            saveButton.setPreferredSize(new Dimension(100, 30));

            JButton cancelButton = new JButton("Anulează");
            cancelButton.setPreferredSize(new Dimension(100, 30));
            cancelButton.addActionListener(E -> {
                resetFrame(f);
                f.revalidate();
                f.repaint();
            });
            saveButton.addActionListener(E -> {
                int id_artwork = Integer.parseInt(txtId.getText());
                if (id_artwork < 0) {
                    id_artwork = DbUtils.getMaxIdOpera() + 1;
                }
                Artwork newArtwork = new Artwork(id_artwork, txtTitle.getText(), txtSize.getText(), Integer.parseInt(txtYear.getText()), txtArtistName.getText(), true);
                if (id_artwork > DbUtils.getMaxIdOpera()) {
                    DbUtils.addArtwork(newArtwork);
                } else {
                    DbUtils.updateArtwork(newArtwork);
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);

            artworkPanel.add(buttonPanel);

            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            JLabel title = new JLabel("Operă de artă                     ");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(title);

            f.add(titlePanel, BorderLayout.NORTH);

            f.add(artworkPanel, BorderLayout.CENTER);
            f.revalidate();
            f.repaint();
        });

        menuAddEditArtCollector.addActionListener(e -> {
            f.getContentPane().removeAll();

            JPanel artCollectorPanel = new JPanel();
            artCollectorPanel.setLayout(new GridLayout(8, 2));

            JLabel lblId = new JLabel("ID: ");
            JTextField txtId = new JTextField();
            artCollectorPanel.add(lblId);
            artCollectorPanel.add(txtId);

            JLabel lblName = new JLabel("Nume: ");
            JTextField txtName = new JTextField();
            txtName.setPreferredSize(new Dimension(400, 50));
            artCollectorPanel.add(lblName);
            artCollectorPanel.add(txtName);

            JLabel lblCnp = new JLabel("CNP: ");
            JTextField txtCnp = new JTextField();
            artCollectorPanel.add(lblCnp);
            artCollectorPanel.add(txtCnp);

            JLabel lblEmail = new JLabel("Email: ");
            JTextField txtEmail = new JTextField();
            artCollectorPanel.add(lblEmail);
            artCollectorPanel.add(txtEmail);

            JLabel lblPhone = new JLabel("Telefon: ");
            JTextField txtPhone = new JTextField();
            artCollectorPanel.add(lblPhone);
            artCollectorPanel.add(txtPhone);

            JLabel lblAddress = new JLabel("Adresă: ");
            JTextField txtAddress = new JTextField();
            artCollectorPanel.add(lblAddress);
            artCollectorPanel.add(txtAddress);

            JLabel lblCollectionValue = new JLabel("Valoare colecție: ");
            JTextField txtCollectionValue = new JTextField();
            artCollectorPanel.add(lblCollectionValue);
            artCollectorPanel.add(txtCollectionValue);

            txtId.addActionListener(E -> {
                int id_artCollector = Integer.parseInt(txtId.getText());
                if (id_artCollector > 0) {
                    ArtCollector artCollector = DbUtils.getArtCollector(id_artCollector);
                    if (artCollector != null) {
                        txtName.setText(artCollector.getName());
                        txtAddress.setText(artCollector.getAddress());
                        txtEmail.setText(artCollector.getEmail());
                        txtCnp.setText(artCollector.getCnp());
                        txtPhone.setText(artCollector.getPhone());
                        txtCollectionValue.setText(String.valueOf(artCollector.getCollection_value()));
                    }
                } else {
                    txtName.setText("");
                    txtAddress.setText("");
                    txtEmail.setText("");
                    txtCnp.setText("");
                    txtPhone.setText("");
                    txtCollectionValue.setText("");
                }
            });

            JButton saveButton = new JButton("Salvează");
            saveButton.setPreferredSize(new Dimension(100, 30));

            JButton cancelButton = new JButton("Anulează");
            cancelButton.setPreferredSize(new Dimension(100, 30));
            cancelButton.addActionListener(E -> {
                resetFrame(f);
                f.revalidate();
                f.repaint();
            });

            saveButton.addActionListener(E -> {
                int id_artCollector = Integer.parseInt(txtId.getText());
                if (id_artCollector < 0) {
                    id_artCollector = DbUtils.getMaxIdArtCollector() + 1;
                }
                ArtCollector newArtCollector = new ArtCollector(id_artCollector,txtName.getText(), txtAddress.getText(), txtEmail.getText(),
                        txtCnp.getText(),txtPhone.getText(),Double.parseDouble(txtCollectionValue.getText()),true);
                if (id_artCollector > DbUtils.getMaxIdArtCollector()) {
                    DbUtils.addArtCollector(newArtCollector);
                } else {
                    DbUtils.updateArtCollector(newArtCollector);
                }
            });


            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);

            artCollectorPanel.add(buttonPanel);

            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            JLabel title = new JLabel("Colecționar de artă                     ");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(title);

            f.add(titlePanel, BorderLayout.NORTH);

            f.add(artCollectorPanel, BorderLayout.CENTER);
            f.revalidate();
            f.repaint();
        });

        menuAddEditArtExhibition.addActionListener(e -> {
            f.getContentPane().removeAll();

            JPanel artExhibitionPanel = new JPanel();
            artExhibitionPanel.setLayout(new GridLayout(8, 2));

            JLabel lblId = new JLabel("ID: ");
            JTextField txtId = new JTextField();
            artExhibitionPanel.add(lblId);
            artExhibitionPanel.add(txtId);

            JLabel lblName = new JLabel("Nume: ");
            JTextField txtName = new JTextField();
            txtName.setPreferredSize(new Dimension(400, 50));
            artExhibitionPanel.add(lblName);
            artExhibitionPanel.add(txtName);

            JLabel lblDate = new JLabel("Dată: ");
            UtilDateModel model = new UtilDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            artExhibitionPanel.add(lblDate);
            artExhibitionPanel.add(datePicker);

            JLabel lblArtGallery = new JLabel("Nume galerie de artă: ");
            JTextField txtArtGallery = new JTextField();
            artExhibitionPanel.add(lblArtGallery);
            artExhibitionPanel.add(txtArtGallery);

            JLabel lblArtworksNr = new JLabel("Număr opere expuse: ");
            JTextField txtArtworksNr = new JTextField();
            artExhibitionPanel.add(lblArtworksNr);
            artExhibitionPanel.add(txtArtworksNr);

            txtId.addActionListener(E -> {
                int id_artExhibition = Integer.parseInt(txtId.getText());
                if (id_artExhibition > 0) {
                    ArtExhibition art_exhibition = DbUtils.getArtExhibition(id_artExhibition);
                    if (art_exhibition != null) {
                        txtName.setText(art_exhibition.getName());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(art_exhibition.getDate());
                        model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                        txtArtGallery.setText(art_exhibition.getName_artgallery());
                        txtArtworksNr.setText(String.valueOf(art_exhibition.getNr_of_artworks()));
                    }
                } else {
                    txtName.setText("");
                    model.setValue(null);
                    txtArtGallery.setText("");
                    txtArtworksNr.setText("");
                }
            });

            JButton saveButton = new JButton("Salvează");
            saveButton.setPreferredSize(new Dimension(100, 30));

            JButton cancelButton = new JButton("Anulează");
            cancelButton.setPreferredSize(new Dimension(100, 30));
            cancelButton.addActionListener(E -> {
                resetFrame(f);
                f.revalidate();
                f.repaint();
            });

            saveButton.addActionListener(E -> {
                int id_artExhibition = Integer.parseInt(txtId.getText());
                if (id_artExhibition < 0) {
                    id_artExhibition = DbUtils.getMaxIdArtExhibition() + 1;
                }
                Date date = (Date) datePicker.getModel().getValue();
                ArtExhibition newArtExhibition = new ArtExhibition(id_artExhibition,txtName.getText(), date, Integer.parseInt(txtArtworksNr.getText()),
                        txtArtGallery.getText(),true);
                if (id_artExhibition > DbUtils.getMaxIdArtExhibition()) {
                    DbUtils.addArtExhibition(newArtExhibition);
                } else {
                    DbUtils.updateArtExhibition(newArtExhibition);
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);

            artExhibitionPanel.add(buttonPanel);

            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            JLabel title = new JLabel("Expoziție de artă                     ");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(title);

            f.add(titlePanel, BorderLayout.NORTH);

            f.add(artExhibitionPanel, BorderLayout.CENTER);
            f.revalidate();
            f.repaint();
        });

        menuAddEditArtGallery.addActionListener(e -> {
            f.getContentPane().removeAll();

            JPanel artGalleryPanel = new JPanel();
            artGalleryPanel.setLayout(new GridLayout(7, 2));

            JLabel lblId = new JLabel("ID: ");
            JTextField txtId = new JTextField();
            artGalleryPanel.add(lblId);
            artGalleryPanel.add(txtId);

            JLabel lblName = new JLabel("Nume: ");
            JTextField txtName = new JTextField();
            txtName.setPreferredSize(new Dimension(400, 50));
            artGalleryPanel.add(lblName);
            artGalleryPanel.add(txtName);

            JLabel lblLocation = new JLabel("Locație: ");
            JTextField txtLocation = new JTextField();
            artGalleryPanel.add(lblLocation);
            artGalleryPanel.add(txtLocation);

            JLabel lblPhone = new JLabel("Telefon: ");
            JTextField txtPhone = new JTextField();
            artGalleryPanel.add(lblPhone);
            artGalleryPanel.add(txtPhone);

            JLabel lblEmail = new JLabel("Email: ");
            JTextField txtEmail = new JTextField();
            artGalleryPanel.add(lblEmail);
            artGalleryPanel.add(txtEmail);

            txtId.addActionListener(E -> {
                int id_artGallery = Integer.parseInt(txtId.getText());
                if (id_artGallery > 0) {
                    ArtGallery artGallery = DbUtils.getArtGallery(id_artGallery);
                    if (artGallery != null) {
                        txtName.setText(artGallery.getName());
                        txtLocation.setText(artGallery.getLocation());
                        txtPhone.setText(artGallery.getPhone());
                        txtEmail.setText(artGallery.getEmail());
                    }
                } else {
                    txtName.setText("");
                    txtLocation.setText("");
                    txtPhone.setText("");
                    txtEmail.setText("");
                }
            });

            JButton saveButton = new JButton("Salvează");
            saveButton.setPreferredSize(new Dimension(100, 30));

            JButton cancelButton = new JButton("Anulează");
            cancelButton.setPreferredSize(new Dimension(100, 30));
            cancelButton.addActionListener(E -> {
                resetFrame(f);
                f.revalidate();
                f.repaint();
            });

            saveButton.addActionListener(E -> {
                int id_artGallery = Integer.parseInt(txtId.getText());
                if (id_artGallery < 0) {
                    id_artGallery = DbUtils.getMaxIdArtGallery() + 1;
                }
                ArtGallery newArtGallery = new ArtGallery(id_artGallery,txtLocation.getText(),
                        txtPhone.getText(), txtEmail.getText(),txtName.getText(),true);
                if (id_artGallery > DbUtils.getMaxIdArtGallery()) {
                    DbUtils.addArtGallery(newArtGallery);
                } else {
                    DbUtils.updateArtGallery(newArtGallery);
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            artGalleryPanel.add(buttonPanel);

            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            JLabel title = new JLabel("Galerie de artă                     ");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            titlePanel.add(title);

            f.add(titlePanel, BorderLayout.NORTH);

            f.add(artGalleryPanel, BorderLayout.CENTER);
            f.revalidate();
            f.repaint();
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        menuShowDeleteArtists.addActionListener(e -> {
            List<Artist> artists = DbUtils.getAllActiveArtists();
            if (artists != null) {
                String[] columnNames = {"Nume", "Data nașterii", "Naționalitate"};
                Object[][] data = new Object[artists.size()][columnNames.length];
                for (int i = 0; i < artists.size(); i++) {
                    Artist artist = artists.get(i);
                    data[i][0] = artist.getName();
                    data[i][1] = artist.getBirthDate();
                    data[i][2] = artist.getNationality();
                }
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(tableModel);
                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        int row = table.rowAtPoint(event.getPoint());
                        Artist selectedArtist = artists.get(row);
                       if(event.getClickCount() == 1)
                        {
                            int response = JOptionPane.showConfirmDialog(null, "Sigur doriți să ștergeți acest artist?", "Confirmare", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.YES_OPTION) {
                                DbUtils.deleteArtist(selectedArtist);
                        }
                    }
                    }
                });
                f.getContentPane().removeAll();

                JPanel titlePanel = new JPanel();
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
                JLabel title = new JLabel("Artiști                     ");
                title.setFont(new Font("Arial", Font.BOLD, 24));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);
                titlePanel.add(title);

                f.add(titlePanel, BorderLayout.NORTH);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 600));
                table.setPreferredScrollableViewportSize(new Dimension(750, 400));
                f.add(scrollPane, BorderLayout.CENTER);

                f.revalidate();
                f.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Nu s-au putut obține datele din baza de date.");
            }
        });

        menuShowDeleteArtworks.addActionListener(e -> {
            List<Artwork> artworks = DbUtils.getAllActiveArtworks();
            if (artworks != null) {
                String[] columnNames = {"Titlu", "Dimensiuni", "Anul creației","Nume artist"};
                Object[][] data = new Object[artworks.size()][columnNames.length];
                for (int i = 0; i < artworks.size(); i++) {
                    Artwork artwork = artworks.get(i);
                    data[i][0] = artwork.getTitle();
                    data[i][1] = artwork.getSize();
                    data[i][2] = artwork.getYear_of_creation();
                    data[i][3] = artwork.getName_artist();
                }
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(tableModel);
                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        int row = table.rowAtPoint(event.getPoint());
                        Artwork selectedArtwork = artworks.get(row);
                   if(event.getClickCount() == 1)
                        {
                            int response = JOptionPane.showConfirmDialog(null, "Sigur doriți să ștergeți această operă de artă?", "Confirmare", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.YES_OPTION) {
                                DbUtils.deleteArtwork(selectedArtwork);
                        }
                    }
                    }
                });
                f.getContentPane().removeAll();

                JPanel titlePanel = new JPanel();
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
                JLabel title = new JLabel("Opere de Artă           ");
                title.setFont(new Font("Arial", Font.BOLD, 24));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);
                titlePanel.add(title);

                f.add(titlePanel, BorderLayout.NORTH);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 600));
                table.setPreferredScrollableViewportSize(new Dimension(750, 400));
                f.add(scrollPane, BorderLayout.CENTER);

                f.revalidate();
                f.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Nu s-au putut obține datele din baza de date.");
            }
        });
        menuShowDeleteArtExhibitions.addActionListener(e -> {
            List<ArtExhibition> artExhibitions = DbUtils.getAllActiveArtExhibitions();
            if (artExhibitions != null) {
                String[] columnNames = {"Nume", "Dată","Număr opere expuse","Nume galerie de artă"};
                Object[][] data = new Object[artExhibitions.size()][columnNames.length];
                for (int i = 0; i < artExhibitions.size(); i++) {
                    ArtExhibition artExhibition = artExhibitions.get(i);
                    data[i][0] = artExhibition.getName();
                    data[i][1] = artExhibition.getDate();
                    data[i][2] = artExhibition.getNr_of_artworks();
                    data[i][3] = artExhibition.getName_artgallery();
                }
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(tableModel);
                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        int row = table.rowAtPoint(event.getPoint());
                        ArtExhibition selectedArtExhibition = artExhibitions.get(row);
                 if(event.getClickCount() == 2)
                        {
                            int response = JOptionPane.showConfirmDialog(null, "Sigur doriți să ștergeți această expoziție?", "Confirmare", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.YES_OPTION) {
                                DbUtils.deleteArtExhibition(selectedArtExhibition);
                        }
                    }
                    }
                });
                f.getContentPane().removeAll();

                JPanel titlePanel = new JPanel();
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
                JLabel title = new JLabel("Expoziții de Artă");
                title.setFont(new Font("Arial", Font.BOLD, 24));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);
                titlePanel.add(title);

                f.add(titlePanel, BorderLayout.NORTH);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 600));
                table.setPreferredScrollableViewportSize(new Dimension(750, 400));
                f.add(scrollPane, BorderLayout.CENTER);

                f.revalidate();
                f.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Nu s-au putut obține datele din baza de date.");
            }
        });

        menuShowDeleteArtCollectors.addActionListener(e -> {
            List<ArtCollector> artCollectors = DbUtils.getAllActiveArtCollectors();
            if (artCollectors != null) {
                String[] columnNames = {"Nume", "Adresă", "Email","Cnp","Telefon","Valoarea colecției"};
                Object[][] data = new Object[artCollectors.size()][columnNames.length];
                for (int i = 0; i < artCollectors.size(); i++) {
                    ArtCollector artCollector = artCollectors.get(i);
                    data[i][0] = artCollector.getName();
                    data[i][1] = artCollector.getAddress();
                    data[i][2] = artCollector.getEmail();
                    data[i][3] = artCollector.getCnp();
                    data[i][4] = artCollector.getPhone();
                    data[i][5] = artCollector.getCollection_value();
                }
                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(tableModel);
                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        int row = table.rowAtPoint(event.getPoint());
                        ArtCollector selectedArtCollector = artCollectors.get(row);
                      if(event.getClickCount() == 1)
                        {
                            int response = JOptionPane.showConfirmDialog(null, "Sigur doriți să ștergeți acest colecționar de artă?", "Confirmare", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.YES_OPTION) {
                                DbUtils.deleteArtCollector(selectedArtCollector);
                            }
                        }
                    }
                });
                f.getContentPane().removeAll();

                JPanel titlePanel = new JPanel();
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
                JLabel title = new JLabel("Colecționari de Artă");
                title.setFont(new Font("Arial", Font.BOLD, 24));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);
                titlePanel.add(title);

                f.add(titlePanel, BorderLayout.NORTH);

                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 600));
                table.setPreferredScrollableViewportSize(new Dimension(750, 400));
                f.add(scrollPane, BorderLayout.CENTER);

                f.revalidate();
                f.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Nu s-au putut obține datele din baza de date.");
            }
        });

        menuShowDeleteArtGalleries.addActionListener(e -> {

            List<ArtGallery> artGalleries = DbUtils.getAllActiveArtGalleries();
            if (artGalleries != null) {
                String[] columnNames = {"Nume", "Locație", "Telefon","Email"};
                Object[][] data = new Object[artGalleries.size()][columnNames.length];
                for (int i = 0; i < artGalleries.size(); i++) {
                    ArtGallery artGallery = artGalleries.get(i);
                    data[i][0] = artGallery.getName();
                    data[i][1] = artGallery.getLocation();
                    data[i][2] = artGallery.getPhone();
                    data[i][3] = artGallery.getEmail();
                }

                DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
                JTable table = new JTable(tableModel);
                table.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        int row = table.rowAtPoint(event.getPoint());
                        ArtGallery selectedArtGallery = artGalleries.get(row);
                        if(event.getClickCount() == 1)
                        {
                            int response = JOptionPane.showConfirmDialog(null, "Sigur doriți să ștergeți această galerie de artă?", "Confirmare", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.YES_OPTION) {
                                DbUtils.deleteArtGallery(selectedArtGallery);
                            }
                        }
                    }
                });

                f.getContentPane().removeAll();


                JPanel titlePanel = new JPanel();
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
                JLabel title = new JLabel("Galerii de Artă     ");
                title.setFont(new Font("Arial", Font.BOLD, 24));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);
                titlePanel.add(title);

                f.add(titlePanel, BorderLayout.NORTH);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 600));
                table.setPreferredScrollableViewportSize(new Dimension(750, 400));
                f.add(scrollPane, BorderLayout.CENTER);
                f.revalidate();
                f.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Nu s-au putut obține datele din baza de date.");
            }
        });

// Facerea ferestrei vizibile
        f.setVisible(true);
    }
}