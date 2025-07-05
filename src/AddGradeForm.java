import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.util.List;

public class AddGradeForm extends JFrame {

    private JFrame parent;

    public AddGradeForm(JFrame parent){

        this.parent = parent;

        setTitle("Εισαγωγή Βαθμού");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        StudentDAO studentDAO = new StudentDAO(DBUtils.getConnection());
        SubjectDAO subjectDAO = new SubjectDAO(DBUtils.getConnection());
        StudentSubjectDAO studentSubjectDAO = new StudentSubjectDAO(DBUtils.getConnection());
        GradeDAO gradeDAO = new GradeDAO(DBUtils.getConnection());

        List<Student> students = studentDAO.getAllStudents();

        JComboBox<Student> studentJComboBox = new JComboBox<>(students.toArray(new Student[0]));
        JComboBox<Subject> subjectJComboBox = new JComboBox<>();

        JTextField gradeField = new JTextField(20);
        JButton insertButton = new JButton("Εισαγωγή");

        setLayout(new FlowLayout());

        add(new JLabel("Μαθητής"));
        add(studentJComboBox);

        add(new JLabel("Μάθημα"));
        add(subjectJComboBox);

        add(new JLabel("Βαθμός"));
        add(gradeField);

        add(insertButton);

        studentJComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student selectedStudent = (Student) studentJComboBox.getSelectedItem();
                if (selectedStudent != null) {
                    List<Subject> assignedSubjects = studentSubjectDAO.getSubjectsByStudentId(selectedStudent.getId());

                    subjectJComboBox.removeAllItems();
                    for (Subject subject : assignedSubjects) {
                        subjectJComboBox.addItem(subject);
                    }
                }


            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Student selectedStudent = (Student) studentJComboBox.getSelectedItem();
                Subject selectedSubject = (Subject) subjectJComboBox.getSelectedItem();
                String insertedGrade = gradeField.getText().trim();

                if (selectedStudent == null || selectedSubject == null) {
                    JOptionPane.showMessageDialog(null, "❗ Παρακαλώ επίλεξε μαθητή και μάθημα.");
                    return;
                }

                if (insertedGrade.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "❗ Παρακαλώ εισήγαγε βαθμό.");
                    return;
                }

                try {
                    double gradeValue = Double.parseDouble(insertedGrade);

                    if (gradeValue < 1.0 || gradeValue > 10.0) {
                        JOptionPane.showMessageDialog(null, "❗ Ο βαθμός πρέπει να είναι μεταξύ 1 και 10.");
                        return;
                    }

                    Grade grade = new Grade(gradeValue, selectedStudent, selectedSubject);
                    boolean success = gradeDAO.addGrade(grade);

                    if (success) {
                        JOptionPane.showMessageDialog(null, "✅ Ο βαθμός προστέθηκε με επιτυχία!");
                        gradeField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ Αποτυχία προσθήκης βαθμού.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "❗ Ο βαθμός πρέπει να είναι αριθμός.");
                }
            }
        });

        if (!students.isEmpty()) {
            studentJComboBox.setSelectedIndex(0);
        }

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                parent.setVisible(true);
            }
        });

        setVisible(true);
    }

}
