package clientui;

import client.BathClient;
import client.Client;
import utils.UIConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/**
 * Created by Fran Firmino on 12/04/2017.
 */
public class BathUI extends ClientUI{
    private static final long serialVersionUID = -5318589393275157185L;
    private JButton heat;
    private JToggleButton lightsToggle;
    private JToggleButton tapToggle;

    private final BathClient bathClient;


    public BathUI(BathClient bathClient) {
        super(bathClient);
        this.bathClient = bathClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        heat = new JButton(UIConstants.BOILER_BUTTON);
        lightsToggle = new JToggleButton(UIConstants.LIGHTS_ON, true);
        tapToggle = new JToggleButton(UIConstants.TAP_ON, true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        addToggle(new JToggleButton[]{lightsToggle, tapToggle});
        addButton(new JButton[]{heat});
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == heat) {
            bathClient.heat();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        Object source = itemEvent.getSource();

        int stateChange = itemEvent.getStateChange();

        lightsSwitch(source, stateChange);

        tapSwitch(source, stateChange);

    }

    private void tapSwitch(Object source, int stateChange) {
        if(source == tapToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    bathClient.tap();
                    tapToggle.setText(UIConstants.TAP_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    bathClient.tap();
                    tapToggle.setText(UIConstants.TAP_ON);
                    break;
            }
        }
    }

    private void lightsSwitch(Object source, int stateChange) {
        if(source == lightsToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    bathClient.lights();
                    lightsToggle.setText(UIConstants.LIGHTS_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    bathClient.lights();
                    lightsToggle.setText(UIConstants.LIGHTS_ON);
                    break;
            }
        }
    }
}
