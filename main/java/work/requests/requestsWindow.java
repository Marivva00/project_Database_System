package work.requests;

import work.MainMenuWindow;
import work.Roles.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.*;

public class requestsWindow extends JFrame {
    private JButton back;
    private JButton go;
    private JButton info;

    private Vector columnNames;
    private Vector strings;

    private JTable table;

    private Map<Integer, String> requests;

    private role userRole;
    public requestsWindow(Connection conn, role userRole, Vector strings){
        super("Запросы в информационной системе аэропорта");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;

        back = new JButton("Назад");
        go = new JButton("Выполнить");
        info = new JButton("Справка");

        addActionListeners(conn, strings, userRole);

        getRequestsTable(strings);
        fillRequestsMap();

        table = new JTable(this.strings,columnNames);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(back);
        buttonsPanel.add(go);
        buttonsPanel.add(info);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        mainPanel.add(buttonsPanel);
        add(mainPanel);
        setVisible(true);
    }
    private void fillRequestsMap(){
        requests = new HashMap<>();
        requests.put(1, "WITH S1 AS ( SELECT carriage.car_id, department.dep_name FROM carriage RIGHT JOIN department USING (dep_id)) SELECT workers.worker_id, workers.worker_lastname, workers.worker_firstname, workers.worker_middlename, workers.worker_age, gender.gen_name, workers.worker_child_count, car_id, dep_name, workers.med_id FROM workers RIGHT JOIN S1 USING (car_id) JOIN gender USING (gen_id)");
        requests.put(2, "WITH S1 AS (SELECT carriage.car_id, department.dep_name FROM carriage RIGHT JOIN department USING (dep_id) WHERE (dep_id BETWEEN 7 AND 12)) SELECT workers.worker_id, workers.worker_lastname, workers.worker_firstname, workers.worker_middlename, workers.worker_age, gender.gen_name, workers.worker_child_count, car_id, dep_name, workers.med_id FROM workers RIGHT JOIN S1 USING (car_id) JOIN gender USING (gen_id)");
    }
    private void addActionListeners(Connection conn, Vector strings, role userRole){
        back.addActionListener((e)->{
            setVisible(false);
            switch (userRole){
                case admin: new adminRoleWindow(conn); break;
                case cashier: new cashierRoleWindow(conn); break;
                case technic: new technicRoleWindow(conn); break;
                case passenger: new passengerRoleWindow(conn); break;
                case adminBD: new MainMenuWindow(conn, role.adminBD);
            }
        });
        info.addActionListener((e)->{
            setVisible(false);
            new infoRequestWindow(conn, userRole, strings);
        });
        go.addActionListener((e)->{
            setVisible(false);
            goRequest(conn, strings);
        });
    }
    private void getRequestsTable(Vector strings){
        columnNames = new Vector();
        this.strings = new Vector();

        columnNames.add("Номер");
        columnNames.add("Запрос");

        this.strings = strings;
    }
    private void goRequest(Connection conn, Vector strings){
        String recordNum = table.getValueAt(table.getSelectedRow(), 0).toString();
        Integer requestNum = Integer.parseInt(recordNum);

        String request = requests.get(requestNum);
        new executeRequestWindow(conn, request, userRole, strings);
    }
}
