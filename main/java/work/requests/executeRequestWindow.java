package work.requests;

import work.Roles.passengerRoleWindow;
import work.Roles.role;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class executeRequestWindow extends JFrame {
    private JButton back;
    private JTable table;
    private Vector strings;
    private Vector columnNames;
    private Integer countRows = 0;
    private JLabel count;

    private role userRole;
    public executeRequestWindow(Connection conn, String sql, role userRole, Vector strings, Integer num){
        super("Результат запроса");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;

        execute(conn, sql);
        table = new JTable(this.strings,columnNames);

        back = new JButton("Назад");
        back.addActionListener((e)->{
            setVisible(false);
            if (num == 3)
                new passengerRoleWindow(conn);
            else
                new requestsWindow(conn, userRole, strings);
        });

        count = new JLabel("Количество записей: " + countRows);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(back);

        JPanel labelPanel = new JPanel();
        labelPanel.add(count);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(labelPanel);
        mainPanel.add(buttonsPanel);
        add(mainPanel);
        setVisible(true);
    }
    private void execute(Connection conn, String sql){
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        ResultSetMetaData resultSetMetaData = null;
        columnNames = new Vector();
        strings = new Vector();
        try{
            resultSetMetaData = resultSet.getMetaData();
            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                columnNames.add(resultSetMetaData.getColumnName(i));

            while (resultSet.next()){
                Vector tmp = new Vector();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                    tmp.add(resultSet.getString(i));
                strings.add(tmp);
                countRows++;
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
