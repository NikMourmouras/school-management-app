import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewGradesByStudentForm extends JFrame {

    private JFrame parent;

    public ViewGradesByStudentForm(JFrame parent) {

        this.parent = parent;

        setTitle("Βαθμολογία Μαθητή");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        StudentDAO studentDAO = new StudentDAO(DBUtils.getConnection());
        GradeDAO gradeDAO = new GradeDAO(DBUtils.getConnection());

        List<Student> students = studentDAO.getAllStudents();

        JComboBox<Student> studentJComboBox = new JComboBox<>(students.toArray(new Student[0]));
        JButton viewButton = new JButton("Προβολή Βαθμών");

        JTextArea gradesArea = new JTextArea(10, 30);
        gradesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gradesArea);

        setLayout(new FlowLayout());

        add(new JLabel("Μαθητής"));
        add(studentJComboBox);
        add(viewButton);
        add(scrollPane);

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student selectedStudent = (Student) studentJComboBox.getSelectedItem();

                if (selectedStudent == null){
                    JOptionPane.showMessageDialog(null,"❗ Επέλεξε μαθητή");
                    return;
                }

                List<Grade> grades = gradeDAO.getGradesByStudentId(selectedStudent.getId());

                if (grades.isEmpty()){
                    gradesArea.setText("Δεν υπάρχουν βαθμοί για αυτόν τον μαθητή.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Grade grade : grades) {
                        sb.append("Μάθημα: ").append(grade.getSubject().getTitle()).append(" -Βαθμός: ").append(grade.getGrade()).append("\n");
                    }
                    gradesArea.setText(sb.toString());
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
