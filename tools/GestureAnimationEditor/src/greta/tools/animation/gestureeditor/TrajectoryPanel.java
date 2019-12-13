/*
 * This file is part of Greta.
 *
 * Greta is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Greta is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Greta.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package greta.tools.animation.gestureeditor;

import greta.core.signals.gesture.Hand;
import greta.core.signals.gesture.TrajectoryDescription;

/**
 *
 * @author Jing Huang
 * <gabriel.jing.huang@gmail.com or jing.huang@telecom-paristech.fr>
 */
public class TrajectoryPanel extends javax.swing.JPanel {

    private Hand _hand;
    private GestureEditor _gestureEditor;

    TrajectoryParametersDialog _dialogue = new TrajectoryParametersDialog(null, true);

    /**
     * Creates new form TrajectoryPanel
     */
    public TrajectoryPanel() {
        initComponents();
    }

    public void loadHand(Hand hand, GestureEditor parent) {
        _hand = hand;
        _gestureEditor = parent;
        if (_hand.getTrajectory() != null) {
            setText(_hand.getTrajectory().getName());
        } else {
            setText("empty");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        trajectoryLabel = new javax.swing.JLabel();
        text = new javax.swing.JLabel();
        modifyButton = new javax.swing.JButton();

        trajectoryLabel.setText("trajectory:");

        modifyButton.setText("modify");
        modifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(trajectoryLabel)
                .addGap(136, 136, 136)
                .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(modifyButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modifyButton)
                    .addComponent(trajectoryLabel)
                    .addComponent(text))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void modifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyButtonActionPerformed
        if (_hand.getTrajectory() == null) {
            _hand.setTrajectory(new TrajectoryDescription());
        }
        _dialogue.initInterface(_hand.getTrajectory());
        _dialogue.setVisible(true);
        _dialogue.showModalDialog(_hand.getTrajectory());
        setText(_hand.getTrajectory().getName());
    }//GEN-LAST:event_modifyButtonActionPerformed

    public void setText(String tex) {
        text.setText(tex);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton modifyButton;
    private javax.swing.JLabel text;
    private javax.swing.JLabel trajectoryLabel;
    // End of variables declaration//GEN-END:variables
}