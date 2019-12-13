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
package greta.core.animation.mpeg4;

import greta.core.animation.mpeg4.bap.BAPFrame;
import greta.core.animation.mpeg4.bap.BAPFramesEmitter;
import greta.core.animation.mpeg4.bap.BAPFramesEmitterImpl;
import greta.core.animation.mpeg4.bap.BAPFramesPerformer;
import greta.core.animation.mpeg4.bap.BAPParser;
import greta.core.animation.mpeg4.fap.FAPFrame;
import greta.core.animation.mpeg4.fap.FAPFrameEmitter;
import greta.core.animation.mpeg4.fap.FAPFrameEmitterImpl;
import greta.core.animation.mpeg4.fap.FAPFramePerformer;
import greta.core.animation.mpeg4.fap.FAPParser;
import greta.core.util.audio.Audio;
import greta.core.util.audio.AudioEmitter;
import greta.core.util.audio.AudioEmitterImpl;
import greta.core.util.audio.AudioPerformer;
import greta.core.util.id.ID;
import greta.core.util.id.IDProvider;
import greta.core.util.time.Timer;
import java.io.File;
import java.util.List;

/**
 *
 * @author Radoslaw Niewiadomski
 * @author Andre-Marie Pez
 */
public class MPEG4FileReader implements FAPFrameEmitter, BAPFramesEmitter, AudioEmitter{

    private final FAPFrameEmitterImpl FAPEmitters = new FAPFrameEmitterImpl();
    private final BAPFramesEmitterImpl BAPEmitters = new BAPFramesEmitterImpl();
    private final AudioEmitterImpl audioEmitters = new AudioEmitterImpl();
    private final BAPParser BAPParser = new BAPParser();
    private final FAPParser FAPParser = new FAPParser();

    @Override
    public void addBAPFramesPerformer(BAPFramesPerformer performer) {
        BAPEmitters.addBAPFramesPerformer(performer);
    }

    @Override
    public void removeBAPFramesPerformer(BAPFramesPerformer performer) {
        BAPEmitters.removeBAPFramesPerformer(performer);
    }

    @Override
    public void addFAPFramePerformer(FAPFramePerformer performer) {
        FAPEmitters.addFAPFramePerformer(performer);
    }

    @Override
    public void removeFAPFramePerformer(FAPFramePerformer performer) {
        FAPEmitters.removeFAPFramePerformer(performer);
    }

    @Override
    public void addAudioPerformer(AudioPerformer ap) {
        audioEmitters.addAudioPerformer(ap);
    }

    @Override
    public void removeAudioPerformer(AudioPerformer ap) {
        audioEmitters.removeAudioPerformer(ap);
    }

    public void load(String filename) {

        String base = hasMPEG4Extention(filename) ? filename.substring(0, filename.length()-4) : filename;

        List<BAPFrame> bap_animation = BAPParser.readFromFile(base+".bap", true);
        List<FAPFrame> fap_animation = FAPParser.readFromFile(base+".fap", true);
        Audio audio = null;
        try {
            audio = Audio.getAudio(base+".wav");
            audio.setTimeMillis(Timer.getTimeMillis());
        } catch (Exception ex) {}

        ID id = IDProvider.createID(base);
        if (!bap_animation.isEmpty()) {
            BAPEmitters.sendBAPFrames(id, bap_animation);
        }

        if (!fap_animation.isEmpty()) {
            FAPEmitters.sendFAPFrames(id, fap_animation);
        }
        if(audio != null){
            audioEmitters.sendAudio(id, audio);
        }
    }

    private static boolean hasMPEG4Extention(String filename){
        String filenameLower = filename.toLowerCase();
        return filenameLower.endsWith(".fap") || filenameLower.endsWith(".bap") || filenameLower.endsWith(".wav");
    }

    public java.io.FileFilter getFileFilter() {
        return new java.io.FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return hasMPEG4Extention(pathname.getName());
            }
        };
    }
}