package clientui;


import client.KitchenClient;
import utils.UIConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

/**
 * Created by Fran Firmino on 11/04/2017.
 */
public class KitchenUI extends ClientUI{
    private static final long serialVersionUID = -5318589393275157185L;
    private JButton boil;
    private JToggleButton ovenToggle;
    private JToggleButton tapToggle;

    private final KitchenClient kitchenClient;

    public KitchenUI(KitchenClient kitchenClient) {
        super(kitchenClient);
        this.kitchenClient = kitchenClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        boil = new JButton(UIConstants.BOIL_BUTTON);
        ovenToggle = new JToggleButton(UIConstants.OVEN_ON, true);
        tapToggle = new JToggleButton(UIConstants.TAP_ON, true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        addToggle(new JToggleButton[]{ovenToggle, tapToggle});
        addButton(new JButton[]{boil});
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == boil) {
            kitchenClient.boil();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
        Object source = itemEvent.getSource();

        int stateChange = itemEvent.getStateChange();

        ovenSwitch(source, stateChange);

        tapSwitch(source, stateChange);


    }

    private void tapSwitch(Object source, int stateChange) {
        if(source == tapToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    kitchenClient.tap();
                    tapToggle.setText(UIConstants.TAP_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    kitchenClient.tap();
                    tapToggle.setText(UIConstants.TAP_ON);
                    break;
            }
        }
    }

    private void ovenSwitch(Object source, int stateChange) {
        if(source == ovenToggle) {
            switch (stateChange) {
                case ItemEvent.SELECTED:
                    kitchenClient.oven();
                    ovenToggle.setText(UIConstants.OVEN_OFF);
                    break;
                case ItemEvent.DESELECTED:
                    kitchenClient.oven();
                    ovenToggle.setText(UIConstants.OVEN_ON);
                    break;
            }
        }
    }
}
