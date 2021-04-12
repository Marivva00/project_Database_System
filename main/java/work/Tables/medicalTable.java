package work.Tables;

import work.MainMenuWindow;
import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class medicalTable extends JFrame {
    JButton lookOnTable;
    JButton goBack;
    Vector columnNames = null;
    Vector strings = null;
    public medicalTable(Connection conn){
        super("work with 'medical' table");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lookOnTable = new JButton("look on table");
        goBack = new JButton("back");

        //addActionListeners(conn);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack);
        JPanel lookOnTablePanel = new JPanel();
        lookOnTablePanel.add(lookOnTable);

        mainPanel.add(lookOnTablePanel);
        mainPanel.add(goBackPanel);
        add(mainPanel);
        setVisible(true);
    }
    /*private void addActionListeners(Connection conn){
        goBack.addActionListener((e)->{
            setVisible(false);
            new MainMenuWindow(conn);
        });
        lookOnTable.addActionListener((e)->{
            setVisible(false);
            columnNames = new Vector();
            columnNames.add("med_id");
            columnNames.add("med_date");
            columnNames.add("med_status");
            strings = new Vector();
            String select = "SELECT * FROM medical";
            ResultSet resultSet = null;
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(select);
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException exception){
                exception.printStackTrace();
            }
            ResultSetMetaData resultSetMetaData = null;
            try {
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        if (i == resultSetMetaData.getColumnCount()) {
                            if (Integer.parseInt(resultSet.getString(i)) == 0)
                                tmp.add("failed");
                            else
                                tmp.add("pass");
                        } else
                            tmp.add(resultSet.getString(i));
                    }
                    strings.add(tmp);
                }
                new lookOnTableView(conn, "medical", columnNames, strings);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }*/
}
