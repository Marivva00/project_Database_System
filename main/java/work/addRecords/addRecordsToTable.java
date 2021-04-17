package work.addRecords;

import org.apache.ibatis.jdbc.SQL;
import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addRecordsToTable extends JFrame {
    private JButton ok;
    private JButton back;
    private JPanel addPanel;
    private JTextField textField1;
    private Integer toDraw = 1;

    public addRecordsToTable(Connection conn, Integer dep,  String tableName){
        super("add record to table '" + tableName + "'");
        //setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.PAGE_AXIS));

        getTable(conn, dep, tableName);

        ok = new JButton("add");
        back = new JButton("back");
        ok.addActionListener((e)->{
            setVisible(false);
            toDraw = 0;
            getTable(conn, dep, tableName);
            new lookOnTableView(conn, dep, tableName);
        });
        back.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, dep, tableName);
        });

        JPanel buttons = new JPanel();
        buttons.add(back);
        buttons.add(ok);

        addPanel.add(buttons);
        add(addPanel);
        setVisible(true);

    }
    private void simpleWindowWithOneLabel(String labelName){
        setSize(400, 200);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Enter " + labelName + " here...");

        textField1 = new JTextField(20);
        Color colorLog = textField1.getCaretColor();
        textField1.setForeground(Color.LIGHT_GRAY);
        textField1.setText("Enter " + labelName + " here...");
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event){
                textField1.setForeground(colorLog);
                if (textField1.getText().equals("Enter " + labelName + " here..."))
                    textField1.setText("");
            }
            @Override
            public void focusLost(FocusEvent event) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.LIGHT_GRAY);
                    textField1.setText("Enter " + labelName + " here...");
                }
            }
        });

        JPanel labelPanel = new JPanel();
        labelPanel.add(label);
        JPanel textPanel = new JPanel();
        textPanel.add(textField1);
        addPanel.add(labelPanel);
        addPanel.add(textPanel);
    }
    private void getTable(Connection conn, Integer dep, String tableName){
        if (dep == 0)
            switch (tableName){
                case "department": departmentTable(conn); break;
                case "medical": medicalTable(conn); break;
                case "aviacompany": aviacompanyTable(conn); break;
                case "ticketClass": ticketClassTable(conn); break;
                case "tripStatus": tripStatusTable(conn); break;
                case "tripType": tripTypeTable(conn); break;
                case "airport": airportTable(conn); break;
                case "gender": genderTable(conn); break;
            }
        else{
            switch (tableName){
                case "carriage": carriageTable(conn); break;
                case "workers": workersTable(conn); break;
                case "technicalInspection": technicalInspectionTable(conn); break;
                case "planes": planesTable(conn); break;
                case "trips": tripsTable(conn); break;
                case "timetable": timetableTable(conn); break;
                case "tickets": ticketsTable(conn); break;
                case "reserveTickets": reserveTicketsTable(conn); break;
                case "passengers": passengersTable(conn); break;
            }
        }
    }
    private void departmentTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("department name");
        else {
            String departmentName = textField1.getText();
            String insert = "INSERT INTO department(dep_name) VALUES ('" + departmentName + "')";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(insert);
                preparedStatement.executeUpdate(insert);
            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
    private void medicalTable(Connection conn){

    }
    private void aviacompanyTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("aviacompany name");
        else {
            String aviacompanyName = textField1.getText();
            String insert = "INSERT INTO aviacompany(aviacomp_name) VALUES ('" + aviacompanyName + "')";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(insert);
                preparedStatement.executeUpdate(insert);
            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
    private void ticketClassTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("ticket class name");
        else {
            String ticketClassName = textField1.getText();
            String insert = "INSERT INTO ticketClass(ticket_class_name) VALUES ('" + ticketClassName + "')";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(insert);
                preparedStatement.executeUpdate(insert);
            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
    private void tripStatusTable(Connection conn){

    }
    private void tripTypeTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("trip type name");
        else {
            String tripTypeName = textField1.getText();
            String insert = "INSERT INTO tripType(trip_type_name) VALUES ('" + tripTypeName + "')";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(insert);
                preparedStatement.executeUpdate(insert);
            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
    private void airportTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("airport name");
        else {
            String airportName = textField1.getText();
            String insert = "INSERT INTO airport(airport_name) VALUES ('" + airportName + "')";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(insert);
                preparedStatement.executeUpdate(insert);
            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
    private void genderTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("gender name");
        else {
            String genderName = textField1.getText();
            String insert = "INSERT INTO gender(gen_name) VALUES ('" + genderName + "')";
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(insert);
                preparedStatement.executeUpdate(insert);
            } catch (SQLException exception){
                exception.printStackTrace();
            }
        }
    }
    private void carriageTable(Connection conn){

    }
    private void workersTable(Connection conn){

    }
    private void technicalInspectionTable(Connection conn){

    }
    private void planesTable(Connection conn){

    }
    private void tripsTable(Connection conn){

    }
    private void timetableTable(Connection conn){

    }
    private void ticketsTable(Connection conn){

    }
    private void reserveTicketsTable(Connection conn){

    }
    private void passengersTable(Connection conn){

    }
}
