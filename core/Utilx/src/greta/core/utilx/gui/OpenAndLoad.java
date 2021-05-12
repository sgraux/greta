/*
 * This file is part of Greta.
 *
 * Greta is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Greta is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Greta.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
package greta.core.utilx.gui;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 *
 * @author Andre-Marie Pez
 */
public class OpenAndLoad extends javax.swing.JFrame {

    /** Creates new form OpenAndLoad */
    public OpenAndLoad() {
        initComponents();
    }

    public void setFileName(String fileName){
        this.fileNameTextField.setText(fileName);
    }

    public String getFileName(){
        return this.fileNameTextField.getText();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser1.setCurrentDirectory(new File("./"));
        jPanel1 = new javax.swing.JPanel();
        fileNameTextField = new javax.swing.JTextField();
        sendButton = new greta.core.utilx.gui.ToolBox.LocalizedJButton("GUI.send");
        openButton = new greta.core.utilx.gui.ToolBox.LocalizedJButton("GUI.open");

        fileNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNameTextFieldActionPerformed(evt);
            }
        });

        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(openButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton)
                    .addComponent(openButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        send(fileNameTextField.getText());
    }//GEN-LAST:event_sendButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        jFileChooser1.setLocale(Locale.getDefault());
        jFileChooser1.updateUI();
        if(jFileChooser1.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION){
            File file = jFileChooser1.getSelectedFile();
            this.fileNameTextField.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_openButtonActionPerformed

    private void fileNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileNameTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton openButton;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables


    private Method loadMethod;
    private Object loader;

    protected void send(String fileName) {
        if(fileName==null || fileName.isEmpty()) return ;
        if(loadMethod!=null){
            try {
                loadMethod.invoke(loader, fileName);
            }
            catch (InvocationTargetException ex) {
                ex.getCause().printStackTrace();
            }
            catch (Exception ex) {
                System.err.println("Can not invoke method load(String) on "+loader.getClass().getCanonicalName());
            }
        }
        else{
            System.out.println("load is null");
        }
    }

    public void setLoader(Object loader){
        this.loader = loader;
        try {
            loadMethod = loader.getClass().getMethod("load", String.class);
        } catch (Exception ex) {
            System.err.println("Can not find method load(String) in "+loader.getClass().getCanonicalName());
        }
        try {
            Method getFileFilterMethod = loader.getClass().getMethod("getFileFilter");
            final java.io.FileFilter ff = (java.io.FileFilter) getFileFilterMethod.invoke(loader);
            jFileChooser1.removeChoosableFileFilter(jFileChooser1.getAcceptAllFileFilter());
            jFileChooser1.setAcceptAllFileFilterUsed(true);
            //jFileChooser1.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){

              //  @Override
                //public boolean accept(File f) {
                  //  return f.isDirectory() || ff.accept(f);
                //}

                //@Override
                //public String getDescription() {
                  //  return OpenAndLoad.this.loader.getClass().getSimpleName()+" Files";
                //}
            //});

        } catch (Exception ex) {}
    }

}
