/*
 *  This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.ideationalunits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vib.core.signals.gesture.GesturePose;
import vib.core.signals.gesture.GestureSignal;
import vib.core.signals.gesture.Hand;
import vib.core.signals.gesture.Position;
import vib.core.signals.gesture.UniformPosition;

/**
 *
 * @author Brice Donval
 */
public class IdeationalUnitFactory {

    private final List<IdeationalUnit> ideationalUnitList;
    private final Map<String, IdeationalUnit> ideationalUnitMap;

    /* ---------------------------------------------------------------------- */

    public IdeationalUnitFactory() {
        ideationalUnitList = new ArrayList<IdeationalUnit>();
        ideationalUnitMap = new HashMap<String, IdeationalUnit>();
    }

    /* ---------------------------------------------------------------------- */

    public IdeationalUnit newIdeationalUnit(String id, String mainIntentionId) {

        IdeationalUnit ideationalUnit = new IdeationalUnit(this, id, mainIntentionId);

        ideationalUnitList.add(ideationalUnit);
        ideationalUnitMap.put(id, ideationalUnit);

        return ideationalUnit;
    }

    public IdeationalUnit getIdeationalUnit(String id) {
        return ideationalUnitMap.get(id);
    }

    public List<IdeationalUnit> getIdeationalUnits() {
        return ideationalUnitList;
    }

    /* ---------------------------------------------------------------------- */

    public void preprocessIdeationalUnits() {
        for (IdeationalUnit ideationalUnit : ideationalUnitList) {
            ideationalUnit.preprocessSignals();
        }
    }

    public void processIdeationalUnits(List<GestureSignal> restPoses) {

        for (IdeationalUnit ideationalUnit : ideationalUnitList) {
            ideationalUnit.processSignals(restPoses);
        }

        for (int i = 0; i < ideationalUnitList.size() - 1; ++i) {
            IdeationalUnit currentIdeationalUnit = ideationalUnitList.get(i);
            IdeationalUnit nextIdeationalUnit = ideationalUnitList.get(i + 1);

            List<GestureSignal> currentIdeationalUnitGestureSignals = currentIdeationalUnit.getGestureSignals();
            List<GestureSignal> nextIdeationalUnitGestureSignals = nextIdeationalUnit.getGestureSignals();

            if ((currentIdeationalUnitGestureSignals != null) && (nextIdeationalUnitGestureSignals != null)) {

                GestureSignal currentIdeationalUnitLastGestureSignal = currentIdeationalUnitGestureSignals.get(currentIdeationalUnitGestureSignals.size() - 1);
                GestureSignal nextIdeationalUnitFirstGestureSignal = nextIdeationalUnitGestureSignals.get(0);

                double currentIdeationalUnitLastGestureSignalEnd = currentIdeationalUnitLastGestureSignal.getEnd().getValue();
                double nextIdeationalUnitFirstGestureSignalStart = nextIdeationalUnitFirstGestureSignal.getStart().getValue();

                GesturePose interIdeationalUnitRelaxPose = new GesturePose(currentIdeationalUnitLastGestureSignal.getRelaxPose());

                Hand interIdeationalUnitRelaxPoseLeftHand = interIdeationalUnitRelaxPose.getLeftHand();
                if (interIdeationalUnitRelaxPoseLeftHand != null) {
                    Position interIdeationalUnitRelaxPoseLeftHandPosition = interIdeationalUnitRelaxPoseLeftHand.getPosition();
                    if (interIdeationalUnitRelaxPoseLeftHandPosition instanceof UniformPosition) {
                        interIdeationalUnitRelaxPoseLeftHandPosition.setX(interIdeationalUnitRelaxPoseLeftHandPosition.getX() / 1.5);
                        interIdeationalUnitRelaxPoseLeftHandPosition.setY(interIdeationalUnitRelaxPoseLeftHandPosition.getY() / 1.5);
                        interIdeationalUnitRelaxPoseLeftHandPosition.setZ(interIdeationalUnitRelaxPoseLeftHandPosition.getZ() / 1.5);
                    }
                }

                Hand interIdeationalUnitRelaxPoseRightHand = interIdeationalUnitRelaxPose.getRightHand();
                if (interIdeationalUnitRelaxPoseRightHand != null) {
                    Position interIdeationalUnitRelaxPoseRightHandPosition = interIdeationalUnitRelaxPoseRightHand.getPosition();
                    if (interIdeationalUnitRelaxPoseRightHandPosition instanceof UniformPosition) {
                        interIdeationalUnitRelaxPoseRightHandPosition.setX(interIdeationalUnitRelaxPoseRightHandPosition.getX() / 1.5);
                        interIdeationalUnitRelaxPoseRightHandPosition.setY(interIdeationalUnitRelaxPoseRightHandPosition.getY() / 1.5);
                        interIdeationalUnitRelaxPoseRightHandPosition.setZ(interIdeationalUnitRelaxPoseRightHandPosition.getZ() / 1.5);
                    }
                }

                if (nextIdeationalUnitFirstGestureSignalStart - currentIdeationalUnitLastGestureSignalEnd < 1.5) {
                    currentIdeationalUnitLastGestureSignal.setEndRestPose(interIdeationalUnitRelaxPose);
                    nextIdeationalUnitFirstGestureSignal.setStartRestPose(interIdeationalUnitRelaxPose);
                }
            }
        }
    }

}
