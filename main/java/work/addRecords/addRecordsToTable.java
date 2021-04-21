package work.addRecords;

import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;

public class addRecordsToTable extends JFrame {
    private JButton ok;
    private JButton back;
    private JPanel addPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;

    private JFormattedTextField numberField1;
    private JFormattedTextField numberField2;
    private JFormattedTextField numberField3;
    private JFormattedTextField numberField4;

    private JSpinner spinDay1;
    private JSpinner spinMonth1;
    private JSpinner spinYear1;
    private JSpinner spinHour1;
    private JSpinner spinMinute1;
    private JSpinner spinDay2;
    private JSpinner spinMonth2;
    private JSpinner spinYear2;
    private JSpinner spinHour2;
    private JSpinner spinMinute2;

    private JCheckBox resultBox1;
    private JCheckBox resultBox2;

    private Vector strings1;
    private Vector strings2;
    private Vector strings3;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private Integer toDraw = 1;

    private Integer error = 0;

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
    private void getStringsFromResultSet(ResultSet resultSet){
        ResultSetMetaData resultSetMetaData = null;
        try{
            resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()){
                Vector tmp = new Vector();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                    tmp.add(resultSet.getString(i));
                strings1.add(tmp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
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
            spinDay1 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth1 = new JSpinner(new SpinnerListModel(months));
            spinYear1 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));

            JLabel result = new JLabel("result of medical:");

            resultBox1 = new JCheckBox("pass");

            JPanel dateLabelPanel = new JPanel();
            dateLabelPanel.add(date);
            JPanel dateSpinPanel = new JPanel();
            dateSpinPanel.add(spinDay1);
            dateSpinPanel.add(spinMonth1);
            dateSpinPanel.add(spinYear1);
            JPanel resultPanel = new JPanel();
            resultPanel.add(result);
            JPanel resCheckPanel = new JPanel();
            resCheckPanel.add(resultBox1);

            addPanel.add(dateLabelPanel);
            addPanel.add(dateSpinPanel);
            addPanel.add(resultPanel);
            addPanel.add(resCheckPanel);
        }
        else{
            String day = spinDay1.getValue().toString();
            String month = spinMonth1.getValue().toString();
            String year = spinYear1.getValue().toString();

            boolean result = resultBox1.isSelected();
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

    private void checkText(String textField, String canon){
        if (textField.equals(canon))
            error = 1;
    }
    private void carriageTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 200);
            setLocationRelativeTo(null);

            String select = "SELECT * FROM department";
            ResultSet resultSet = selectFromTable(conn, select);

            strings1 = new Vector();

            getStringsFromResultSet(resultSet);

            comboBox1 = new JComboBox(strings1);
            JPanel comboBoxPanel = new JPanel();
            comboBoxPanel.add(comboBox1);

