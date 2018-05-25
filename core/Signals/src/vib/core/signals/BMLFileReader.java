/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.signals;

import vib.core.util.Mode;
import vib.core.util.id.ID;
import vib.core.util.id.IDProvider;
import vib.core.util.xml.XML;
import vib.core.util.xml.XMLParser;
import vib.core.util.xml.XMLTree;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of {@code SignalEmitter} interface.<br/> When
 * calling the {@code load} function, It sends the {@code Signals} contained in
 * a specified BML file to all {@code SignalPerformers} added with the
 * {@code addSignalPerformer} function.
 *
 * @author Andre-Marie Pez
 */
public class BMLFileReader implements SignalEmitter {

    private ArrayList<SignalPerformer> signalPerformers = new ArrayList<SignalPerformer>();
    private XMLParser bmlparser = XML.createParser();

    @Override
    public void addSignalPerformer(SignalPerformer performer) {
        if (performer != null) {
            signalPerformers.add(performer);
        }
    }

    @Override
    public void removeSignalPerformer(SignalPerformer performer) {
        signalPerformers.remove(performer);
    }

    /**
     * Loads a BML file.<br/> The behavior signals in the specified file will be
     * send to all {@code SignalPerformer} added with the
     * {@link #addSignalPerformer(vib.core.signals.SignalPerformer) addSignalPerformer} function.<br/> The
     * base file name of the BML file is used as {@code requestId} parameter
     * when calling the
     * {@link vib.core.signals.SignalPerformer#performSignals(java.util.List, vib.core.util.id.ID, vib.core.util.Mode) performSignals}
     * function.
     *
     * @param bmlfilename the name of the file to load
     * @return The ID of the generated event
     */
    public ID load(String bmlfilename) {
        //get the base file name to use it as requestId
        String base = (new File(bmlfilename)).getName().replaceAll("\\.xml$", "");

        //get the signals of the BML file
        bmlparser.setValidating(true);
        XMLTree bml = bmlparser.parseFile(bmlfilename);
        Mode mode = BMLTranslator.getDefaultBMLMode();
        if (bml.hasAttribute("composition")) {
            mode.setCompositionType(bml.getAttribute("composition"));
        }
        if (bml.hasAttribute("reaction_type")) {
            mode.setReactionType(bml.getAttribute("reaction_type"));
        }
        if (bml.hasAttribute("reaction_duration")) {
            mode.setReactionDuration(bml.getAttribute("reaction_duration"));
        }
        if (bml.hasAttribute("social_attitude")) {
            mode.setSocialAttitude(bml.getAttribute("social_attitude"));
        }
        List<Signal> signals = BMLTranslator.BMLToSignals(bml);

        ID id = IDProvider.createID(base);
        //send to all SignalPerformer added
        for (SignalPerformer performer : signalPerformers) {
            performer.performSignals(signals, id, mode);
        }
        return id;
    }

    /**
     * Returns a {@code java.io.FileFilter} corresponding to BML Files.
     *
     * @return a {@code java.io.FileFilter} corresponding to BML Files
     */
    public java.io.FileFilter getFileFilter() {
        return new java.io.FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String filename = pathname.getName().toLowerCase();
                if (filename.endsWith(".xml") || filename.endsWith(".bml")) {
                    try {
                        bmlparser.setValidating(false);
                        return bmlparser.parseFile(pathname.getAbsolutePath()).getName().equalsIgnoreCase("bml");
                    } catch (Exception e) {
                    }
                }
                return false;
            }
        };
    }
}
