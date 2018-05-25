/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.animation.performer;

import java.util.LinkedList;
import java.util.List;
import vib.core.animation.body.Arm;
import vib.core.animation.body.Head;
import vib.core.animation.body.Shoulder;
import vib.core.animation.body.Torso;
import vib.core.keyframes.GestureKeyframe;
import vib.core.keyframes.HeadKeyframe;
import vib.core.keyframes.Keyframe;
import vib.core.keyframes.KeyframePerformer;
import vib.core.keyframes.ShoulderKeyframe;
import vib.core.keyframes.TorsoKeyframe;
import vib.core.util.Mode;
import vib.core.util.enums.Side;
import vib.core.util.id.ID;

/**
 *
 * @author Jing Huang
 * new class for receive key frame, then generate upper body key frames for each body, need to gather then rescatter
 */

//TODO: wait for AndreMarie pour nouveau structure de keyframe
public class UpperBodyKeyFrameGenerator  implements KeyframePerformer{
    SymbolicConverter _symbolicConverter = new SymbolicConverter();
    @Override
    public void performKeyframes(List<Keyframe> keyframes, ID id) {
        LinkedList<Arm> _left = new LinkedList<Arm>();
        LinkedList<Arm> _right = new LinkedList<Arm>();
        LinkedList<Torso> _torse = new LinkedList<Torso>();
        LinkedList<Head> _head = new LinkedList<Head>();
        LinkedList<Shoulder> _leftShoulder = new LinkedList<Shoulder>();
        LinkedList<Shoulder> _rightShoulder = new LinkedList<Shoulder>();

        for (Keyframe kf : keyframes) {
            if (kf instanceof GestureKeyframe) {
                GestureKeyframe keyframe = (GestureKeyframe) kf;
                Side side = keyframe.getSide();
                if (side == Side.LEFT) {
                    Arm arm = _symbolicConverter.getArm(keyframe);
                    _left.add(arm);
                } else if (side == Side.RIGHT) {
                    Arm arm = _symbolicConverter.getArm(keyframe);
                    _right.add(arm);
                    //System.out.println(arm.getTarget() + " " + arm.getTime());
                } else {
                    System.out.println("IKKeyFramePerformer: GestureKeyframe side error:" + side);
                }
            } else if (kf instanceof TorsoKeyframe) {
                TorsoKeyframe keyframe = (TorsoKeyframe) kf;
                _torse.add(_symbolicConverter.getTorse(keyframe));
            } else if (kf instanceof HeadKeyframe) {
                HeadKeyframe keyframe = (HeadKeyframe) kf;
                _head.add(_symbolicConverter.getHead(keyframe));
            } else if (kf instanceof ShoulderKeyframe) {
                ShoulderKeyframe keyframe = (ShoulderKeyframe) kf;
                String side = keyframe.getSide();
                if (side.equalsIgnoreCase("LEFT")) {
                    _leftShoulder.add(_symbolicConverter.getShoulder(keyframe));
                } else if (side.equalsIgnoreCase("RIGHT")) {
                    _rightShoulder.add(_symbolicConverter.getShoulder(keyframe));
                }

            } else {
                //System.out.println("IKKeyFramePerformer: Keyframe type error : "+kf.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void performKeyframes(List<Keyframe> keyframes, ID requestId, Mode mode) {
        // TODO : Mode management in progress
        performKeyframes(keyframes, requestId);
    }
}
