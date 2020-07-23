/*
 * This file is part of the auxiliaries of Greta.
 *
 * Greta is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Greta is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Greta.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
package greta.auxiliary.asr;

import greta.auxiliary.activemq.Sender;
import java.util.Map;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * @author Mukesh Barange
 */
public class TranscriptSender extends Sender<String>{

    private boolean active;

    public TranscriptSender() {
        super();
        this.active = true;
    }

    public TranscriptSender(String host, String port, String topic) {
        super(host, port, topic);
        this.active = true;
    }

    @Override
    protected void onSend(Map<String, Object> properties) {
        if (!this.active) {
            return;
        }
        //else, also do nothing
    }

    @Override
    protected Message createMessage(String content) throws JMSException {
        if (!this.active) {
            return null;
        }
        return session.createTextMessage(content.toString());
    }

    public void deactivate() {
        this.active = false;
    }

}
