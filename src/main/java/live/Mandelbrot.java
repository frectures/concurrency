package live;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Mandelbrot {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1024; // leave room for title bar and task bar
    public static final int DETAIL = 1024;

    private static double top = -1.0;
    private static double left = -2.0;
    private static double zoom = 1.0 / 512.0;

    private static BufferedImage image;
    private static JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            JLabel label = new JLabel(new ImageIcon(image));
            label.addMouseListener(mouseListener());

            frame = new JFrame("The Mandelbrot Set");
            frame.add(label);
            frame.pack();
            frame.setVisible(true);
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            calculateWithMeasurement();
        });
    }

    private static MouseListener mouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON1) {
                    // zoom in 4x
                    left = (event.getX() - WIDTH / 8.0) * zoom + left;
                    top = (event.getY() - HEIGHT / 8.0) * zoom + top;
                    zoom = zoom / 4.0;
                } else {
                    // zoom out 2x
                    left = (event.getX() - WIDTH) * zoom + left;
                    top = (event.getY() - HEIGHT) * zoom + top;
                    zoom = zoom * 2.0;
                }
                calculateWithMeasurement();
            }
        };
    }

    private static void calculateWithMeasurement() {
        long before = System.nanoTime();
        calculate();
        long after = System.nanoTime();
        long delta = after - before;
        double seconds = delta / 1_000_000_000.0;
        System.out.println(seconds + " seconds");
    }

    private static void calculate() {
        // TODO parallelize
        for (int y = 0; y < HEIGHT; ++y) {
            double ci = y * zoom + top;

            for (int x = 0; x < WIDTH; ++x) {
                double cr = x * zoom + left;

                double zr = 0.0;
                double zi = 0.0;
                int color = 0x000000; // paint The Mandelbrot Set black
                int i;
                for (i = 0; i < DETAIL; ++i) {
                    double zrzr = zr * zr;
                    double zizi = zi * zi;
                    if (zrzr + zizi >= 4) {
                        // c is outside The Mandelbrot Set
                        color = PALETTE[i & 15];
                        break;
                    }
                    zi = 2.0 * zr * zi + ci;
                    zr = zrzr - zizi + cr;
                }
                image.setRGB(x, y, color);
            }
        }
        frame.repaint();
    }

    private static final int[] PALETTE = {
            0x00421E0F, 0x0019071A, 0x0009012F, 0x00040449,
            0x00000764, 0x000C2C8A, 0x001852B1, 0x00397DD1,
            0x0086B5E5, 0x00D3ECF8, 0x00F1E9BF, 0x00F8C95F,
            0x00FFAA00, 0x00CC8000, 0x00995700, 0x006A3403,
    };
}
