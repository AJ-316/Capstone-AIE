package com.AIE.WindowPackage;

public class ColorPallet extends AbstractWindow {

    public static String NAME = "ColorPallet";

    public ColorPallet(MainFrame mainFrame) {
        super(mainFrame, 200, 400, MainFrame.SCREEN_WIDTH - 500, 200);
        setTitle("ColorPallet");
        setFocusableWindowState(true);
        add(ColorChannelSlider.create(0));
        add(ColorChannelSlider.create(1));
        add(ColorChannelSlider.create(2));
        add(HSVSlider.create(0));
        add(HSVSlider.create(1));
        add(HSVSlider.create(2));

        setVisible(true);
    }

//    private static class SliderOptionButton extends JButton implements ActionListener {
//
//        private static final String optionEnable = "More >>";
//        private static final String optionDisable = "<< Less";
//        private final ColorPallet pallet;
//
//        public SliderOptionButton(ColorPallet pallet) {
//            super(optionEnable);
//            this.pallet = pallet;
//            addActionListener(this);
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if(getText().equals(optionEnable)) {
////                pallet.setFocusableWindowState(true);
//                setText(optionDisable);
//            } else if(getText().equals(optionDisable)) {
////                pallet.setFocusableWindowState(false);
//                setText(optionEnable);
//            }
//        }
//    }
}
