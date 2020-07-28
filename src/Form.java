import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Form extends JFrame {
    private JTable table;
    private JButton startButton;
    private JButton exitButton;

    private boolean isGameActive = false;

    private int count = 0;

    public Form() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        initComponents();
        addListeners();
        setSize(350, 400);
        setResizable(false);
        setVisible(true);
    }

    private void initComponents() {
        table = new JTable(3, 3) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setRowHeight(100);
        for (int i = 0; i < table.getColumnCount(); ++i) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
            column.setCellRenderer(centerRenderer);
        }
        table.setFont(new Font("Hack", Font.PLAIN, 70));
        table.setEnabled(false);
        add(table);

        startButton = new JButton("Start");
        add(startButton);

        exitButton = new JButton("Exit");
        add(exitButton);
    }

    private void addListeners() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isGameActive)
                    return;
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (!table.getValueAt(row, col).equals(""))
                    return;
                if (count % 2 == 0)
                    table.setValueAt("X", row, col);
                else
                    table.setValueAt("O", row, col);
                ++count;
                checkGame();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isGameActive = true;
                clearTable();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void checkGame() {
        int n = table.getRowCount();
        String[][] cells = new String[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                cells[i][j] = (String) table.getValueAt(i, j);
            }
        }
        String winner = "";
        for (int i = 0; i < n; ++i) {
            if (!cells[i][0].equals("") && cells[i][0].equals(cells[i][1]) && cells[i][0].equals(cells[i][2])) {
                winner = cells[i][0];
                JOptionPane.showMessageDialog(this, winner);
                break;
            }
        }
        if (winner.isEmpty() && count == 9) {
            JOptionPane.showMessageDialog(this, "Draw!");
            isGameActive = false;
            count = 0;
        }
    }

    private void clearTable() {
        for (int i = 0; i < table.getRowCount(); ++i)
            for (int j = 0; j < table.getColumnCount(); ++j)
                table.setValueAt("", i, j);
    }
}
