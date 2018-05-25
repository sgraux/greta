/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.animation;

import vib.core.util.math.Quaternion;
import vib.core.util.math.Vec3d;

/**
 *
 * @author Jing Huang
 */
public class ObjectFrame {

    protected Vec3d _translation;  //local
    protected Quaternion _rotation;  //local
    protected ObjectFrame _pRef;

    public ObjectFrame() {
        _pRef = null;
        setTranslation(new Vec3d());
        setRotation(new Quaternion());
    }

    public ObjectFrame(ObjectFrame obj) {
        setTranslation(obj.getTranslation().clone());
        setRotation(obj.getRotation().clone());
        setReferenceObject(obj.getReferenceObject());
    }

    public ObjectFrame(Vec3d position, Quaternion orientation, ObjectFrame ref) {
        if (_pRef == null) {
            setTranslation(position.clone());
            setRotation(orientation.clone());
        } else {
            setPosition(position.clone());
            setOrientation(orientation.clone());
        }
    }

    public void set(Vec3d position, Quaternion orientation, ObjectFrame ref) {
        _pRef = ref;
        if (_pRef == null) {
            setTranslation(position.clone());
            setRotation(orientation.clone());
        } else {
            setPosition(position.clone());
            setOrientation(orientation.clone());
        }
    }

    public void setReferenceObject(ObjectFrame obj) {
        _pRef = obj;
    }

    public ObjectFrame getReferenceObject() {
        return _pRef;
    }

    public void setTranslation(Vec3d translation) {
        _translation = translation.clone();
    }

    public Vec3d getTranslation() {
        return _translation;
    }

    public void setRotation(Quaternion rotation) {
        _rotation = rotation.clone();
    }

    public Quaternion getRotation() {
        return _rotation;
    }

    public void setPosition(Vec3d position) {
        _translation = computeLocalPosition(position);
    }

    public Vec3d getPosition() {
        return computeGlobalPosition(new Vec3d(0, 0, 0));
    }

    public void setOrientation(Quaternion orientation) {
        if (getReferenceObject() != null) {
            setRotation(Quaternion.multiplication(getReferenceObject().getOrientation().inverse(), orientation));
        } else {
            setRotation(orientation);
        }
    }

    public Quaternion getOrientation() {
        Quaternion res = getRotation();
        ObjectFrame so = getReferenceObject();
        while (so != null) {
            res = Quaternion.multiplication(so.getRotation(), res);
            so = so.getReferenceObject();
        }
        return res;
    }

    /**
     * give a global position, return the local position in this obj space
     */
    public Vec3d computeLocalPosition(Vec3d globalposition) {
        ObjectFrame so = getReferenceObject();
        if (so != null) {
            return localCoordinatesOfReference(so.computeLocalPosition(globalposition));
        } else {
            return localCoordinatesOfReference(globalposition);
        }
    }

    /**
     * give referenced globalpositionm, return reference local position
     */
    public Vec3d localCoordinatesOfReference(Vec3d globalposition) {
        return Quaternion.multiplication(getRotation().inverse(), Vec3d.substraction(globalposition, getTranslation()));
    }

    /**
     * give a local position, return the global position
     */
    public Vec3d computeGlobalPosition(Vec3d localposition) {
        ObjectFrame so = this;
        Vec3d res = localposition;
        while (so != null) {
            res = so.coordinatesOfReference(res);
            so = so.getReferenceObject();
        }
        return res;
    }

    /**
     * give a local position, return the global-reference position in this reference obj space
     */
    public Vec3d coordinatesOfReference(Vec3d localposition) {
        return Vec3d.addition(Quaternion.multiplication(getRotation(), localposition), getTranslation());
    }
}
