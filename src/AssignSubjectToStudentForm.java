import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignSubjectToStudentForm extends JFrame {

    private JFrame parent;

    public AssignSubjectToStudentForm (JFrame parent) {

        this.parent = parent;

        setTitle("Ανάθεση Μαθήματος σε Μαθητή");
        setSize(350, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        StudentDAO studentDAO = new StudentDAO(DBUtils.getConnection());
        SubjectDAO subjectDAO = new SubjectDAO(DBUtils.getConnection());
        StudentSubjectDAO ssDAO = new StudentSubjectDAO(DBUtils.getConnection());

        List<Student> students = studentDAO.getAllStudents();
        List<Subject> subjects = subjectDAO.getAllSubjects();

        JComboBox<Student> studentJComboBox = new JComboBox<>(students.toArray(new Student[0]));
        JComboBox<Subject> subjectJComboBox = new JComboBox<>(subjects.toArray(new Subject[0]));

        JButton assignButton = new JButton("Ανάθεση");

        setLayout(new FlowLayout());

        add(new JLabel("Μαθητής"));
        add(studentJComboBox);

        add(new JLabel("Μάθημα"));
        add(subjectJComboBox);

        add(assignButton);

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student selectedStudent = (Student) studentJComboBox.getSelectedItem();
                Subject selectedSubject = (Subject) subjectJComboBox.getSelectedItem();

                if (selectedStudent == null || selectedSubject == null) {
                    JOptionPane.showMessageDialog(null, "❗ Παρακαλώ επίλεξε μαθητή και μάθημα.");
                    return;
                }

                boolean success = ssDAO.assignSubjectToStudent(selectedStudent, selectedSubject);

                if (success) {
                    JOptionPane.showMessageDialog(null, "✅ Το μάθημα ανατέθηκε με επιτυχία!");
                } else {
                    JOptionPane.showMessageDialog(null, "❌ Αποτυχία ανάθεσης.");
                }
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                parent.setVisible(true);
            }
        });

        setVisible(true);
    }
}
