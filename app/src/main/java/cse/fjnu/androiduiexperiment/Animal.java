package cse.fjnu.androiduiexperiment;

public class Animal {
    private final String name;
    private final int iconRes;

    public Animal(String name, int iconRes) {
        this.name = name;
        this.iconRes = iconRes;
    }

    public String getName() { return name; }
    public int getIconRes() { return iconRes; }
}