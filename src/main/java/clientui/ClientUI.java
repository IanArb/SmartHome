/*
 * 
 */
package clientui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.*;

import client.Client;

/**
 * The Class ClientUI.
 *
 * @author dominic
 */
public abstract class ClientUI extends JPanel implements ActionListener, ItemListener{

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    protected JComboBox services;
    protected JPanel controls;
    protected JTextArea textArea;
    protected JScrollPane scroll;
    protected Client client;

    public ClientUI(Client client) {
        this.client = client;
    }

    public void init() {
        setLayout(null);
        services = new JComboBox();
        services.addActionListener(actionListener);
        add(services);
        services.setBounds(170, 5, 200, 30);
        controls = new JPanel();
        controls.setBounds(5, UIConstants.CONTROLY, UIConstants.COMPONENTWIDTH,
                50);
        controls.setLayout(new FlowLayout());
        controls.setBorder(BorderFactory.createLineBorder(Color.black));
        add(controls);
        textArea = new JTextArea();
        scroll = new JScrollPane();
        scroll.setViewportView(textArea);
        add(scroll);
    }

    public void add(JButton[] a) {
        for (JButton in : a) {
            in.addActionListener(this);
            controls.add(in);
        }
    }

    public void addToggle(JToggleButton[] toggleButtons) {
        for(JToggleButton jToggleButtons: toggleButtons) {
            jToggleButtons.addItemListener(this);
            controls.add(jToggleButtons);
        }
    }

    public void addChoices(Vector<String> a) {
        System.out.println("passed to add choices" + a);
        remove(services);
        services = new JComboBox(a);
        services.addActionListener(actionListener);
        services.setBounds(170, 5, 200, 30);
        add(services);
        updateUI();
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JComboBox cb = (JComboBox) actionEvent.getSource();
            String serviceName = (String) cb.getSelectedItem();
            client.switchService(serviceName);
        }
    };

    public void refresh(Vector<String> a) {
        this.removeAll();
        init();
        addChoices(a);
    }

    public void clearArea() {
        textArea.setText("");
    }

    public void updateArea(String string) {
        if (textArea.getText().equals("")) {
            textArea.append(string);
        } else {
            textArea.append("\n" + string);
        }
    }
}
