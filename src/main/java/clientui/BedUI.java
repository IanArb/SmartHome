package clientui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

import client.BedClient;

public class BedUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton warm;
    private JToggleButton lightsToggle;

    private final BedClient bedClient;

    public BedUI(BedClient bedClient) {
        super(bedClient);
        this.bedClient = bedClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        warm = new JButton(UIConstants.WARM_BUTTON);
        lightsToggle = new JToggleButton("On/Off", true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        addToggle(new JToggleButton[]{lightsToggle});
        add(new JButton[]{warm});
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == warm) {
            bedClient.warm();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        if(itemEvent.getSource() == lightsToggle) {
            if(itemEvent.getStateChange() == ItemEvent.SELECTED) {
                bedClient.lights();
            } else if(itemEvent.getStateChange() == ItemEvent.DESELECTED) {
                bedClient.lights();
            }
        }
    }
}
