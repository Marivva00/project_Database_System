package work.lookOnTable;

import work.MainMenuWindow;
import work.Tables.*;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class lookOnTableView extends JFrame {
    private JButton goBack;
    private Vector strings;
    private Vector columnNames;
    public lookOnTableView(Connection conn, Integer dep, String tableName){
        super("work with '" + tableName + "' table");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        goBack = new JButton("back");
        addActionListener(conn, tableName);

        getTable(conn, dep, tableName);

        JTable table = new JTable(strings, columnNames);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(goBackPanel);
        add(mainPanel);

        setVisible(true);
    }
    private void addActionListener(Connection conn, String tableName){
        goBack.addActionListener((e)->{
            setVisible(false);
            new MainMenuWindow(conn);
        });
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
    private void easySelect(Connection conn, String select){
        ResultSet resultSet = null;
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(select);
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
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    private void departmentTable(Connection conn){
        String select = "SELECT * FROM department";
        easySelect(conn, select);
    }
    private void medicalTable(Connection conn){
        String select = "SELECT * FROM medical";
        easySelect(conn, select);
    }
    private void aviacompanyTable(Connection conn){
        String select = "SELECT * FROM aviacompany";
        easySelect(conn, select);
    }
    private void ticketClassTable(Connection conn){
        String select = "SELECT * FROM ticketClass";
        easySelect(conn, select);
    }
    private void tripStatusTable(Connection conn){
        String select = "SELECT * FROM tripStatus";
        easySelect(conn, select);
    }
    private void tripTypeTable(Connection conn){
        String select = "SELECT * FROM tripType";
        easySelect(conn, select);
    }
    private void airportTable(Connection conn){
        String select = "SELECT * FROM airport";
        easySelect(conn, select);
    }
    private void genderTable(Connection conn){
        String select = "SELECT * FROM gender";
        easySelect(conn, select);
    }
    private void carriageTable(Connection conn){
        String select = "SELECT carriage.car_id, department.dep_name FROM carriage RIGHT JOIN department USING (dep_id)";
        easySelect(conn, select);
    }
    private void workersTable(Connection conn){
        String select = "WITH S1 AS ( SELECT carriage.car_id, department.dep_name FROM carriage RIGHT JOIN department USING (dep_id)) SELECT workers.worker_id, workers.worker_lastname, workers.worker_firstname, workers.worker_middlename, workers.worker_age, gender.gen_name, workers.worker_child_count, car_id, dep_name, workers.med_id FROM workers RIGHT JOIN S1 USING (car_id) JOIN gender USING (gen_id)";
        easySelect(conn, select);
    }
    private void technicalInspectionTable(Connection conn){
        String select = "SELECT ti_id, ti_date, deg_of_wear, ti_result, worker_id, worker_lastname, worker_firstname, worker_middlename, workers.car_id FROM technicalInspection JOIN workers USING(worker_id)";
        easySelect(conn, select);
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
