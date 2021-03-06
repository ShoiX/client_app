/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author jerryco09
 */
class RowPopup2 extends JPopupMenu{
    public RowPopup2(Events2 events2){
        JMenuItem delete = new JMenuItem("Cancel");
        JMenuItem edit = new JMenuItem("Edit");
        
        // delete recipient item
        delete.addActionListener((ActionEvent e) -> {
            // Confirm to the user for deletion
            int reply = JOptionPane.showConfirmDialog(null, "Do you really Cancel this event", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (reply != JOptionPane.YES_OPTION) {
              return;
            }
            MysqlConnect c = new MysqlConnect();
            int id = events2.SelectedID;
            int res = c.queryUpdate("UPDATE memo SET deleted = 1 WHERE id = "+ id);
            //alert user
            if (res > 0){
                JOptionPane.showMessageDialog(events2, "Memo Succesfully deleted");
            }
            else{
                JOptionPane.showMessageDialog(events2, "An unexpected error has occured please try again");
            }
            events2.populate();
        });
        add(delete);
        //add(edit);
    }
}
public class Events2 extends javax.swing.JPanel {
    public NewEvent n;
    public user User;
    public MysqlConnect c;
    public int SelectedID;
    private Events2 myalias;
    DefaultTableModel model;
    public MysqlConnect runnablecon;
    /**
     * Creates new form Events2
     */
    public Events2() {
        initComponents();
        myalias = this;
        runnablecon = new MysqlConnect();
        // create popup for table
        final RowPopup2 pop = new RowPopup2(myalias);
        jTable1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                JTable target = (JTable)me.getSource();
                int row = target.getSelectedRow();
                if (row == -1)
                    return;
                SelectedID = Integer.parseInt(target.getModel().getValueAt(row, 0).toString());
                if (SwingUtilities.isRightMouseButton(me)){
                    System.out.println(SelectedID);
                    pop.show(me.getComponent(), me.getX(), me.getY());
                    
                }
                else if (me.getClickCount() == 2){
                    OpenMemo o = new OpenMemo(SelectedID, runnablecon);
                    o.setVisible(true);
                }
            }
        });
        
    }
    public void registerRunnable(){

        Runnable refreshRunnable = new Runnable() {
            public void run() {
                /*DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);
                try {
                        System.out.println("refreshing");
                       ResultSet r = runnablecon.query("SELECT * FROM memo WHERE user_id = "+User.UserId + " AND status = 0 AND `deleted` = 0 ORDER BY schedule ASC");
                    while (r.next()){
                        int id = r.getInt("id");
                        String name = r.getString("name");
                        String message = r.getString("message");
                        String sched = r.getString("schedule").toString();
                        model.insertRow(jTable1.getRowCount(), new Object[] {id, name, message, sched});
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Events2.class.getName()).log(Level.SEVERE, null, ex);
                }*/populate();
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(refreshRunnable, 0, 25, TimeUnit.SECONDS);
    }
    public void populate(){
        
        Thread executerefresh = new Thread(() -> {
            System.out.println("RefreshingG");
            /*if (conn == null)
                c = new MysqlConnect();
            else
                c = conn;*/
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            try {
                   ResultSet r = runnablecon.query("SELECT * FROM memo WHERE user_id = "+User.UserId + " AND status = 0 AND `deleted` = 0 ORDER BY schedule ASC");
                while (r.next()){
                    int id = r.getInt("id");
                    String name = r.getString("name");
                    String message = r.getString("message");
                    String sched = r.getString("schedule").toString();
                    model.insertRow(jTable1.getRowCount(), new Object[] {id, name, message, sched});
                }
            } catch (SQLException ex) {
                Logger.getLogger(Events2.class.getName()).log(Level.SEVERE, null, ex);
            }
            //c.close();
        });
        executerefresh.start();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 255, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Title", "Message", "Schedule"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(6);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        jButton1.setBackground(new java.awt.Color(1, 1, 1));
        jButton1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton1.setForeground(new java.awt.Color(254, 254, 254));
        jButton1.setText("Add New");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(1, 1, 1));
        jButton2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jButton2.setForeground(new java.awt.Color(254, 254, 254));
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        n = new NewEvent(User, this);
        n.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        populate();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
