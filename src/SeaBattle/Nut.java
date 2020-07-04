package SeaBattle;

import java.io.Serializable;


public class Nut implements Serializable{

    public Type type;
    private int health;
    public int height;
    public int width;
    public int price;
    public boolean completeHealth;
    public boolean attack = true;

    public Nut(Type type) {
        this.type = type;
        if (type.equals(type.SOLDIER)) {
            this.height = 1;
            this.width = 1;
            this.health = this.width * this.height;
            completeHealth = true;
        }
        if (type.equals(type.HORSEBACK)) {
            this.height = 2;
            this.width = 1;
            this.health = this.width * this.height;
            completeHealth = true;

        }
        if (type.equals(type.CASTLE)) {
            this.height = 2;
            this.width = 2;
            this.health = this.width * this.height;
            completeHealth = true;

        }
        if (type.equals(type.KING)) {
            this.height = 3;
            this.width = 3;
            this.health = this.width * this.height;
            completeHealth = true;

        }
        price = health * 10;
        System.out.println("create nut");
    }

    public Nut(int health) {
        if (health == 1) {
            this.height = 1;
            this.width = 1;
            this.type = Type.SOLDIER;
            this.health = health;
            completeHealth = true;
        }
        if (health == 2) {
            this.height = 2;
            this.width = 1;
            this.type = Type.HORSEBACK;
            this.health = health;
            completeHealth = true;

        }
        if (health == 4) {
            this.height = 2;
            this.width = 2;
            this.type = Type.CASTLE;

            this.health = health;
            completeHealth = true;

        }
        if (health == 9) {
            this.height = 3;
            this.width = 3;
            this.type = Type.KING;

            this.health = health;
            completeHealth = true;

        }
        price = health * 10;
    }

    public void hit() {
        this.health--;
        completeHealth = false;

    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Nut{" + "type=" + type + ", health=" + health + ", height=" + height + ", width=" + width + ", price=" + price + ", completeHealth=" + completeHealth + ", attack=" + attack + '}';
    }

}

enum Type {
    SOLDIER,
    HORSEBACK,
    CASTLE,
    KING;
}
