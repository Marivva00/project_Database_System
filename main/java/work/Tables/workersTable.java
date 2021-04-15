package work.Tables;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class workersTable extends JFrame {
    JButton lookOnTable;
    JButton goBack;
    JButton askWorkersFromSomeDepartment;
    JButton deleteRecord;
    Vector columnNames = null;
    Vector strings = null;
    public workersTable(Connection conn){
        super("work with 'workers' table");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lookOnTable = new JButton("look on table");
        goBack = new JButton("back");
        askWorkersFromSomeDepartment = new JButton("workers from some department");
        deleteRecord = new JButton("delete record by id");

        //addActionListeners(conn);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JPanel goBackPanel = new JPanel();
        goBackPanel.add(goBack);
        JPanel lookOnTablePanel = new JPanel();
        lookOnTablePanel.add(lookOnTable);
        JPanel askPanel = new JPanel();
        askPanel.add(askWorkersFromSomeDepartment);
        JPanel deleteRecordPanel = new JPanel();
        deleteRecordPanel.add(deleteRecord);

        mainPanel.add(lookOnTablePanel);
        mainPanel.add(askPanel);
        mainPanel.add(deleteRecordPanel);
        mainPanel.add(goBackPanel);
        add(mainPanel);
        setVisible(true);
    }
    /*private void addActionListeners(Connection conn){
        goBack.addActionListener((e)->{
            setVisible(false);
            new MainMenuWindow(conn);
        });
        lookOnTable.addActionListener((e)->{
            setVisible(false);
            String select = "WITH S1 AS ( SELECT carriage.car_id, department.dep_name FROM carriage RIGHT JOIN department USING (dep_id)) SELECT workers.worker_id, workers.worker_lastname, workers.worker_firstname, workers.worker_middlename, workers.worker_age, gender.gen_name, workers.worker_child_count, car_id, dep_name, workers.med_id FROM workers RIGHT JOIN S1 USING (car_id) JOIN gender USING (gen_id)";
            executeAndGetResult(conn, select);
            new lookOnTableView(conn, "workers", columnNames, strings);
        });
        askWorkersFromSomeDepartment.addActionListener((e)->{
            setVisible(false);
            String select = "WITH S1 AS ( SELECT carriage.car_id, department.dep_name FROM carriage RIGHT JOIN department USING (dep_id)) SELECT workers.worker_id, workers.worker_lastname, workers.worker_firstname, workers.worker_middlename, workers.worker_age, gender.gen_name, workers.worker_child_count, car_id, dep_name, workers.med_id FROM workers RIGHT JOIN S1 USING (car_id) JOIN gender USING (gen_id) WHERE (dep_name = 'pilots')";
            executeAndGetResult(conn, select);
            new lookOnTableView(conn, "workers", columnNames, strings);
        });
        deleteRecord.addActionListener((e)->{
            setVisible(false);
            new deleteRecordClass(conn, "workers", "worker_id");
        });
    }*/
    private void executeAndGetResult(Connection conn, String sqlSelect){
        columnNames = new Vector();
        columnNames.add("worker_id");
        columnNames.add("worker_lastname");
        columnNames.add("worker_firstname");
        columnNames.add("worker_middlename");
        columnNames.add("worker_age");
        columnNames.add("gen_name");
        columnNames.add("child_count");
        columnNames.add("car_id");
        columnNames.add("dep_name");
        columnNames.add("med_id");
        strings = new Vector();
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        ResultSetMetaData resultSetMetaData = null;
        try {
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
    }
}
