package greta.auxiliary.asr;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.websockets.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MBarange
 */
public class SpeechRecogniser {    
    
    private long messageID = 0;
    private String language = "fr-FR";
    private String pathToCertificate ="Common/Data/ASRResources/cert/cert.p12" ;

    private HttpServer server;
    private List<WebSocket> clients = new ArrayList<>();
    private Broadcaster webSocketBroadcaster = new OptimizedBroadcaster();
    SpeechRecogniser cTest = null;
    
    TranscriptSender transcriptSender;
    
    public SpeechRecogniser(){

    }
    public void setTranscriptSender(TranscriptSender sender){
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
                 System.out.println("websocket connection started   ");
            }

            @Override
            public void onClose(WebSocket conn, DataFrame frame) {
                clients.remove(conn);
                 System.out.println("websocket connection Closed   ");
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                transcriptReceived(message);
               //  System.out.println("websocket received message   ");
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
           try{
         currentPath= new java.io.File( "." ).getCanonicalPath();
        }catch(Exception e ){
            
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



