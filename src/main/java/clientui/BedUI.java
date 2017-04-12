package clientui;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.*;

import client.bedroom.BedClient;
import utils.UIConstants;

public class BedUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton warm;
    private JToggleButton lightsToggle;
    private JToggleButton lampsToggle;

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
        lightsToggle = new JToggleButton(UIConstants.LIGHTS_LABEL, true);
        lampsToggle = new JToggleButton(UIConstants.LAMP_LABEL, true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        addToggle(new JToggleButton[]{lightsToggle, lampsToggle});
        addButton(new JButton[]{warm});
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == warm) {
            bedClient.warm();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        Object source = itemEvent.getSource();

        lightsSwitch(source);

        lampSwitch(source);
    }

    private void lightsSwitch(Object source) {
        if(source == lightsToggle) {
            bedClient.lights();
        }
    }

    private void lampSwitch(Object source) {
        if(source == lampsToggle) {
            bedClient.lamp();
        }
    }
}
