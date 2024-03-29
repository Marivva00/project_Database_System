package work.GUI.lookOnTable;

import work.GUI.MainMenuWindow;
import work.Roles.*;
import work.EDIT_BD.addRecords.addRecordsToTable;
import work.EDIT_BD.addRecords.chooseTable;
import work.EDIT_BD.addRecords.errorInDelete.errorInDeleteRecord;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class lookOnTableView extends JFrame {
    private JButton goBack;
    private JButton addRecord;
    private JButton deleteRecord;
    private JButton editRecord;
    private JButton help;
    private Vector strings;
    private Vector columnNames;
    private JTable table;
    private Integer del = 0;
    private Integer add = 1;
    private Integer edit = 2;
    private Integer select = 3;
    private String tableName;
    private Integer dep;
    private Integer errorDelete = 0;
    private role userRole;

    public lookOnTableView(Connection conn, Integer dep, String tableName, role userRole){
        super("Работа с '" + tableName + "' таблицей");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;
        this.tableName = tableName;
        this.dep = dep;

        goBack = new JButton("Назад");
        goBack.addActionListener((e)->{
            setVisible(false);
            switch (userRole){
                case adminBD: new MainMenuWindow(conn, userRole); break;
                case admin: new adminRoleWindow(conn); break;
                case cashier: new cashierRoleWindow(conn); break;
                case technic: new technicRoleWindow(conn); break;
                case passenger: new passengerRoleWindow(conn); break;
            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(goBack);

        switch (userRole){
            case adminBD:{
                addRecord = new JButton("Добавить запись");
                deleteRecord = new JButton("Удалить запись");
                editRecord = new JButton("Изменить запись");
                help = new JButton("Справка");
                addActionListener(conn);
                buttonsPanel.add(addRecord);
                buttonsPanel.add(editRecord);
                buttonsPanel.add(deleteRecord);
                buttonsPanel.add(help);
            }break;
            case admin:{
                if (tableName.equals("workers") || tableName.equals("timetable") || tableName.equals("trips")){
                    addRecord = new JButton("Добавить запись");
                    deleteRecord = new JButton("Удалить запись");
                    editRecord = new JButton("Изменить запись");
                    help = new JButton("Справка");
                    addActionListener(conn);
                    buttonsPanel.add(addRecord);
                    buttonsPanel.add(editRecord);
                    buttonsPanel.add(deleteRecord);
                    buttonsPanel.add(help);
                }
            } break;
            case cashier:{
                if (tableName.equals("reserveTickets") || tableName.equals("tickets") || tableName.equals("passengers")){
                    addRecord = new JButton("Добавить запись");
                    deleteRecord = new JButton("Удалить запись");
                    editRecord = new JButton("Изменить запись");
                    help = new JButton("Справка");
                    addActionListener(conn);
                    buttonsPanel.add(addRecord);
                    buttonsPanel.add(editRecord);
                    buttonsPanel.add(deleteRecord);
                    buttonsPanel.add(help);
                }
            } break;
            case technic:{
                if (tableName.equals("technicalInspection")){
                    addRecord = new JButton("Добавить запись");
                    deleteRecord = new JButton("Удалить запись");
                    editRecord = new JButton("Изменить запись");
                    help = new JButton("Справка");
                    addActionListener(conn);
                    buttonsPanel.add(addRecord);
                    buttonsPanel.add(editRecord);
                    buttonsPanel.add(deleteRecord);
                    buttonsPanel.add(help);
                }
            } break;
        }

        getTable(conn, select, null);

        table = new JTable(strings, columnNames);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonsPanel);
        add(mainPanel);

        setVisible(true);
    }
    private void addActionListener(Connection conn){
        deleteRecord.addActionListener((e) -> {
            setVisible(false);
            deleteRecordFromTable(conn);
            if (errorDelete == 0)
                new lookOnTableView(conn, dep, tableName, userRole);
        });
        addRecord.addActionListener((e) -> {
            setVisible(false);
            addRecordToTable(conn);
        });
        editRecord.addActionListener((e) -> {
            setVisible(false);
            editRecordInTable(conn);
        });
        help.addActionListener((e) -> {
            setVisible(false);
            new helpInfoWindow(conn, tableName, dep, userRole);
        });
    }
    private void deleteRecordFromTable(Connection conn){
        String recordId = table.getValueAt(table.getSelectedRow(), 0).toString();
        getTable(conn, del, recordId);
    }
    private void addRecordToTable(Connection conn){
        setVisible(false);
        getTable(conn, add, null);
    }
    private void editRecordInTable(Connection conn){

    }

    private void getTable(Connection conn, Integer what, String recordId){
        if (dep == 0)
            switch (tableName){
                case "department": departmentTable(conn, what, recordId); break;
                case "medical": medicalTable(conn, what, recordId); break;
                case "aviacompany": aviacompanyTable(conn, what, recordId); break;
                case "ticketClass": ticketClassTable(conn, what, recordId); break;
                case "tripStatus": tripStatusTable(conn, what, recordId); break;
                case "tripType": tripTypeTable(conn, what, recordId); break;
                case "airport": airportTable(conn, what, recordId); break;
                case "gender": genderTable(conn, what, recordId); break;
            }
        else{
            switch (tableName){
                case "carriage": carriageTable(conn, what, recordId); break;
                case "workers": workersTable(conn, what, recordId); break;
                case "technicalInspection": technicalInspectionTable(conn, what, recordId); break;
                case "planes": planesTable(conn, what, recordId); break;
                case "trips": tripsTable(conn, what, recordId); break;
                case "timetable": timetableTable(conn, what, recordId); break;
                case "tickets": ticketsTable(conn, what, recordId); break;
                case "reserveTickets": reserveTicketsTable(conn, what, recordId); break;
                case "passengers": passengersTable(conn, what, recordId); break;
            }
        }
    }
    private void generalSelect(Connection conn, String select){
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = conn.prepareStatement(select);
            resultSet = preparedStatement.executeQuery();
            conn.commit();
        } catch (SQLException exception){
            try {
                conn.rollback();
            } catch (SQLException exception1){
                exception1.printStackTrace();
            }
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
        try {
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    private void generalDelete(Connection conn, String delete){
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(delete);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception){
            setVisible(false);
            errorDelete = 1;
            new errorInDeleteRecord(conn, tableName, dep, userRole);
        }
    }

    private void departmentTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM department WHERE dep_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM department";
                generalSelect(conn, select);
            } break;
        }
    }
    private void medicalTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM medical WHERE med_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM medical";
                generalSelect(conn, select);
            } break;
        }
    }
    private void aviacompanyTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM aviacompany WHERE aviacomp_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM aviacompany";
                generalSelect(conn, select);
            } break;
        }
    }
    private void ticketClassTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM ticketClass WHERE ticket_class_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM ticketClass";
                generalSelect(conn, select);
            } break;
        }
    }
    private void tripStatusTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM tripStatus WHERE trip_status_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM tripStatus";
                generalSelect(conn, select);
            } break;
        }
    }
    private void tripTypeTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM tripType WHERE trip_type_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM tripType";
                generalSelect(conn, select);
            } break;
        }
    }
    private void airportTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM airport WHERE airport_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM airport";
                generalSelect(conn, select);
            } break;
        }
    }
    private void genderTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM gender WHERE gen_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep, tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM gender";
                generalSelect(conn, select);
            } break;
        }
    }

    private void carriageTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM carriage WHERE car_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT carriage.car_id, department.dep_name FROM carriage RIGHT JOIN department USING (dep_id)";
                generalSelect(conn, select);
            } break;
        }
    }
    private void workersTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM workers WHERE worker_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new chooseTable(conn, userRole);
            } break;
            case 3:{
                String select = "WITH S1 AS ( SELECT carriage.car_id, department.dep_name FROM carriage RIGHT JOIN department USING (dep_id)) SELECT workers.worker_id, workers.worker_lastname, workers.worker_firstname, workers.worker_middlename, workers.worker_age, gender.gen_name, car_id, dep_name, workers.is_manager FROM workers RIGHT JOIN S1 USING (car_id) JOIN gender USING (gen_id)";
                generalSelect(conn, select);
            } break;
        }
    }
    private void technicalInspectionTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM technicalInspection WHERE ti_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT ti_id, ti_date, deg_of_wear, ti_result, worker_id, worker_lastname, worker_firstname, worker_middlename, workers.car_id FROM technicalInspection JOIN workers USING(worker_id)";
                generalSelect(conn, select);
            } break;
        }
    }
    private void planesTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM planes WHERE plane_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT plane_id, plane_type, ti_id, plane_passengers_max, plane_age, trip_count, repairing_count, airport.airport_name FROM planes JOIN airport USING(airport_id)";
                generalSelect(conn, select);
            } break;
        }
    }
    private void tripsTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM trips WHERE trip_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT trips.trip_id, trips.plane_id, depart_time, arrival_time, depart_place, plane_change_place, arrival_place, tripType.trip_type_name, trips.car_id, max_count_tickets, prodano_count_tickets, reserve_count_tickets, sdano_count_tickets, ticket_cost FROM trips JOIN tripType USING(trip_type_id)";
                generalSelect(conn, select);
            } break;
        }
    }
    private void timetableTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM timetable WHERE rec_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT rec_id, trip_id, trips.depart_time, trips.arrival_time, trips.depart_place, trips.plane_change_place, trips.arrival_place, tripStatus.trip_status_name, tripStatus.trip_status_reason FROM timetable JOIN trips USING(trip_id) JOIN tripStatus USING(trip_status_id)";
                generalSelect(conn, select);
            } break;
        }
    }
    private void ticketsTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                try {
                    if (errorDelete == 1) {
                        conn.rollback();
                        throw new SQLException();
                    }
                    else conn.commit();
                    String tripIDSelect = "SELECT trip_id FROM tickets WHERE(ticket_id = " + recordId + ")";
                    Integer tripID = -1;
                    try {
                        PreparedStatement preStatement = conn.prepareStatement(tripIDSelect);
                        ResultSet resultSet = preStatement.executeQuery();
                        while (resultSet.next()) {
                            tripID = resultSet.getInt(1);
                        }
                        resultSet.close();
                        preStatement.close();
                        conn.commit();
                    } catch (SQLException exception) {
                        try {
                            conn.rollback();
                        } catch (SQLException exception1) {
                            exception1.printStackTrace();
                        }
                        exception.printStackTrace();
                    }
                    String sdanoCountSelect = "SELECT sdano_count_tickets FROM trips WHERE(trip_id = " + tripID.toString() + ")";
                    Integer sdanoCount = -1;
                    try {
                        PreparedStatement preStatement = conn.prepareStatement(sdanoCountSelect);
                        ResultSet resultSet = preStatement.executeQuery();
                        while (resultSet.next()) {
                            sdanoCount = resultSet.getInt(1);
                        }
                        resultSet.close();
                        preStatement.close();
                        conn.commit();
                    } catch (SQLException exception) {
                        try {
                            conn.rollback();
                        } catch (SQLException exception1) {
                            exception1.printStackTrace();
                        }
                        exception.printStackTrace();
                    }
                    sdanoCount++;
                    String upd = "UPDATE trips SET sdano_count_tickets = " + sdanoCount.toString() + " WHERE(trip_id = " + tripID.toString() + ")";
                    PreparedStatement preparedStatement = conn.prepareStatement(upd);
                    preparedStatement.executeUpdate(upd);
                    preparedStatement.close();
                    String delete = "DELETE FROM tickets WHERE ticket_id = " + recordId;
                    generalDelete(conn, delete);
                    if (errorDelete == 1) {
                        conn.rollback();
                    }
                    else conn.commit();
                } catch (SQLException exception){
                    try{
                        conn.rollback();
                    } catch (SQLException exception1){
                        exception1.printStackTrace();
                    }
                    exception.printStackTrace();
                }
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT tickets.ticket_id, trip_id, trips.depart_place, trips.plane_change_place, trips.arrival_place, trips.depart_time, trips.arrival_time, tickets.ticket_seat_num, ticketClass.ticket_class_name, aviacompany.aviacomp_name FROM tickets JOIN trips USING(trip_id) JOIN ticketClass USING (ticket_class_id) JOIN aviacompany USING (aviacomp_id) ORDER BY (ticket_id)";
                generalSelect(conn, select);
            } break;
        }
    }
    private void reserveTicketsTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM reserveTickets WHERE reserve_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT * FROM reserveTickets";
                generalSelect(conn, select);
            } break;
        }
    }
    private void passengersTable(Connection conn, Integer what, String recordId){
        switch (what){
            case 0:{
                String delete = "DELETE FROM passengers WHERE passenger_id = " + recordId;
                generalDelete(conn, delete);
            } break;
            case 1:{
                new addRecordsToTable(conn, dep,  tableName, userRole);
            } break;
            case 3:{
                String select = "SELECT passenger_id, passenger_lastname, passenger_firstname, passenger_middlename, gender.gen_name, passenger_age, passport_id, passport_abroad_id, custom_inspection, ticket_id, luggage FROM passengers JOIN gender USING(gen_id)";
                generalSelect(conn, select);
            }
        }
    }
}
