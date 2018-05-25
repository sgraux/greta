/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vib.core.animation.styleIK;

import java.util.ArrayList;
import vib.core.animation.math.Vector3d;
import vib.core.animation.math.Vector4d;

/**
 *
 * @author Jing Huang
 * <gabriel.jing.huang@gmail.com or jing.huang@telecom-paristech.fr>
 */
public class GPModel {
    //q: global root orientation, position
    //joint angles
    
    //y: joint angles, 
    
    //x: hands, feet
    public class EndEffector{
        public Vector3d _pos;
        public Vector3d _orientation;
    }
    
    public class X{
        public ArrayList<EndEffector> _endeffectors;
        public ArrayList<Double> _Xvalues;
        
        void initValues(){
            for(EndEffector e: _endeffectors){
                _Xvalues.add(e._pos.getEntry(0));
                _Xvalues.add(e._pos.getEntry(1));
                _Xvalues.add(e._pos.getEntry(2));
                _Xvalues.add(e._orientation.getEntry(0));
                _Xvalues.add(e._orientation.getEntry(1));
                _Xvalues.add(e._orientation.getEntry(2));
               // _Xvalues.add(e._orientation.getEntry(3));
            }
        }
        
        void feedbackValues(){
            int index = 0;
            for(EndEffector e: _endeffectors){
                e._pos.setEntry(0,_Xvalues.get(index));
                ++index;
                e._pos.setEntry(1,_Xvalues.get(index));
                ++index;
                e._pos.setEntry(2,_Xvalues.get(index));
                ++index;
                
                e._orientation.setEntry(0,_Xvalues.get(index));
                ++index;
                e._orientation.setEntry(1,_Xvalues.get(index));
                ++index;
                e._orientation.setEntry(2,_Xvalues.get(index));
                ++index;
//                e._orientation.setEntry(3,_Xvalues.get(index));
//                ++index;
            }
        }
    }
    
    public class JointValue{
        public Vector3d _rotation;
    }
    
    public class RootValue{
        public Vector3d _pos;
        public Vector3d _orientation;
    }
   
    public class Q{
        public RootValue _root;
        public ArrayList<JointValue> _joints;
        public ArrayList<Double> _qvalues;
        
        void initValues(){
            _qvalues.add(_root._pos.getEntry(0));
            _qvalues.add(_root._pos.getEntry(1));
            _qvalues.add(_root._pos.getEntry(2));
            _qvalues.add(_root._orientation.getEntry(0));
            _qvalues.add(_root._orientation.getEntry(1));
            _qvalues.add(_root._orientation.getEntry(2)); 
            //_qvalues.add(_root._orientation.getEntry(3)); 
            
            for(JointValue e: _joints){
                _qvalues.add(e._rotation.getEntry(0));
                _qvalues.add(e._rotation.getEntry(1));
                _qvalues.add(e._rotation.getEntry(2));
            }
        }
        
        void feedbackValues(){
            int index = 0;
            _root._pos.setEntry(0, _qvalues.get(index));
            ++index;
            _root._pos.setEntry(1, _qvalues.get(index));
            ++index;
            _root._pos.setEntry(2, _qvalues.get(index));
            ++index;
            _root._orientation.setEntry(0, _qvalues.get(index));
            ++index;
            _root._orientation.setEntry(1, _qvalues.get(index));
            ++index;
            _root._orientation.setEntry(2, _qvalues.get(index));
            ++index;
//            _root._orientation.setEntry(3, _qvalues.get(index));
//            ++index;
            
            for(JointValue e: _joints){
                e._rotation.setEntry(0,_qvalues.get(index));
                ++index;
                e._rotation.setEntry(1,_qvalues.get(index));
                ++index;
                e._rotation.setEntry(2,_qvalues.get(index));
                ++index;
            }
        }
    }
    
    public class Y{
        public Q _current;
        public Q _speed;
        public Q _acc;
        public ArrayList<Double> _Yvalues;
        
        void initValues(){
            _current.initValues();
            _speed.initValues();
            _acc.initValues();
            
            _Yvalues.addAll(_current._qvalues);
            _Yvalues.addAll(_speed._qvalues);
            _Yvalues.addAll(_acc._qvalues);
        }
        
        void feedbackValues(){
            int idx = (int) (_Yvalues.size()/3.0);
            _current._qvalues = new ArrayList<>(_Yvalues.subList(0, idx - 1));
            _speed._qvalues = new ArrayList<>(_Yvalues.subList(idx, 2 * idx - 1));
            _acc._qvalues = new ArrayList<>(_Yvalues.subList(2 * idx, 3 * idx - 1));
            _current.feedbackValues();
            _speed.feedbackValues();
            _acc.feedbackValues();
        }
    }
}
