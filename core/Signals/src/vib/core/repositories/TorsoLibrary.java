/*
 *  This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.repositories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import vib.core.signals.SpineDirection;
import vib.core.signals.SpinePhase;
import vib.core.signals.TorsoSignal;
import vib.core.util.CharacterDependent;
import vib.core.util.CharacterManager;
import vib.core.util.IniManager;
import vib.core.util.log.Logs;
import vib.core.util.math.Quaternion;
import vib.core.util.xml.XML;
import vib.core.util.xml.XMLParser;
import vib.core.util.xml.XMLTree;

/**
 *
 * @author Andre-Marie Pez
 * @author Brice Donval
 */
public class TorsoLibrary extends SignalLibrary<TorsoSignal> implements CharacterDependent {

    private static final String TORSO_LIBRARY_PARAM_NAME;
    private static final String TORSO_LIBRARY_XSD;
    public static final TorsoLibrary globalLibrary;

    static {
        TORSO_LIBRARY_PARAM_NAME = "TORSOGESTURES";
        TORSO_LIBRARY_XSD = IniManager.getGlobals().getValueString("XSD_TORSOGESTURES");
        globalLibrary = new TorsoLibrary();
        CharacterManager.add(globalLibrary);
    }

    /**
     * @return The global {@code TorsoLibrary}.
     */
    public static TorsoLibrary getGlobalLibrary() {
        return globalLibrary;
    }

    private TorsoIntervals intervals = new TorsoIntervals();

    public TorsoIntervals getTorsoIntervals() {
        return intervals;
    }

    public TorsoLibrary() {
        super(CharacterManager.getDefaultValueString(TORSO_LIBRARY_PARAM_NAME));
        setDefinition(CharacterManager.getValueString(TORSO_LIBRARY_PARAM_NAME));
    }

    @Override
    protected List<SignalEntry<TorsoSignal>> load(String definition) {

        List<SignalEntry<TorsoSignal>> torsos = new LinkedList<SignalEntry<TorsoSignal>>();
        XMLParser parser = XML.createParser();
        XMLTree torsotree = parser.parseFileWithXSD(definition, TORSO_LIBRARY_XSD);
        if (torsotree != null) {
            for (XMLTree torso : torsotree.getChildrenElement()) {
                if (torso.isNamed("torso")) {
                    TorsoSignal torsoSignal = new TorsoSignal(torso.getAttribute("id"));
                    torsoSignal.setLexeme(torso.getAttribute("lexeme"));
                    double amount = torso.getAttributeNumber("amount");
                    for (XMLTree phase : torso.getChildrenElement()) {
                        SpinePhase torsoPhase = new SpinePhase(phase.getAttribute("type"), 0, 0);
                        for (XMLTree direction : phase.getChildrenElement()) {
                            SpineDirection torsoDirection = null;
                            if (direction.isNamed("VerticalTorsion")) {
                                torsoDirection = torsoPhase.verticalTorsion;
                            }
                            if (direction.isNamed("SagittalTilt")) {
                                torsoDirection = torsoPhase.sagittalTilt;
                            }
                            if (direction.isNamed("LateralRoll")) {
                                torsoDirection = torsoPhase.lateralRoll;
                            }
                            if (direction.isNamed("Collapse")) {
                                torsoDirection = torsoPhase.collapse;
                            }
                            if (torsoDirection != null) {
                                torsoDirection.direction = SpineDirection.Direction.valueOf(direction.getAttribute("direction").toUpperCase());
                                torsoDirection.flag = true;
                                if (direction.hasAttribute("amount")) {
                                    torsoDirection.value = direction.getAttributeNumber("amount");
                                } else {
                                    torsoDirection.value = amount;
                                }
                                torsoDirection.valueMax = direction.getAttributeNumber("max");
                                torsoDirection.valueMin = direction.getAttributeNumber("min");
                            }
                            if (direction.getName().equalsIgnoreCase("Rotations")) {
                                for (XMLTree rotates : direction.getChildrenElement()) {
                                    String name = rotates.getName();
                                    float x = (float) (rotates.getAttributeNumber("x"));
                                    float y = (float) (rotates.getAttributeNumber("y"));
                                    float z = (float) (rotates.getAttributeNumber("z"));
                                    float w = (float) (rotates.getAttributeNumber("w"));
                                    Quaternion q = new Quaternion(x, y, z, w);
                                    torsoPhase._rotations.put(name, q);
                                }
                            }
                        }
                        torsoSignal.getPhases().add(torsoPhase);
                    }
                    for (int i = 0; i < torsoSignal.getRepetitions() - 1; i++) {
                        int sum = torsoSignal.getPhases().size();
                        for (int j = 0; j < sum; j++) {
                            SpinePhase torsoPhase = new SpinePhase(torsoSignal.getPhases().get(j).getType(), 0, 0);
                            torsoPhase.collapse = torsoSignal.getPhases().get(j).collapse;
                            torsoPhase.lateralRoll = torsoSignal.getPhases().get(j).lateralRoll;
                            torsoPhase.sagittalTilt = torsoSignal.getPhases().get(j).sagittalTilt;
                            torsoPhase.verticalTorsion = torsoSignal.getPhases().get(j).verticalTorsion;
                            torsoSignal.getPhases().add(sum + j, torsoPhase);
                        }
                    }
                    torsos.add(new SignalEntry<TorsoSignal>(torsoSignal.getId(), torsoSignal));
                }
                if (torso.isNamed("torsoDirectionShift")) {
                    TorsoSignal torsoSignal = new TorsoSignal(torso.getAttribute("id"));
                    torsoSignal.setDirectionShift(true);
                    SpinePhase torsoPhase = new SpinePhase("strokeEnd", 0, 0);
                    for (XMLTree direction : torso.getChildrenElement()) {
                        SpineDirection torsoDirection = null;
                        if (direction.isNamed("VerticalTorsion")) {
                            torsoDirection = torsoPhase.verticalTorsion;
                        }
                        if (direction.isNamed("SagittalTilt")) {
                            torsoDirection = torsoPhase.sagittalTilt;
                        }
                        if (direction.isNamed("LateralRoll")) {
                            torsoDirection = torsoPhase.lateralRoll;
                        }
                        if (direction.isNamed("Collapse")) {
                            torsoDirection = torsoPhase.collapse;
                        }
                        if (torsoDirection != null) {
                            torsoDirection.direction = SpineDirection.Direction.valueOf(direction.getAttribute("direction").toUpperCase());
                            torsoDirection.flag = true;
                            torsoDirection.value = direction.getAttributeNumber("amount");
                            torsoDirection.valueMax = direction.getAttributeNumber("max");
                            torsoDirection.valueMin = direction.getAttributeNumber("min");
                        }
                    }
                    torsoSignal.getPhases().add(torsoPhase);
                    torsos.add(new SignalEntry<TorsoSignal>(torsoSignal.getId(), torsoSignal));
                }
            }
        }
        return new ArrayList<SignalEntry<TorsoSignal>>(torsos);
    }

    @Override
    protected void save(String definition, List<SignalEntry<TorsoSignal>> paramToSave) {
        Logs.info("Torso movement cannot be save");
    }

    @Override
    public void onCharacterChanged() {
        setDefinition(CharacterManager.getValueString(TORSO_LIBRARY_PARAM_NAME));
    }

}
