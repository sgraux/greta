/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.tools.editors.bml.timelines;

import java.awt.Color;
import java.awt.Font;
import vib.tools.editors.SpeechUtil;
import vib.tools.editors.TimeLine;
import vib.core.signals.SpeechSignal;
import java.awt.FontMetrics;
import java.awt.Graphics;
import vib.core.util.time.Temporizable;
import vib.core.util.time.TimeMarker;
import vib.tools.editors.MultiTimeLineEditors;
import vib.tools.editors.TemporizableContainer;

/**
 *
 * @author Andre-Marie
 */
public class SpeechSignalTimeLine extends TimeLine<SpeechSignal>{
    
    private static final int SPEECH_SIGNAL_ID_FONTSIZE = 8;
    private static final int SPEECH_SIGNAL_ID_X_POS_OFFSET = 4;
    private static final int SPEECH_SIGNAL_ID_Y_POS_OFFSET = 12;
    private static final int SPEECH_SIGNAL_WORD_Y_POS_OFFSET = 4;

    public SpeechSignalTimeLine(MultiTimeLineEditors<? extends Temporizable> _bmlEditor) {
        super(_bmlEditor);    
    }
    
    @Override
    protected  TemporizableContainer<SpeechSignal> instanciateTemporizable(double startTime, double endTime) {
        return new TemporizableContainer<SpeechSignal>(new SpeechSignal(SpeechUtil.instanciateTemporizable(startTime, endTime)), manager.getLabel());
    }

    @Override
    protected String getDescription(TemporizableContainer<SpeechSignal> temporizableContainer, FontMetrics metrics, int limitSize) {
        return SpeechUtil.getDescription(temporizableContainer.getTemporizable(), metrics, limitSize);
    }
    
    // Override method to display words according to TimeMarkers
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int h = getHeight()-1;
        int y = h/2;
        g.setColor(Color.black);
        g.drawLine(0, 0, 0, h);
        
        for(TemporizableContainer<SpeechSignal> t : this.getItems()){
            int x = getPosOf(t.getStart().getValue());
            int w = getPosOf(t.getEnd().getValue()) - x;
            String description = getDescription(t,g.getFontMetrics(), w-10);
            
            int currentFontSize = (int) g.getFont().getSize2D();
            g.setFont(new Font("default", Font.BOLD, SPEECH_SIGNAL_ID_FONTSIZE));
            String id = new String("id");
            id = t.getId();
            g.drawString(id, x + SPEECH_SIGNAL_ID_X_POS_OFFSET, SPEECH_SIGNAL_ID_Y_POS_OFFSET);
            g.setFont(new Font("default", Font.PLAIN, currentFontSize));
            if(description!=null){
                String delims = " ";
                String[] tokens = description.split(delims);
                int i = 0;
                g.setColor(this.getForeground());
                //Setting the font back to normal
                g.setFont(new Font("default", Font.PLAIN, currentFontSize));
                for(TimeMarker tm : t.getTimeMarkers()){
                    if(tm.isConcretized() && i < tokens.length){
                        int tmpos = getPosOf(tm.getValue());
                        g.drawString(tokens[i], tmpos+2, y + SPEECH_SIGNAL_WORD_Y_POS_OFFSET);
                        i++;
                    }
                }
            }
        }
    }
    
    // Added method to try to display a popup when willing to edit a speech.
    @Override
    protected TemporizableContainer<SpeechSignal> editTemporizable(TemporizableContainer<SpeechSignal> temporizableContainer){
        return new TemporizableContainer<SpeechSignal>(new SpeechSignal(SpeechUtil.editTemporizable(temporizableContainer.getTemporizable())), manager.getLabel());
    }
    
}
