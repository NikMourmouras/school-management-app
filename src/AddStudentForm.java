import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentForm extends JFrame {

    private JFrame parent;

    public AddStudentForm(JFrame parent){

        this.parent = parent;

        setTitle("Προσθήκη Μαθητή");
        setSize(250, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel nameLabel = new JLabel("Όνομα");
        JTextField nameField = new JTextField(20);

        JLabel surnameLabel = new JLabel("Eπίθετο");
        JTextField surnameField = new JTextField(20);

        JLabel dateOfBirthLabel = new JLabel("Ημερομηνία Γέννησης");
        JTextField dateOfBirthField = new JTextField(20);

        JLabel genderLabel = new JLabel("Φύλο");
        JTextField genderField = new JTextField(20);

        JLabel addressLabel = new JLabel("Διεύθυνση");
        JTextField addressField = new JTextField(20);

        JButton addButton = new JButton("Προσθήκη");

        setLayout(new FlowLayout());
        add(nameLabel);
        add(nameField);

        add(surnameLabel);
        add(surnameField);

        add(dateOfBirthLabel);
        add(dateOfBirthField);

        add(genderLabel);
        add(genderField);

        add(addressLabel);
        add(addressField);

        add(addButton);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentName = nameField.getText().trim();
                String studentSurname = surnameField.getText().trim();
                String studentDateOfBirth = dateOfBirthField.getText().trim();
                String studentGender = genderField.getText().trim();
                String studentAddress = addressField.getText().trim();

                if (studentName.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Το πεδίο Όνομα δεν μπορεί να είναι κενό");
                    return;
                }
                if (studentSurname.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Το πεδίο Επίθετο δεν μπορεί να είναι κενό");
                    return;
                }
                if (studentDateOfBirth.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Το πεδίο Ημερομηνία Γένωησης δεν μπορεί να είναι κενό");
                    return;
                }
                if (studentGender.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Το πεδίο Φύλο δεν μπορεί να είναι κενό");
                    return;
                }
                if (studentAddress.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Το πεδίο Διεύθυνση δεν μπορεί να είναι κενό");
                    return;
                }

                 try {
                     StudentDAO studentDAO = new StudentDAO(DBUtils.getConnection());
                     Student student = new Student(studentName, studentSurname, studentDateOfBirth, studentGender, studentAddress);

                     boolean success = studentDAO.addStudent(student);

                     if (success) {

                         JOptionPane.showMessageDialog(null, "✅ Ο μαθητής προστέθηκε με επιτυχία!");
                         nameField.setText("");
                         surnameField.setText("");
                         dateOfBirthField.setText("");
                         genderField.setText("");
                         addressField.setText("");
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
