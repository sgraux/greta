/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.tools.editors.fml.timelines;

import vib.core.intentions.BasicIntention;
import vib.core.util.time.TimeMarker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import javax.swing.JComponent;
import vib.tools.editors.TemporizableContainer;

/**
 *
 * @author Andre-Marie
 */
public class BasicIntentionEditor extends javax.swing.JDialog {

    TemporizableContainer<BasicIntention> edited;

    /** Creates new form BasicIntentionEditor */
    public BasicIntentionEditor(java.awt.Frame parent, boolean modal) {
        this(parent, modal, null);
    }

    public BasicIntentionEditor(java.awt.Frame parent, boolean modal, TemporizableContainer<BasicIntention> intention, String... types) {
        super(parent, modal);
        initComponents();
        Hashtable<Integer, JComponent> dic =  new Hashtable<Integer, JComponent>();
        dic.put(0, new javax.swing.JLabel("0"));
        dic.put(50, new javax.swing.JLabel("0.5"));
        dic.put(100, new javax.swing.JLabel("1"));
        importanceSlider.setLabelTable(dic);
        List<String> selectTypes = new ArrayList<String>(types.length+1);
        selectTypes.add(0, "...");
        selectTypes.addAll(Arrays.asList(types));
        javax.swing.DefaultComboBoxModel model = new javax.swing.DefaultComboBoxModel(selectTypes.toArray());
        typeComboBox.setModel(model);

        edited = intention;

        if(edited!=null){
            intentionLabel.setText(edited.getTemporizable().getName().toUpperCase());
            idField.setText(edited.getId());
            typeComboBox.setSelectedItem(edited.getTemporizable().getType().toUpperCase());
            typeTextField.setText(edited.getTemporizable().getType());
            if(typeComboBox.getSelectedIndex()==0){
                typeTextField.setEnabled(true);
            }
            else{
                typeTextField.setEnabled(false);
            }
            startField.setText(""+edited.getStart().getValue());
            endField.setText(""+edited.getEnd().getValue());
            importanceSlider.setValue((int)(edited.getTemporizable().getImportance()*100.0));
        }
    }

     public void close(){
        this.dispose();
    }

    public void apply(){

        TimeMarker start = edited.getStart();
        TimeMarker end = edited.getEnd();
        if( ! edited.getTemporizable().getType().equalsIgnoreCase(typeTextField.getText()) ||
            ! edited.getId().equals(idField.getText())){
            edited.setTemporizable(new BasicIntention(edited.getTemporizable().getName(),idField.getText(),typeTextField.getText(),start,end));
        }
        try{
            double startValue = Double.parseDouble(startField.getText()); //if it is not number, it throws an exception
            start.setValue(startValue);
        }
        catch(Exception e){
            //what to do ?
        }
        try{
            double endValue = Double.parseDouble(endField.getText()); //if it is not number, it throws an exception
            end.setValue(endValue);
        }
        catch(Exception e){
            //what to do ?
        }
        edited.getTemporizable().setImportance(importanceSlider.getValue()/100.0);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idLabel = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        importanceLabel = new javax.swing.JLabel();
        startLabel = new javax.swing.JLabel();
        endLabel = new javax.swing.JLabel();
        deleteButton = new vib.core.utilx.gui.ToolBox.LocalizedJButton("GUI.delete");
        cancelButton = new vib.core.utilx.gui.ToolBox.LocalizedJButton("GUI.cancel");
        okButton = new vib.core.utilx.gui.ToolBox.LocalizedJButton("GUI.ok");
        idField = new javax.swing.JTextField();
        typeComboBox = new javax.swing.JComboBox();
        startField = new javax.swing.JTextField();
        endField = new javax.swing.JTextField();
        importanceSlider = new javax.swing.JSlider();
        intentionLabel = new javax.swing.JLabel();
        typeTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        idLabel.setText("ID");

        typeLabel.setText("Type");

        importanceLabel.setText("Importance");

        startLabel.setText("Start");

        endLabel.setText("End");

        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        idField.setEditable(false);

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeComboBoxItemStateChanged(evt);
            }
        });

        importanceSlider.setPaintLabels(true);

        intentionLabel.setText("INTENTION TYPE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(intentionLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(importanceLabel)
                                    .addComponent(endLabel)
                                    .addComponent(startLabel)
                                    .addComponent(typeLabel)
                                    .addComponent(idLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(importanceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(endField, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(startField)
                                        .addComponent(idField))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(intentionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idLabel)
                    .addComponent(idField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startLabel)
                    .addComponent(startField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(endLabel)
                    .addComponent(endField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(importanceLabel)
                    .addComponent(importanceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteButton)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        apply();
        close();
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        edited = null;
        close();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void typeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeComboBoxItemStateChanged
        if(evt.getStateChange()==java.awt.event.ItemEvent.SELECTED){
            if(typeComboBox.getSelectedIndex()==0){
                typeTextField.setText("");
                typeTextField.setEnabled(true);
            }
            else{
                typeTextField.setText(typeComboBox.getSelectedItem().toString());
                typeTextField.setEnabled(false);
            }
        }
    }//GEN-LAST:event_typeComboBoxItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BasicIntentionEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BasicIntentionEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BasicIntentionEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BasicIntentionEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                BasicIntentionEditor dialog = new BasicIntentionEditor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField endField;
    private javax.swing.JLabel endLabel;
    private javax.swing.JTextField idField;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel importanceLabel;
    private javax.swing.JSlider importanceSlider;
    private javax.swing.JLabel intentionLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField startField;
    private javax.swing.JLabel startLabel;
    private javax.swing.JComboBox typeComboBox;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JTextField typeTextField;
    // End of variables declaration//GEN-END:variables
}
