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
    private final BedClient client = new BedClient();

    public ClientManager() throws IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        jmdns.addServiceListener(client.getServiceType(), this);
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
        if (client.getServiceType().equals(type) && client.hasMultiple()) {
            if (client.isCurrent(name)) {
                ServiceInfo[] a = jmdns.list(type);
                for (ServiceInfo in : a) {
                    if (!in.getName().equals(name)) {
                        newService = in;
                    }
                }
                client.switchService(newService);
            }
            client.remove(name);
        } else if (client.getServiceType().equals(type)) {
            ui.removePanel(client.returnUI());
            client.disable();
            client.initialized = false;
        }
    }

    public void serviceResolved(ServiceEvent serviceEvent) {
        System.out.println(serviceEvent);
        String address = serviceEvent.getInfo().getHostAddress();
        int port = serviceEvent.getInfo().getPort();
        String type = serviceEvent.getInfo().getType();

        if (client.getServiceType().equals(type) && !client.isInitialized()) {
            client.setUp(address, port);
            ui.addPanel(client.returnUI(), client.getName());
            client.setCurrent(serviceEvent.getInfo());
            client.addChoice(serviceEvent.getInfo());
        } else if (client.getServiceType().equals(type)
                && client.isInitialized()) {
            client.addChoice(serviceEvent.getInfo());

        }
    }

    public static void main(String[] args) throws IOException {
        new ClientManager();
    }
}
