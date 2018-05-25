/*
 *  This file is part of the auxiliaries of VIB (Virtual Interactive Behaviour).
 */
package vib.auxiliary.thrift;

import vib.auxiliary.thrift.gen_java.Message;
import vib.auxiliary.thrift.gen_java.ThriftAnimParam;
import vib.auxiliary.thrift.gen_java.ThriftAnimParamFrame;
import vib.auxiliary.thrift.services.Sender;
import vib.core.util.animationparameters.AnimationParameter;
import vib.core.util.animationparameters.AnimationParametersFrame;
import vib.core.util.id.ID;
import vib.core.util.log.Logs;
import vib.core.util.time.Timer;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Ken Prepin
 */
public abstract class APSender<APF extends AnimationParametersFrame> extends Sender{

    public APSender(){
        this(Sender.DEFAULT_THRIFT_HOST,Sender.DEFAULT_THRIFT_PORT);
    }
    public APSender(String host, int port){
        super(host, port);
    }


    public void sendAnimParamFrameList(List<APF> vibAPframes, String type, ID requestId) {
       List<ThriftAnimParamFrame> thriftAPFrameList = vibAPFrameList2thriftAPFrameList(vibAPframes);
       Message m = new Message();
       m.type = type;
       m.id = requestId.toString();
       m.time = Timer.getTimeMillis();
       m.APFrameList = thriftAPFrameList;
       send(m);
    }

    private List<ThriftAnimParamFrame> vibAPFrameList2thriftAPFrameList(List<APF> vibAPframes) {

        List<ThriftAnimParamFrame> thriftAPFrameList  = new ArrayList<ThriftAnimParamFrame>(vibAPframes.size());

        for(AnimationParametersFrame vibFrame:vibAPframes){

            ThriftAnimParamFrame thriftFrame = new ThriftAnimParamFrame();
            thriftFrame.frameNumber = vibFrame.getFrameNumber();
            Logs.debug("thriftFrame.frameNumber: "+thriftFrame.frameNumber);
            thriftFrame.animParamList = new ArrayList<ThriftAnimParam>(vibFrame.size());
            List<AnimationParameter> apList= vibFrame.getAnimationParametersList();
            for(AnimationParameter ap:apList){
                ThriftAnimParam thriftAP = new ThriftAnimParam(ap.getMask(), ap.getValue());
                thriftFrame.animParamList.add(thriftAP);
            }
            thriftAPFrameList.add(thriftFrame);
        }
        return thriftAPFrameList;
    }
}
