package work.authorisation;

import work.MainMenuWindow;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class SuccessConnectionInfoWindow extends JFrame {
    private JLabel success;
    private JButton ok;
    private static final String[] tableNames = {"gender.sql", "department.sql", "medical.sql", "aviacompany.sql",
            "ticketClass.sql", "tripStatus.sql", "tripType.sql", "airport.sql", "carriage.sql", "workers.sql",
            "technicalInspection.sql", "planes.sql", "trips.sql", "timetable.sql", "tickets.sql", "reserveTickets.sql",
            "passengers.sql"};
    private List<String> tableNamesList = new LinkedList<>();
    public SuccessConnectionInfoWindow(Connection conn){
        super("Authorisation");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        tableNamesList.addAll(Arrays.asList(tableNames));

        success = new JLabel("You're successfully connected...");
        ok = new JButton("ok");

        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.PAGE_AXIS));
        JPanel successPanel = new JPanel();
        JPanel okPanel = new JPanel();

        successPanel.add(success);
        okPanel.add(ok);

        ok.addActionListener((e)->{
            setVisible(false);
            createPartOfSystem(conn);
            MainMenuWindow mainMenuWindow = new MainMenuWindow(conn);
        });

        windowPanel.add(successPanel);
        windowPanel.add(okPanel);
        add(windowPanel);
        setVisible(true);
    }
    private void createPartOfSystem(Connection conn){
        List<String> tablesCreationSQL = new LinkedList<>();
        List<String> tablesDroppingSQL = new LinkedList<>();
        List<String> sequencesCreationSQL = new LinkedList<>();
        List<String> sequencesDroppingSQL = new LinkedList<>();
        List<String> triggersCreationSQL = new LinkedList<>();

        Collections.reverse(tableNamesList);
        for (String tableName: tableNamesList)
            tablesDroppingSQL.add((getSQLFromFile("/dropTables/" + tableName)));
        Collections.reverse(tableNamesList);
        for (String tableName: tableNamesList) {
            tablesCreationSQL.add(getSQLFromFile("/createTables/" + tableName));
            sequencesCreationSQL.add(getSQLFromFile("/createSequences/" + tableName));
            sequencesDroppingSQL.add(getSQLFromFile("/dropSequences/" + tableName));
            triggersCreationSQL.add(getSQLFromFile("/createTriggers/" + tableName));
        }

        try {
            executeSQL(tablesDroppingSQL, conn);
            executeSQL(tablesCreationSQL, conn);
            executeSQL(sequencesDroppingSQL, conn);
            executeSQL(sequencesCreationSQL, conn);
            executeSQL(triggersCreationSQL, conn);
            insertDefaultInfoSQL(conn);
        } catch (SQLIntegrityConstraintViolationException ignored){
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    private String getSQLFromFile(String localPath){
        InputStream inputStream = this.getClass().getResourceAsStream(localPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String str = bufferedReader.readLine();
            return str;
        } catch (IOException exception){
            System.out.println("wrong ((((((");
            return "";
        }
    }
    private List<String> getListOfCommandsFromFile(String localPath){
        InputStream inputStream = this.getClass().getResourceAsStream(localPath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> commands = new LinkedList<>();
        Object[] lines = bufferedReader.lines().toArray();
        for (int i = 0; i < lines.length; i++)
            commands.add(lines[i].toString());
        //System.out.println("commands = " + commands);
        return commands;
    }
    private void executeSQL(List<String> commandsSQL, Connection conn) throws SQLException {
        PreparedStatement preparedStatement = null;
        for (String sql: commandsSQL) {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);
        }
    }
    private void insertDefaultInfoSQL(Connection conn) throws SQLException {
        for (String tableName: tableNamesList){
            List<String> listOfCommands = getListOfCommandsFromFile("/inserts/" + tableName);
            if (!listOfCommands.isEmpty())
                executeSQL(listOfCommands, conn);
        }
    }
}