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

/**
 *
 * @author Mukesh Barange
 */
public class ASRMain {

    private SpeechRecognizer sr = null;
    private ActiveMQConnector activeMQConnection = null;

    private String host = null;
    private String port = null;
    private String requestTopic = null;
    private String responseTopic = null;

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRequestTopic() {
        return this.requestTopic;
    }

    public String getResponseTopic() {
        return this.responseTopic;
    }

    public void setRequestTopic(String requestTopic) {
        this.requestTopic = requestTopic;
    }

    public void setResponseTopic(String responseTopic) {
        this.responseTopic = responseTopic;
    }

    public ASRMain(){
        activeMQConnection = new ActiveMQConnector();
        sr = new SpeechRecognizer();
    }

    public void initialiseSpeechRecognition(){
        sr.setTranscriptSender(activeMQConnection.getSender());
        sr.start();
    }

    public ActiveMQConnector getActiveMQConnector(){
        return activeMQConnection;
    }

    public void establishActiveMqConnection(String host, String port, String requestTopic, String responseTopic) {

        this.host = host;
        this.port = port;
        this.requestTopic = requestTopic;
        this.responseTopic = responseTopic;

        this.activeMQConnection.setHost(host);
        this.activeMQConnection.setPort(port);
        this.activeMQConnection.setRequestTopic(requestTopic);
        this.activeMQConnection.setResponseTopic(responseTopic);
        this.activeMQConnection.initializeSenderAndReceiver();

    }

}
