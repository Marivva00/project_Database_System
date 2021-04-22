package work.addRecords;

import work.authorisation.role;
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
    private role userRole;
    public addRecordsToTable(Connection conn, Integer dep,  String tableName, role userRole){
        super("Добавление записи в '" + tableName + "' таблицу");
        //setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;
        addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.PAGE_AXIS));

        getTable(conn, dep, tableName);

        ok = new JButton("Добавить запись");
        back = new JButton("Назад");
        ok.addActionListener((e)->{
            setVisible(false);
            toDraw = 0;
            getTable(conn, dep, tableName);
            new lookOnTableView(conn, dep, tableName, userRole);
        });
        back.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, dep, tableName, userRole);
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

        JLabel label = new JLabel("Введите " + labelName + " здесь...");

        textField1 = new JTextField(20);
        Color colorLog = textField1.getCaretColor();
        textField1.setForeground(Color.LIGHT_GRAY);
        textField1.setText("Введите " + labelName + " здесь...");
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event){
                textField1.setForeground(colorLog);
                if (textField1.getText().equals("Введите " + labelName + " здесь..."))
                    textField1.setText("");
            }
            @Override
            public void focusLost(FocusEvent event) {
                if (textField1.getText().isEmpty()) {
                    textField1.setForeground(Color.LIGHT_GRAY);
                    textField1.setText("Введите " + labelName + " здесь...");
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
            case "Январь": return "01";
            case "Февраль": return "02";
            case "Март": return "03";
            case "Апрель": return "04";
            case "Май": return "05";
            case "Июнь": return "06";
            case "Июль": return "07";
            case "Август": return "08";
            case "Сентябрь": return "09";
            case "Октябрь": return "10";
            case "Ноябрь": return "11";
            case "Декабрь": return "12";
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
            simpleWindowWithOneLabel("название отдела");
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

            JLabel date = new JLabel("Введите дату мед. осмотра:");

            String months[] = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                               "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
            spinDay1 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth1 = new JSpinner(new SpinnerListModel(months));
            spinYear1 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));

            JLabel result = new JLabel("Результат мед. осмотра:");

            resultBox1 = new JCheckBox("пройден");

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
            simpleWindowWithOneLabel("название авиакомпании");
        else {
            String aviacompanyName = textField1.getText();
            String insert = "INSERT INTO aviacompany(aviacomp_name) VALUES ('" + aviacompanyName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void ticketClassTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("название класса билета");
        else {
            String ticketClassName = textField1.getText();
            String insert = "INSERT INTO ticketClass(ticket_class_name) VALUES ('" + ticketClassName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void tripStatusTable(Connection conn){
        if (toDraw == 1){
            simpleWindowWithOneLabel("название статуса рейса");
            JLabel label = new JLabel("Введите причину данного статуса:");

            textField2 = new JTextField(20);
            Color colorLog = textField2.getCaretColor();
            textField2.setForeground(Color.LIGHT_GRAY);
            textField2.setText("Введите причину данного статуса...");
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField2.setForeground(colorLog);
                    if (textField2.getText().equals("Введите причину данного статуса..."))
                        textField2.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField2.getText().isEmpty()) {
                        textField2.setForeground(Color.LIGHT_GRAY);
                        textField2.setText("Введите причину данного статуса...");
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
            if (tripStatusReason.equals("Введите причину данного статуса..."))
                tripStatusReason = "";
            String insert = "INSERT INTO tripStatus(trip_status_name, trip_status_reason) VALUES ('" + tripStatusName + "', '" + tripStatusReason + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void tripTypeTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("название типа рейса");
        else {
            String tripTypeName = textField1.getText();
            String insert = "INSERT INTO tripType(trip_type_name) VALUES ('" + tripTypeName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void airportTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("название аэропорта");
        else {
            String airportName = textField1.getText();
            String insert = "INSERT INTO airport(airport_name) VALUES ('" + airportName + "')";
            insertIntoTable(conn, insert);
        }
    }
    private void genderTable(Connection conn){
        if (toDraw == 1)
            simpleWindowWithOneLabel("название пола");
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

            JLabel fullNameLabel = new JLabel("Введите полное имя работника:");
            textField1 = new JTextField(20);
            Color colorLog = textField1.getCaretColor();
            textField1.setForeground(Color.LIGHT_GRAY);
            textField1.setText("Введите фамилию...");
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField1.setForeground(colorLog);
                    if (textField1.getText().equals("Введите фамилию..."))
                        textField1.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField1.getText().isEmpty()) {
                        textField1.setForeground(Color.LIGHT_GRAY);
                        textField1.setText("Введите фамилию...");
                    }
                }
            });
            textField2 = new JTextField(20);
            textField2.setForeground(Color.LIGHT_GRAY);
            textField2.setText("Введите имя...");
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField2.setForeground(colorLog);
                    if (textField2.getText().equals("Введите имя..."))
                        textField2.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField2.getText().isEmpty()) {
                        textField2.setForeground(Color.LIGHT_GRAY);
                        textField2.setText("Введите имя...");
                    }
                }
            });
            textField3 = new JTextField(20);
            textField3.setForeground(Color.LIGHT_GRAY);
            textField3.setText("Введите отчество (при наличии)...");
            textField3.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField3.setForeground(colorLog);
                    if (textField3.getText().equals("Введите отчество (при наличии)..."))
                        textField3.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField3.getText().isEmpty()) {
                        textField3.setForeground(Color.LIGHT_GRAY);
                        textField3.setText("Введите отчество (при наличии)...");
                    }
                }
            });

            JLabel ageLabel = new JLabel("Введите возраст работника:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(2);

            JLabel genderLabel = new JLabel("Выберите пол:");
            String select = "SELECT * FROM gender";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);

            comboBox1 = new JComboBox(strings1);

            JLabel childLabel = new JLabel("Введите количество детей у работника:");
            numberField2 = new JFormattedTextField(numberFormat);
            numberField2.setValue(new Integer(0));
            numberField2.setColumns(2);

            JLabel carriageLabel = new JLabel("Выберите бригаду работника:");
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

            JLabel medicalLabel = new JLabel("Выберите номер мед. осмотра работника (если необходимо):");
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
            checkText(lastname, "Введите фамилию...");
            checkText(firstname, "Введите имя...");
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
                if (middlename.equals("Введите отчество (при наличии)..."))
                    insert = "INSERT INTO workers(worker_lastname, worker_firstname, worker_age, gen_id, worker_child_count, car_id) VALUES('" + lastname + "', '" + firstname + "', " + age + ", " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + child + ", " + carriageID[0].substring(1, carriageID[0].length() - 1) + ")";
                else
                    insert = "INSERT INTO workers(worker_lastname, worker_firstname, worker_middlename, worker_age, gen_id, worker_child_count, car_id) VALUES('" + lastname + "', '" + firstname + "', '" + middlename + "', " + age + ", " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + child + ", " + carriageID[0].substring(1, carriageID[0].length() - 1) + ")";
            } else {
                if (middlename.equals("Введите отчество (при наличии)..."))
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

            JLabel date = new JLabel("Введите дату тех. осмотра:");

            String months[] = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                    "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
            spinDay1 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth1 = new JSpinner(new SpinnerListModel(months));
            spinYear1 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));

            JLabel workerIDLabel = new JLabel("Выберите ИД работника:");
            String select = "WITH S1 AS(SELECT car_id FROM carriage WHERE dep_id = 3) SELECT worker_id FROM workers JOIN S1 USING(car_id)";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel degOfWearLabel = new JLabel("Введите степень износки самолёта:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(3);

            JLabel result = new JLabel("Результат тех. осмотра:");
            resultBox1 = new JCheckBox("пройден");

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

            JLabel label = new JLabel("Введите тип самолёта:");

            textField1 = new JTextField(20);
            Color colorLog = textField1.getCaretColor();
            textField1.setForeground(Color.LIGHT_GRAY);
            textField1.setText("Введите тип самолёта...");
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField1.setForeground(colorLog);
                    if (textField1.getText().equals("Введите тип самолёта..."))
                        textField1.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField1.getText().isEmpty()) {
                        textField1.setForeground(Color.LIGHT_GRAY);
                        textField1.setText("Введите тип самолёта...");
                    }
                }
            });

            JLabel tiLabel = new JLabel("Выберите номер тех. сомотра:");
            String select = "SELECT ti_id FROM technicalInspection";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel passengersLabel = new JLabel("Введите пассажировместимость самолёта:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(4);

            JLabel airportLabel = new JLabel("Выберите аэропорт самолёта:");
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

            JLabel tripLabel = new JLabel("Введите количество полётов:");
            numberField2 = new JFormattedTextField(numberFormat);
            numberField2.setValue(new Integer(0));
            numberField2.setColumns(4);

            JLabel repairingLabel = new JLabel("Введите количество починок:");
            numberField3 = new JFormattedTextField(numberFormat);
            numberField3.setValue(new Integer(0));
            numberField3.setColumns(2);

            JLabel ageLabel = new JLabel("Введите возраст самолёта:");
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

            JLabel plane = new JLabel("Выберите ИД самолёта:");
            String select = "SELECT plane_id FROM planes";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel dateDepart = new JLabel("Введите дату и время отлёта:");
            String months[] = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                    "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
            spinDay1 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth1 = new JSpinner(new SpinnerListModel(months));
            spinYear1 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));
            spinHour1 = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
            spinMinute1 = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));

            JLabel dateArrival = new JLabel("Введите дату и время прилёта:");
            spinDay2 = new JSpinner(new SpinnerNumberModel(1, 1, 31, 1));
            spinMonth2 = new JSpinner(new SpinnerListModel(months));
            spinYear2 = new JSpinner(new SpinnerNumberModel(2021, 2021, 2100, 1));
            spinHour2 = new JSpinner(new SpinnerNumberModel(00, 00, 23, 1));
            spinMinute2 = new JSpinner(new SpinnerNumberModel(00, 00, 59, 1));

            JLabel departPlaceLabel = new JLabel("Введите место отправления:");
            textField1 = new JTextField(20);
            Color colorLog = textField1.getCaretColor();
            textField1.setForeground(Color.LIGHT_GRAY);
            textField1.setText("Введите место отправления...");
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField1.setForeground(colorLog);
                    if (textField1.getText().equals("Введите место отправления..."))
                        textField1.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField1.getText().isEmpty()) {
                        textField1.setForeground(Color.LIGHT_GRAY);
                        textField1.setText("Введите место отправления...");
                    }
                }
            });
            JLabel planeChangePlaceLabel = new JLabel("Введите место пересадки (если имеется):");
            textField2 = new JTextField(20);
            textField2.setForeground(Color.LIGHT_GRAY);
            textField2.setText("Введите место пересадки (если имеется)...");
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField2.setForeground(colorLog);
                    if (textField2.getText().equals("Введите место пересадки (если имеется)..."))
                        textField2.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField2.getText().isEmpty()) {
                        textField2.setForeground(Color.LIGHT_GRAY);
                        textField2.setText("Введите место пересадки (если имеется)...");
                    }
                }
            });
            JLabel arrivalPLaceLabel = new JLabel("Введите место назначения:");
            textField3 = new JTextField(20);
            textField3.setForeground(Color.LIGHT_GRAY);
            textField3.setText("Введите место назначения...");
            textField3.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField3.setForeground(colorLog);
                    if (textField3.getText().equals("Введите место назначения..."))
                        textField3.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField3.getText().isEmpty()) {
                        textField3.setForeground(Color.LIGHT_GRAY);
                        textField3.setText("Введите место назначения...");
                    }
                }
            });

            JLabel tripTypeLabel = new JLabel("Выберите тип рейса:");
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

            JLabel carriageLabel = new JLabel("Выберите обслуживающую бригаду:");
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

            JLabel purchasedTicketsLabel = new JLabel("Введите количество проданных билетов:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(4);

            JLabel surfoldTicketsLabel = new JLabel("Введите минимально необходимое количество билетов:");
            numberField2 = new JFormattedTextField(numberFormat);
            numberField2.setValue(new Integer(0));
            numberField2.setColumns(4);

            JLabel reserveTicketsLabel = new JLabel("Введите количество забронированных билетов:");
            numberField3 = new JFormattedTextField(numberFormat);
            numberField3.setValue(new Integer(0));
            numberField3.setColumns(4);

            JLabel ticketCostLabel = new JLabel("Введите стоимость билета:");
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
            if (changePlace.equals("Введите место пересадки (если имеется)..."))
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

            JLabel tripLabel = new JLabel("Выберите номер рейса:");
            String select = "SELECT trip_id FROM trips";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel tripStatusLabel = new JLabel("Выберите статус рейса и причину:");
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

            JLabel tripLabel = new JLabel("Выберите номер рейса:");
            String select = "SELECT trip_id FROM trips";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel seatNumLabel = new JLabel("Введите номер места:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(4);

            JLabel ticketClassLabel = new JLabel("Выберите класс билета:");
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

            JLabel aviacompanyLabel = new JLabel("Выберите авикомпанию:");
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

            JLabel ticketLabel = new JLabel("Выберите номер билета:");
            String select = "SELECT ticket_id FROM tickets";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            resultBox1 = new JCheckBox("оплачено");

            JLabel date = new JLabel("Введите дату оплаты (если необходимо):");
            String months[] = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                    "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
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

            JLabel ticketLabel = new JLabel("Выберите номер билета:");
            String select = "SELECT ticket_id FROM tickets";
            ResultSet resultSet = selectFromTable(conn, select);
            strings1 = new Vector();
            getStringsFromResultSet(resultSet);
            comboBox1 = new JComboBox(strings1);

            JLabel fullNameLabel = new JLabel("Введите полное имя пассажира:");
            textField1 = new JTextField(20);
            Color colorLog = textField1.getCaretColor();
            textField1.setForeground(Color.LIGHT_GRAY);
            textField1.setText("Введите фамилию...");
            textField1.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField1.setForeground(colorLog);
                    if (textField1.getText().equals("Введите фамилию..."))
                        textField1.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField1.getText().isEmpty()) {
                        textField1.setForeground(Color.LIGHT_GRAY);
                        textField1.setText("Введите фамилию...");
                    }
                }
            });
            textField2 = new JTextField(20);
            textField2.setForeground(Color.LIGHT_GRAY);
            textField2.setText("Введите имя...");
            textField2.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField2.setForeground(colorLog);
                    if (textField2.getText().equals("Введите имя..."))
                        textField2.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField2.getText().isEmpty()) {
                        textField2.setForeground(Color.LIGHT_GRAY);
                        textField2.setText("Введите имя...");
                    }
                }
            });
            textField3 = new JTextField(20);
            textField3.setForeground(Color.LIGHT_GRAY);
            textField3.setText("Введите отчество (если имеется)...");
            textField3.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField3.setForeground(colorLog);
                    if (textField3.getText().equals("Введите отчество (если имеется)..."))
                        textField3.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField3.getText().isEmpty()) {
                        textField3.setForeground(Color.LIGHT_GRAY);
                        textField3.setText("Введите отчество (если имеется)...");
                    }
                }
            });

            JLabel passportLabel = new JLabel("Введите номер паспорта пассажира:");
            textField4 = new JTextField(20);
            textField4.setForeground(Color.LIGHT_GRAY);
            textField4.setText("Введите номер паспорта пассажира...");
            textField4.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField4.setForeground(colorLog);
                    if (textField4.getText().equals("Введите номер паспорта пассажира..."))
                        textField4.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField4.getText().isEmpty()) {
                        textField4.setForeground(Color.LIGHT_GRAY);
                        textField4.setText("Введите номер паспорта пассажира...");
                    }
                }
            });

            JLabel genderLabel = new JLabel("Выберите пол пассажира:");
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

            JLabel ageLabel = new JLabel("Введите возраст пассажира:");
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            numberField1 = new JFormattedTextField(numberFormat);
            numberField1.setValue(new Integer(0));
            numberField1.setColumns(2);

            JLabel passportAbroadLabel = new JLabel("Введите номер загран. пасспорта пассажира (если необходимо):");
            textField5 = new JTextField(20);
            textField5.setForeground(Color.LIGHT_GRAY);
            textField5.setText("Введите номер загран. пасспорта пассажира (если необходимо)...");
            textField5.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent event){
                    textField5.setForeground(colorLog);
                    if (textField5.getText().equals("Введите номер загран. пасспорта пассажира (если необходимо)..."))
                        textField5.setText("");
                }
                @Override
                public void focusLost(FocusEvent event) {
                    if (textField5.getText().isEmpty()) {
                        textField5.setForeground(Color.LIGHT_GRAY);
                        textField5.setText("Введите номер загран. пасспорта пассажира (если необходимо)...");
                    }
                }
            });

            resultBox1 = new JCheckBox("пограничный досмотр пройден");
            resultBox2 = new JCheckBox("наличие багажа");

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
            if (middlename.equals("Введите отчество (если имеется)...")){
                if (passportAbroadID.equals("Введите номер загран. пасспорта пассажира (если необходимо)..."))
                    insert = "INSERT INTO passengers(ticket_id, passenger_lastname, passenger_firstname, passport_id, gen_id, passenger_age, luggage) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", '" + lastname + "', '" + firstname + "', '" + passportID + "', " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + age + ", " + lug + ")";
                else
                    insert = "INSERT INTO passengers(ticket_id, passenger_lastname, passenger_firstname, passport_id, gen_id, passenger_age, passport_abroad_id, custom_inspection, luggage) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", '" + lastname + "', '" + firstname + "', '" + passportID + "', " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + age + ", '" + passportAbroadID + "', " + custom + ", " + lug + ")";
            } else{
                if (passportAbroadID.equals("Введите номер загран. пасспорта пассажира (если необходимо)..."))
                    insert = "INSERT INTO passengers(ticket_id, passenger_lastname, passenger_firstname, passenger_middlename, passport_id, gen_id, passenger_age, luggage) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", '" + lastname + "', '" + firstname + "', '" + middlename + "', '" + passportID + "', " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + age + ", " + lug + ")";
                else
                    insert = "INSERT INTO passengers(ticket_id, passenger_lastname, passenger_firstname, passenger_middlename, passport_id, gen_id, passenger_age, passport_abroad_id, custom_inspection, luggage) VALUES(" + ticketID[0].substring(1, ticketID[0].length() - 1) + ", '" + lastname + "', '" + firstname + "', '" + middlename + "', '" + passportID + "', " + genderID[0].substring(1, genderID[0].length() - 1) + ", " + age + ", '" + passportAbroadID + "', " + custom + ", " + lug + ")";
            }
            System.out.println(insert);
            insertIntoTable(conn, insert);
        }
    }
}
