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

package vib.core.animation.body;

import vib.core.animation.CharacterLowerBody;
import vib.core.animation.Frame;
import vib.core.animation.Skeleton;
import vib.core.util.math.Vec3d;

/**
 *
 * @author Jing Huang
 */


public class LowerBody  extends ExpressiveFrame {
    Vec3d _offset;
    public LowerBody(double time, Vec3d offset, Skeleton sk){
        _offset = offset;
        CharacterLowerBody boy = new CharacterLowerBody();
        boy.setRootOffset(_offset);
        boy.setSkeleton(sk);
        boy.compute();

        for (String name : boy.getRotations().keySet()) {
            sk.getJoint(name).setLocalRotation(boy.getRotations().get(name));
        }
        sk.getJoint(0).setLocalPosition(_offset);
        sk.update();
        this.addRotations(boy.getFrame().getRotations());
        this.setRootTranslation(boy.getFrame().getRootTranslation());
    }

}
