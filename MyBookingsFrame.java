import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MyBookingsFrame extends JFrame {
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Booking ID","Equipment","Booked","Return","Qty","Status"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model);
    public MyBookingsFrame(){setTitle("My Bookings");setSize(700,420);setLocationRelativeTo(null);JButton cancel=new JButton("Cancel Selected");JButton refresh=new JButton("Refresh");JPanel p=new JPanel();p.add(cancel);p.add(refresh);add(new JScrollPane(table),BorderLayout.CENTER);add(p,BorderLayout.SOUTH);cancel.addActionListener(e->cancel());refresh.addActionListener(e->load());load();}
    private void load(){model.setRowCount(0);String s="SELECT b.booking_id,e.equipment_name,b.booking_date,b.return_date,b.quantity,b.status FROM bookings b JOIN equipment e ON b.equipment_id=e.equipment_id WHERE b.user_id=? ORDER BY b.booking_id DESC";try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement(s)){p.setInt(1,Session.userId);ResultSet r=p.executeQuery();while(r.next())model.addRow(new Object[]{r.getInt(1),r.getString(2),r.getDate(3),r.getDate(4),r.getInt(5),r.getString(6)});}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}}
    private void cancel(){int row=table.getSelectedRow();if(row<0){JOptionPane.showMessageDialog(this,"Select a booking");return;}int id=(int)model.getValueAt(row,0);String status=(String)model.getValueAt(row,5);if(!"Pending".equals(status)){JOptionPane.showMessageDialog(this,"Only pending bookings can be cancelled");return;}try(Connection c=DBConnection.getConnection();PreparedStatement p=c.prepareStatement("UPDATE bookings SET status='Cancelled' WHERE booking_id=? AND user_id=? AND status='Pending'")){p.setInt(1,id);p.setInt(2,Session.userId);p.executeUpdate();load();}catch(Exception ex){JOptionPane.showMessageDialog(this,ex.getMessage());}}
}
