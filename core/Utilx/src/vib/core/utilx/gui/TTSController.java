/* This file is part of Greta.
 * Greta is free software: you can redistribute it and / or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* Greta is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with Greta.If not, see <http://www.gnu.org/licenses/>.
*//*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.utilx.gui;

import vib.core.util.CharacterManager;
import vib.core.util.speech.Speech;
import vib.core.util.speech.TTS;

/**
 *
 * @author Andre-Marie Pez
 */
public class TTSController extends javax.swing.JFrame {

    private CharacterManager cm;

    /** Creates new form TTSController */
    public TTSController(CharacterManager cm) {       
        this.cm = cm;
        initComponents();
        updateTTSOptions();
    }

    private void updateTTSOptions(){
        Speech.setTTSOptions(
                doTemporize.isSelected(),
                doAudio.isSelected(),
                doPhonem.isSelected());
    }

    public void setTTS(TTS tts){        
        cm.setTTS(tts);
    }

    public void setDoTemporize(boolean todo){
        doTemporize.setSelected(todo);
        updateTTSOptions();
    }
    public boolean getDoTemporize(){
        return doTemporize.isSelected();
    }

    public void setDoAudio(boolean todo){
        doAudio.setSelected(todo);
        updateTTSOptions();
    }

    public boolean getDoAudio(){
        return doAudio.isSelected();
    }

    public void setDoPhonemes(boolean todo){
        doPhonem.setSelected(todo);
        updateTTSOptions();
    }

    public boolean getDoPhonemes(){
        return doPhonem.isSelected();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        doTemporize = new vib.core.utilx.gui.ToolBox.LocalizedJCheckBox("utilx.tts.temporize");
        doAudio = new vib.core.utilx.gui.ToolBox.LocalizedJCheckBox("utilx.tts.audio");
        doPhonem = new vib.core.utilx.gui.ToolBox.LocalizedJCheckBox("utilx.tts.phonems");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        doTemporize.setSelected(true);
        doTemporize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doTemporizeActionPerformed(evt);
            }
        });

        doAudio.setSelected(true);
        doAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doAudioActionPerformed(evt);
            }
        });

        doPhonem.setSelected(true);
        doPhonem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doPhonemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(doTemporize)
                    .addComponent(doAudio)
                    .addComponent(doPhonem))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doTemporize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doAudio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doPhonem)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void doTemporizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doTemporizeActionPerformed
        updateTTSOptions();
    }//GEN-LAST:event_doTemporizeActionPerformed

    private void doAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doAudioActionPerformed
        updateTTSOptions();
    }//GEN-LAST:event_doAudioActionPerformed

    private void doPhonemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doPhonemActionPerformed
        updateTTSOptions();
    }//GEN-LAST:event_doPhonemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox doAudio;
    private javax.swing.JCheckBox doPhonem;
    private javax.swing.JCheckBox doTemporize;
    // End of variables declaration//GEN-END:variables

}
