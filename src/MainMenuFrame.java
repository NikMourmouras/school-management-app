import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuFrame extends JFrame {

    public MainMenuFrame () {

        setTitle("Μενού");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton addStudentButton = new JButton("➕ Προσθήκη Μαθητή");
        JButton addSubjectButton = new JButton("➕ Προσθήκη Μαθήματος");
        JButton assignSubjectToStudentButton = new JButton("➕ Ανάθεση Μαθήματος σε Μαθητή");
        JButton insertGradeButton = new JButton("➕ Καταχώρηση Βαθμού");
        JButton showGradesButton = new JButton("\uD83D\uDD0D Εμφάνιση Βαθμών Μαθητή");
        JButton deleteStudentButton = new JButton("❌ Διαγραφή Μαθητή");

        setLayout(new FlowLayout());

        add(addStudentButton);
        add(addSubjectButton);
        add(assignSubjectToStudentButton);
        add(insertGradeButton);
        add(showGradesButton);
        add(deleteStudentButton);

        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddStudentForm(MainMenuFrame.this);
            }
        });

        addSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddSubjectForm(MainMenuFrame.this);
            }
        });

        assignSubjectToStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AssignSubjectToStudentForm(MainMenuFrame.this);
            }
        });

        insertGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AddGradeForm(MainMenuFrame.this);
            }
        });

        showGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new ViewGradesByStudentForm(MainMenuFrame.this);
            }
        });

        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new DeleteStudentForm(MainMenuFrame.this);
            }
        });


        setVisible(true);

    }
}
