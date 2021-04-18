package work.addRecords;

import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class addRecordsToTable extends JFrame {
    private JButton ok;
    private JButton back;
    private JPanel addPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JSpinner spinDay;
    private JSpinner spinMonth;
    private JSpinner spinYear;
    private JCheckBox resultBox;
    private Vector strings;
    private Vector columnNames;
    private JComboBox comboBox;
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
    private String getMonthNum(String month){
        switch (month){
            case "January": return "01";
            case "February": return "02";
            case "March": return "03";
            case "April": return "04";
            case "May": return "05";
            case "June": return "06";
            case "July": return "07";
            case "August": return "08";
            case "September": return "09";
            case "October": return "10";
            case "November": return "11";
            case "December": return "12";
        }
        return "0";
    }
    private String getDay(String day){
        switch (day){
            case "1": return "01";
            case "2": return "02";
            case "3": return "03";
            case "4": return "04";
            case "5": return "05";
            case "6": return "06";
            case "7": return "07";
            case "8": return "08";
            case "9": return "09";
            default: return day;
        }
    }
    private void insertIntoTable(Connection conn, String insert){
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insert);
            preparedStatement.executeUpdate(insert);
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    private ResultSet selectFromTable(Connection conn, String select){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(select);
            return preparedStatement.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }

    private void departmentTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("department name");
        else {
            String departmentName = textField1.getText();
            String insert = "INSERT INTO department(dep_name) VALUES ('" + departmentName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void medicalTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 300);
            setLocationRelativeTo(null);

            JLabel date = new JLabel("enter date of medical:");

            String months[] = { "January", "February", "March", "April", "May", "June", "July", "August",
                               "September", "October", "November", "December" };
            spinDay = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth = new JSpinner(new SpinnerListModel(months));
            spinYear = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));

            JLabel result = new JLabel("result of medical:");

            resultBox = new JCheckBox("pass");

            JPanel dateLabelPanel = new JPanel();
            dateLabelPanel.add(date);
            JPanel dateSpinPanel = new JPanel();
            dateSpinPanel.add(spinDay);
            dateSpinPanel.add(spinMonth);
            dateSpinPanel.add(spinYear);
            JPanel resultPanel = new JPanel();
            resultPanel.add(result);
            JPanel resCheckPanel = new JPanel();
            resCheckPanel.add(resultBox);

            addPanel.add(dateLabelPanel);
            addPanel.add(dateSpinPanel);
            addPanel.add(resultPanel);
            addPanel.add(resCheckPanel);
        }
        else{
            String day = spinDay.getValue().toString();
            String month = spinMonth.getValue().toString();
            String year = spinYear.getValue().toString();

            boolean result = resultBox.isSelected();
            Integer res;
            if (result)
                res = 1;
            else
                res = 0;

            String insert = "INSERT INTO medical(med_date, med_status) VALUES(TO_DATE('"+ getDay(day) + "." + getMonthNum(month) + "." + year + "','dd.mm.yyyy'), " + res + ")";
            insertIntoTable(conn, insert);
        }
    }
    private void aviacompanyTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("aviacompany name");
        else {
            String aviacompanyName = textField1.getText();
            String insert = "INSERT INTO aviacompany(aviacomp_name) VALUES ('" + aviacompanyName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void ticketClassTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("ticket class name");
        else {
            String ticketClassName = textField1.getText();
            String insert = "INSERT INTO ticketClass(ticket_class_name) VALUES ('" + ticketClassName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void tripStatusTable(Connection conn){
        if (toDraw == 1){
            simpleWindowWithOneLabel("trip status name");
            JLabel label = new JLabel("Enter trip status reason here...");

            textField2 = new JTextField(20);
            Color colorLog = textField2.getCaretColor();
            textField2.setForeground(Color.LIGHT_GRAY);
            textField2.setText("Enter trip status reason here...");
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField2.setForeground(colorLog);
                    if (textField2.getText().equals("Enter trip status reason here..."))
                        textField2.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField2.getText().isEmpty()) {
                        textField2.setForeground(Color.LIGHT_GRAY);
                        textField2.setText("Enter trip status reason here...");
                    }
                }
            });

            JPanel labelPanel = new JPanel();
            labelPanel.add(label);
            JPanel textPanel = new JPanel();
            textPanel.add(textField2);
            addPanel.add(labelPanel);
            addPanel.add(textPanel);
        }
        else{
            String tripStatusName = textField1.getText();
            String tripStatusReason = textField2.getText();
            if (tripStatusReason.equals("Enter trip status reason here..."))
                tripStatusReason = "";
            String insert = "INSERT INTO tripStatus(trip_status_name, trip_status_reason) VALUES ('" + tripStatusName + "', '" + tripStatusReason + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void tripTypeTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("trip type name");
        else {
            String tripTypeName = textField1.getText();
            String insert = "INSERT INTO tripType(trip_type_name) VALUES ('" + tripTypeName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void airportTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("airport name");
        else {
            String airportName = textField1.getText();
            String insert = "INSERT INTO airport(airport_name) VALUES ('" + airportName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void genderTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("gender name");
        else {
            String genderName = textField1.getText();
            String insert = "INSERT INTO gender(gen_name) VALUES ('" + genderName + "')";
            insertIntoTable(conn, insert);
        }
    }

    private void carriageTable(Connection conn){
        if (toDraw == 1){
            String select = "SELECT * FROM department";
            ResultSet resultSet = selectFromTable(conn, select);

            ResultSetMetaData resultSetMetaData = null;
            strings = new Vector();
            try{
                resultSetMetaData = resultSet.getMetaData();

                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            setSize(400, 200);
            setLocationRelativeTo(null);

            comboBox = new JComboBox(strings);
            JPanel comboBoxPanel = new JPanel();
            comboBoxPanel.add(comboBox);

            addPanel.add(comboBoxPanel);
        }
        else {
            String string = comboBox.getSelectedItem().toString();
            String[] stringArray = string.split(" ");

            String insert = "INSERT INTO carriage(dep_id) VALUES (" + stringArray[0].substring(1, stringArray[0].length() - 1) + ")";
            System.out.println(insert);
            insertIntoTable(conn, insert);
        }
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
