package blitz.rijks;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class showLargeImage extends JFrame {
    public showLargeImage(ArtObject artObject) {
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
