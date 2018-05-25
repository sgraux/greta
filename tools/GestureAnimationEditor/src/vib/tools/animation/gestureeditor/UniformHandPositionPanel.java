package vib.tools.animation.gestureeditor;

import javax.swing.AbstractButton;
import vib.core.signals.gesture.Hand;
import vib.core.signals.gesture.UniformPosition;
import vib.core.utilx.gui.ToolBox;

/**
 *
 * @author Jing Huang
 */
public class UniformHandPositionPanel extends javax.swing.JPanel {

    private Hand _hand;
    private GestureEditor _gestureEditor;

    /**
     * Creates new form UniformHandPosition
     */
    public UniformHandPositionPanel() {
        initComponents();
        initField(xValue);
        initField(yValue);
        initField(zValue);
    }

    public void loadHand(Hand hand, GestureEditor parent) {
        _hand = hand;
        _gestureEditor = parent;
        UniformPosition pos = (UniformPosition) hand.getPosition();
        xFixedButton.setSelected(pos.isXFixed());
        yFixedButton.setSelected(pos.isYFixed());
        zFixedButton.setSelected(pos.isZFixed());
        xInvariantButton.setSelected(!pos.isXOverridable());
        yInvariantButton.setSelected(!pos.isYOverridable());
        zInvariantButton.setSelected(!pos.isZOverridable());
        xParameter.setValue(translate(pos.getX()));
        yParameter.setValue(translate(pos.getY()));
        zParameter.setValue(translate(pos.getZ()));
    }

    private int translate(double d) {
        return (int) (Math.round(d * 1000.0));
    }

    private double translate(int i) {
        return (double) (i / 1000.0);
    }

    private String parameterToValue(javax.swing.JSlider parameter) {
        return String.valueOf(translate(parameter.getValue()));
    }

    private int valueToParameter(javax.swing.JTextField value) {
        return translate(Double.parseDouble(value.getText()));
    }

