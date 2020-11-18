import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CrystalMain {

    JLabel counterLabel, perSecLabel;
    UpgradePanel upgradePanel1, upgradePanel2, upgradePanel3, upgradePanel4, upgradePanel5;
    int SCALE, crystalCounter, timerSpeed;
    double perSecond;
    boolean timerOn, mineUnlocked, explosivesUnlocked, quarryUnlocked, laserUnlocked;
    Font font1, font2;
    CrystalHandler cHandler = new CrystalHandler();
    Timer timer;
    JTextArea messageText;
    MouseHandler mHandler = new MouseHandler();

    public static void main(String[] args){
        new CrystalMain();
    }

    public CrystalMain(){
        timerOn = false;
        perSecond = 0;
        crystalCounter = 0;
        SCALE = 1;

        createFont();
        createUI();
    }

    public void createFont(){
        font1 = new Font("Comic Sans MS", Font.PLAIN, 32);
        font2 = new Font("Comic Sans MS", Font.PLAIN, 15);
    }

    public void createUI(){
        JFrame window = new JFrame();
        window.setSize(800*SCALE, 600*SCALE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);

        JPanel crystalPanel = new JPanel();
        crystalPanel.setBounds(100*SCALE, 220*SCALE, 200*SCALE, 200*SCALE);
        crystalPanel.setBackground(Color.BLACK);
        window.add(crystalPanel);

        ImageIcon crystal = new ImageIcon(getClass().getClassLoader().getResource("Crystal.png"));

        JButton crystalButton = new JButton();
        crystalButton.setBackground(Color.BLACK);
        crystalButton.setFocusPainted(false);
        crystalButton.setBorder(null);
        crystalButton.setIcon(crystal);
        crystalButton.addActionListener(cHandler);
        crystalButton.setActionCommand("crystal");
        crystalPanel.add(crystalButton);

        JPanel counterPanel = new JPanel();
        counterPanel.setBounds(100*SCALE, 100*SCALE, 300*SCALE, 100*SCALE);
        counterPanel.setBackground(Color.BLACK);
        counterPanel.setLayout(new GridLayout(2, 1));
        window.add(counterPanel);

        counterLabel = new JLabel(String.format("%d Crystals", crystalCounter));
        counterLabel.setForeground(Color.WHITE);
        counterLabel.setFont(font1);
        counterPanel.add(counterLabel);

        perSecLabel = new JLabel();
        perSecLabel.setForeground(Color.WHITE);
        perSecLabel.setFont(font2);
        counterPanel.add(perSecLabel);

        JPanel itemPanel = new JPanel();
        itemPanel.setBounds(500*SCALE, 170*SCALE, 250*SCALE, 320*SCALE);
        itemPanel.setBackground(Color.BLACK);
        itemPanel.setLayout(new GridLayout(5,1));
        window.add(itemPanel);

        upgradePanel1 = new UpgradePanel("Pickaxe", 0, 10, font1, 0.1, cHandler, mHandler);
        upgradePanel1.updateText();
        itemPanel.add(upgradePanel1.getButton());

        upgradePanel2 = new UpgradePanel("Mine", 0, 100, font1, 1, cHandler, mHandler);
        itemPanel.add(upgradePanel2.getButton());

        upgradePanel3 = new UpgradePanel("Explosives", 0, 1000, font1, 10, cHandler, mHandler);
        itemPanel.add(upgradePanel3.getButton());

        upgradePanel4 = new UpgradePanel("Quarry", 0, 10000, font1, 100, cHandler, mHandler);
        itemPanel.add(upgradePanel4.getButton());

        upgradePanel5 = new UpgradePanel("Laser", 0, 100000, font1, 1000, cHandler, mHandler);
        itemPanel.add(upgradePanel5.getButton());

        JPanel messagePanel = new JPanel();
        messagePanel.setBounds(500*SCALE, 70*SCALE, 250*SCALE, 150*SCALE);
        messagePanel.setBackground(Color.BLACK);
        window.add(messagePanel);

        messageText = new JTextArea();
        messageText.setBounds(500*SCALE,70*SCALE,250*SCALE,150*SCALE);
        messageText.setForeground(Color.WHITE);
        messageText.setBackground(Color.BLACK);
        messageText.setFont(font2);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setEditable(false);
        messagePanel.add(messageText);

        window.setVisible(true);
    }

    public void setTimer(){
        timer = new Timer(timerSpeed, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                crystalCounter++;
                counterLabel.setText(String.format("%d Crystals", crystalCounter));

                if (!mineUnlocked) {
                    if (crystalCounter >= upgradePanel2.getInitialPrice()) {
                        mineUnlocked = true;
                        upgradePanel2.updateText();
                    }
                }

                if (!explosivesUnlocked) {
                    if (crystalCounter >= upgradePanel3.getInitialPrice()) {
                        explosivesUnlocked = true;
                        upgradePanel3.updateText();
                    }
                }

                if (!quarryUnlocked) {
                    if (crystalCounter >= upgradePanel4.getInitialPrice()) {
                        quarryUnlocked = true;
                        upgradePanel4.updateText();
                    }
                }

                if (!laserUnlocked) {
                    if (crystalCounter >= upgradePanel5.getInitialPrice()) {
                        laserUnlocked = true;
                        upgradePanel5.updateText();
                    }
                }
            }
        });

    }

    public void timerUpdate(){
        if (!timerOn){
            timerOn = true;
        } else {
            timer.stop();
        }

        double speed = 1/perSecond*1000;
        timerSpeed = (int)Math.round(speed);

        perSecLabel.setText(String.format("per second: %.1f", perSecond));

        setTimer();
        timer.start();
    }

    public class CrystalHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String action = event.getActionCommand();

            switch (action){
                case "crystal":
                    crystalCounter++;
                    counterLabel.setText(String.format("%d Crystals", crystalCounter));
                    break;
                case "Pickaxe":
                    if (crystalCounter >= upgradePanel1.getPrice()) {
                        crystalCounter -= upgradePanel1.getPrice();
                        upgradePanel1.increasePrice();
                        counterLabel.setText(String.format("%d Crystals", crystalCounter));

                        upgradePanel1.increaseQuantity();
                        upgradePanel1.updateText();
                        messageText.setText(upgradePanel1.getMessageText());
                        perSecond += upgradePanel1.getMineRate();
                        timerUpdate();
                    } else {
                        messageText.setText("You don't have enough Crystals!");
                    }
                case "Mine":
                    if (crystalCounter >= upgradePanel2.getPrice()) {
                        crystalCounter -= upgradePanel2.getPrice();
                        upgradePanel2.increasePrice();
                        counterLabel.setText(String.format("%d Crystals", crystalCounter));

                        upgradePanel2.increaseQuantity();
                        upgradePanel2.updateText();
                        messageText.setText(upgradePanel2.getMessageText());
                        perSecond += upgradePanel2.getMineRate();
                        timerUpdate();
                    } else {
                        messageText.setText("You don't have enough Crystals!");
                    }
                case "Explosives":
                    if (crystalCounter >= upgradePanel3.getPrice()) {
                        crystalCounter -= upgradePanel3.getPrice();
                        upgradePanel3.increasePrice();
                        counterLabel.setText(String.format("%d Crystals", crystalCounter));

                        upgradePanel3.increaseQuantity();
                        upgradePanel3.updateText();
                        messageText.setText(upgradePanel3.getMessageText());
                        perSecond += upgradePanel3.getMineRate();
                        timerUpdate();
                    } else {
                        messageText.setText("You don't have enough Crystals!");
                    }
                case "Quarry":
                    if (crystalCounter >= upgradePanel4.getPrice()) {
                        crystalCounter -= upgradePanel4.getPrice();
                        upgradePanel4.increasePrice();
                        counterLabel.setText(String.format("%d Crystals", crystalCounter));

                        upgradePanel4.increaseQuantity();
                        upgradePanel4.updateText();
                        messageText.setText(upgradePanel4.getMessageText());
                        perSecond += upgradePanel4.getMineRate();
                        timerUpdate();
                    } else {
                        messageText.setText("You don't have enough Crystals!");
                    }
                case "Laser":
                    if (crystalCounter >= upgradePanel5.getPrice()) {
                        crystalCounter -= upgradePanel5.getPrice();
                        upgradePanel5.increasePrice();
                        counterLabel.setText(String.format("%d Crystals", crystalCounter));

                        upgradePanel5.increaseQuantity();
                        upgradePanel5.updateText();
                        messageText.setText(upgradePanel5.getMessageText());
                        perSecond += upgradePanel5.getMineRate();
                        timerUpdate();
                    } else {
                        messageText.setText("You don't have enough Crystals!");
                    }
            }


        }
    }

    public class MouseHandler implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton button = (JButton)e.getSource();

            if (button == upgradePanel1.getButton()){
                messageText.setText(upgradePanel1.getMessageText());
            } else if (button == upgradePanel2.getButton()){
                if (!mineUnlocked) {
                    messageText.setText(String.format("Mine %d crystals to unlock!", upgradePanel2.getInitialPrice()));
                } else {
                    messageText.setText(upgradePanel2.getMessageText());
                }
            } else if (button == upgradePanel3.getButton()){
                if (!mineUnlocked) {
                    messageText.setText("This item is currently locked!");
                } else if (!explosivesUnlocked){
                    messageText.setText(String.format("Mine %d crystals to unlock!", upgradePanel3.getInitialPrice()));
                } else {
                    messageText.setText(upgradePanel3.getMessageText());
                }
            } else if (button == upgradePanel4.getButton()){
                if (!explosivesUnlocked) {
                    messageText.setText("This item is currently locked!");
                } else if (!quarryUnlocked){
                    messageText.setText(String.format("Mine %d crystals to unlock!", upgradePanel4.getInitialPrice()));
                } else {
                    messageText.setText(upgradePanel4.getMessageText());
                }
            } else if (button == upgradePanel5.getButton()){
                if (!quarryUnlocked) {
                    messageText.setText("This item is currently locked!");
                } else if (!laserUnlocked){
                    messageText.setText(String.format("Mine %d crystals to unlock!", upgradePanel5.getInitialPrice()));
                } else {
                    messageText.setText(upgradePanel5.getMessageText());
                }
            }

        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton)e.getSource();

            if (button == upgradePanel1.getButton()){
                messageText.setText(null);
            } else if (button == upgradePanel2.getButton()){
                messageText.setText(null);
            } else if (button == upgradePanel3.getButton()){
                messageText.setText(null);
            } else if (button == upgradePanel4.getButton()){
                messageText.setText(null);
            } else if (button == upgradePanel5.getButton()){
                messageText.setText(null);
            }
        }
    }
}
