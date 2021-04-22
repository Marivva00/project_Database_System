package work.lookOnTable;

import work.authorisation.role;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class helpInfoWindow extends JFrame {
    private JButton goBack;
    private JLabel genInfo;
    private JLabel toGo;
    private JLabel toAdd;
    private JLabel toDelete;
    private JLabel toEdit;
    private role userRole;

    public helpInfoWindow(Connection conn, String tableName, Integer dep, role userRole){
        super("Справка");
        setSize(700, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.userRole = userRole;

        goBack = new JButton("Назад");
        genInfo = new JLabel("Некоторая информация о возможных действиях с таблицами:");
        toGo = new JLabel("- нажмите на 'Назад' чтобы вернуться в главное меню");
        toAdd = new JLabel("- нажмите на 'Добавить запись' чтобы добавить новую запись в таблицу");
        toDelete = new JLabel("- нажмите в таблице на запись, которую хотите удалить, затем нажмите 'Удалить запись'");
        toEdit = new JLabel("- нажмите в таблице на запись, которую хотите изменить, затем нажмите 'Изменить запись'");

        addActionListener(conn, tableName, dep);

        JPanel mainPanel = new JPanel();

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(6, 1, 5, 5));
        labelPanel.add(genInfo);
        labelPanel.add(toGo);
        labelPanel.add(toAdd);
        labelPanel.add(toDelete);
        labelPanel.add(toEdit);
        JPanel button = new JPanel();
        button.add(goBack);

        mainPanel.add(labelPanel);
        mainPanel.add(button);
        add(mainPanel);
        setVisible(true);
    }
    private void addActionListener(Connection conn, String tableName, Integer dep){
        goBack.addActionListener((e)-> {
            setVisible(false);
            new lookOnTableView(conn, dep, tableName, userRole);
        });
    }
}
