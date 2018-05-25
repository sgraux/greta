/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */

package vib.core.behaviorplanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import vib.core.behaviorplanner.baseline.DynamicLine;
import vib.core.behaviorplanner.lexicon.BehaviorSet;
import vib.core.behaviorplanner.lexicon.Shape;
import vib.core.behaviorplanner.lexicon.SignalItem;
import vib.core.intentions.EmotionIntention;
import vib.core.intentions.IntensifiableIntention;
import vib.core.intentions.Intention;
import vib.core.signals.LaughSignal;
import vib.core.signals.ParametricSignal;
import vib.core.signals.Signal;
import vib.core.signals.SignalProvider;
import vib.core.signals.SpeechSignal;
import vib.core.util.laugh.Laugh;
import vib.core.util.speech.Speech;

/**
 * Maurizio's algorithm of signal selection
 * @author Andre-Marie Pez
 */

public class MultimodalSignalSelector implements SignalSelector{

    @Override
    public String getType() {
        return "simple";
    }

    private static final String idSeparator = "_";

    
    public List<List<SignalItem>> findCandidates(Intention intention, BehaviorSet behaviorSet, DynamicLine dynamicLine, List<Signal> existingSignals)
    {
        List<List<SignalItem>> candidates = new ArrayList<List<SignalItem>>();
        //if the intention is Speech, return corresponding SpeechSignal
        if(intention instanceof Speech){
            return candidates;
        }
        //if the intention is Laugh, return corresponding LaughSignal
        if(intention instanceof Laugh){
            return candidates;
        }

        if(behaviorSet==null){
            return candidates;
        }
        //I) check in the behaviorSet which combination we can use :
        //   1) oac in dynamicLine must be in [random(0,1), 1] for all signal (modality)
        //      oac (overall activation) is the movement quantity of the agent.
        //   2) no modality must be completely used, if a modality is only partially used
        //      a signals can be insert in the available holes
        //from MultimodalSignalSelection::SelectMultimodalSignals in MultimodalSignalSelection.cpp

        //mapSignalItem contains all the available free holes for each modality
        Map<String,List<Hole>> mapSignalItem = new HashMap<String,List<Hole>>();

        for(List<SignalItem> combination : behaviorSet.getCombinations()){
            //I-1)
            boolean activable = true;
            for(int i=0; i<combination.size() && activable; ++i){
                SignalItem s = combination.get(i);
                double oac = dynamicLine.getParameter(s.getModality(), "OAC").getValueIn(0, 1);
                activable = Math.random() <= oac;
                //I-2)
                if(activable){
                    //check if in the modality there is still time (holes) for another signal
                    //otherwise the combination is desactivated and not added among the candidates

                    //find all the available time holes in the modality
                    List<Hole> listHole = new LinkedList<Hole>();

                    if(mapSignalItem.containsKey(s.getModality()) == false){
                        Hole h = new Hole(intention.getStart().getValue(),intention.getEnd().getValue());
                        listHole.add(h);
                        for(int j=0; j<existingSignals.size(); ++j){
                            if(SignalProvider.hasSameModality(existingSignals.get(j), (Signal) s)){
                                listHole = findHoles(listHole, existingSignals.get(j).getStart().getValue(),existingSignals.get(j).getEnd().getValue());
                            }
                        }
                        mapSignalItem.put(s.getModality(), listHole);
                    }
                    else {
                        listHole=mapSignalItem.get(s.getModality());
                    }

                    Hole maxHole = findMaximumHole(listHole);
                    if(maxHole == null || (maxHole.end-maxHole.start)<s.getMinSignalDuration()) {
                        activable = false;
                    }
                }
            }
            if(activable){
                candidates.add(combination);
            }
        }
        return candidates;
    }
    
