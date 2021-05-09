package work.requests;

import work.MainMenuWindow;
import work.Roles.role;

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
    public requestsWindow(Connection conn, role userRole){
        super("Запросы в информационной системе аэропорта");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;

        back = new JButton("Назад");
        go = new JButton("Выполнить");
        info = new JButton("Справка");

        addActionListeners(conn);

        fillRequestsMap();
        getRequestsTable();

        table = new JTable(strings,columnNames);

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
    private void addActionListeners(Connection conn){
        back.addActionListener((e)->{
            setVisible(false);
            new MainMenuWindow(conn, userRole);
        });
        info.addActionListener((e)->{
            setVisible(false);
            new infoRequestWindow(conn, userRole);
        });
        go.addActionListener((e)->{
            setVisible(false);
            goRequest(conn);
        });
    }
    private void getRequestsTable(){
        columnNames = new Vector();
        strings = new Vector();

        columnNames.add("Номер");
        columnNames.add("Запрос");

        Vector tmp1 = new Vector();
        tmp1.add("1");
        tmp1.add("Получить список и общее число всех pаботников аэpопоpта");
        strings.add(tmp1);
        Vector tmp2 = new Vector();
        tmp2.add("2");
        tmp2.add("Получить начальников отделов");
        strings.add(tmp2);
    }
    private void goRequest(Connection conn){
        String recordNum = table.getValueAt(table.getSelectedRow(), 0).toString();
        Integer requestNum = Integer.parseInt(recordNum);

        String request = requests.get(requestNum);
        new executeRequestWindow(conn, request, userRole);
    }
}
