package work.requests;

import work.Roles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Vector;

public class getInfoAboutRequest extends JFrame {
    private JButton ok;
    private JButton back;
    private JPanel mainPanel;

    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;

    private JFormattedTextField numberField1;
    private JFormattedTextField numberField2;
    private JFormattedTextField numberField3;
    private JFormattedTextField numberField4;
    private JFormattedTextField numberField5;
    private JFormattedTextField numberField6;
    private JFormattedTextField numberField7;
    private JFormattedTextField numberField8;
    private JFormattedTextField numberField9;

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    private JCheckBox checkBox1;
    private JCheckBox checkBox2;

    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;

    private role userRole;
    private String request;
    public getInfoAboutRequest(Connection conn, role userRole, Vector strings, Integer numReq){
        super("Введите данные");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;

        ok = new JButton("Найти");
        back = new JButton("Назад");

        addActionListeners(conn, strings, numReq);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        createWindow(numReq, conn);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(ok);
        buttonsPanel.add(back);

        mainPanel.add(buttonsPanel);

        add(mainPanel);
        setVisible(true);
    }

    private ResultSet selectFromTable(Connection conn, String select){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(select);
            ResultSet tmp = preparedStatement.executeQuery();
            return tmp;
        } catch (SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }
    private Vector selectToComboBox(String select, Connection conn){
        Vector strings = new Vector();
        ResultSetMetaData resultSetMetaData = null;
        ResultSet resultSet = selectFromTable(conn, select);
        try{
            resultSetMetaData = resultSet.getMetaData();
            Vector init = new Vector();
            init.add("0, не выбрано");
            strings.add(init);
            while (resultSet.next()){
                Vector tmp = new Vector();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
                    tmp.add(resultSet.getString(i));
                strings.add(tmp);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return strings;
    }

    private void createWindow(Integer numReq, Connection conn){
        switch (userRole){
            case adminBD: {
                createAdminWindow(numReq, conn);
            } break;
            case cashier: {
                createCashierWindow(numReq, conn);
            } break;
            case technic: {
                createTechnicWindow(numReq, conn);
            } break;
            case passenger: {
                createPassengerWindow(numReq, conn);
            } break;
        }
    }
    private void createAdminWindow(Integer numReq, Connection conn){
        switch (numReq){
            case 1:{
                label1 = new JLabel("Получить список и общее число всех работников аэропорта:");

                checkBox1 = new JCheckBox("начальник отдела");

                label2 = new JLabel("пол");
                comboBox1 = new JComboBox(selectToComboBox("SELECT * FROM gender", conn));

                label3 = new JLabel("возраст");
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberField1 = new JFormattedTextField(numberFormat);
                numberField1.setValue(new Integer(0));
                numberField1.setColumns(5);

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();

                panel1.add(label1);
                panel2.add(checkBox1);
                panel3.add(label2);
                panel3.add(comboBox1);
                panel3.add(label3);
                panel3.add(numberField1);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
                mainPanel.add(panel3);
            } break;
            case 2: {
                label1 = new JLabel("Получить список и общее число всех работников аэропорта:");

                label2 = new JLabel("бригада");
                comboBox1 = new JComboBox(selectToComboBox("SELECT car_id FROM carriage", conn));

                label3 = new JLabel("обслуживают рейс");
                comboBox2 = new JComboBox(selectToComboBox("SELECT trip_id FROM trips", conn));

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();

                panel1.add(label1);
                panel2.add(label2);
                panel2.add(comboBox1);
                panel3.add(label3);
                panel3.add(comboBox2);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
                mainPanel.add(panel3);
            } break;
        }
    }
    private void createCashierWindow(Integer numReq, Connection conn){
        switch (numReq){
            case 1:{
                label1 = new JLabel("категория авиарейса");
                comboBox1 = new JComboBox(selectToComboBox("SELECT * FROM tripType", conn));

                label2 = new JLabel("направление: ");

                label3 = new JLabel("откуда");
                textField1 = new JTextField(25);
                Color colorLog1 = textField1.getCaretColor();
                textField1.setForeground(Color.LIGHT_GRAY);
                textField1.setText("откуда");
                textField1.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent event){
                        textField1.setForeground(colorLog1);
                        if (textField1.getText().equals("откуда"))
                            textField1.setText("");
                    }
                    @Override
                    public void focusLost(FocusEvent event) {
                        if (textField1.getText().isEmpty()) {
                            textField1.setForeground(Color.LIGHT_GRAY);
                            textField1.setText("откуда");
                        }
                    }
                });

                label4 = new JLabel("куда");
                textField2 = new JTextField(25);
                Color colorLog2 = textField2.getCaretColor();
                textField2.setForeground(Color.LIGHT_GRAY);
                textField2.setText("куда");
                textField2.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent event){
                        textField2.setForeground(colorLog2);
                        if (textField2.getText().equals("куда"))
                            textField2.setText("");
                    }
                    @Override
                    public void focusLost(FocusEvent event) {
                        if (textField2.getText().isEmpty()) {
                            textField2.setForeground(Color.LIGHT_GRAY);
                            textField2.setText("куда");
                        }
                    }
                });

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();
                JPanel panel4 = new JPanel();

                panel1.add(label1);
                panel1.add(comboBox1);
                panel2.add(label2);
                panel3.add(label3);
                panel3.add(textField1);
                panel4.add(label4);
                panel4.add(textField2);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
                mainPanel.add(panel3);
                mainPanel.add(panel4);
            } break;
            case 2: {
                label1 = new JLabel("номер рейса");
                comboBox1 = new JComboBox(selectToComboBox("SELECT trip_id FROM trips", conn));

                checkBox1 = new JCheckBox("улетел за границу");

                checkBox2 = new JCheckBox("сдал вещи в багаж");

                label2 = new JLabel("пол");
                comboBox2 = new JComboBox(selectToComboBox("SELECT * FROM gender", conn));

                label3 = new JLabel("возраст");
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberField1 = new JFormattedTextField(numberFormat);
                numberField1.setValue(new Integer(0));
                numberField1.setColumns(5);

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();
                JPanel panel4 = new JPanel();

                panel1.add(label1);
                panel1.add(comboBox1);
                panel2.add(checkBox1);
                panel2.add(checkBox2);
                panel3.add(label2);
                panel3.add(comboBox2);
                panel4.add(label3);
                panel4.add(numberField1);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
                mainPanel.add(panel3);
                mainPanel.add(panel4);
            } break;
        }
    }
    private void createTechnicWindow(Integer numReq, Connection conn){
        switch (numReq){
            case 1:{
                label1 = new JLabel("аэропорт, к которому приписан самолёт");
                comboBox1 = new JComboBox(selectToComboBox("SELECT * FROM airport", conn));

                label2 = new JLabel("количество совершенных рейсов");
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberField1 = new JFormattedTextField(numberFormat);
                numberField1.setValue(new Integer(0));
                numberField1.setColumns(5);

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();

                panel1.add(label1);
                panel1.add(comboBox1);
                panel2.add(label2);
                panel2.add(numberField1);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
            } break;
            case 2:{
                label1 = new JLabel("Получить список и общее число всех самолётов:");

                label2 = new JLabel("т.о. пройден в период с");
                NumberFormat numberFormat = NumberFormat.getNumberInstance();
                numberField1 = new JFormattedTextField(numberFormat);
                numberField1.setValue(new Integer(1));
                numberField1.setColumns(3);
                numberField2 = new JFormattedTextField(numberFormat);
                numberField2.setValue(new Integer(1));
                numberField2.setColumns(3);
                numberField3 = new JFormattedTextField(numberFormat);
                numberField3.setValue(new Integer(2000));
                numberField3.setColumns(5);

                label3 = new JLabel("по");
                numberField4 = new JFormattedTextField(numberFormat);
                numberField4.setValue(new Integer(1));
                numberField4.setColumns(3);
                numberField5 = new JFormattedTextField(numberFormat);
                numberField5.setValue(new Integer(1));
                numberField5.setColumns(3);
                numberField6 = new JFormattedTextField(numberFormat);
                numberField6.setValue(new Integer(2000));
                numberField6.setColumns(5);

                label4 = new JLabel("кол-во ремонтов");
                numberField7 = new JFormattedTextField(numberFormat);
                numberField7.setValue(new Integer(0));
                numberField7.setColumns(3);

                label5 = new JLabel("кол-во совершенных рейсов");
                numberField8 = new JFormattedTextField(numberFormat);
                numberField8.setValue(new Integer(0));
                numberField8.setColumns(3);

                label6 = new JLabel("возраст");
                numberField9 = new JFormattedTextField(numberFormat);
                numberField9.setValue(new Integer(0));
                numberField9.setColumns(3);

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();

                panel1.add(label1);
                panel2.add(label2);
                panel2.add(numberField1);
                panel2.add(numberField2);
                panel2.add(numberField3);
                panel2.add(label3);
                panel2.add(numberField4);
                panel2.add(numberField5);
                panel2.add(numberField6);
                panel3.add(label4);
                panel3.add(numberField7);
                panel3.add(label5);
                panel3.add(numberField8);
                panel3.add(label6);
                panel3.add(numberField9);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
                mainPanel.add(panel3);
            } break;
        }
    }
    private void createPassengerWindow(Integer numReq, Connection conn){
        switch (numReq){
            case 1:{
                label1 = new JLabel("категория авиарейса");
                comboBox1 = new JComboBox(selectToComboBox("SELECT * FROM tripType", conn));

                label2 = new JLabel("направление: ");

                label3 = new JLabel("откуда");
                textField1 = new JTextField(25);
                Color colorLog1 = textField1.getCaretColor();
                textField1.setForeground(Color.LIGHT_GRAY);
                textField1.setText("откуда");
                textField1.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent event){
                        textField1.setForeground(colorLog1);
                        if (textField1.getText().equals("откуда"))
                            textField1.setText("");
                    }
                    @Override
                    public void focusLost(FocusEvent event) {
                        if (textField1.getText().isEmpty()) {
                            textField1.setForeground(Color.LIGHT_GRAY);
                            textField1.setText("откуда");
                        }
                    }
                });

                label4 = new JLabel("куда");
                textField2 = new JTextField(25);
                Color colorLog2 = textField2.getCaretColor();
                textField2.setForeground(Color.LIGHT_GRAY);
                textField2.setText("куда");
                textField2.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent event){
                        textField2.setForeground(colorLog2);
                        if (textField2.getText().equals("куда"))
                            textField2.setText("");
                    }
                    @Override
                    public void focusLost(FocusEvent event) {
                        if (textField2.getText().isEmpty()) {
                            textField2.setForeground(Color.LIGHT_GRAY);
                            textField2.setText("куда");
                        }
                    }
                });

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();
                JPanel panel4 = new JPanel();

                panel1.add(label1);
                panel1.add(comboBox1);
                panel2.add(label2);
                panel3.add(label3);
                panel3.add(textField1);
                panel4.add(label4);
                panel4.add(textField2);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
                mainPanel.add(panel3);
                mainPanel.add(panel4);
            } break;
            case 2:{
                label1 = new JLabel("авиарейсы:");

                label2 = new JLabel("состояние реса");
                comboBox1 = new JComboBox(selectToComboBox("SELECT * FROM tripStatus", conn));

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();

                panel1.add(label1);
                panel2.add(label2);
                panel2.add(comboBox1);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
            } break;
            case 3:{
                label1 = new JLabel("введите ФИО:");
                label2 = new JLabel("фамилия");
                textField1 = new JTextField(25);
                label3 = new JLabel("имя");
                textField2 = new JTextField(25);
                label4 = new JLabel("отчество (при наличии)");
                textField3 = new JTextField(25);

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();
                JPanel panel3 = new JPanel();
                JPanel panel4 = new JPanel();

                panel1.add(label1);
                panel2.add(label2);
                panel2.add(textField1);
                panel3.add(label3);
                panel3.add(textField2);
                panel4.add(label4);
                panel4.add(textField3);

                mainPanel.add(panel1);
                mainPanel.add(panel2);
                mainPanel.add(panel3);
                mainPanel.add(panel4);
            }
        }
    }

    private void fillRequest(Integer numReq){
        switch (userRole){
            case adminBD: fillRequestsAdmin(numReq); break;
            case cashier: fillRequestsCashier(numReq); break;
            case technic: fillRequestsTechnic(numReq); break;
            case passenger: fillRequestsPassenger(numReq); break;
        }
    }
    private void fillRequestsAdmin(Integer numReq){
        Integer selected = 0;
        switch (numReq){
            case 1:{
                request = "SELECT * FROM workers";
                if (checkBox1.isSelected()) {
                    selected = 1;
                    request = request + " WHERE(is_manager = 1";
                }
                String genIdStr = comboBox1.getSelectedItem().toString();
                String[] genIdArr = genIdStr.split(" ");
                String genId = genIdArr[0].substring(1, genIdArr[0].length()- 1);
                if (!genId.equals("0")) {
                    if (selected == 1)
                        request = request + " AND gen_id = " + genId;
                    else
                        request = request + " WHERE(gen_id = " + genId;
                    selected = 1;
                }
                if (!numberField1.getValue().toString().equals("0")) {
                    if (selected == 1)
                        request = request + " AND worker_age = " + numberField1.getValue().toString();
                    else
                        request = request + " WHERE(worker_age = " + numberField1.getValue().toString();
                    selected = 1;
                }
                if (selected == 1)
                    request = request + ")";
            } break;
            case 2: {
                request = "SELECT * FROM passengers";
                String carIdStr = comboBox1.getSelectedItem().toString();
                String[] carIdArr = carIdStr.split(" ");
                String carId = carIdArr[0].substring(1, carIdArr[0].length()- 1);
                if (!carId.equals("0")) {
                    if (selected == 1)
                        request = request + " AND car_id = " + carId;
                    else
                        request = request + " WHERE(car_id = " + carId;
                    selected = 1;
                }
                String tripIdStr = comboBox2.getSelectedItem().toString();
                String[] tripIdArr = tripIdStr.split(" ");
                String tripId = tripIdArr[0].substring(1, tripIdArr[0].length()- 1);
                if (!tripId.equals("0") && selected == 0) {
                    request = "WITH S1 AS (SELECT car_id FROM trips WHERE(trip_id = " + tripId + ")) SELECT * FROM workers JOIN S1 USING(car_id)";
                }
                if (selected == 1)
                    request = request + ")";
            } break;
        }
    }
    private void fillRequestsCashier(Integer numReq){
        Integer selected = 0;
        switch (numReq){
            case 1:{
                request = "SELECT * FROM trips";
                String tripIdStr = comboBox1.getSelectedItem().toString();
                String[] tripIdArr = tripIdStr.split(" ");
                String tripId = tripIdArr[0].substring(1, tripIdArr[0].length()- 1);
                if (!tripId.equals("0")) {
                    if (selected == 1)
                        request = request + " AND trip_type_id = " + tripId;
                    else
                        request = request + " WHERE(trip_type_id = " + tripId;
                    selected = 1;
                }
                if (!textField1.getText().equals("откуда")){
                    if (selected == 1)
                        request = request + " AND depart_place = '" + textField1.getText() + "'";
                    else
                        request = request + " WHERE(depart_place = '" + textField1.getText() + "'";
                    selected = 1;
                }
                if (!textField2.getText().equals("куда")){
                    if (selected == 1)
                        request = request + " AND arrival_place = '" + textField2.getText() + "'";
                    else
                        request = request + " WHERE(arrival_place = '" + textField2.getText() + "'";
                    selected = 1;
                }
                if (selected == 1)
                    request = request + ")";
            } break;
            case 2: {
                request = "SELECT * FROM passengers";
                String tripIdStr = comboBox1.getSelectedItem().toString();
                String[] tripIdArr = tripIdStr.split(" ");
                String tripId = tripIdArr[0].substring(1, tripIdArr[0].length()- 1);
                if (!tripId.equals("0")) {
                    request = "WITH S1 AS (SELECT ticket_id, trip_id FROM tickets WHERE(trip_id = " + tripId + ")) SELECT * FROM passengers JOIN S1 USING (ticket_id)";
                    selected = 1;
                }
                if (checkBox1.isSelected()){
                    request = request + " WHERE(passport_abroad_id IS NOT NULL";
                } else {
                    request = request + " WHERE(passport_abroad_id IS NULL";
                }
                selected = 1;
                if (checkBox2.isSelected()){
                    if (selected == 1)
                        request = request + " AND luggage = 1";
                    else
                        request = request + " WHERE(luggage = 1";
                } else {
                    if (selected == 1)
                        request = request + " AND luggage = 0";
                    else
                        request = request + " WHERE(luggage = 0";
                }
                selected = 1;
                String genIdStr = comboBox2.getSelectedItem().toString();
                String[] genIdArr = genIdStr.split(" ");
                String genId = genIdArr[0].substring(1, genIdArr[0].length()- 1);
                if (!genId.equals("0")) {
                    if (selected == 1)
                        request = request + " AND gen_id = " + genId;
                    else
                        request = request + " WHERE(gen_id = " + genId;
                    selected = 1;
                }
                if (!numberField1.getValue().toString().equals("0")){
                    if (selected == 1)
                        request = request + " AND passenger_age = " + numberField1.getValue().toString();
                    else
                        request = request + " WHERE(passenger_age = " + numberField1.getValue().toString();
                    selected = 1;
                }
                if (selected == 1)
                    request = request + ")";
            } break;
        }
    }
    private void fillRequestsTechnic(Integer numReq){
        Integer selected = 0;
        switch (numReq){
            case 1:{
                request = "SELECT * FROM planes";
                String airportIdStr = comboBox1.getSelectedItem().toString();
                String[] airportIdArr = airportIdStr.split(" ");
                String airportId = airportIdArr[0].substring(1, airportIdArr[0].length()- 1);
                if (!airportId.equals("0")) {
                    if (selected == 1)
                        request = request + " AND airport_id = " + airportId;
                    else
                        request = request + " WHERE(airport_id = " + airportId;
                    selected = 1;
                }
                if (!numberField1.getValue().toString().equals("0")) {
                    if (selected == 1)
                        request = request + " AND trip_count = " + numberField1.getValue().toString();
                    else
                        request = request + " WHERE(trip_count = " + numberField1.getValue().toString();
                    selected = 1;
                }
                if (selected == 1)
                    request = request + ")";
            } break;
            case 2:{
                if (!numberField1.getValue().toString().equals(numberField4.getValue().toString()) || !numberField2.getValue().toString().equals(numberField5.getValue().toString())
                        || !numberField3.getValue().toString().equals(numberField6.getValue().toString())) {
                    request = "WITH S1 AS (SELECT ti_id, ti_date FROM technicalInspection WHERE(ti_result = 1)) SELECT * FROM planes JOIN S1 USING (ti_id) WHERE(ti_date BETWEEN TO_DATE('"
                            + numberField1.getValue().toString() + "." + numberField2.getValue().toString() + "." + numberField3.getValue().toString()
                            + "','dd.mm.yyyy') AND TO_DATE('" + numberField4.getValue().toString() + "." + numberField5.getValue().toString()
                            + "." + numberField6.getValue().toString() + "','dd.mm.yyyy')";
                    selected = 1;
                }
                else
                    request = "WITH S1 AS (SELECT ti_id, ti_date FROM technicalInspection WHERE(ti_result = 1)) SELECT * FROM planes JOIN S1 USING (ti_id)";
                if (!numberField7.getValue().toString().equals("0")) {
                    if (selected == 1)
                        request = request + " AND repairing_count = " + numberField7.getValue().toString();
                    else
                        request = request + " WHERE(repairing_count = " + numberField7.getValue().toString();
                    selected = 1;
                }
                if (!numberField8.getValue().toString().equals("0")) {
                    if (selected == 1)
                        request = request + " AND trip_count = " + numberField8.getValue().toString();
                    else
                        request = request + " WHERE(trip_count = " + numberField8.getValue().toString();
                    selected = 1;
                }
                if (!numberField9.getValue().toString().equals("0")) {
                    if (selected == 1)
                        request = request + " AND plane_age = " + numberField9.getValue().toString();
                    else
                        request = request + " WHERE(plane_age = " + numberField9.getValue().toString();
                    selected = 1;
                }
                if (selected == 1)
                    request = request + ")";
            } break;
        }
    }
    private void fillRequestsPassenger(Integer numReq){
        Integer selected = 0;
        switch (numReq){
            case 1:{
                request = "SELECT * FROM trips";
                String tripIdStr = comboBox1.getSelectedItem().toString();
                String[] tripIdArr = tripIdStr.split(" ");
                String tripId = tripIdArr[0].substring(1, tripIdArr[0].length()- 1);
                if (!tripId.equals("0")) {
                    if (selected == 1)
                        request = request + " AND trip_type_id = " + tripId;
                    else
                        request = request + " WHERE(trip_type_id = " + tripId;
                    selected = 1;
                }
                if (!textField1.getText().equals("откуда")){
                    if (selected == 1)
                        request = request + " AND depart_place = '" + textField1.getText() + "'";
                    else
                        request = request + " WHERE(depart_place = '" + textField1.getText() + "'";
                    selected = 1;
                }
                if (!textField2.getText().equals("куда")){
                    if (selected == 1)
                        request = request + " AND arrival_place = '" + textField2.getText() + "'";
                    else
                        request = request + " WHERE(arrival_place = '" + textField2.getText() + "'";
                    selected = 1;
                }
                if (selected == 1)
                    request = request + ")";
            } break;
            case 2:{
                request = "SELECT rec_id, trip_id, trips.depart_time, trips.arrival_time, trips.depart_place, trips.plane_change_place, trips.arrival_place, tripStatus.trip_status_name, tripStatus.trip_status_reason FROM timetable JOIN trips USING(trip_id) JOIN tripStatus USING(trip_status_id)";
                String tripStatusIdStr = comboBox1.getSelectedItem().toString();
                String[] tripStatusIdArr = tripStatusIdStr.split(" ");
                String tripStatusId = tripStatusIdArr[0].substring(1, tripStatusIdArr[0].length()- 1);
                if (!tripStatusId.equals("0")) {
                    request = request + " WHERE(trip_status_id = " + tripStatusId + ")";
                }
            } break;
            case 3: {
                if (!textField3.getText().equals(""))
                    request = "WITH S1 AS(SELECT ticket_id FROM passengers WHERE (passenger_lastname = '" + textField1.getText() + "' AND passenger_firstname = '" + textField2.getText() + "' AND passenger_middlename = '" + textField3.getText() + "')) SELECT ticket_id, trip_id, trips.depart_time, trips.arrival_time, trips.depart_place, trips.plane_change_place, trips.arrival_place, ticket_seat_num, aviacompany.aviacomp_name, ticketClass.ticket_class_name FROM tickets JOIN S1 USING(ticket_id) JOIN ticketClass USING(ticket_class_id) JOIN aviacompany USING(aviacomp_id) JOIN trips USING(trip_id)";
                else
                    request = "WITH S1 AS(SELECT ticket_id FROM passengers WHERE (passenger_lastname = '" + textField1.getText() + "' AND passenger_firstname = '" + textField2.getText() + "')) SELECT ticket_id, trip_id, trips.depart_time, trips.arrival_time, trips.depart_place, trips.plane_change_place, trips.arrival_place, ticket_seat_num, aviacompany.aviacomp_name, ticketClass.ticket_class_name FROM tickets JOIN S1 USING(ticket_id) JOIN ticketClass USING(ticket_class_id) JOIN aviacompany USING(aviacomp_id) JOIN trips USING(trip_id)";
            }
        }
    }

    private void addActionListeners(Connection conn, Vector strings, Integer num){
        ok.addActionListener((e)->{
            setVisible(false);
            fillRequest(num);
            new executeRequestWindow(conn, request, userRole, strings, num);
        });
        back.addActionListener((e)->{
            setVisible(false);
            switch(userRole){
                case adminBD: new adminRoleWindow(conn); break;
                case cashier: new cashierRoleWindow(conn); break;
                case technic: new technicRoleWindow(conn); break;
                case passenger: new passengerRoleWindow(conn); break;
            }
        });
    }
}
