package client;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import clientui.ClientManagerUI;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientManager implements ServiceListener {

    private final ClientManagerUI ui;
    private JmDNS jmdns;
    private final BedClient bedClient = new BedClient();

    public ClientManager() throws IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        jmdns.addServiceListener(bedClient.getServiceType(), this);
        ui = new ClientManagerUI(this);
    }

    public void end() {
        try {
            jmdns.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void serviceAdded(ServiceEvent serviceEvent) {
        System.out.println(serviceEvent);
        serviceEvent.getDNS().requestServiceInfo(serviceEvent.getType(), serviceEvent.getName(), 0);
    }

    public void serviceRemoved(ServiceEvent serviceEvent) {
        System.out.println(serviceEvent);
        String type = serviceEvent.getType();
        String name = serviceEvent.getName();
        ServiceInfo newService = null;

        removeService(type, name, newService);
    }

    private void removeService(String type, String name, ServiceInfo newService) {
        if (bedClient.getServiceType().equals(type) && bedClient.hasMultiple()) {
            if (bedClient.isCurrent(name)) {
                ServiceInfo[] a = jmdns.list(type);
                for (ServiceInfo in : a) {
                    if (!in.getName().equals(name)) {
                        newService = in;
                    }
                }
                bedClient.switchService(newService);
            }
            bedClient.remove(name);
        } else if (bedClient.getServiceType().equals(type)) {
            ui.removePanel(bedClient.returnUI());
            bedClient.disable();
            bedClient.initialized = false;
        }
    }

    public void serviceResolved(ServiceEvent serviceEvent) {
        System.out.println(serviceEvent);
        String address = serviceEvent.getInfo().getHostAddress();
        int port = serviceEvent.getInfo().getPort();
        String type = serviceEvent.getInfo().getType();

        initService(serviceEvent, address, port, type);
    }

    private void initService(ServiceEvent serviceEvent, String address, int port, String type) {
        if (bedClient.getServiceType().equals(type) && !bedClient.isInitialized()) {
            bedClient.setUp(address, port);
            ui.addPanel(bedClient.returnUI(), bedClient.getName());
            bedClient.setCurrent(serviceEvent.getInfo());
            bedClient.addChoice(serviceEvent.getInfo());
        } else if (bedClient.getServiceType().equals(type)
                && bedClient.isInitialized()) {
            bedClient.addChoice(serviceEvent.getInfo());
        }
    }


    public static void main(String[] args) throws IOException {
        new ClientManager();
    }
}
