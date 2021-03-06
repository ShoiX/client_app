/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
class RowPopup3 extends JPopupMenu{
    
    Scheduled parent;
    public RowPopup3(Scheduled p){
        JMenuItem delete = new JMenuItem("Cancel");
        //JMenuItem Cancel = new JMenuItem("Cancel");
        parent = p;
        // delete recipient item
        delete.addActionListener((ActionEvent e) -> {
            // Confirm to the user for deletion
            int reply = JOptionPane.showConfirmDialog(null, "Do you really Cancel this event", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (reply != JOptionPane.YES_OPTION) {
              return;
            }
            MysqlConnect c = parent.c;
            int res = c.queryUpdate("UPDATE memo_sched SET isdeleted = 1 WHERE sched_id = "+parent.Selected);
            if (res > 0){
                parent.populate();
                JOptionPane.showMessageDialog(parent, "Scheduled Memo Succesfully deleted");
                
            }
            else{
                JOptionPane.showMessageDialog(parent, "An unexpected error has occured please try again");
            }
            
        });
        add(delete);
        //add(edit);
    }
}
public class Scheduled extends javax.swing.JPanel {
    public user User;
    public MysqlConnect c;
    public int Selected;
    public Scheduled alias = this;
    /**
     * Creates new form Scheduled
     */
    public Scheduled() {
        initComponents();
        c = new MysqlConnect();
        final RowPopup3 pop = new RowPopup3(this);
        jTable1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent me){
                JTable target = (JTable)me.getSource();
                int row = target.getSelectedRow();
                if (row == -1)
                    return;
                Selected = Integer.parseInt(target.getModel().getValueAt(row, 0).toString());
                if (SwingUtilities.isRightMouseButton(me)){
                    System.out.println(Selected);
                    pop.show(me.getComponent(), me.getX(), me.getY());
                    
                }
                else if (me.getClickCount() == 2){
                    System.out.println("double");
                    OpenSched op = new OpenSched(Selected, c, alias);
                    op.setVisible(true);
                    
                }
            }
        });
    }
    public void registerRunnable(){
        Runnable refreshRunnable;
        
        
        // register refresh
        refreshRunnable = new Runnable() {
            public void run() {
                populate();
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(refreshRunnable, 0, 25, TimeUnit.SECONDS);
    }
    public void populate(){
        Thread executerefresh = new Thread(() -> {
            System.out.println("Refreshinge");
            ResultSet r = c.query("SELECT sched_id, name, message, type, status FROM memo_sched WHERE user_id = "+User.UserId+" AND isdeleted = 0 ORDER BY sched_id DESC");
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            try{
                while(r.next()){
                    int id = r.getInt("sched_id");
                    String Name = r.getString("name");
                    String Message = r.getString("message");
                    String Schedule = r.getString("type");
                    String Status = r.getBoolean("status") ? "Active" : "Inactive";
                    model.insertRow(jTable1.getRowCount(), new Object[] {id, Name, Message, Schedule, Status});
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(0, 248, 148));

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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Message", "Schedule", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ScheduledMemo m = new ScheduledMemo(User.UserId, this);
        m.setVisible(true);
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
