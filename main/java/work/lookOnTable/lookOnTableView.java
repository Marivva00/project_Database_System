package work.lookOnTable;

import work.Tables.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Vector;

public class lookOnTableView extends JFrame {
    private JButton goBack;
    public lookOnTableView(Connection conn, String tableName, Vector columnNames, Vector strings){
        super("work with '" + tableName + "' table");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        goBack = new JButton("back");
        addActionListener(conn, tableName);

        JTable table = new JTable(strings, columnNames);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(goBackPanel);
        add(mainPanel);
        setVisible(true);
    }
    private void addActionListener(Connection conn, String tableName){
        goBack.addActionListener((e)->{
            setVisible(false);
            if (tableName == "gender")
                new genderTable(conn);
            if (tableName == "department")
                new departmentTable(conn);
            if (tableName == "medical")
                new medicalTable(conn);
            if (tableName == "carriage")
                new carriageTable(conn);
            if (tableName == "workers")
                new workersTable(conn);
        });
    }
}
