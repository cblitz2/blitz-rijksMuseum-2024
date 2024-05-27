package blitz.rijks;

import com.andrewoid.ApiKey;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

public class RijksSearchFrame extends JFrame {

    public JButton prevPageButton = new JButton("Previous Page");
    public JButton nextPageButton = new JButton("Next Page");
    public JTextField search = new JTextField(80);
    public JPanel resultPanel = new JPanel();
    public int currentPage = 1;
    private final RijksService service;


    public RijksSearchFrame() {
        setTitle("Museum Frame");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.add(prevPageButton);
        searchPanel.add(search);
        searchPanel.add(nextPageButton);

        JScrollPane resultScrollPane = new JScrollPane(resultPanel);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(resultScrollPane, BorderLayout.CENTER);

        prevPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 1) {
                    currentPage--;
                    searchArtworks();
                }
            }
        });

        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage++;
                searchArtworks();
            }
        });

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPage = 1;
                searchArtworks();
            }
        });

        resultPanel.setLayout(new GridLayout(0, 4, 10, 10));
        service = new RijksServiceFactory().getService();
        searchArtworks();
    }

    private void searchArtworks() {
        ApiKey apiKey = new ApiKey();
        String query = search.getText().trim();
        if (query.isEmpty()) {
            service.getPage(apiKey.get(), currentPage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                    .subscribe(this::updateResults, throwable ->
                            showError(throwable.getMessage()));
        } else {
            service.query(apiKey.get(), currentPage, query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                    .subscribe(this::updateResults, throwable ->
                            showError(throwable.getMessage()));
        }
    }

    private void updateResults(ArtObjects response) {
        resultPanel.removeAll();
        for (ArtObject artObject : response.artObjects) {
            try {
                URL url = new URL(artObject.webImage.url);
                Image image = ImageIO.read(url);
                Image scaledImage = image.getScaledInstance(
                        200, -1, Image.SCALE_SMOOTH);
                ImageIcon imageIcon = new ImageIcon(scaledImage);
                JLabel imageLabel = new JLabel(imageIcon);
                imageLabel.setText(artObject.title);
                imageLabel.setToolTipText("<html>Title: " + artObject.title
                        + "<br>Artist: " + artObject.principalOrFirstMaker + "</html>");
                imageLabel.setHorizontalTextPosition(JLabel.CENTER);
                imageLabel.setVerticalTextPosition(JLabel.BOTTOM);
                imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        showLargeImage(artObject);
                    }
                });
                resultPanel.add(imageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
                "Failed to fetch data: " + message,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showLargeImage(ArtObject artObject) {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            URL url = new URL(artObject.webImage.url);
            Image image = ImageIO.read(url);
            Image scaledImage = image.getScaledInstance(
                    800, -1, Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(imageIcon);

            JScrollPane scrollPane = new JScrollPane(imageLabel);
            add(scrollPane);
            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}