import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CustomArithmeticException extends ArithmeticException {
    public CustomArithmeticException(String message) {
        super(message);
    }
}

public class SwingApp extends JFrame {
    private JTextField inputField;
    private JButton loadButton;
    private JTable dataTable;
    private DefaultTableModel tableModel;

    public SwingApp() {
        setTitle("Data Loader");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        inputField = new JTextField(20);
        loadButton = new JButton("Load Data");
        panel.add(inputField);
        panel.add(loadButton);
        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[] { "Data" }, 0);
        dataTable = new JTable(tableModel);
        add(new JScrollPane(dataTable), BorderLayout.CENTER);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData(inputField.getText());
            }
        });
    }

    private void loadData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                if (!line.matches("\\d+")) {
                    throw new CustomArithmeticException("Invalid data format: " + line);
                }
                tableModel.addRow(new Object[] { line });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error opening file: " + e.getMessage());
        } catch (CustomArithmeticException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Number format error: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingApp app = new SwingApp();
            app.setVisible(true);
        });
    }
}