            addPanel.add(comboBoxPanel);
        }
        else {
            String string = comboBox1.getSelectedItem().toString();
            String[] stringArray = string.split(" ");

            String insert = "INSERT INTO carriage(dep_id) VALUES (" + stringArray[0].substring(1, stringArray[0].length() - 1) + ")";
            //System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
    private void workersTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 700);
            setLocationRelativeTo(null);

            JLabel fullNameLabel = new JLabel("Enter worker's full name:");
            textField1 = new JTextField(20);
            Color colorLog = textField1.getCaretColor();
            textField1.setForeground(Color.LIGHT_GRAY);
            textField1.setText("Enter lastname here...");
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField1.setForeground(colorLog);
                    if (textField1.getText().equals("Enter lastname here..."))
                        textField1.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField1.getText().isEmpty()) {
                        textField1.setForeground(Color.LIGHT_GRAY);
                        textField1.setText("Enter lastname here...");
                    }
                }
            });
            textField2 = new JTextField(20);
            textField2.setForeground(Color.LIGHT_GRAY);
            textField2.setText("Enter firstname here...");
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField2.setForeground(colorLog);
                    if (textField2.getText().equals("Enter firstname here..."))
                        textField2.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField2.getText().isEmpty()) {
                        textField2.setForeground(Color.LIGHT_GRAY);
                        textField2.setText("Enter firstname here...");
                    }
                }
            });
            textField3 = new JTextField(20);
            textField3.setForeground(Color.LIGHT_GRAY);
            textField3.setText("Enter middle name here...");
            textField3.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField3.setForeground(colorLog);
                    if (textField3.getText().equals("Enter middle name here..."))
                        textField3.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField3.getText().isEmpty()) {
                        textField3.setForeground(Color.LIGHT_GRAY);
                        textField3.setText("Enter middle name here...");
                    }
                }
            });

            JLabel ageLabel = new JLabel("Enter worker's age:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(2);

            JLabel genderLabel = new JLabel("Choose gender:");
            String select = "SELECT * FROM gender";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);

            comboBox1 = new JComboBox(strings1);

            JLabel childLabel = new JLabel("Enter worker's child count:");
            numberField2 = new JFormattedTextField(numberFormat);
            numberField2.setValue(new Integer(0));
            numberField2.setColumns(2);

            JLabel carriageLabel = new JLabel("Enter worker's carriage number");
            select = "SELECT car_id FROM carriage";
            resultSet = selectFromTable(conn, select);
            strings2 = new Vector();
            ResultSetMetaData resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings2.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            comboBox2 = new JComboBox(strings2);

            JLabel medicalLabel = new JLabel("Enter worker's medical (if necessary):");
            select = "SELECT med_id FROM medical";
            resultSet = selectFromTable(conn, select);
            strings3 = new Vector();
            String noneStr = "none";
            Vector noneVec = new Vector();
            noneVec.add(noneStr);
            strings3.add(noneVec);
            resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings3.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }

            comboBox3 = new JComboBox(strings3);

            JPanel fullNamePanel = new JPanel();
            fullNamePanel.add(fullNameLabel);

            JPanel fullNameTextPanel = new JPanel();
            fullNameTextPanel.add(textField1);
            fullNameTextPanel.add(textField2);
            fullNameTextPanel.add(textField3);

            JPanel agePanel = new JPanel();
            agePanel.add(ageLabel);
            agePanel.add(numberField1);

            JPanel genderPanel = new JPanel();
            genderPanel.add(genderLabel);
            genderPanel.add(comboBox1);

            JPanel childPanel = new JPanel();
            childPanel.add(childLabel);
            childPanel.add(numberField2);

            JPanel carriagePanel = new JPanel();
            carriagePanel.add(carriageLabel);
            carriagePanel.add(comboBox2);

            JPanel medicalPanel = new JPanel();
            medicalPanel.add(medicalLabel);
            medicalPanel.add(comboBox3);

            addPanel.add(fullNamePanel);
            addPanel.add(fullNameTextPanel);
            addPanel.add(agePanel);
            addPanel.add(genderPanel);
            addPanel.add(childPanel);
            addPanel.add(carriagePanel);
            addPanel.add(medicalPanel);
        } else {
            String lastname = textField1.getText();
            String firstname = textField2.getText();
            String middlename = textField3.getText();
            checkText(lastname, "Enter lastname here...");
            checkText(firstname, "Enter firstname here...");
            if (error == 1)
                System.out.println("error");            //окно ошибки
            String age = numberField1.getValue().toString();
            String gender = comboBox1.getSelectedItem().toString();
            String[] genderID = gender.split(" ");
            String child = numberField2.getValue().toString();
            String carriage = comboBox2.getSelectedItem().toString();
            String[] carriageID = carriage.split(" ");
            String medical = comboBox3.getSelectedItem().toString();
            String[] medicalID = medical.split(" ");

            String insert = null;
            if (medicalID[0].substring(1, medicalID[0].length() - 1).equals("none")){
                if (middlename.equals("Enter middle name here..."))
                    insert = "INSERT INTO workers(worker_lastname, worker_firstname, worker_age, gen_id, worker_child_count, car_id) VALUES('" + lastname + "', '" + firstname + "', " + age + ", " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + child + ", " + carriageID[0].substring(1, carriageID[0].length() - 1) + ")";
                else
                    insert = "INSERT INTO workers(worker_lastname, worker_firstname, worker_middlename, worker_age, gen_id, worker_child_count, car_id) VALUES('" + lastname + "', '" + firstname + "', '" + middlename + "', " + age + ", " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + child + ", " + carriageID[0].substring(1, carriageID[0].length() - 1) + ")";
            } else {
                if (middlename.equals("Enter middle name here..."))
                    insert = "INSERT INTO workers(worker_lastname, worker_firstname, worker_age, gen_id, worker_child_count, car_id, med_id) VALUES('" + lastname + "', '" + firstname + "', " + age + ", " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + child + ", " + carriageID[0].substring(1, carriageID[0].length() - 1) + ", " + medicalID[0].substring(1, medicalID[0].length()- 1) + ")";
                else
                    insert = "INSERT INTO workers(worker_lastname, worker_firstname, worker_middlename, worker_age, gen_id, worker_child_count, car_id, med_id) VALUES('" + lastname + "', '" + firstname + "', '" + middlename + "', " + age + ", " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + child + ", " + carriageID[0].substring(1, carriageID[0].length() - 1) + ", " + medicalID[0].substring(1, medicalID[0].length()- 1) + ")";
            }
            //System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
    private void technicalInspectionTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 300);
            setLocationRelativeTo(null);

            JLabel date = new JLabel("Enter date of technical inspection:");

            String months[] = { "January", "February", "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December" };
            spinDay1 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth1 = new JSpinner(new SpinnerListModel(months));
            spinYear1 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));

            JLabel workerIDLabel = new JLabel("Enter worker's ID");
            String select = "WITH S1 AS(SELECT car_id FROM carriage WHERE dep_id = 3) SELECT worker_id FROM workers JOIN S1 USING(car_id)";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel degOfWearLabel = new JLabel("Enter plane's degree of wear:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(3);

            JLabel result = new JLabel("Result of technical inspection:");
            resultBox1 = new JCheckBox("pass");

            JPanel dateLabelPanel = new JPanel();
            dateLabelPanel.add(date);

            JPanel dateSpinPanel = new JPanel();
            dateSpinPanel.add(spinDay1);
            dateSpinPanel.add(spinMonth1);
            dateSpinPanel.add(spinYear1);

            JPanel workerPanel = new JPanel();
            workerPanel.add(workerIDLabel);
            workerPanel.add(comboBox1);

            JPanel degOfWearPanel = new JPanel();
            degOfWearPanel.add(degOfWearLabel);
            degOfWearPanel.add(numberField1);

            JPanel resultPanel = new JPanel();
            resultPanel.add(result);
            resultPanel.add(resultBox1);

            addPanel.add(dateLabelPanel);
            addPanel.add(dateSpinPanel);
            addPanel.add(workerPanel);
            addPanel.add(degOfWearPanel);
            addPanel.add(resultPanel);
        } else {
            String day = spinDay1.getValue().toString();
            String month = spinMonth1.getValue().toString();
            String year = spinYear1.getValue().toString();

            String worker = comboBox1.getSelectedItem().toString();
            String[] workerID = worker.split(" ");

            String degOfWear = numberField1.getValue().toString();

            boolean result = resultBox1.isSelected();
            Integer res;
            if (result)
                res = 1;
            else
                res = 0;

            String insert = "INSERT INTO technicalInspection(ti_date, worker_id, deg_of_wear, ti_result) VALUES(TO_DATE('" + getDay(day) + "." + getMonthNum(month) + "." + year + "','dd.mm.yyyy'), " + workerID[0].substring(1, workerID[0].length() - 1) + ", " + degOfWear + ", " + res + ")";
            //System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
    private void planesTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 500);
            setLocationRelativeTo(null);

            JLabel label = new JLabel("Enter plane type:");

            textField1 = new JTextField(20);
            Color colorLog = textField1.getCaretColor();
            textField1.setForeground(Color.LIGHT_GRAY);
            textField1.setText("Enter plane type:");
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField1.setForeground(colorLog);
                    if (textField1.getText().equals("Enter plane type:"))
                        textField1.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField1.getText().isEmpty()) {
                        textField1.setForeground(Color.LIGHT_GRAY);
                        textField1.setText("Enter plane type:");
                    }
                }
            });

            JLabel tiLabel = new JLabel("Choose technical inspection ID:");
            String select = "SELECT ti_id FROM technicalInspection";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel passengersLabel = new JLabel("Enter plane's passengers capacity:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(4);

            JLabel airportLabel = new JLabel("Choose plane's airport:");
            select = "SELECT * FROM airport";
            resultSet = selectFromTable(conn, select);
            strings2 = new Vector();
            ResultSetMetaData resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings2.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            comboBox2 = new JComboBox(strings2);

            JLabel tripLabel = new JLabel("Enter plane's trip count:");
            numberField2 = new JFormattedTextField(numberFormat);
            numberField2.setValue(new Integer(0));
            numberField2.setColumns(4);

            JLabel repairingLabel = new JLabel("Enter plane's repairing count:");
            numberField3 = new JFormattedTextField(numberFormat);
            numberField3.setValue(new Integer(0));
            numberField3.setColumns(2);

            JLabel ageLabel = new JLabel("Enter plane's age:");
            numberField4 = new JFormattedTextField(numberFormat);
            numberField4.setValue(new Integer(0));
            numberField4.setColumns(2);

            JPanel labelPanel = new JPanel();
            labelPanel.add(label);

            JPanel textPanel = new JPanel();
            textPanel.add(textField1);

            JPanel tiPanel = new JPanel();
            tiPanel.add(tiLabel);
            tiPanel.add(comboBox1);

            JPanel passengersPanel = new JPanel();
            passengersPanel.add(passengersLabel);
            passengersPanel.add(numberField1);

            JPanel airportPanel = new JPanel();
            airportPanel.add(airportLabel);
            airportPanel.add(comboBox2);

            JPanel tripPanel = new JPanel();
            tripPanel.add(tripLabel);
            tripPanel.add(numberField2);

            JPanel repairingPanel = new JPanel();
            repairingPanel.add(repairingLabel);
            repairingPanel.add(numberField3);

            JPanel agePanel = new JPanel();
            agePanel.add(ageLabel);
            agePanel.add(numberField4);

            addPanel.add(labelPanel);
            addPanel.add(textPanel);
            addPanel.add(tiPanel);
            addPanel.add(passengersPanel);
            addPanel.add(airportPanel);
            addPanel.add(tripPanel);
            addPanel.add(repairingPanel);
            addPanel.add(agePanel);
        } else {
            String planeType = textField1.getText();
            String ti = comboBox1.getSelectedItem().toString();
            String[] tiID = ti.split(" ");
            String passengers = numberField1.getValue().toString();
            String airport = comboBox2.getSelectedItem().toString();
            String[] airportID = airport.split(" ");
            String trips = numberField2.getValue().toString();
            String repairing = numberField3.getValue().toString();
            String age = numberField4.getValue().toString();

            String insert = "INSERT INTO planes(plane_type, ti_id, plane_passengers_max, airport_id, trip_count, repairing_count, plane_age) VALUES('" + planeType + "', " + tiID[0].substring(1, tiID[0].length() - 1) + ", " + passengers + ", " + airportID[0].substring(1, airportID[0].length() - 1) + ", " + trips + ", " + repairing + ", " + age + ")";
            //System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
    private void tripsTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 700);
            setLocationRelativeTo(null);

            JLabel plane = new JLabel("Enter plane ID:");
            String select = "SELECT plane_id FROM planes";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel dateDepart = new JLabel("Enter depart date and time:");
            String months[] = { "January", "February", "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December" };
            spinDay1 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth1 = new JSpinner(new SpinnerListModel(months));
            spinYear1 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));
            spinHour1 = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
            spinMinute1 = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));

            JLabel dateArrival = new JLabel("Enter arrival date and time:");
            spinDay2 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth2 = new JSpinner(new SpinnerListModel(months));
            spinYear2 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));
            spinHour2 = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
            spinMinute2 = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));

            JLabel departPlaceLabel = new JLabel("Enter depart place:");
            textField1 = new JTextField(20);
            Color colorLog = textField1.getCaretColor();
            textField1.setForeground(Color.LIGHT_GRAY);
            textField1.setText("Enter depart place here...");
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField1.setForeground(colorLog);
                    if (textField1.getText().equals("Enter depart place here..."))
                        textField1.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField1.getText().isEmpty()) {
                        textField1.setForeground(Color.LIGHT_GRAY);
                        textField1.setText("Enter depart place here...");
                    }
                }
            });
            JLabel planeChangePlaceLabel = new JLabel("Enter plane change place:");
            textField2 = new JTextField(20);
            textField2.setForeground(Color.LIGHT_GRAY);
            textField2.setText("Enter plane change here...");
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField2.setForeground(colorLog);
                    if (textField2.getText().equals("Enter plane change here..."))
                        textField2.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField2.getText().isEmpty()) {
                        textField2.setForeground(Color.LIGHT_GRAY);
                        textField2.setText("Enter plane change here...");
                    }
                }
            });
            JLabel arrivalPLaceLabel = new JLabel("Enter arrival place:");
            textField3 = new JTextField(20);
            textField3.setForeground(Color.LIGHT_GRAY);
            textField3.setText("Enter arrival place here...");
            textField3.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField3.setForeground(colorLog);
                    if (textField3.getText().equals("Enter arrival place here..."))
                        textField3.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField3.getText().isEmpty()) {
                        textField3.setForeground(Color.LIGHT_GRAY);
                        textField3.setText("Enter arrival place here...");
                    }
                }
            });

            JLabel tripTypeLabel = new JLabel("Choose trip's type:");
            select = "SELECT * FROM tripType";
            resultSet = selectFromTable(conn, select);
            strings2 = new Vector();
            ResultSetMetaData resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings2.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            comboBox2 = new JComboBox(strings2);

            JLabel carriageLabel = new JLabel("Choose trip's carriage:");
            select = "SELECT car_id FROM carriage";
            resultSet = selectFromTable(conn, select);
            strings3 = new Vector();
            resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings3.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            comboBox3 = new JComboBox(strings3);

            JLabel purchasedTicketsLabel = new JLabel("Enter purchased count of tickets:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(4);

            JLabel surfoldTicketsLabel = new JLabel("Enter surfold count of tickets:");
            numberField2 = new JFormattedTextField(numberFormat);
            numberField2.setValue(new Integer(0));
            numberField2.setColumns(4);

            JLabel reserveTicketsLabel = new JLabel("Enter reserve count of tickets:");
            numberField3 = new JFormattedTextField(numberFormat);
            numberField3.setValue(new Integer(0));
            numberField3.setColumns(4);

            JLabel ticketCostLabel = new JLabel("Enter cost of tickets:");
            numberField4 = new JFormattedTextField(numberFormat);
            numberField4.setValue(new Integer(0));
            numberField4.setColumns(4);

            JPanel planePanel = new JPanel();
            planePanel.add(plane);
            plane.add(comboBox1);

            JPanel departDatePanel = new JPanel();
            departDatePanel.add(dateDepart);
            departDatePanel.add(spinDay1);
            departDatePanel.add(spinMonth1);
            departDatePanel.add(spinYear1);
            departDatePanel.add(spinHour1);
            departDatePanel.add(spinMinute1);

            JPanel arrivalDatePanel = new JPanel();
            arrivalDatePanel.add(dateArrival);
            arrivalDatePanel.add(spinDay2);
            arrivalDatePanel.add(spinMonth2);
            arrivalDatePanel.add(spinYear2);
            arrivalDatePanel.add(spinHour2);
            arrivalDatePanel.add(spinMinute2);

            JPanel departPlacePanel = new JPanel();
            departPlacePanel.add(departPlaceLabel);
            departPlacePanel.add(textField1);

            JPanel planeChangePanel = new JPanel();
            planeChangePanel.add(planeChangePlaceLabel);
            planeChangePanel.add(textField2);

            JPanel arrivalPlacePanel = new JPanel();
            arrivalPlacePanel.add(arrivalPLaceLabel);
            arrivalPlacePanel.add(textField3);

            JPanel tripPanel = new JPanel();
            tripPanel.add(tripTypeLabel);
            tripPanel.add(comboBox2);

            JPanel carriagePanel = new JPanel();
            carriagePanel.add(carriageLabel);
            carriagePanel.add(comboBox3);

            JPanel purchasedTicketsPanel = new JPanel();
            purchasedTicketsPanel.add(purchasedTicketsLabel);
            purchasedTicketsPanel.add(numberField1);

            JPanel surfoldTicketsPanel = new JPanel();
            surfoldTicketsPanel.add(surfoldTicketsLabel);
            surfoldTicketsPanel.add(numberField2);

            JPanel reserveTicketsPanel = new JPanel();
            reserveTicketsPanel.add(reserveTicketsLabel);
            reserveTicketsPanel.add(numberField3);

            JPanel ticketsCostPanel = new JPanel();
            ticketsCostPanel.add(ticketCostLabel);
            ticketsCostPanel.add(numberField4);

            addPanel.add(planePanel);
            addPanel.add(departDatePanel);
            addPanel.add(arrivalDatePanel);
            addPanel.add(departPlacePanel);
            addPanel.add(planeChangePanel);
            addPanel.add(arrivalPlacePanel);
            addPanel.add(tripPanel);
            addPanel.add(carriagePanel);
            addPanel.add(purchasedTicketsPanel);
            addPanel.add(surfoldTicketsPanel);
            addPanel.add(reserveTicketsPanel);
            addPanel.add(ticketsCostPanel);
        } else {
            String plane = comboBox1.getSelectedItem().toString();
            String[] planeID = plane.split(" ");
            String dayDep = spinDay1.getValue().toString();
            String monthDep = spinMonth1.getValue().toString();
            String yearDep = spinYear1.getValue().toString();
            String hourDep = spinHour1.getValue().toString();
            String minuteDep = spinMinute1.getValue().toString();
            String dayArr = spinDay1.getValue().toString();
            String monthArr = spinMonth1.getValue().toString();
            String yearArr = spinYear1.getValue().toString();
            String hourArr = spinHour2.getValue().toString();
            String minuteArr = spinMinute2.getValue().toString();
            String departPlace = textField1.getText();
            String changePlace = textField2.getText();
            String arrivalPlace = textField3.getText();
            String trip = comboBox2.getSelectedItem().toString();
            String[] tripTypeID = trip.split(" ");
            String carriage = comboBox3.getSelectedItem().toString();
            String[] carriageID = carriage.split(" ");
            String purchased = numberField1.getValue().toString();
            String surfold = numberField2.getValue().toString();
            String reserve = numberField3.getValue().toString();
            String cost = numberField4.getValue().toString();

            String insert = null;
            if (changePlace.equals("Enter plane change here..."))
                insert = "INSERT INTO trips(plane_id, depart_time, arrival_time, depart_place, arrival_place, trip_type_id, car_id, purchased_count_tickets, surfold_count_tickets, reserve_count_tickets, ticket_cost) VALUES(" + planeID[0].substring(1, planeID[0].length()- 1) + ", TO_DATE('" + getDay(dayDep) + "." + getMonthNum(monthDep) + "." + yearDep + " " + hourDep + ":" + minuteDep + ":00','dd.mm.yyyy hh24:mi:ss'), TO_DATE('" + getDay(dayArr) + "." + getMonthNum(monthArr) + "." + yearArr + " " +  hourArr+ ":" + minuteArr + ":00','dd.mm.yyyy hh24:mi:ss'), '" + departPlace + "', '" + arrivalPlace + "', " + tripTypeID[0].substring(1, tripTypeID[0].length() - 1) + ", " + carriageID[0].substring(1, carriageID[0].length() - 1) + ", " + purchased + ", " + surfold + ", " + reserve + ", " + cost + ")";
            else
                insert = "INSERT INTO trips(plane_id, depart_time, arrival_time, depart_place, plane_change_place, arrival_place, trip_type_id, car_id, purchased_count_tickets, surfold_count_tickets, reserve_count_tickets, ticket_cost) VALUES(" + planeID[0].substring(1, planeID[0].length()- 1) + ", TO_DATE('" + getDay(dayDep) + "." + getMonthNum(monthDep) + "." + yearDep + " " + hourDep + ":" + minuteDep + ":00','dd.mm.yyyy hh24:mi:ss'), TO_DATE('" + getDay(dayArr) + "." + getMonthNum(monthArr) + "." + yearArr + " " +  hourArr+ ":" + minuteArr + ":00','dd.mm.yyyy hh24:mi:ss'), '" + departPlace + "', '" + changePlace + "', '" + arrivalPlace + "', " + tripTypeID[0].substring(1, tripTypeID[0].length() - 1) + ", " + carriageID[0].substring(1, carriageID[0].length() - 1) + ", " + purchased + ", " + surfold + ", " + reserve + ", " + cost + ")";
            //System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
    private void timetableTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 400);
            setLocationRelativeTo(null);

            JLabel tripLabel = new JLabel("Choose trip ID:");
            String select = "SELECT trip_id FROM trips";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel tripStatusLabel = new JLabel("Chose trip status:");
            select = "SELECT * FROM tripStatus";
            resultSet = selectFromTable(conn, select);
            strings2 = new Vector();
            ResultSetMetaData resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings2.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            comboBox2 = new JComboBox(strings2);

            JPanel tripPanel = new JPanel();
            tripPanel.add(tripLabel);
            tripPanel.add(comboBox1);

            JPanel statusPanel = new JPanel();
            statusPanel.add(tripStatusLabel);
            statusPanel.add(comboBox2);

            addPanel.add(tripPanel);
            addPanel.add(statusPanel);
        } else {
            String trip = comboBox1.getSelectedItem().toString();
            String[] tripID = trip.split(" ");
            String status = comboBox2.getSelectedItem().toString();
            String[] statusID = status.split(" ");

            String insert = "INSERT INTO timetable(trip_id, trip_status_id) VALUES (" + tripID[0].substring(1, tripID[0].length() - 1) + ", " + statusID[0].substring(1, statusID[0].length() - 1) + ")";
            //System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
    private void ticketsTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 400);
            setLocationRelativeTo(null);

            JLabel tripLabel = new JLabel("Choose trip ID:");
            String select = "SELECT trip_id FROM trips";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel seatNumLabel = new JLabel("Enter seat number:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(4);

            JLabel ticketClassLabel = new JLabel("Choose ticket class:");
            select = "SELECT * FROM ticketClass";
            resultSet = selectFromTable(conn, select);
            strings2 = new Vector();
            ResultSetMetaData resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings2.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            comboBox2 = new JComboBox(strings2);

            JLabel aviacompanyLabel = new JLabel("Chose aviacompany:");
            select = "SELECT * FROM aviacompany";
            resultSet = selectFromTable(conn, select);
            strings3 = new Vector();
            resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings3.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            comboBox3 = new JComboBox(strings3);

            JPanel tripPanel = new JPanel();
            tripPanel.add(tripLabel);
            tripPanel.add(comboBox1);

            JPanel seatPanel = new JPanel();
            seatPanel.add(seatNumLabel);
            seatPanel.add(numberField1);

            JPanel classPanel = new JPanel();
            classPanel.add(ticketClassLabel);
            classPanel.add(comboBox2);

            JPanel aviacompanyPanel = new JPanel();
            aviacompanyPanel.add(aviacompanyLabel);
            aviacompanyPanel.add(comboBox3);

            addPanel.add(tripPanel);
            addPanel.add(seatPanel);
            addPanel.add(classPanel);
            addPanel.add(aviacompanyPanel);
        } else {
            String trip = comboBox1.getSelectedItem().toString();
            String[] tripID = trip.split(" ");
            String seatNum = numberField1.getValue().toString();
            String ticketClass = comboBox2.getSelectedItem().toString();
            String[] ticketClassID = ticketClass.split(" ");
            String aviacompany = comboBox3.getSelectedItem().toString();
            String[] aviacompanyID = aviacompany.split(" ");

            String insert = "INSERT INTO tickets(trip_id, ticket_seat_num, ticket_class_id, aviacomp_id) VALUES (" + tripID[0].substring(1, tripID[0].length() - 1) + ", " + seatNum + ", " + ticketClassID[0].substring(1, ticketClassID[0].length() - 1) + ", " + aviacompanyID[0].substring(1, aviacompanyID[0].length() - 1) + ")";
            //System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
    private void reserveTicketsTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 400);
            setLocationRelativeTo(null);

            JLabel ticketLabel = new JLabel("Choose ticket ID:");
            String select = "SELECT ticket_id FROM tickets";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            resultBox1 = new JCheckBox("paid");

            JLabel date = new JLabel("Enter date of payment:");
            String months[] = { "January", "February", "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December" };
            spinDay1 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth1 = new JSpinner(new SpinnerListModel(months));
            spinYear1 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));

            JPanel ticketPanel = new JPanel();
            ticketPanel.add(ticketLabel);
            ticketPanel.add(comboBox1);

            JPanel resultPanel = new JPanel();
            resultPanel.add(resultBox1);

            JPanel datePanel = new JPanel();
            datePanel.add(date);
            datePanel.add(spinDay1);
            datePanel.add(spinMonth1);
            datePanel.add(spinYear1);

            addPanel.add(ticketPanel);
            addPanel.add(resultPanel);
            addPanel.add(datePanel);
        } else {
            String ticket = comboBox1.getSelectedItem().toString();
            String[] ticketID = ticket.split(" ");
            boolean result = resultBox1.isSelected();
            Integer res;
            if (result)
                res = 1;
            else
                res = 0;
            String day = spinDay1.getValue().toString();
            String month = spinMonth1.getValue().toString();
            String year = spinYear1.getValue().toString();

            String insert = null;
            if (res == 1)
                insert = "INSERT INTO reserveTickets(ticket_id, is_paid, paid_date) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", 1, TO_DATE('" + getDay(day) + "." + getMonthNum(month) + "." + year + "', 'dd.mm.yyyy'))";
            else
                insert = "INSERT INTO reserveTickets(ticket_id, is_paid) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", 0)";
            //System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
    private void passengersTable(Connection conn){
        if (toDraw == 1){
            setSize(400, 800);
            setLocationRelativeTo(null);

            JLabel ticketLabel = new JLabel("Choose ticket ID:");
            String select = "SELECT ticket_id FROM tickets";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel fullNameLabel = new JLabel("Enter passenger's full name:");
            textField1 = new JTextField(20);
            Color colorLog = textField1.getCaretColor();
            textField1.setForeground(Color.LIGHT_GRAY);
            textField1.setText("Enter lastname here...");
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField1.setForeground(colorLog);
                    if (textField1.getText().equals("Enter lastname here..."))
                        textField1.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField1.getText().isEmpty()) {
                        textField1.setForeground(Color.LIGHT_GRAY);
                        textField1.setText("Enter lastname here...");
                    }
                }
            });
            textField2 = new JTextField(20);
            textField2.setForeground(Color.LIGHT_GRAY);
            textField2.setText("Enter firstname here...");
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField2.setForeground(colorLog);
                    if (textField2.getText().equals("Enter firstname here..."))
                        textField2.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField2.getText().isEmpty()) {
                        textField2.setForeground(Color.LIGHT_GRAY);
                        textField2.setText("Enter firstname here...");
                    }
                }
            });
            textField3 = new JTextField(20);
            textField3.setForeground(Color.LIGHT_GRAY);
            textField3.setText("Enter middle name here...");
            textField3.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField3.setForeground(colorLog);
                    if (textField3.getText().equals("Enter middle name here..."))
                        textField3.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField3.getText().isEmpty()) {
                        textField3.setForeground(Color.LIGHT_GRAY);
                        textField3.setText("Enter middle name here...");
                    }
                }
            });

            JLabel passportLabel = new JLabel("Enter passenger's passport ID:");
            textField4 = new JTextField(20);
            textField4.setForeground(Color.LIGHT_GRAY);
            textField4.setText("Enter passport ID here...");
            textField4.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField4.setForeground(colorLog);
                    if (textField4.getText().equals("Enter passport ID here..."))
                        textField4.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField4.getText().isEmpty()) {
                        textField4.setForeground(Color.LIGHT_GRAY);
                        textField4.setText("Enter passport ID here...");
                    }
                }
            });

            JLabel genderLabel = new JLabel("Choose passenger's gender:");
            select = "SELECT * FROM gender";
            resultSet = selectFromTable(conn, select);
            strings2 = new Vector();
            ResultSetMetaData resultSetMetaData = null;
            try{
                resultSetMetaData = resultSet.getMetaData();
                while (resultSet.next()){
                    Vector tmp = new Vector();
                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                        tmp.add(resultSet.getString(i));
                    strings2.add(tmp);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            comboBox2 = new JComboBox(strings2);

            JLabel ageLabel = new JLabel("Enter passenger's age:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(2);

            JLabel passportAbroadLabel = new JLabel("Enter passenger's passport abroad ID:");
            textField5 = new JTextField(20);
            textField5.setForeground(Color.LIGHT_GRAY);
            textField5.setText("Enter passport abroad ID here...");
            textField5.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField5.setForeground(colorLog);
                    if (textField5.getText().equals("Enter passport abroad ID here..."))
                        textField5.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField5.getText().isEmpty()) {
                        textField5.setForeground(Color.LIGHT_GRAY);
                        textField5.setText("Enter passport abroad ID here...");
                    }
                }
            });

            resultBox1 = new JCheckBox("custom inspection");
            resultBox2 = new JCheckBox("luggage");

            JPanel ticketPanel = new JPanel();
            ticketPanel.add(ticketLabel);
            ticketPanel.add(comboBox1);

            JPanel fullNamePanel = new JPanel();
            fullNamePanel.add(fullNameLabel);
            fullNamePanel.add(textField1);
            fullNamePanel.add(textField2);
            fullNamePanel.add(textField3);

            JPanel passportPanel = new JPanel();
            passportPanel.add(passportLabel);
            passportPanel.add(textField4);

            JPanel genderPanel = new JPanel();
            genderPanel.add(genderLabel);
            genderPanel.add(comboBox2);

            JPanel agePanel = new JPanel();
            agePanel.add(ageLabel);
            agePanel.add(numberField1);

            JPanel passportAbroadPanel = new JPanel();
            passportAbroadPanel.add(passportAbroadLabel);
            passportAbroadPanel.add(textField5);

            JPanel checkPanel = new JPanel();
            checkPanel.add(resultBox1);
            checkPanel.add(resultBox2);

            addPanel.add(ticketPanel);
            addPanel.add(fullNamePanel);
            addPanel.add(passportPanel);
            addPanel.add(genderPanel);
            addPanel.add(agePanel);
            addPanel.add(passportAbroadPanel);
            addPanel.add(checkPanel);
        } else {
            String ticket = comboBox1.getSelectedItem().toString();
            String[] ticketID = ticket.split(" ");
            String lastname = textField1.getText();
            String firstname = textField2.getText();
            String middlename = textField3.getText();
            String passportID = textField4.getText();
            String gender = comboBox2.getSelectedItem().toString();
            String[] genderID = gender.split(" ");
            String age = numberField1.getValue().toString();
            String passportAbroadID = textField5.getText();

            boolean customInspection = resultBox1.isSelected();
            Integer custom = 0;
            if (customInspection)
                custom = 1;
            boolean luggage = resultBox2.isSelected();
            Integer lug = 0;
            if (luggage)
                lug = 1;

            String insert = null;
            if (middlename.equals("Enter middle name here...")){
                if (passportAbroadID.equals("Enter passport abroad ID here..."))
                    insert = "INSERT INTO passengers(ticket_id, passenger_lastname, passenger_firstname, passport_id, gen_id, passenger_age, luggage) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", '" + lastname + "', '" + firstname + "', '" + passportID + "', " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + age + ", " + lug + ")";
                else
                    insert = "INSERT INTO passengers(ticket_id, passenger_lastname, passenger_firstname, passport_id, gen_id, passenger_age, passport_abroad_id, custom_inspection, luggage) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", '" + lastname + "', '" + firstname + "', '" + passportID + "', " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + age + ", '" + passportAbroadID + "', " + custom + ", " + lug + ")";
            } else{
                if (passportAbroadID.equals("Enter passport abroad ID here..."))
                    insert = "INSERT INTO passengers(ticket_id, passenger_lastname, passenger_firstname, passenger_middlename, passport_id, gen_id, passenger_age, luggage) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", '" + lastname + "', '" + firstname + "', '" + middlename + "', '" + passportID + "', " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + age + ", " + lug + ")";
                else
                    insert = "INSERT INTO passengers(ticket_id, passenger_lastname, passenger_firstname, passenger_middlename, passport_id, gen_id, passenger_age, passport_abroad_id, custom_inspection, luggage) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", '" + lastname + "', '" + firstname + "', '" + middlename + "', '" + passportID + "', " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + age + ", '" + passportAbroadID + "', " + custom + ", " + lug + ")";
            }
            System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
}
