/*
 * This file is part of Greta.
 * 
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
import vib.core.util.CharacterManager;

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
    private CharacterManager cm;
    
    public BMLFileReader(CharacterManager cm){
        this.cm = cm;
    }

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
        List<Signal> signals = BMLTranslator.BMLToSignals(bml, this.cm);

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
