import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CookieMain {

    JLabel counterLabel, perSecLabel;
    JButton button1, button2, button3, button4;
    int cookieCounter, timerSpeed, pickaxeNumber, pickaxePrice, mineNumber, minePrice, explosivesNumber, explosivesPrice, quarryNumber, quarryPrice;
    double perSecond;
    boolean timerOn, mineUnlocked, explosivesUnlocked, quarryUnlocked;
    Font font1, font2;
    CookieHandler cHandler = new CookieHandler();
    Timer timer;
    JTextArea messageText;
    MouseHandler mHandler = new MouseHandler();

    public static void main(String[] args){
        new CookieMain();
    }

    public CookieMain(){
        timerOn = false;
        perSecond = 0;
        cookieCounter = 0;
        pickaxeNumber = 0;
        pickaxePrice = 10;
        mineNumber = 0;
        minePrice = 100;
        explosivesNumber = 0;
        explosivesPrice = 1000;
        quarryNumber = 0;
        quarryPrice = 10000;

        createFont();
        createUI();
    }

    public void createFont(){
        font1 = new Font("Comic Sans MS", Font.PLAIN, 32);
        font2 = new Font("Comic Sans MS", Font.PLAIN, 15);
    }

    public void createUI(){
        JFrame window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);

        JPanel cookiePanel = new JPanel();
        cookiePanel.setBounds(100, 220, 200, 200);
        cookiePanel.setBackground(Color.BLACK);
        window.add(cookiePanel);

        ImageIcon cookie = new ImageIcon(getClass().getClassLoader().getResource("Crystal.png"));

        JButton cookieButton = new JButton();
        cookieButton.setBackground(Color.BLACK);
        cookieButton.setFocusPainted(false);
        cookieButton.setBorder(null);
        cookieButton.setIcon(cookie);
        cookieButton.addActionListener(cHandler);
        cookieButton.setActionCommand("cookie");
        cookiePanel.add(cookieButton);

        JPanel counterPanel = new JPanel();
        counterPanel.setBounds(100, 100, 300, 100);
        counterPanel.setBackground(Color.BLACK);
        counterPanel.setLayout(new GridLayout(2, 1));
        window.add(counterPanel);

        counterLabel = new JLabel(String.format("%d Crystals", cookieCounter));
        counterLabel.setForeground(Color.WHITE);
        counterLabel.setFont(font1);
        counterPanel.add(counterLabel);

        perSecLabel = new JLabel();
        perSecLabel.setForeground(Color.WHITE);
        perSecLabel.setFont(font2);
        counterPanel.add(perSecLabel);

        JPanel itemPanel = new JPanel();
        itemPanel.setBounds(500, 170, 250, 250);
        itemPanel.setBackground(Color.BLACK);
        itemPanel.setLayout(new GridLayout(4,1));
        window.add(itemPanel);

        button1 = new JButton(String.format("Pickaxe x%d", pickaxeNumber));
        button1.setFont(font1);
        button1.setFocusPainted(false);
        button1.addActionListener(cHandler);
        button1.setActionCommand("Pickaxe");
        button1.addMouseListener(mHandler);
        itemPanel.add(button1);

        button2 = new JButton("?");
        button2.setFont(font1);
        button2.setFocusPainted(false);
        button2.addActionListener(cHandler);
        button2.setActionCommand("Mine");
        button2.addMouseListener(mHandler);
        itemPanel.add(button2);

        button3 = new JButton("?");
        button3.setFont(font1);
        button3.setFocusPainted(false);
        button3.addActionListener(cHandler);
        button3.setActionCommand("Explosives");
        button3.addMouseListener(mHandler);
        itemPanel.add(button3);

        button4 = new JButton("?");
        button4.setFont(font1);
        button4.setFocusPainted(false);
        button4.addActionListener(cHandler);
        button4.setActionCommand("Quarry");
        button4.addMouseListener(mHandler);
        itemPanel.add(button4);

        JPanel messagePanel = new JPanel();
        messagePanel.setBounds(500, 70, 250, 150);
        messagePanel.setBackground(Color.BLACK);
        window.add(messagePanel);

        messageText = new JTextArea();
        messageText.setBounds(500,70,250,150);
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
                cookieCounter++;
                counterLabel.setText(String.format("%d Crystals", cookieCounter));

                if (!mineUnlocked) {
                    if (cookieCounter >= 100) {
                        mineUnlocked = true;
                        button2.setText(String.format("Mine x%d", mineNumber));
                    }
                }

                if (!explosivesUnlocked) {
                    if (cookieCounter >= 1000) {
                        explosivesUnlocked = true;
                        button3.setText(String.format("Explosives x%d", explosivesNumber));
                    }
                }

                if (!quarryUnlocked) {
                    if (cookieCounter >= 10000) {
                        quarryUnlocked = true;
                        button4.setText(String.format("Quarry x%d", quarryNumber));
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

    public class CookieHandler implements ActionListener{
        public void actionPerformed(ActionEvent event){
            String action = event.getActionCommand();

            switch (action){
                case "cookie":
                    cookieCounter++;
                    counterLabel.setText(String.format("%d Crystals", cookieCounter));
                    break;
                case "Pickaxe":
                    if (cookieCounter >= pickaxePrice) {
                        cookieCounter -= pickaxePrice;
                        pickaxePrice += 5;
                        counterLabel.setText(String.format("%d Crystals", cookieCounter));

                        pickaxeNumber++;
                        button1.setText(String.format("Pickaxe x%d", pickaxeNumber));
                        messageText.setText(String.format("Pickaxe\n[price: %d Crystals]\nEach pickaxe mines a crystal every 10 seconds", pickaxePrice));
                        perSecond += 0.1;
                        timerUpdate();
                    } else {
                        messageText.setText("You don't have enough Crystals!");
                    }
                case "Mine":
                    if (cookieCounter >= minePrice) {
                        cookieCounter -= minePrice;
                        minePrice += 50;
                        counterLabel.setText(String.format("%d Crystals", cookieCounter));

                        mineNumber++;
                        button2.setText(String.format("Mine x%d", mineNumber));
                        messageText.setText(String.format("Mine\n[price: %d Crystals]\nEach mine generates a crystal every 1 second", minePrice));
                        perSecond += 1;
                        timerUpdate();
                    } else {
                        messageText.setText("You don't have enough Crystals!");
                    }
                case "Explosives":
                    if (cookieCounter >= explosivesPrice) {
                        cookieCounter -= explosivesPrice;
                        explosivesPrice += 500;
                        counterLabel.setText(String.format("%d Crystals", cookieCounter));

                        explosivesNumber++;
                        button3.setText(String.format("Explosives x%d", explosivesNumber));
                        messageText.setText(String.format("Explosives\n[price: %d Crystals]\nEach explosive produces 10 crystals every 1 second", explosivesPrice));
                        perSecond += 10;
                        timerUpdate();
                    } else {
                        messageText.setText("You don't have enough Crystals!");
                    }
                case "Quarry":
                    if (cookieCounter >= quarryPrice) {
                        cookieCounter -= quarryPrice;
                        quarryPrice += 5000;
                        counterLabel.setText(String.format("%d Crystals", cookieCounter));

                        quarryNumber++;
                        button4.setText(String.format("Quarry x%d", quarryNumber));
                        messageText.setText(String.format("Quarry\n[price: %d Crystals]\nEach quarry provides 100 crystals every 1 second", quarryPrice));
                        perSecond += 100;
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

            if (button == button1){
                messageText.setText(String.format("Pickaxe\n[price: %d Crystals]\nEach pickaxe mines a crystal every 10 seconds", pickaxePrice));
            } else if (button == button2){
                if (!mineUnlocked) {
                    messageText.setText("Mine 100 crystals to unlock!");
                } else {
                    messageText.setText(String.format("Mine\n[price: %d Crystals]\nEach mine generates a crystal every 1 second", minePrice));
                }
            } else if (button == button3){
                if (!mineUnlocked) {
                    messageText.setText("This item is currently locked!");
                } else if (!explosivesUnlocked){
                    messageText.setText("Mine 1000 crystals to unlock!");
                } else {
                    messageText.setText(String.format("Explosives\n[price: %d Crystals]\nEach explosive produces 10 crystals every 1 second", explosivesPrice));
                }
            } else if (button == button4){
                if (!explosivesUnlocked) {
                    messageText.setText("This item is currently locked!");
                } else if (!quarryUnlocked){
                    messageText.setText("Mine 10000 crystals to unlock!");
                } else {
                    messageText.setText(String.format("Quarry\n[price: %d Crystals]\nEach quarry provides 100 crystals every 1 second", quarryPrice));
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton button = (JButton)e.getSource();

            if (button == button1){
                messageText.setText(null);
            } else if (button == button2){
                messageText.setText(null);
            } else if (button == button3){
                messageText.setText(null);
            } else if (button == button4){
                messageText.setText(null);
            }
        }
    }
}