    @Override
    public List<Signal> selectFrom(Intention intention, BehaviorSet behaviorSet, DynamicLine dynamicLine, List<Signal> existingSignals) {
        List<Signal> toReturn = new ArrayList<Signal>();

        //if the intention is Speech, return corresponding SpeechSignal
        if(intention instanceof Speech){
            toReturn.add(new SpeechSignal((Speech)intention));
            return toReturn;
        }
        //if the intention is Laugh, return corresponding LaughSignal
        if(intention instanceof Laugh){
            toReturn.add(new LaughSignal((Laugh)intention));
            return toReturn;
        }

        if(behaviorSet==null){
            return toReturn;
        }

        //I) check in the behaviorSet which combination we can use :
        //   1) oac in dynamicLine must be in [random(0,1), 1] for all signal (modality)
        //      oac (overall activation) is the movement quantity of the agent.
        //   2) no modality must be completely used, if a modality is only partially used
        //      a signals can be insert in the available holes
        //from MultimodalSignalSelection::SelectMultimodalSignals in MultimodalSignalSelection.cpp
        List<List<SignalItem>> candidates = new ArrayList<List<SignalItem>>();

        //mapSignalItem contains all the available free holes for each modality
        Map<String,List<Hole>> mapSignalItem = new HashMap<String,List<Hole>>();

        for(List<SignalItem> combination : behaviorSet.getCombinations()){
            //I-1)
            boolean activable = true;
            for(int i=0; i<combination.size() && activable; ++i){
                SignalItem s = combination.get(i);
                double oac = dynamicLine.getParameter(s.getModality(), "OAC").getValueIn(0, 1);
                activable = Math.random() <= oac;
                //I-2)
                if(activable){
                    //check if in the modality there is still time (holes) for another signal
                    //otherwise the combination is desactivated and not added among the candidates

                    //find all the available time holes in the modality
                    List<Hole> listHole = new LinkedList<Hole>();

                    if(mapSignalItem.containsKey(s.getModality()) == false){
                        Hole h = new Hole(intention.getStart().getValue(),intention.getEnd().getValue());
                        listHole.add(h);
                        for(int j=0; j<existingSignals.size(); ++j){
                            if(SignalProvider.hasSameModality(existingSignals.get(j), (Signal) s)){
                                listHole = findHoles(listHole, existingSignals.get(j).getStart().getValue(),existingSignals.get(j).getEnd().getValue());
                            }
                        }
                        mapSignalItem.put(s.getModality(), listHole);
                    }
                    else {
                        listHole=mapSignalItem.get(s.getModality());
                    }

                    Hole maxHole = findMaximumHole(listHole);
                    if(maxHole == null || (maxHole.end-maxHole.start)<s.getMinSignalDuration()) {
                        activable = false;
                    }
                }
            }
            if(activable){
                candidates.add(combination);
            }
        }
        
        //no combination kept, return an empty selection...
        if(candidates.isEmpty()) {
            return toReturn;
        }

        //II) select a combination :
        //   1) use muliplicity (how many signal in combination) computed with importance and size of combinations
        //   2) compute preference (with dynamicLine) for each combination and keep combinations which have the higher pref
        //   3) in the kept combinations choose one randomly
        //from MultimodalSignalSelection::SelectMultimodalSignal in MultimodalSignalSelection.cpp
        //II-1)
        int min = candidates.get(0).size();
        int max = candidates.get(0).size();
        for(List<SignalItem> combination : candidates){
            int size = combination.size();
            max = size > max ? size : max;
            min = size < min ? size : min;
        }
        int selectedmultiplicity = (int)(min+(max-min)*intention.getImportance());
        int counter = 0;
        int lookingformultiplicity = selectedmultiplicity;
        //counts how many signals have the desired multiplicity
        while(counter==0){
            for(List<SignalItem> combination : candidates){
                if(combination.size()==lookingformultiplicity) {
                    counter++;
                }
            }
            //if there is no signal with that multiplicity, try another one
            if(counter==0){
                if((max-selectedmultiplicity)<(selectedmultiplicity-min)) {
                    lookingformultiplicity++;
                }
                else {
                    lookingformultiplicity--;
                }
            }
        }
        //II-2)
        List<List<SignalItem>> kept = new ArrayList<List<SignalItem>>();
	double maxpref = 0;
	double mmspref = 0;
        for(List<SignalItem> combination : candidates){
            if(combination.size() == lookingformultiplicity){
                mmspref = 0;
                for(SignalItem s : combination) {
                    mmspref += dynamicLine.getParameter(s.getModality(), "preference").getValue();
                }
                if((mmspref-maxpref) > 0.1){
                    counter=1;
                    kept.clear();
                    maxpref=mmspref;
                    kept.add(combination);
                    continue;
                }
                else if(Math.abs(mmspref-maxpref) <= 0.1){
                    counter++;
                    kept.add(combination);
                }
            }
        }
        //II-3)
        int which = (int)(Math.random()*(double)counter);
        List<SignalItem> lastCombination = kept.get(which);

        //III) to each signalItems :
        //   1) affect an id to each signals :
        //      signal.id = intention.id + iterateur
        //   2) select shapes randomly (with proba of the alternative shape)
        //      in the choosen combination
        //   3) set intensity :
        //      intensity of intention if it is an emotion else 1
        //   4) affect epressivity parameters from dynamicline
        //   5) affect timming
        //   6) affect multi strokes (gesture only) from stress point of speech ? (only with openMary)
        int signalcount = 0;
        String intentionId = intention.getId();
        for(SignalItem s : lastCombination){
            //III-1)
            String modality = s.getModality();
            String signalId = intentionId+idSeparator+(signalcount++);
            Signal signal = SignalProvider.create(modality, signalId);
            if(signal == null) {
                continue;
            }
            //III-2)
            //Select only that combination which can fit in the holes of the interval
            Hole maxHole = findMaximumHole(mapSignalItem.get(s.getModality()));
            Shape shape = s.getRandomShape(maxHole.end-maxHole.start);
            if(shape == null) {
                continue;
            }

            if(signal instanceof ParametricSignal){
                ParametricSignal parametric = (ParametricSignal) signal;
                parametric.setReference(shape.getName());
                //III-3)
                double intensity = intention instanceof IntensifiableIntention ?
                    ((IntensifiableIntention)intention).getIntensity()
                    : 1;
                parametric.setIntensity(intensity);
                //III-4)
                double spc = dynamicLine.getParameter(modality, "SPC").getValueIn(-1, 1);
                parametric.setSPC(spc);
                double tmp = dynamicLine.getParameter(modality, "TMP").getValueIn(-1, 1);
                parametric.setTMP(tmp);
                double fld = dynamicLine.getParameter(modality, "FLD").getValueIn(-1, 1);
                parametric.setFLD(fld);
                double pwr = dynamicLine.getParameter(modality, "PWR").getValueIn(-1, 1);
                parametric.setPWR(pwr);
                double rep = dynamicLine.getParameter(modality, "REP").getValueIn(-1, 1);
                parametric.setREP(rep);
            }
            //III-5)
            //a signal is inserted at the beginning of the biggest available hole.
            //Its end time is determined by the max value defined in the lexicon
            //if no max is given the signal will occupy all the available interval of time
            SignalProvider.setBegining(signal, maxHole.start);
            //signal.getStart().setValue(maxHole.start);
            if(shape.getMax()>(maxHole.end-maxHole.start)){
                SignalProvider.setEnding(signal, maxHole.end);
                //signal.getEnd().setValue(maxHole.end);
            }
            else{
                SignalProvider.setEnding(signal, maxHole.start+shape.getMax());
                //signal.getEnd().setValue(maxHole.start+shape.getMax());
            }

            //TODO relative time (here ?)
            toReturn.add(signal);
        }
        return toReturn;
    }

    @Override
    public boolean acceptIntention(Intention intention, List<Intention> context) {
        //accept all
        return true;
        //return false;
    }

     private class Hole{
        public double start;
        public double end;
        public Hole(double start, double end){
            this.start = start;
            this.end = end;
        }
    }

     private List<Hole> findHoles(List<Hole> oldListHole, double start, double end){
        List<Hole> newListHole = new LinkedList<Hole>();
        for(Hole h : oldListHole){
            if(start>h.start && start<h.end){
                Hole nh = new Hole(h.start, start);
                newListHole.add(nh);
            }
            if(end>h.start && end<h.end){
                Hole nh = new Hole(end, h.end);
                newListHole.add(nh);
            }
            if(end<h.start || start>h.end) {
                newListHole.add(h);
            }
        }
        return newListHole;
    }

     private Hole findMaximumHole(List<Hole> listHole){
         double durationHole = 0;
         Hole maxHole = null;
         for(Hole h : listHole){
             if((h.end-h.start)>durationHole){
                 maxHole = h;
                 durationHole = h.end-h.start;
             }
         }
         return maxHole;
     }
}
