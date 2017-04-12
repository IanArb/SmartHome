package client;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import client.bedroom.BedClient;
import client.livingroom.LivingRoomClient;
import clientui.ClientManagerUI;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientManager implements ServiceListener {

    private final ClientManagerUI ui;
    private JmDNS jmdns;
    private final BedClient bedClient = new BedClient();
    private final LivingRoomClient livingRoomClient = new LivingRoomClient();

    public ClientManager() throws IOException {
        jmdns = JmDNS.create(InetAddress.getLocalHost());
        jmdns.addServiceListener(bedClient.getServiceType(), this);
        jmdns.addServiceListener(livingRoomClient.getServiceType(), this);
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
        boolean isBedServiceType = bedClient.getServiceType().equals(type);
        boolean isLivingServiceType = livingRoomClient.getServiceType().equals(type);

        if (isBedServiceType && bedClient.hasMultiple()) {
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
        } else if (isBedServiceType) {
            ui.removePanel(bedClient.returnUI());
            bedClient.disable();
            bedClient.initialized = false;
        }

        if(isLivingServiceType && livingRoomClient.hasMultiple()) {
            if(livingRoomClient.isCurrent(name)) {
                ServiceInfo[] serviceInfos = jmdns.list(type);
                for(ServiceInfo serviceInfo : serviceInfos) {
                    if(!serviceInfo.getName().equals(name)) {
                        newService = serviceInfo;
                    }
                }
                livingRoomClient.switchService(newService);
            }
            livingRoomClient.remove(name);
        } else if(isBedServiceType) {
            ui.removePanel(livingRoomClient.returnUI());
            livingRoomClient.disable();
            livingRoomClient.initialized = false;
        }
    }

    public void serviceResolved(ServiceEvent serviceEvent) {
        System.out.println(serviceEvent);
        String address = serviceEvent.getInfo().getHostAddress();
        int port = serviceEvent.getInfo().getPort();
        String type = serviceEvent.getInfo().getType();

        initBedService(serviceEvent, address, port, type);
        initLivingService(serviceEvent, address, port, type);
    }

    private void initBedService(ServiceEvent serviceEvent, String address, int port, String type) {
        boolean isBedServiceType = bedClient.getServiceType().equals(type);

        if (isBedServiceType && !bedClient.isInitialized()) {
            bedClient.setUp(address, port);
            ui.addPanel(bedClient.returnUI(), bedClient.getName());
            bedClient.setCurrent(serviceEvent.getInfo());
            bedClient.addChoice(serviceEvent.getInfo());
        } else if (isBedServiceType && bedClient.isInitialized()) {
            bedClient.addChoice(serviceEvent.getInfo());
        }
    }

    private void initLivingService(ServiceEvent serviceEvent, String address, int port, String type) {
        boolean isLivingServiceType = livingRoomClient.getServiceType().equals(type);

        if (isLivingServiceType && !livingRoomClient.isInitialized()) {
            livingRoomClient.setUp(address, port);
            ui.addPanel(livingRoomClient.returnUI(), livingRoomClient.getName());
            livingRoomClient.setCurrent(serviceEvent.getInfo());
            livingRoomClient.addChoice(serviceEvent.getInfo());
        } else if(livingRoomClient.getServiceType().equals(type) && livingRoomClient.isInitialized()) {
            livingRoomClient.addChoice(serviceEvent.getInfo());
        }
    }


    public static void main(String[] args) throws IOException {
        new ClientManager();
    }
}
