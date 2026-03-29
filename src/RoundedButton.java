import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;

    public RoundedButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Adding mouse listener for hover effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define hover and pressed colors based on the current background
        Color bg = getBackground();
        hoverBackgroundColor = new Color(Math.min(bg.getRed() + 30, 255), Math.min(bg.getGreen() + 30, 255), Math.min(bg.getBlue() + 30, 255));
        pressedBackgroundColor = new Color(Math.max(bg.getRed() - 30, 0), Math.max(bg.getGreen() - 30, 0), Math.max(bg.getBlue() - 30, 0));

        if (getModel().isPressed()) {
            g2.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverBackgroundColor);
        } else {
            g2.setColor(bg);
        }

        // Draw a circle for square buttons, or round rectangle otherwise
        int diameter = Math.min(getWidth(), getHeight());
        int radius = diameter;
        // g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        // For standard iOS-like calculator look, mostly circular buttons
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getWidth()/2, getHeight()/2);

        super.paintComponent(g);
        g2.dispose();
    }
}
