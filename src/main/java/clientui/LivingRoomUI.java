package clientui;

import client.LivingRoomClient;
import utils.UIConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public class LivingRoomUI extends ClientUI {

    private JButton warm;
    private JToggleButton lightsToggle;
    private JToggleButton curtainToggle;
    private JToggleButton tvToggle;

    private final LivingRoomClient livingRoomClient;

    public LivingRoomUI(LivingRoomClient livingRoomClient) {
        super(livingRoomClient);
        this.livingRoomClient = livingRoomClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        warm = new JButton(UIConstants.WARM_BUTTON);
        lightsToggle = new JToggleButton(UIConstants.LIGHTS_LABEL, true);
        curtainToggle = new JToggleButton(UIConstants.CURTAIN_ON, true);
        tvToggle = new JToggleButton(UIConstants.TV_ON, true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        addToggle(new JToggleButton[]{lightsToggle, curtainToggle, tvToggle});
        addButton(new JButton[]{warm});
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == warm) {
            livingRoomClient.warm();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        Object source = itemEvent.getSource();
        int stateChange = itemEvent.getStateChange();

        lightsSwitch(source, stateChange);
        curtainSwitch(source, stateChange);
        tvSwitch(source, stateChange);
    }

    private void lightsSwitch(Object source, int stateChange) {
        if(source == lightsToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    livingRoomClient.lights();
                    lightsToggle.setText(UIConstants.LIGHTS_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    livingRoomClient.lights();
                    lightsToggle.setText(UIConstants.LIGHTS_ON);
                    break;
            }
        }
    }

    private void curtainSwitch(Object source, int stateChange) {
        if(source == curtainToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    livingRoomClient.curtains();
                    lightsToggle.setText(UIConstants.CURTAIN_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    livingRoomClient.curtains();
                    lightsToggle.setText(UIConstants.CURTAIN_ON);
                    break;
            }
        }
    }

    private void tvSwitch(Object source, int stateChange) {
        if(source == tvToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    livingRoomClient.tvRemote();
                    lightsToggle.setText(UIConstants.TV_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    livingRoomClient.tvRemote();
                    lightsToggle.setText(UIConstants.TV_ON);
                    break;
            }
        }
    }
}
