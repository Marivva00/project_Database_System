package work.Roles.Actions;

import work.Roles.passengerRoleWindow;

import javax.swing.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Vector;

public class getTicketInfo extends JFrame {
    private JLabel enterLabel;
    private JFormattedTextField numberField;
    private JButton enterButton;
    private JButton back;

    private Vector columnNames;
    private Vector strings;

    public getTicketInfo(Connection conn){
        super("Информация о билете");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        enterLabel = new JLabel("Введите id(номер) билета:");
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberField = new JFormattedTextField(numberFormat);
        numberField.setValue(new Integer(0));
        numberField.setColumns(2);
        enterButton = new JButton("показать");
        back = new JButton("Назад");

        addActionListener(conn);

        JPanel infoPanel = new JPanel();
        infoPanel.add(enterLabel);
        infoPanel.add(numberField);
        infoPanel.add(enterButton);
        JPanel backPanel = new JPanel();
        backPanel.add(back);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(infoPanel);
        mainPanel.add(backPanel);

        add(mainPanel);
        setVisible(true);
    }
    private void addActionListener(Connection conn){
        enterButton.addActionListener((e)->{
            setVisible(false);
            String id = numberField.getValue().toString();
            String select = "SELECT ticket_id, trip_id, ticket_seat_num, ticketclass.ticket_class_name, aviacompany.aviacomp_name FROM tickets JOIN ticketclass USING(ticket_class_id) JOIN aviacompany USING(aviacomp_id) WHERE(ticket_id = " + id + ")";
            ResultSet resultSet = null;
            PreparedStatement preparedStatement = null;
            columnNames = new Vector();
            strings = new Vector();
            try{
                preparedStatement = conn.prepareStatement(select);
                resultSet = preparedStatement.executeQuery();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                    columnNames.add(resultSetMetaData.getColumnName(i));
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings.add(tmp);
                }
                //resultSet.close();
                //preparedStatement.close();
            } catch (SQLException exception){
                exception.printStackTrace();
            }
            new resWindow(conn, columnNames, strings);
        });
        back.addActionListener((e)->{
            setVisible(false);
            new passengerRoleWindow(conn);
        });
    }
}
