import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeleteSubjectForm extends JFrame{

    private JFrame parent;

    public DeleteSubjectForm(JFrame parent) {

        this.parent = parent;

        setTitle("Διαγραφή Μαθήματος");
        setSize(400, 150);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        SubjectDAO subjectDAO = new SubjectDAO(DBUtils.getConnection());
        List<Subject> subjects = subjectDAO.getAllSubjects();

        JComboBox<Subject> subjectJComboBox = new JComboBox<>(subjects.toArray(new Subject[0]));
        JButton deleteButton = new JButton("Διαγραφή");

        setLayout(new FlowLayout());

        add(new JLabel("Επίλεξε Μάθημα"));
        add(subjectJComboBox);
        add(deleteButton);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Subject selectedSubject = (Subject) subjectJComboBox.getSelectedItem();

                if (selectedSubject == null) {
                    JOptionPane.showMessageDialog(null, "Δεν επιλέχθηκε μάθημα");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Θέλετε σίγουρα να διαγράψετε το μάθημα: " + selectedSubject + ";",
                        "Επιβεβαίωση Διαγραφής",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = subjectDAO.deleteSubjectCompletely(selectedSubject.getId());

                    if (success){
                        JOptionPane.showMessageDialog(null, "✅ Το μάθημα διαγράφηκε επιτυχώς.");
                        dispose();
                        new DeleteSubjectForm(parent);
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
