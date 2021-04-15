package work.errorInDelete;

import work.lookOnTable.lookOnTableView;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class errorInDeleteRecord extends JFrame {
    JLabel message1;
    JLabel message2;
    JButton back;

    public errorInDeleteRecord(Connection conn, String tableName, Integer dep){
        super("work with '" + tableName + "' table");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        message1 = new JLabel("Error in delete record in '" + tableName + "' table.");
        message2 = new JLabel("Records in other tables depend on this record. Chose another.");
        back = new JButton("back");

        back.addActionListener((e)->{
            setVisible(false);
            new lookOnTableView(conn, dep, tableName);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 5, 5));
        JPanel button = new JPanel();

        button.add(back);
        panel.add(message1);
        panel.add(message2);
        panel.add(button);

        add(panel);
        setVisible(true);
    }
}
