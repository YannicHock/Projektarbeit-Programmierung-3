package de.prog3.projektarbeit;

public class Player {
    private String name; // Name of the player
    private int age; // Age of the player
    private String position; // position of the player

    public Player(String name, int age, String position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }
}
