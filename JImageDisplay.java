import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;

public class JImageDisplay extends JComponent{
    
    private BufferedImage imgBuf;
    static final long serialVersionUID = 0;

    public JImageDisplay(int width, int height){
        imgBuf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Dimension size = new Dimension(width, height);
        super.setPreferredSize(size);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(imgBuf, 0, 0, imgBuf.getWidth(), imgBuf.getHeight(), null);
    }

    public void drawPixel(int x, int y, int rgbColor) {
        imgBuf.setRGB(x, y, rgbColor);
    }

    public void clearImage() {
        for (int i = 0; i < imgBuf.getWidth(); i++)
            for (int j = 0; i < imgBuf.getHeight(); j++)
                drawPixel(i, j, 0);
    }
}
