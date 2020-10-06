import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay img;
    private FractalGenerator gen;
    private Rectangle2D.Double range;

    public FractalExplorer(int displaySize){
        this.displaySize = displaySize;
        img = new JImageDisplay(displaySize, displaySize);
        gen = new Mandelbrot();
        range = new Rectangle2D.Double();
    }

    public void createAndSHowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(img, BorderLayout.CENTER);
        JButton button = new JButton("Reset");
        button.setActionCommand("reset");
        button.addActionListener(new ButtonHandler());
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.getContentPane().addMouseListener(new MouseHandler());

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

    }

    private void drawFractal(){
        float hue;

        for (int y = 0; y < displaySize; y++){
            for (int x = 0; x < displaySize; x++){
                // x is the pixel-coordinate; xCoord is the coordinate in the fractal's space
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
                int iters = gen.numIterations(xCoord, yCoord);
                hue = 0.7f + (float) iters / 200f;
                int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                img.drawPixel(x, y, rgbColor);
            }
        }
        img.repaint();
    }

    public class ButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if (cmd.equals("reset")){
                range = new Rectangle2D.Double();
                gen.getInitialRange(range);
                drawFractal();
            }
        }
    }

    public class MouseHandler extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, e.getY());
            gen.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
	        drawFractal();
        }
    }

    public static void main(String[] args){
        FractalExplorer window = new FractalExplorer(800);
        window.createAndSHowGUI();
        window.drawFractal();
    }
}
