import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private final JTextField email = new JTextField(20);
    private final JPasswordField password = new JPasswordField(20);

    public LoginFrame() {
        setTitle("Campus Equipment Booking - Login");
        setSize(420,260);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8,8,8,8);
        g.gridx=0; g.gridy=0; add(new JLabel("Email"),g);
        g.gridx=1; add(email,g);
        g.gridx=0; g.gridy=1; add(new JLabel("Password"),g);
        g.gridx=1; add(password,g);
        JButton login = new JButton("Login");
        g.gridx=0; g.gridy=2; g.gridwidth=2; add(login,g);
        JLabel demo = new JLabel("Admin: admin@campus.com / admin123   Student: student@campus.com / student123");
        g.gridy=3; add(demo,g);
        login.addActionListener(e -> login());
    }

    private void login() {
        String sql="SELECT user_id,name,role FROM users WHERE email=? AND password=?";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)) {
            p.setString(1,email.getText().trim());
            p.setString(2,new String(password.getPassword()));
            ResultSet r=p.executeQuery();
            if(r.next()) {
                Session.userId=r.getInt("user_id"); Session.name=r.getString("name"); Session.role=r.getString("role");
                dispose();
                if("ADMIN".equals(Session.role)) new AdminDashboard().setVisible(true);
                else new StudentDashboard().setVisible(true);
            } else JOptionPane.showMessageDialog(this,"Invalid email or password");
        } catch(Exception ex) { JOptionPane.showMessageDialog(this,ex.getMessage()); }
    }
}
