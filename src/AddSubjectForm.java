import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSubjectForm extends JFrame {

    private JFrame parent;

    public AddSubjectForm(JFrame parent){

        this.parent = parent;

        setTitle("Προσθήκη Μαθήματος");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Τίτλος Μαθήματος");
        JTextField titleField = new JTextField(20);
        JButton addButton = new JButton("Προσθήκη");

        setLayout(new FlowLayout());
        add(titleLabel);
        add(titleField);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String subjectTitle = titleField.getText().trim();

                if (subjectTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Το πεδίο δεν μπορεί να είναι κενό");
                    return;
                }

                try {
                    SubjectDAO dao = new SubjectDAO(DBUtils.getConnection());
                    Subject subject = new Subject(subjectTitle);

                    boolean success = dao.addSubject(subject);

                    if (success) {

                        JOptionPane.showMessageDialog(null, "✅ Το μάθημα προστέθηκε με επιτυχία!");
                        titleField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "❌ Σφάλμα κατά την προσθήκη.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "⚠️ Σφάλμα σύνδεσης: " + ex.getMessage());
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
