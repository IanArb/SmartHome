package services;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import serviceui.ServiceUI;

public abstract class Service extends Thread {

    protected String SERVICE_TYPE;
    protected String SERVICE_NAME;
    protected int SERVICE_PORT;
    protected int my_backlog = 5;
    protected ServerSocket serverSocket;
    protected JmDNS jmdns;
    protected Socket socket;
    protected ServiceUI ui;
    protected ServiceInfo info;
    protected PrintWriter out;
    protected BufferedReader in;

    public Service(String name, String type) {
        this(name, type, "");
    }

    public Service(String name, String type, String location) {
        SERVICE_NAME = name;
        initPort();
        SERVICE_TYPE = type;
        initSocket();
        initJmdns(location);
        start();
    }

    private void initPort() {
        try {
            SERVICE_PORT = findFreePort();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private void initSocket() {
        try {
            serverSocket = new ServerSocket(SERVICE_PORT, my_backlog);
        } catch (IOException e) {
            initPort();
            e.printStackTrace();
        }
    }

    private void initJmdns(String location) {
        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
            info = ServiceInfo.create(SERVICE_TYPE, SERVICE_NAME, SERVICE_PORT,
                    "params=" + location);
            jmdns.registerService(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deRegister() {
        jmdns.unregisterService(info);
        try {
            this.stop();
            serverSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method that listens on the server socket forever and prints each incoming
     * message to the console.
     */
    @Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();

                in = new BufferedReader(new InputStreamReader(socket
                        .getInputStream()));

                String msg = in.readLine();
                performAction(msg);
                in.close();
                socket.close();
            } catch (IOException | SecurityException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public String getType() {
        return SERVICE_TYPE;
    }

    public String getServiceName() {
        return SERVICE_NAME;
    }

    public int getPort() {
        return SERVICE_PORT;
    }

    public void sendBack(String action) {
        getOutStream(action);
    }

    private void getOutStream(String action) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            out = new PrintWriter(outputStream, true);
            out.println(action);
            out.close();
        } catch (IOException exception) {
            ui.updateArea("Client not accessible: " + exception.getMessage());
        }
    }

    protected abstract void performAction(String action);

    public static int findFreePort() throws IOException {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        server.close();
        return port;
    }

    public abstract String getStatus();

    public abstract String getLightsStatus();

}
