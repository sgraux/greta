/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.animation.mpeg4.bap;

import vib.core.util.animationparameters.AnimationParametersFrame;

/**
 *
 * @author Jing Huang
 */
public class BAPFrame extends AnimationParametersFrame<BAP> {

    public BAPFrame() {
        super(BAPType.NUMBAPS);
    }

    public BAPFrame(int frameNum) {
        super(BAPType.NUMBAPS, frameNum);
    }

    public BAPFrame(BAPFrame bapFrame) {
        super(bapFrame);
    }

    @Override
    public BAPFrame clone(){
        return new BAPFrame(this);
    }

    public void setRadianValue(int which, double radian){
        getAnimationParameter(which).setRadianValue(radian);
    }

    public double getRadianValue(int which){
        return getAnimationParameter(which).getRadianValue();
    }

    public void setRadianValue(BAPType which, double radian){
        setRadianValue(which.ordinal(), radian);
    }

    public double getRadianValue(BAPType which){
        return getAnimationParameter(which.ordinal()).getRadianValue();
    }

    public void setDegreeValue(int which, double degree){
        setRadianValue(which, Math.toRadians(degree));
    }

    public double getDegreeValue(int which){
        return getAnimationParameter(which).getDegreeValue();
    }

    public void setDegreeValue(BAPType which, double degree){
        setRadianValue(which, Math.toRadians(degree));
    }

    public double getDegreeValue(BAPType which){
        return getAnimationParameter(which.ordinal()).getDegreeValue();
    }

    public void setValue(BAPType which, int value) {
        setValue(which.ordinal(), value);
    }

    public void applyValue(BAPType which, int value) {
        applyValue(which.ordinal(), value);
    }

    public int getValue(BAPType which) {
        return getValue(which.ordinal());
    }

    public void setMask(BAPType which, boolean mask) {
        setMask(which.ordinal(), mask);
    }

    public boolean getMask(BAPType which) {
        return getMask(which.ordinal());
    }

    public void setMaskAndValue(BAPType which, boolean mask, int value) {
        super.setMaskAndValue(which.ordinal(), mask, value);
    }


    @Override
    protected BAP newAnimationParameter() {
        return new BAP();
    }

    @Override
    protected BAP copyAnimationParameter(BAP ap) {
        return new BAP(ap);
    }

    @Override
    public BAP newAnimationParameter(boolean mask, int value) {
        return new BAP(mask, value);
    }
}
