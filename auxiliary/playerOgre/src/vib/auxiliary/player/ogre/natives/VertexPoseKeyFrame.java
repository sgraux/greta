/*
 *  This file is part of the auxiliaries of VIB (Virtual Interactive Behaviour).
 */
package vib.auxiliary.player.ogre.natives;

/**
 *
 * @author André-Marie
 */
public class VertexPoseKeyFrame extends _Object_{

    public VertexPoseKeyFrame(long pointer) {
        super(pointer);
    }

    public void addPoseReference(int poseIndex, float influence ) {
        _addPoseReference(getNativePointer(), poseIndex, influence );
    }
    
    private native void _addPoseReference(long thisPointer, int poseIndex, float influence );

    public void updatePoseReference(int poseIndex, float influence ) {
        _updatePoseReference(getNativePointer(), poseIndex, influence );
    }
    
    private native void _updatePoseReference(long thisPointer, int poseIndex, float influence );
    
    @Override
    protected native void delete(long nativePointer);
}
