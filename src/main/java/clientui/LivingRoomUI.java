package clientui;

import client.livingroom.LivingRoomClient;
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
        curtainToggle = new JToggleButton(UIConstants.CURTAIN_LABEL, true);
        tvToggle = new JToggleButton(UIConstants.TV_LABEL, true);
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

        lightsSwitch(source);
        curtainSwitch(source);
        tvSwitch(source);
    }

    private void lightsSwitch(Object source) {
        if(source == lightsToggle) {
            livingRoomClient.lights();
        }
    }

    private void curtainSwitch(Object source) {
        if(source == curtainToggle) {
            livingRoomClient.curtains();
        }
    }

    private void tvSwitch(Object source) {
        if(source == tvToggle) {
            livingRoomClient.tvRemote();
        }
    }
}
