import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeleteStudentForm extends JFrame {

    private JFrame parent;

    public DeleteStudentForm(JFrame parent) {

        this.parent = parent;

        setTitle("Διαγραφή Μαθητή");
        setSize(400, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        StudentDAO studentDAO = new StudentDAO(DBUtils.getConnection());
        List<Student> students = studentDAO.getAllStudents();

        JComboBox<Student> studentJComboBox = new JComboBox<>(students.toArray(new Student[0]));
        JButton deleteButton = new JButton("Διαγραφή");

        setLayout(new FlowLayout());

        add(new JLabel("Επίλεξε Μαθητή"));
        add(studentJComboBox);
        add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Student selectedStudent = (Student) studentJComboBox.getSelectedItem();

                if (selectedStudent == null) {
                    JOptionPane.showMessageDialog(null, "Δεν επιλέχθηκε μαθητής");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Θέλετε σίγουρα να διαγράψετε τον μαθητή: " + selectedStudent + ";",
                        "Επιβεβαίωση Διαγραφής",
                        JOptionPane.YES_NO_OPTION
                        );

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = studentDAO.deleteStudentCompletely(selectedStudent.getId());

                    if (success){
                        JOptionPane.showMessageDialog(null, "✅ Ο μαθητής διαγράφηκε επιτυχώς.");
                        dispose();
                        new DeleteStudentForm(parent);
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ Σφάλμα κατά τη διαγραφή.");

                    }
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
