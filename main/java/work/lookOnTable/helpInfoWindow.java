package work.lookOnTable;

import org.omg.PortableInterceptor.INACTIVE;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class helpInfoWindow extends JFrame {
    JButton goBack;
    JLabel genInfo;
    JLabel toGo;
    JLabel toAdd;
    JLabel toDelete;
    JLabel toEdit;

    public helpInfoWindow(Connection conn, String tableName, Integer dep){
        super("help information");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        goBack = new JButton("back");
        genInfo = new JLabel("Some information about the actions above the table:");
        toGo = new JLabel("- click on 'back' to return to the main menu");
        toAdd = new JLabel("- click on 'add record' to add new record to the table");
        toDelete = new JLabel("- click on record in table you're interested in then click on 'delete record'");
        toEdit = new JLabel("- click on record in table you're interested in then click on 'edit record'");

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
            new lookOnTableView(conn, dep, tableName);
        });
    }
}
