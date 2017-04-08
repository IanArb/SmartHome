package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.BedClient;

public class BedUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton warm;
    private JButton roomLight;
    private JButton lamps;

    private final BedClient parent;

    public BedUI(BedClient bedClient) {
        super(bedClient);
        parent = bedClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        warm = new JButton(UIConstants.WARM_BUTTON);
        roomLight = new JButton("Room light");
        lamps = new JButton("Lamps");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{warm, roomLight, lamps});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == warm) {
            parent.warm();
        }

        if(e.getSource() == roomLight) {
            parent.lights();
        }
    }
}
