package clientui;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import javax.swing.*;

import client.BedClient;
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
        lightsToggle = new JToggleButton(UIConstants.LIGHTS_ON, true);
        lampsToggle = new JToggleButton(UIConstants.LAMP_ON, true);
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
        int stateChange = itemEvent.getStateChange();

        lightsSwitch(source, stateChange);

        lampSwitch(source, stateChange);
    }

    private void lightsSwitch(Object source, int stateChange) {
        if(source == lightsToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    bedClient.lights();
                    lightsToggle.setText(UIConstants.LIGHTS_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    bedClient.lights();
                    lightsToggle.setText(UIConstants.LIGHTS_ON);
                    break;
            }
        }
    }

    private void lampSwitch(Object source, int stateChange) {
        if(source == lampsToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    bedClient.lamp();
                    lampsToggle.setText(UIConstants.LAMP_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    bedClient.lamp();
                    lampsToggle.setText(UIConstants.LAMP_ON);
                    break;
            }
        }
    }
}
