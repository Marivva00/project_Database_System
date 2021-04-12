package work.Tables;

import work.MainMenuWindow;
import work.inserts.insertGender;
import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class genderTable extends JFrame {
    JButton lookOnTable;
    JButton goBack;
    JButton addRecord;
    //JButton delRecord;
    Vector columnNames = null;
    Vector strings = null;
    public genderTable(Connection conn){
        super("work with 'gender' table");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lookOnTable = new JButton("look on table");
        goBack = new JButton("back");
        addRecord = new JButton("add record");
        //delRecord = new JButton("delete record");

        //addActionListeners(conn);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack);
        JPanel lookOnTablePanel = new JPanel();
        lookOnTablePanel.add(lookOnTable);
        JPanel addRecordPanel = new JPanel();
        addRecordPanel.add(addRecord);
        //JPanel deletePanel = new JPanel();
        //deletePanel.add(delRecord);

        mainPanel.add(lookOnTablePanel);
        mainPanel.add(addRecordPanel);
        //mainPanel.add(deletePanel);
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
            String sql = "SELECT * FROM gender";
            executeAndGetResult(conn, sql);
            new lookOnTableView(conn, "gender", columnNames, strings);
        });
        addRecord.addActionListener((e)->{
            setVisible(false);
            new insertGender(conn);
        });
        /*delRecord.addActionListener((e)->{
            setVisible(false);
            new deleteRecord(conn, "gender", "gen_id");
        });
    }*/
    private void executeAndGetResult(Connection conn, String sqlSelect) {
        columnNames = new Vector();
        columnNames.add("gen_id");
        columnNames.add("gen_name");
        strings = new Vector();
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        ResultSetMetaData resultSetMetaData = null;
        try {
            resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Vector tmp = new Vector();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                    tmp.add(resultSet.getString(i));
                strings.add(tmp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
