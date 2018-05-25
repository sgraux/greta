/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.animation.common.IK;
//import java.util.ArrayList;
import vib.core.util.math.Vec3d;

/**
 *
 * @author Jing Huang
 */
public class IKMass {

		double _mass = 1;
		Vec3d _position;
		Vec3d _oldPosition;

		Vec3d _acceleration = new Vec3d();

		boolean _movable = true;

		public IKMass(boolean movable){
                     _movable =  movable;
                }

		public IKMass(Vec3d position, double mass, boolean movable){
                    _position = position;
                    _oldPosition = position;
                    _mass = mass;
                    _movable = movable;
                }

		public void setMass(double mass){ _mass = mass;}
		public double getMass(){return _mass;}


		public void setPosition(Vec3d position){
                    _position = position;
                    _oldPosition = position;
                }
		public Vec3d getPosition(){return _position;}

		public void setMovable(boolean movable){ _movable = movable;}
		public boolean isMovable(){return _movable;}

		public void addForce(Vec3d force)
		{
			_acceleration.add(Vec3d.division(force,_mass));
		}

		public Vec3d getAcceleration(){ return _acceleration;}
		public void resetAcceleration(){ _acceleration = new Vec3d(0);}

		//verlet intergration      if do not put into .h file, they can not use for Cuda, or need .cu file
		public void move(double time, double dampling)
		{
			if(!_movable) return;
			Vec3d temp = _position;
			_position  = Vec3d.addition(_position ,
                                        Vec3d.multiplication(
                                                        Vec3d.addition(           Vec3d.substraction(_position, _oldPosition) ,       Vec3d.multiplication(_acceleration, time)      )
                                                        , ((double)(1.0 - dampling))
                                        )
                                     );
			_oldPosition = temp;
			_acceleration = new Vec3d(0,0,0);
		}

                public void move()
		{
			if(!_movable) return;
			Vec3d temp = _position;
			_position  = Vec3d.addition(_position ,
                                        Vec3d.multiplication(
                                                        Vec3d.addition(           Vec3d.substraction(_position, _oldPosition) ,       Vec3d.multiplication(_acceleration, 0.25f)      )
                                                        , ((double)(0.8))
                                        )
                                     );
			_oldPosition = temp;
			_acceleration = new Vec3d(0,0,0);
		}



}
