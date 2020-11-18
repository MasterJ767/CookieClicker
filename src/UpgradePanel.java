import javax.swing.*;
import java.awt.*;

public class UpgradePanel {
    private String name;
    private JButton button;
    private int quantity, price, initialPrice;
    private double mineRate;

    UpgradePanel(String name, int quantity, int price, Font font, double mineRate, CrystalMain.CrystalHandler cHandler, CrystalMain.MouseHandler mHandler) {
        this.name = name;

        this.button = new JButton();
        this.button.setText("?");
        this.button.setFont(font);
        this.button.setFocusPainted(false);
        this.button.addActionListener(cHandler);
        this.button.setActionCommand(name);
        this.button.addMouseListener(mHandler);

        this.quantity = quantity;
        this.price = price;
        this.initialPrice = price;
        this.mineRate = mineRate;
    }

    public int getPrice() { return price; }
    public int getInitialPrice() { return initialPrice; }
    public int getQuantity() { return quantity; }
    public String getName() { return name; }
    public JButton getButton() { return button; }
    public double getMineRate() { return mineRate; }
    public double getCrystalsPerSecond() { return (mineRate * quantity); }

    public String getMessageText() {
        return String.format("%s\n[price: %d Crystals]\nEach %s mines %.1f crystals every second", this.name, this.price, this.name.toLowerCase(), this.getCrystalsPerSecond());
    }

    public void increasePrice() {
        this.price = (int) Math.floor(this.price *= 1.25);
    }

    public void increaseQuantity() {
        this.quantity++;
    }

    public void setText(String text) {
        this.button.setText(text);
    }

    public void updateText() {
        this.setText(String.format("%s x%d", this.name, this.quantity));
    }
}
