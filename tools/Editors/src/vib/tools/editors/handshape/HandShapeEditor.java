/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.tools.editors.handshape;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javax.swing.JFileChooser;
import vib.core.animation.mpeg4.bap.BAPFrame;
import vib.core.animation.mpeg4.bap.BAPFramesEmitter;
import vib.core.animation.mpeg4.bap.BAPFramesPerformer;
import vib.core.animation.mpeg4.bap.BAPType;
import vib.core.animation.mpeg4.bap.JointType;
import vib.core.repositories.HandShape;
import vib.core.repositories.HandShapeLibrary;
import vib.core.util.CharacterDependent;
import vib.core.util.CharacterManager;
import vib.core.util.Constants;
import vib.core.util.IniManager;
import vib.core.util.id.ID;
import vib.core.util.id.IDProvider;
import vib.core.util.math.Vec3d;
import vib.core.util.time.Timer;
import vib.tools.editors.XMLFileChooser;

/**
 *
 * @author Andre-Marie
 */
public class HandShapeEditor extends javax.swing.JFrame implements BAPFramesEmitter, CharacterDependent{

    private ArrayList<BAPFramesPerformer> bapPerformers = new ArrayList<BAPFramesPerformer>();
    private BAPFrame frame = new BAPFrame();
    private int bapIndexX = BAPType.null_bap.ordinal();
    private int bapIndexY = BAPType.null_bap.ordinal();
    private int bapIndexZ = BAPType.null_bap.ordinal();
    private HandShapeLibrary lib = new HandShapeLibrary();
    private JointType rightJoint = JointType.null_joint;
    private int oldPos = 0;
    private int eventButton = 0;
    private HandShape currentshape = null;

    /**
     * Creates new form HandShapeEditor
     */
    public HandShapeEditor() {
        initComponents();
        setEnabledX(false);
        setEnabledY(false);
        setEnabledZ(false);
        frame.setAnimationParameter(BAPType.l_elbow_flexion.ordinal(), degreeToBapValue(-100));
        frame.setAnimationParameter(BAPType.r_elbow_flexion.ordinal(), degreeToBapValue(-100));
        frame.setAnimationParameter(BAPType.l_wrist_twisting.ordinal(), degreeToBapValue(45));
        frame.setAnimationParameter(BAPType.r_wrist_twisting.ordinal(), degreeToBapValue(-45));
        jFileChooser1.setCurrentDirectory(new File("./BehaviorRealizer/AnimationLexicon"));
        jFileChooser1.removeChoosableFileFilter(jFileChooser1.getAcceptAllFileFilter());
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        jFileChooser1.addChoosableFileFilter(new XMLFileChooser());
        updateFrame(jComboBox1.getSelectedItem().toString());
        CharacterManager.add(this);
        onCharacterChanged();
    }

