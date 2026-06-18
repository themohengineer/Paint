import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Painting extends JFrame {
    private JPanel canvas;
    private JButton clearButton;
    // List to store points for drawing lines
    private ArrayList<Point> points = new ArrayList<>();
    // Flag to control drawing state
    private boolean isDrawing = false;

    public Painting() {
        setTitle("My Professional Painting App");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Custom canvas panel
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);

                // Draw lines between consecutive points
                for (int i = 0; i < points.size() - 1; i++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(i + 1);

                    // Only draw if both points are valid (separator handling)
                    if (p1 != null && p2 != null) {
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }
        };
        canvas.setBackground(Color.WHITE);

        // Handle mouse clicks
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDrawing = true;
                points.add(null); // Add a separator for a new stroke
                points.add(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
            }
        });

        // Handle mouse movement
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    points.add(e.getPoint());
                    canvas.repaint(); // Redraw the component
                }
            }
        });

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            points.clear();
            canvas.repaint(); // Clear the screen
        });

        add(canvas, BorderLayout.CENTER);
        add(clearButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new Painting().setVisible(true));
    }
}