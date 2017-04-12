package clientui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.ClientManager;
import utils.UIConstants;

public class ClientManagerUI extends JFrame {

    private final JTabbedPane allPanels;
    private static final long serialVersionUID = -4512962459244007477L;

    public ClientManagerUI(final ClientManager clientManager) {
        super("Smart Home");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                clientManager.end();
            }
        });
        setResizable(false);
        pack();
        setSize(UIConstants.UIWIDTH, UIConstants.UIWIDTH);
        setLocation(setPosition(this));
        allPanels = new JTabbedPane();
        getContentPane().add(allPanels);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        update(this.getGraphics());
    }

    public static Point setPosition(Component component) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - component.getWidth()) / 2);
        return new Point(x, 0);
    }

    public void addPanel(JPanel jPanel, String name) {
        allPanels.addTab(name, jPanel);
    }

    public void removePanel(JPanel returnUI) {
        allPanels.remove(returnUI);
    }
}
