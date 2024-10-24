import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;

public class AnalogClock extends JPanel {
    private int hours;
    private int minutes;
    private Timer timer;

    public AnalogClock() {
        setPreferredSize(new Dimension(400, 400));

        Calendar calendar = Calendar.getInstance();
        hours = calendar.get(Calendar.HOUR);
        minutes = calendar.get(Calendar.MINUTE);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClock();
            }
        });
        timer.start();
    }

    private void updateClock() {
        minutes++;
        if (minutes == 60) {
            minutes = 0;
            hours++;
            if (hours == 12) {
                hours = 0;
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g2.setColor(Color.BLACK);
        g2.drawOval(centerX - 150, centerY - 150, 300, 300);

        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians((i * 30) - 90);
            int x1 = (int) (centerX + 130 * Math.cos(angle));
            int y1 = (int) (centerY + 130 * Math.sin(angle));
            int x2 = (int) (centerX + 140 * Math.cos(angle));
            int y2 = (int) (centerY + 140 * Math.sin(angle));
            g2.drawLine(x1, y1, x2, y2);
        }

        double hourAngle = Math.toRadians((hours * 30) - 90 + (minutes / 2.0));
        int hourX = (int) (centerX + 80 * Math.cos(hourAngle));
        int hourY = (int) (centerY + 80 * Math.sin(hourAngle));
        g2.setStroke(new BasicStroke(4));
        g2.drawLine(centerX, centerY, hourX, hourY);

        double minuteAngle = Math.toRadians((minutes * 6) - 90);
        int minuteX = (int) (centerX + 120 * Math.cos(minuteAngle));
        int minuteY = (int) (centerY + 120 * Math.sin(minuteAngle));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(centerX, centerY, minuteX, minuteY);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analog Clock with Buttons");
        AnalogClock clock = new AnalogClock();

        JButton hourUpButton = new JButton("Hour +");
        JButton hourDownButton = new JButton("Hour -");
        JButton minuteUpButton = new JButton("Minute +");
        JButton minuteDownButton = new JButton("Minute -");

        hourUpButton.addActionListener(e -> {
            clock.hours = (clock.hours + 1) % 12;
            clock.repaint();
        });

        hourDownButton.addActionListener(e -> {
            clock.hours = (clock.hours == 0) ? 11 : clock.hours - 1;
            clock.repaint();
        });

        minuteUpButton.addActionListener(e -> {
            clock.minutes = (clock.minutes + 1) % 60;
            clock.repaint();
        });

        minuteDownButton.addActionListener(e -> {
            clock.minutes = (clock.minutes == 0) ? 59 : clock.minutes - 1;
            clock.repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hourUpButton);
        buttonPanel.add(hourDownButton);
        buttonPanel.add(minuteUpButton);
        buttonPanel.add(minuteDownButton);

        frame.add(clock, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
