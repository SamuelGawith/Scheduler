import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame {

    public static JLabel status;

    private JTextField dbField, nameField, passwordField;

    GUI() {
        super("Scheduler");

        status = new JLabel();

        final String db = "";
        final String name = "";
        final String password = "";

        dbField = new JTextField(db, 11);
        nameField = new JTextField(name, 6);
        passwordField = new JPasswordField(password, 8);

        JButton chooseButton = new JButton("Открыть файл");
        chooseButton.addActionListener(new ChoiceButtonClick());

        JButton helpButton = new JButton("Помощь");
        helpButton.addActionListener(new HelpButtonClick());

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(dbField);
        panel.add(nameField);
        panel.add(passwordField);
        panel.add(chooseButton);
        panel.add(helpButton);
        panel.add(status, BorderLayout.CENTER);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(panel);
        setSize(400, 355);
        setLocationRelativeTo(null);
    }

    private class ChoiceButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ArrayList<Data> list;

            JFileChooser fileChooser = new JFileChooser("C:/");
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Microsoft Excel", "xlsx"));

            int resultOfChoice = fileChooser.showDialog(null, "Открыть файл");

            if (resultOfChoice == JFileChooser.APPROVE_OPTION) {
                list = Parser.parse(fileChooser.getSelectedFile());

                if (list.isEmpty()) {
                    status.setText("Файл некорректно заполнен");
                } else {
                    String db = dbField.getText();
                    String name = nameField.getText();
                    String password = passwordField.getText();

                    if (db.isEmpty() || name.isEmpty()) {
                        status.setText("Поля некорректно заполнены");
                    } else {
                        DBWorker dbWorker = new DBWorker(db, name, password);
                        dbWorker.createAndClearTable();
                        dbWorker.fillTable(list);
                    }
                }
            }
        }
    }

    private class HelpButtonClick implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            status.setText("<html>" +
            "Файл должен состоять из 6 листов, имеющих названия <br> дней недели.<br>" +
            "В каждой строке первой ячейкой идёт группа,<br>" +
            " а в последующих \"предмет аудитория преподаватель\"<br>" +
            "именно в таком порядке через пробел, пробелы<br>" +
            "должны быть только между элементами.<br>" +
            "Например, \"Ин.Яз. 401 А.С.Петров\"" +
            "(допускается отсутствие <br>аудитории, если она по " +
            "каким-то причинам<br> неизвестна на момент заполнения).<br>" +
            "Каждая ячейка соответствует времени начала пары,<br>" +
            "поэтому нужно оставить пустыми столько ячеек, сколько<br>" +
            "требуется конкретной группе. Например, если занятия с 12.30,<br>" +
            "то первые две ячейки пусты и заполнение начинается с третьей.<br>" +
            "В случае, если группа в этот день не учится,<br>" +
            "её номер должен присутствовать, но остальные<br>" +
            "ячейки строки должны быть пусты.<br>" +
            "При неправильно заполненном файле программа его не примет.");
        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }
}