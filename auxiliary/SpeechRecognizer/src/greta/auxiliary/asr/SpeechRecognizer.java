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

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.websockets.*;

/**
 *
 * @author Mukesh Barange
 */
public class SpeechRecognizer {

    private long messageID = 0;
    private String language = "fr-FR";
    private String pathToCertificate ="Common/Data/ASRResources/cert/cert.p12";

    private HttpServer server;
    private List<WebSocket> clients = new ArrayList<>();
    private Broadcaster webSocketBroadcaster = new OptimizedBroadcaster();
    SpeechRecognizer cTest = null;

    TranscriptSender transcriptSender;

    public void setTranscriptSender(TranscriptSender sender) {
        this.transcriptSender = sender;
    }

    public void start() {
        NetworkListener listener = new NetworkListener("speech_listener", "localhost", 8686);
        listener.registerAddOn(new WebSocketAddOn());
        WebSocketEngine.getEngine().register("", "/", new WebSocketApplication() {
            @Override
            public void onConnect(WebSocket conn) {
                clients.add(conn);
                conn.send(language);
                System.out.println("websocket connection started.");
            }

            @Override
            public void onClose(WebSocket conn, DataFrame frame) {
                clients.remove(conn);
                System.out.println("websocket connection closed.");
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                transcriptReceived(message);
                //System.out.println("websocket received message.");
            }
        });

        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(pathToCertificate), new char[0]);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, new char[0]);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            SSLEngineConfigurator sslEngineConfigurator = new SSLEngineConfigurator(sslContext);
            sslEngineConfigurator.setClientMode(false);
            sslEngineConfigurator.setNeedClientAuth(false);
            sslEngineConfigurator.setWantClientAuth(false);

            listener.setSecure(true);
            listener.setSSLEngineConfig(sslEngineConfigurator);
        }
        catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }

        server = new HttpServer();
        server.addListener(listener);

        server.getServerConfiguration().addHttpHandler(new CLStaticHttpHandler(getClass().getClassLoader(), "/"), "");
        String currentPath="";
        try {
            server.start();
            try {
                currentPath= new java.io.File( "." ).getCanonicalPath();
            } catch(Exception e ) {

            }
            System.setProperty("webdriver.chrome.driver", ".//Common//Data//ASRResources//chromedriver.exe");
            new org.openqa.selenium.chrome.ChromeDriver().get("https://localhost:8686/");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void transcriptReceived(String transcript) {
        System.out.println("\nTranscript: "+transcript);
        transcriptSender.send(transcript);
    }

    protected void handleData() {
        webSocketBroadcaster.broadcast(clients, "start");
    }
}
