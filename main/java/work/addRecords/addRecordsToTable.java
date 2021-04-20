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
    private JFormattedTextField numberField1;
    private JFormattedTextField numberField2;
    private JFormattedTextField numberField3;
    private JFormattedTextField numberField4;

    private JSpinner spinDay;
    private JSpinner spinMonth;
    private JSpinner spinYear;
    private JCheckBox resultBox;
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
            spinDay = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth = new JSpinner(new SpinnerListModel(months));
            spinYear = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));

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
            resultBox = new JCheckBox("pass");

            JPanel dateLabelPanel = new JPanel();
            dateLabelPanel.add(date);

            JPanel dateSpinPanel = new JPanel();
            dateSpinPanel.add(spinDay);
            dateSpinPanel.add(spinMonth);
            dateSpinPanel.add(spinYear);

            JPanel workerPanel = new JPanel();
            workerPanel.add(workerIDLabel);
            workerPanel.add(comboBox1);

            JPanel degOfWearPanel = new JPanel();
            degOfWearPanel.add(degOfWearLabel);
            degOfWearPanel.add(numberField1);

            JPanel resultPanel = new JPanel();
            resultPanel.add(result);
            resultPanel.add(resultBox);

            addPanel.add(dateLabelPanel);
            addPanel.add(dateSpinPanel);
            addPanel.add(workerPanel);
            addPanel.add(degOfWearPanel);
            addPanel.add(resultPanel);
        } else {
            String day = spinDay.getValue().toString();
            String month = spinMonth.getValue().toString();
            String year = spinYear.getValue().toString();

            String worker = comboBox1.getSelectedItem().toString();
            String[] workerID = worker.split(" ");

            String degOfWear = numberField1.getValue().toString();

            boolean result = resultBox.isSelected();
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

        }
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
