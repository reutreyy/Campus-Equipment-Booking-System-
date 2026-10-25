import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class StudentDashboard extends JFrame {
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"ID","Equipment","Category","Available","Status"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table = new JTable(model);
    private final JTextField search = new JTextField(18);

    public StudentDashboard() {
        setTitle("Student Dashboard - "+Session.name); setSize(850,520); setDefaultCloseOperation(EXIT_ON_CLOSE); setLocationRelativeTo(null);
        JPanel top=new JPanel(); top.add(new JLabel("Search:")); top.add(search); JButton find=new JButton("Search"); JButton refresh=new JButton("Refresh"); JButton book=new JButton("Book Selected"); JButton mine=new JButton("My Bookings"); JButton logout=new JButton("Logout");
        top.add(find); top.add(refresh); top.add(book); top.add(mine); top.add(logout);
        add(top,BorderLayout.NORTH); add(new JScrollPane(table),BorderLayout.CENTER);
        find.addActionListener(e->load(search.getText())); refresh.addActionListener(e->load("")); book.addActionListener(e->book()); mine.addActionListener(e->new MyBookingsFrame().setVisible(true)); logout.addActionListener(e->{dispose();new LoginFrame().setVisible(true);});
        load("");
    }

    private void load(String q) {
        model.setRowCount(0);
        String sql="SELECT e.equipment_id,e.equipment_name,c.category_name,e.available_quantity,e.status FROM equipment e JOIN categories c ON e.category_id=c.category_id WHERE e.equipment_name LIKE ? OR c.category_name LIKE ? ORDER BY e.equipment_id";
        try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){p.setString(1,"%"+q+"%");p.setString(2,"%"+q+"%");ResultSet r=p.executeQuery();while(r.next())model.addRow(new Object[]{r.getInt(1),r.getString(2),r.getString(3),r.getInt(4),r.getString(5)});}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}
    }

    private void book() {
        int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Select equipment");return;}
        int id=(int)model.getValueAt(row,0); int available=(int)model.getValueAt(row,3);
        String qs=JOptionPane.showInputDialog(this,"Quantity:"); if(qs==null)return;
        String ds=JOptionPane.showInputDialog(this,"Return date (YYYY-MM-DD):",LocalDate.now().plusDays(7)); if(ds==null)return;
        try { int qty=Integer.parseInt(qs); LocalDate ret=LocalDate.parse(ds); if(qty<=0||qty>available||ret.isBefore(LocalDate.now())) throw new IllegalArgumentException("Invalid quantity or return date");
            try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("INSERT INTO bookings(user_id,equipment_id,booking_date,return_date,quantity,status) VALUES(?,?,CURDATE(),?,?, 'Pending')")){p.setInt(1,Session.userId);p.setInt(2,id);p.setDate(3,Date.valueOf(ret));p.setInt(4,qty);p.executeUpdate();JOptionPane.showMessageDialog(this,"Booking submitted for approval");}
        } catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}
    }
}
