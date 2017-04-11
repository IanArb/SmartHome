package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.jmdns.ServiceInfo;
import javax.swing.JPanel;

import clientui.ClientUI;
import utils.Constants;

/**
 * Abstract super class for all clients.
 *
 * @author dominic
 */
public abstract class Client {

    protected ClientUI ui;
    protected String serverHost = "";
    protected int serverPort = 0;
    protected String serviceType = "";
    protected boolean initialized = false;
    protected String name = " ";
    protected Socket toServer;
    protected Timer timer;
    protected HashMap<String, ServiceInfo> services;
    protected ServiceInfo current;
    protected String serverStatus;

    /**
     * Constructor.
     */
    public Client() {
        serverStatus = "";
        services = new HashMap<>();
    }

    public void remove(String key) {
        for (String keySet : services.keySet()) {
            if (keySet.equals(key)) {
                ServiceInfo s = services.remove(keySet);
                services.remove(s);
                break;
            }
        }
        Vector<String> vector = new Vector<>();
        vector.addAll(services.keySet());
        ui.addChoices(vector);
    }

    public boolean isCurrent(String name) {
        return current.getName().equals(name);
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds the choice.
     *
     * @param serviceInfo the service info
     */
    public void addChoice(ServiceInfo serviceInfo) {
        services.put(serviceInfo.getName(), serviceInfo);
        HashMap<String, ServiceInfo> as = (HashMap<String, ServiceInfo>) services
                .clone();
        as.remove(current.getName());
        Vector<String> d = new Vector<>();
        d.add(current.getName());
        System.out.println("current " + current.getName());
        d.addAll(as.keySet());
        System.out.println("called add choices from addchoice");
        ui.addChoices(d);
    }

    /**
     * Switch service.
     *
     * @param a the a
     */
    public void switchService(String a) {
        switchService(services.get(a));
    }

    /**
     * Switch service.
     *
     * @param newService the new service
     */
    @SuppressWarnings("unchecked")
    public void switchService(ServiceInfo newService) {
        System.out.println("switched");
        serverStatus = "";
        current = newService;
        HashMap<String, ServiceInfo> as = (HashMap<String, ServiceInfo>) services
                .clone();
        as.remove(current.getName());
        System.out.println(current.getName());
        Vector<String> d = new Vector<>();
        d.add(current.getName());
        d.addAll(as.keySet());
        ui.refresh(d);
        setUp(newService.getHostAddress(), newService.getPort());
    }

    /**
     * Checks for multiple.
     *
     * @return true, if successful
     */
    public boolean hasMultiple() {
        return services.size() > 1;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setUp(String server, int port) {
        System.out.println(serverHost + " " + server + " " + serverPort + " " + port);
        serverHost = server;
        serverPort = port;
        initialized = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new PollServer(), 6000, 20000);
    }

    public JPanel returnUI() {
        return ui;
    }

    protected String sendMessage(String a_message) {
        String msg = "";
        try {
            toServer = new Socket(serverHost, serverPort);
            PrintWriter out = new PrintWriter(toServer.getOutputStream(), true);
            out.println(a_message);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    toServer.getInputStream()));
            msg = in.readLine();
            out.close();
            toServer.close();
        } catch (Exception ioe) {
            ui.updateArea("Server Down");
        }
        return msg;
    }

    private class PollServer extends TimerTask {

        @Override
        public void run() {
            try {
                pollSocket();
            } catch (Exception ioe) {
                ui.updateArea("Server Down - attempting to poll service");
            }
        }
    }

    private void pollSocket() throws IOException {
        String msg;
        Socket pollSocket = new Socket(serverHost, serverPort);
        PrintWriter out = new PrintWriter(pollSocket.getOutputStream(),
                true);
        out.println(Constants.STATUS_REQUEST);
        out.println(Constants.LIGHTS_ON_REQUEST);
        out.println(Constants.LIGHTS_OFF_REQUEST);
        BufferedReader in = new BufferedReader(new InputStreamReader(
                pollSocket.getInputStream()));
        msg = in.readLine();
        String prevStatus = serverStatus;
        serverStatus = msg;
        if (!prevStatus.equals(serverStatus) && !msg.isEmpty()) {
            ui.updateArea(msg);
            updatePoll(msg);
        }
        out.close();
        pollSocket.close();
    }

    public void disable() {
        initialized = false;
    }

    public void updatePoll(String msg) {
        //Stub method
    }

    public void setCurrent(ServiceInfo info) {
        current = info;
    }
}