    private void updatePosition() {
        if (_hand != null) {
            UniformPosition pos = new UniformPosition(
                    translate(xParameter.getValue()),
                    translate(yParameter.getValue()),
                    translate(zParameter.getValue())
            );
            pos.setXFixed(xFixedButton.isSelected());
            pos.setYFixed(yFixedButton.isSelected());
            pos.setZFixed(zFixedButton.isSelected());
            pos.setXOverridable(!xInvariantButton.isSelected());
            pos.setYOverridable(!yInvariantButton.isSelected());
            pos.setZOverridable(!zInvariantButton.isSelected());
            _hand.setPosition(pos);
            _gestureEditor.sendCurrentKeyFrame();
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

        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        xValue = new javax.swing.JFormattedTextField();
        yValue = new javax.swing.JFormattedTextField();
        zValue = new javax.swing.JFormattedTextField();
        xParameter = new javax.swing.JSlider();
        yParameter = new javax.swing.JSlider();
        zParameter = new javax.swing.JSlider();
        xFixedButton = new javax.swing.JCheckBox();
        yFixedButton = new javax.swing.JCheckBox();
        zFixedButton = new javax.swing.JCheckBox();
        xInvariantButton = new javax.swing.JCheckBox();
        yInvariantButton = new javax.swing.JCheckBox();
        zInvariantButton = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Position"));

        xLabel.setText("X:");

        yLabel.setText("Y:");

        zLabel.setText("Z:");

        xValue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                valueFocusLost(evt);
            }
        });
        xValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueActionPerformed(evt);
            }
        });
        xValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                xValueKeyTyped(evt);
            }
        });

        yValue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                valueFocusLost(evt);
            }
        });
        yValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueActionPerformed(evt);
            }
        });
        yValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                yValueKeyTyped(evt);
            }
        });

        zValue.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                valueFocusLost(evt);
            }
        });
        zValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valueActionPerformed(evt);
            }
        });
        zValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                zValueKeyTyped(evt);
            }
        });

        xParameter.setMaximum(1000);
        xParameter.setMinimum(-1000);
        xParameter.setValue(0);
        xParameter.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                parameterStateChanged(evt);
            }
        });

        yParameter.setMaximum(1000);
        yParameter.setMinimum(-1000);
        yParameter.setValue(0);
        yParameter.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                parameterStateChanged(evt);
            }
        });

        zParameter.setMaximum(1000);
        zParameter.setMinimum(-1000);
        zParameter.setValue(0);
        zParameter.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                parameterStateChanged(evt);
            }
        });

        xFixedButton.setText("Fixed");
        xFixedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingButtonActionPerformed(evt);
            }
        });

        yFixedButton.setText("Fixed");
        yFixedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingButtonActionPerformed(evt);
            }
        });

        zFixedButton.setText("Fixed");
        zFixedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingButtonActionPerformed(evt);
            }
        });

        xInvariantButton.setText("Invariant");
        xInvariantButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingButtonActionPerformed(evt);
            }
        });

        yInvariantButton.setText("Invariant");
        yInvariantButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingButtonActionPerformed(evt);
            }
        });

        zInvariantButton.setText("Invariant");
        zInvariantButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(xLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(yLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(zLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(zValue, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(xValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(yValue, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(xParameter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(yParameter, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(zParameter, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(yFixedButton)
                        .addGap(18, 18, 18)
                        .addComponent(yInvariantButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(zFixedButton)
                        .addGap(18, 18, 18)
                        .addComponent(zInvariantButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(xFixedButton)
                        .addGap(18, 18, 18)
                        .addComponent(xInvariantButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(xParameter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(xValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(xLabel))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(xFixedButton)
                                        .addComponent(xInvariantButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(yParameter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(yFixedButton)
                                        .addComponent(yInvariantButton))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(yValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(yLabel)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(zParameter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(zFixedButton)
                                .addComponent(zInvariantButton))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(zValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zLabel)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initField(javax.swing.JFormattedTextField field) {
        field.setFormatterFactory(
                new javax.swing.text.DefaultFormatterFactory(
                        new javax.swing.text.NumberFormatter(
                                vib.core.util.IniManager.getNumberFormat()
                        )
                )
        );
        field.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    }

    private void xValueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_xValueKeyTyped
        ToolBox.checkDoubleInRange(evt, xValue, -1.0, 1.0);
    }//GEN-LAST:event_xValueKeyTyped

    private void yValueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_yValueKeyTyped
        ToolBox.checkDoubleInRange(evt, yValue, -1.0, 1.0);
    }//GEN-LAST:event_yValueKeyTyped

    private void zValueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_zValueKeyTyped
        ToolBox.checkDoubleInRange(evt, zValue, -1.0, 1.0);
    }//GEN-LAST:event_zValueKeyTyped

    private void valueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valueActionPerformed
        xParameter.setValue(valueToParameter(xValue));
        yParameter.setValue(valueToParameter(yValue));
        zParameter.setValue(valueToParameter(zValue));
    }//GEN-LAST:event_valueActionPerformed

    private void valueFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_valueFocusLost
        valueActionPerformed(null);
    }//GEN-LAST:event_valueFocusLost

    private void parameterStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_parameterStateChanged
        xValue.setText(parameterToValue(xParameter));
        yValue.setText(parameterToValue(yParameter));
        zValue.setText(parameterToValue(zParameter));
        updatePosition();
    }//GEN-LAST:event_parameterStateChanged

    private void settingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingButtonActionPerformed

        AbstractButton settingButton = (AbstractButton) evt.getSource();

        String settingName = "";
        if (settingButton == xFixedButton) {
            settingName = "xFixed";
        } else if (settingButton == yFixedButton) {
            settingName = "yFixed";
        } else if (settingButton == zFixedButton) {
            settingName = "zFixed";
        } else if (settingButton == xInvariantButton) {
            settingName = "xInvariant";
        } else if (settingButton == yInvariantButton) {
            settingName = "yInvariant";
        } else if (settingButton == zInvariantButton) {
            settingName = "zInvariant";
        }
        _gestureEditor.askApplySettingToOtherPhases(settingName, settingButton.isSelected(), _hand.getSide());

        updatePosition();
    }//GEN-LAST:event_settingButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox xFixedButton;
    private javax.swing.JCheckBox xInvariantButton;
    private javax.swing.JLabel xLabel;
    private javax.swing.JSlider xParameter;
    private javax.swing.JFormattedTextField xValue;
    private javax.swing.JCheckBox yFixedButton;
    private javax.swing.JCheckBox yInvariantButton;
    private javax.swing.JLabel yLabel;
    private javax.swing.JSlider yParameter;
    private javax.swing.JFormattedTextField yValue;
    private javax.swing.JCheckBox zFixedButton;
    private javax.swing.JCheckBox zInvariantButton;
    private javax.swing.JLabel zLabel;
    private javax.swing.JSlider zParameter;
    private javax.swing.JFormattedTextField zValue;
    // End of variables declaration//GEN-END:variables
}
