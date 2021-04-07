package work.inserts;

import work.Tables.genderTable;

import javax.swing.*;
import java.sql.*;

public class insertGender extends JFrame {
    JButton add;
    JLabel gen_nameLabel;
    JTextField gen_nameText;
    public insertGender(Connection conn){
        super("work with 'gender' table");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gen_nameLabel = new JLabel("new gen_name:");
        gen_nameText = new JTextField(20);
        add = new JButton("add record");

        addActionListener(conn);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel infoPanel = new JPanel();
        infoPanel.add(gen_nameLabel);
        infoPanel.add(gen_nameText);
        JPanel addPanel = new JPanel();
        addPanel.add(add);

        mainPanel.add(infoPanel);
        mainPanel.add(addPanel);
        add(mainPanel);
        setVisible(true);
    }
    private void addActionListener(Connection conn){
        add.addActionListener((e)->{
            setVisible(false);
            String gen_nameNew = gen_nameText.getText();
            String insert = "INSERT INTO gender(gen_name) VALUES('" + gen_nameNew + "')";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(insert);
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            new genderTable(conn);
        });
    }
}
