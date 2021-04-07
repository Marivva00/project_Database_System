package work.delete;

import work.Tables.genderTable;
import work.Tables.workersTable;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class deleteRecordClass extends JFrame {
    JLabel info;
    JTextField id;
    JButton delete;
    JButton goBack;
    public deleteRecordClass(Connection conn, String tableName, String idName){
        super("work with '" + tableName + "' table");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        info = new JLabel("enter id of record you want to delete:");
        id = new JTextField(20);
        delete = new JButton("delete record");
        goBack = new JButton("back");

        addActionListener(conn, tableName, idName);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel infoPanel = new JPanel();
        infoPanel.add(info);
        infoPanel.add(id);
        JPanel deletePanel = new JPanel();
        deletePanel.add(goBack);
        deletePanel.add(delete);

        mainPanel.add(infoPanel);
        mainPanel.add(deletePanel);
        add(mainPanel);
        setVisible(true);
    }
    private void addActionListener(Connection conn, String tableName, String idName){
        delete.addActionListener((e)->{
            setVisible(false);
            String idDel = id.getText();
            String drop = "DELETE FROM " + tableName + " WHERE " + idName + " = " + idDel;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(drop);
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            new workersTable(conn);
        });
        goBack.addActionListener((e)->{
            setVisible(false);
            new workersTable(conn);
        });
    }
}