    @Override
    public void setLocale(Locale l) {
        super.setLocale(l);
        if(jMenu1!=null) {
            jMenu1.setText(IniManager.getLocaleProperty("GUI.File"));
        }
        if(jMenuItem1!=null) {
            jMenuItem1.setText(IniManager.getLocaleProperty("GUI.Save"));
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

        jFileChooser1 = new javax.swing.JFileChooser();
        HandPanel = new HandPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hand Shape Editor");

        HandPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        HandPanel.setMinimumSize(new java.awt.Dimension(33, 44));
        HandPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                HandPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                HandPanelMouseReleased(evt);
            }
        });
        HandPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                HandPanelMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout HandPanelLayout = new javax.swing.GroupLayout(HandPanel);
        HandPanel.setLayout(HandPanelLayout);
        HandPanelLayout.setHorizontalGroup(
            HandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );
        HandPanelLayout.setVerticalGroup(
            HandPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(getShapeNames()));
        jComboBox1.setSelectedIndex(0);
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jLabel1.setText("X");

        jSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner2StateChanged(evt);
            }
        });

        jLabel2.setText("Y");

        jSpinner3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner3StateChanged(evt);
            }
        });

        jLabel3.setText("Z");

        jMenu1.setText(IniManager.getLocaleProperty("GUI.File"));

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText(IniManager.getLocaleProperty("GUI.Save"));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HandPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSpinner3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jComboBox1, 0, 121, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(0, 269, Short.MAX_VALUE))
                    .addComponent(HandPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void HandPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HandPanelMousePressed
        ((HandPanel)HandPanel).selectAt(evt.getX(), evt.getY());
        rightJoint = ((HandPanel)HandPanel).getJointTypesOfSelected()[1];
        setJoint(rightJoint);
        eventButton = evt.getButton();
        if(rightJoint!=JointType.null_joint){
            if(eventButton==java.awt.event.MouseEvent.BUTTON1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                oldPos = evt.getY();
            }
            else{
                if(eventButton==java.awt.event.MouseEvent.BUTTON3){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                    oldPos = evt.getX();
                }
            }
        }
        else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        sendShape();
    }//GEN-LAST:event_HandPanelMousePressed

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        if(bapIndexX>0){
            frame.setAnimationParameter(bapIndexX-1, degreeToBapValue(((Integer)(jSpinner1.getValue())).intValue()));
            frame.setAnimationParameter(bapIndexX, degreeToBapValue(((Integer)(jSpinner1.getValue())).intValue()));
            sendShape();
        }
    }//GEN-LAST:event_jSpinner1StateChanged

    private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner2StateChanged
        if(bapIndexY>0){
            frame.setAnimationParameter(bapIndexY-1, degreeToBapValue(-((Integer)(jSpinner2.getValue())).intValue()));
            frame.setAnimationParameter(bapIndexY, degreeToBapValue(((Integer)(jSpinner2.getValue())).intValue()));
            sendShape();
        }
    }//GEN-LAST:event_jSpinner2StateChanged

    private void jSpinner3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner3StateChanged
        if(bapIndexZ>0){
            frame.setAnimationParameter(bapIndexZ-1, degreeToBapValue(-((Integer)(jSpinner3.getValue())).intValue()));
            frame.setAnimationParameter(bapIndexZ, degreeToBapValue(((Integer)(jSpinner3.getValue())).intValue()));
            sendShape();
        }
    }//GEN-LAST:event_jSpinner3StateChanged

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if(evt.getStateChange()==ItemEvent.SELECTED){

            updateFrame(evt.getItem().toString());
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void HandPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HandPanelMouseReleased
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_HandPanelMouseReleased

    private void HandPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HandPanelMouseDragged
        if(rightJoint!=JointType.null_joint){
            if(eventButton==java.awt.event.MouseEvent.BUTTON1){
                //moving verticaly
                int deltaY = evt.getY()-oldPos;
                if(rightJoint==JointType.r_thumb1){
                    addToX(deltaY);
                }
                else{
                    if(rightJoint==JointType.r_thumb2 ||
                       rightJoint==JointType.r_thumb3){
                        addToX(deltaY);
                    }
                    else{
                        addToZ(deltaY);
                    }
                }
                oldPos = evt.getY();
            }
            if(eventButton==java.awt.event.MouseEvent.BUTTON3){
                //moving horizontaly
                if(rightJoint==JointType.r_thumb1){
                    int deltaX = evt.getX()-oldPos;
                    addToY(deltaX);
                }
                else{
                    if(rightJoint==JointType.r_index1||
                       rightJoint==JointType.r_middle1||
                       rightJoint==JointType.r_ring1||
                       rightJoint==JointType.r_pinky1){
                        int deltaX = evt.getX()-oldPos;
                        addToY(-deltaX/3.0);
                        addToX(deltaX);
                    }
                }
                oldPos = evt.getX();
            }

        }
    }//GEN-LAST:event_HandPanelMouseDragged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        //check file name
        String handShapeFileName = CharacterManager.getValueString(HandShapeLibrary.CHARACTER_PARAMETER_HAND_SHAPE_LIBRARY);
        if(! (new File(handShapeFileName)).exists()){
            //there is no specific file: we ask to create a new one
            jFileChooser1.setLocale(Locale.getDefault());
            jFileChooser1.updateUI();
            jFileChooser1.setSelectedFile(new File(handShapeFileName));
            if(jFileChooser1.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
                try {
                    String choosenFileName = "./"+
                            new File("./").getCanonicalFile().toURI()
                            .relativize(jFileChooser1.getSelectedFile().getCanonicalFile().toURI()).getPath();
                    if( ! choosenFileName.equals(handShapeFileName)){
                        handShapeFileName = choosenFileName;
                        lib.getCurrentDefinition().setName(choosenFileName);
                        CharacterManager.addValueString(HandShapeLibrary.CHARACTER_PARAMETER_HAND_SHAPE_LIBRARY, handShapeFileName);
                    }
                    CharacterManager.getIniManager().saveCurrentDefinition();
                } catch (Exception ex) {}
            }
            else{
                handShapeFileName = null;
            }
        }

        if(handShapeFileName != null){
            lib.saveCurrentDefinition();
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HandPanel;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addBAPFramesPerformer(BAPFramesPerformer bapfp) {
        if(bapfp!=null) {
            bapPerformers.add(bapfp);
        }
    }

    @Override
    public void removeBAPFramesPerformer(BAPFramesPerformer bapfp) {
        bapPerformers.remove(bapfp);
    }

    private void sendShape(){
        if(rightJoint != JointType.null_joint){
            Vec3d joint = currentshape.getJoint(rightJoint.name());
            if(joint==null){
                currentshape.setJoint(rightJoint.name(), 0, 0, 0);
                joint = currentshape.getJoint(rightJoint.name());
            }
            if(bapIndexX>0){
                joint.setX((float)bapValueToDegree(frame.getValue(bapIndexX)));
            }
            if(bapIndexY>0){
                joint.setY((float)bapValueToDegree(frame.getValue(bapIndexY)));
            }
            if(bapIndexZ>0){
                joint.setZ((float)bapValueToDegree(frame.getValue(bapIndexZ)));
            }
        }
        ArrayList<BAPFrame> frames = new ArrayList<BAPFrame>(1);
        frames.add(new BAPFrame(frame));
        frames.get(0).setFrameNumber((int)(Timer.getTime()*Constants.FRAME_PER_SECOND));

        ID id = IDProvider.createID("HandShapeEditor");
        for(BAPFramesPerformer performer : bapPerformers){
            performer.performBAPFrames(frames, id);
        }
    }

    private int degreeToBapValue(double degree){
        return (int)(Math.toRadians(degree) * 100000.0 + 0.5);
    }

    private double bapValueToDegree(int bapValue){
        return Math.toDegrees(((double)bapValue) / 100000.0);
    }

    private void setEnabledX(boolean enabled){
        jLabel1.setEnabled(enabled);
        jSpinner1.setEnabled(enabled);
    }
    private void setEnabledY(boolean enabled){
        jLabel2.setEnabled(enabled);
        jSpinner2.setEnabled(enabled);
    }
    private void setEnabledZ(boolean enabled){
        jLabel3.setEnabled(enabled);
        jSpinner3.setEnabled(enabled);
    }

    private void setJoint(JointType rightJointType){
        bapIndexX = rightJointType.rotationX.ordinal();
        if(bapIndexX != BAPType.null_bap.ordinal()){
            setEnabledX(true);
            jSpinner1.setValue(new Integer((int)(bapValueToDegree(frame.getAnimationParametersList().get(bapIndexX).getValue())+0.5)));
        }
        else{
            setEnabledX(false);
            jSpinner1.setValue(new Integer(0));
        }


        bapIndexY = rightJointType.rotationY.ordinal();
        if(bapIndexY != BAPType.null_bap.ordinal()){
            setEnabledY(true);
            jSpinner2.setValue(new Integer((int)(bapValueToDegree(frame.getAnimationParametersList().get(bapIndexY).getValue())+0.5)));
        }
        else{
            setEnabledY(false);
            jSpinner2.setValue(new Integer(0));
        }

        bapIndexZ = rightJointType.rotationZ.ordinal();
        if(bapIndexZ != BAPType.null_bap.ordinal()){
            setEnabledZ(true);
            jSpinner3.setValue(new Integer((int)(bapValueToDegree(frame.getAnimationParametersList().get(bapIndexZ).getValue())+0.5)));
        }
        else{
            setEnabledZ(false);
            jSpinner3.setValue(new Integer(0));
        }
    }

    private String[] getShapeNames(){
        List<HandShape> shapes = lib.getAll();
        Collections.sort(shapes, new Comparator<HandShape>() {
            @Override
            public int compare(HandShape o1, HandShape o2) {
                return o1.getParamName().compareToIgnoreCase(o2.getParamName());
            }
        });

        String[] names = new String[shapes.size()];
        for(int i=0; i< names.length; ++i){
            names[i] = shapes.get(i).getParamName();
        }

        return names;
    }

    private void updateFrame(String shapeName) {
        if( ! lib.getCurrentDefinition().contains(shapeName)){
            currentshape = new HandShape(lib.getDefault(shapeName));
            lib.getCurrentDefinition().addParameter(currentshape);
        }
        else{
            currentshape = lib.get(shapeName);
        }
        for(String jointName : currentshape.getJointNames()){
            boolean isRightJoint = jointName.substring(0,2).equalsIgnoreCase("r_");
            Vec3d angles = currentshape.getJoint(jointName);
            JointType joint = JointType.get(jointName);
            int rx = joint.rotationX.ordinal();
            if(rx != BAPType.null_bap.ordinal()){
                frame.setAnimationParameter(rx - (isRightJoint? 1:0), degreeToBapValue(angles.x())); //left
                frame.setAnimationParameter(rx + (isRightJoint? 0:1), degreeToBapValue(angles.x())); //right
            }
            int ry = joint.rotationY.ordinal();
            if(ry != BAPType.null_bap.ordinal()){
                frame.setAnimationParameter(ry - (isRightJoint? 1:0), degreeToBapValue(angles.y()) * (isRightJoint? -1:1)); //left
                frame.setAnimationParameter(ry + (isRightJoint? 0:1), degreeToBapValue(angles.y()) * (isRightJoint? 1:-1)); //right
            }
            int rz = joint.rotationZ.ordinal();
            if(rz != BAPType.null_bap.ordinal()){
                frame.setAnimationParameter(rz - (isRightJoint? 1:0), degreeToBapValue(angles.z()) * (isRightJoint? -1:1)); //left
                frame.setAnimationParameter(rz + (isRightJoint? 0:1), degreeToBapValue(angles.z()) * (isRightJoint? 1:-1)); //right
            }

        }
        setJoint(((HandPanel)HandPanel).getJointTypesOfSelected()[1]);
        sendShape();
    }

    private void addToX(double degree){
        double newVal = warp(bapValueToDegree(frame.getValue(bapIndexX)) + degree);
        jSpinner1.setValue(new Integer((int)(newVal+0.5)));
        frame.setAnimationParameter(bapIndexX, degreeToBapValue(newVal));//right
        frame.setAnimationParameter(bapIndexX-1, degreeToBapValue(newVal));//left
    }
    private void addToY(double degree){
        double newVal = warp(bapValueToDegree(frame.getValue(bapIndexY)) + degree);
        jSpinner2.setValue(new Integer((int)(newVal+0.5)));
        frame.setAnimationParameter(bapIndexY, degreeToBapValue(newVal));//right
        frame.setAnimationParameter(bapIndexY-1, degreeToBapValue(-newVal));//left
    }
    private void addToZ(double degree){
        double newVal = warp(bapValueToDegree(frame.getValue(bapIndexZ)) + degree);
        jSpinner3.setValue(new Integer((int)(newVal+0.5)));
        frame.setAnimationParameter(bapIndexZ, degreeToBapValue(newVal));//right
        frame.setAnimationParameter(bapIndexZ-1, degreeToBapValue(-newVal));//left
    }

    private double warp(double degree){
        while(degree>180){
            degree-=360;
        }
        while(degree<-180){
            degree+=360;
        }
        return degree;
    }

    @Override
    public void onCharacterChanged() {
        lib.onCharacterChanged();
        if( ! CharacterManager.getDefaultCharacterFile().equals(
            CharacterManager.getCurrentCharacterFile())){
            if(lib.getCurrentDefinition()==lib.getDefaultDefinition()){
                String filename = "./BehaviorRealizer/AnimationLexicon/HandShape_"+CharacterManager.getCurrentCharacterName()+".xml";
                int count = 1;
                while((new File(filename)).exists()){
                    filename = "./BehaviorRealizer/AnimationLexicon/HandShape_"+CharacterManager.getCurrentCharacterName()+count+".xml";
                    count++;
                }
                lib.createEmptyDefinition(filename);
                lib.setDefinition(filename);
                CharacterManager.addValueString(HandShapeLibrary.CHARACTER_PARAMETER_HAND_SHAPE_LIBRARY, filename);
            }
        }
        updateFrame(jComboBox1.getSelectedItem().toString());
    }
}
