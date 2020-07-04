package SeaBattle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle implements Serializable {

    public Nut nut = null;
    public boolean wasShot = false;
    public static boolean moveTurn = false;
    boolean min = false; 

    public Cell() {
        super(50 - 0.82 * Board1.size, 50 - 0.82 * Board1.size);
       
        setFill(Color.ANTIQUEWHITE);
        setStroke(Color.BLACK);
    }

    public Cell(Cell cell, boolean original, boolean swich) {
        super(50 - 0.8 * Board1.size, 50 - 0.8 * Board1.size);
        this.nut = cell.nut;
        this.wasShot = cell.wasShot;
        FileInputStream input = null;
        if (this.nut == null && original && !wasShot && cell.min) {
            try {
                input = new FileInputStream("images\\min.png");
            } catch (FileNotFoundException ex) {
            }
            Image img = new Image(input);
            setFill(new ImagePattern(img));
        }
        if (this.nut == null && original && !wasShot && !cell.min) {
            setFill(Color.ANTIQUEWHITE);
        }
        if (!wasShot && !original) {
            setFill(Color.ANTIQUEWHITE);

        }
        if (wasShot && this.nut == null && original && !swich) {
            try {
                input = new FileInputStream("images\\space.png");
            } catch (FileNotFoundException ex) {
            }
            Image img2 = new Image(input);
            setFill(new ImagePattern(img2));
        }
        if (wasShot && !original && !swich) {
            try {
                input = new FileInputStream("images\\space.png");
            } catch (FileNotFoundException ex) {
            }
            Image img2 = new Image(input);
            setFill(new ImagePattern(img2));
        }
        if (wasShot && !original && swich) {
            try {
                input = new FileInputStream("images\\red2.png");
            } catch (FileNotFoundException ex) {
            }
            Image img = new Image(input);
            setFill(new ImagePattern(img));
        }
        if (wasShot && this.nut != null && original) {
            try {
                input = new FileInputStream("images\\red2.png");
            } catch (FileNotFoundException ex) {
            }
            Image img = new Image(input);
            setFill(new ImagePattern(img));
        }
        if (this.nut != null && !wasShot && original) {
            setFill(Color.BROWN);
        }

        setStroke(Color.BLACK);
    }

    public boolean shoot(Cell[][] cell, Cell[][] cell2, Cell[][] cell3, String name, int width, int height, int x, int y) {
        boolean check = false;
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                String path = "music\\boomb.mp3";
                Media media = new Media(new File(path).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                if (cell[i][j].min) {
                    FileInputStream input = null;
                    try {
                        input = new FileInputStream("images\\red2.png");
                    } catch (FileNotFoundException ex) {
                    }
                    Image img = new Image(input);
                    if(name.equals("enemy"))name = "My";
                    else name = "enemy";
                    cell2[i][j].shoot(cell3, cell2, cell, name, 1, 1, x, y);
                    cell[i][j].setFill(new ImagePattern(img));

                    mediaPlayer.setAutoPlay(true);
                    cell[i][j].min = false;
                    continue;
                }
                FileInputStream input2 = null;
                try {
                    input2 = new FileInputStream("images\\space.png");
                } catch (FileNotFoundException ex) {
                }
                Image img2 = new Image(input2);
                cell2[i][j].setFill(new ImagePattern(img2));
                cell[i][j].setFill(new ImagePattern(img2));
                if (cell[i][j].nut != null) {
                    cell[i][j].nut.hit();

                    mediaPlayer.setAutoPlay(true);
                    FileInputStream input = null;
                    try {
                        input = new FileInputStream("images\\red2.png");
                    } catch (FileNotFoundException ex) {
                    }
                    Image img = new Image(input);
                    cell2[i][j].setFill(new ImagePattern(img));
                    cell[i][j].setFill(new ImagePattern(img));
                    if (!cell[i][j].nut.isAlive() && !cell[i][j].wasShot) {
                        if (name.equals("enemy")) {
                            Board1.countNutMy--; // decrese count of nut
                            Board1.moneyEnemy += cell[i][j].nut.getPrice();
                            if (cell[i][j].nut.price == 10) {
                                Board1.soldier1--;
                                Board1.soldierAttack1--;
                                
                            }
                            if (cell[i][j].nut.price == 20) {
                                Board1.horse1--;
                                Board1.horseAttack1--;
                            }
                            if (cell[i][j].nut.price == 40) {
                                Board1.castle1--;
                                Board1.castleAttack1--;
                            }
                            if (cell[i][j].nut.price == 90) {
                                Board1.king1--;
                                Board1.kingAttack1--;
                            }

                        } else {
                            Board1.countNutEnemy--;
                            Board1.moneyMy += cell[i][j].nut.getPrice();
                            if (cell[i][j].nut.price == 10) {
                                Board1.soldier2--;
                                Board1.soldierAttack2--;
                            }
                            if (cell[i][j].nut.price == 20) {
                                Board1.horse2--;
                                Board1.horseAttack2--;
                            }
                            if (cell[i][j].nut.price == 40) {
                                Board1.castle2--;
                                Board1.soldierAttack2--;
                            }
                            if (cell[i][j].nut.price == 90) {
                                Board1.king2--;
                                Board1.kingAttack2--;
                            }
                        }
                    }
                    check = true;
                }

                cell[i][j].wasShot = true;
                cell2[i][j].wasShot = true;
            }
        }

        System.out.println("count nut enemy " + Board1.countNutEnemy);
        System.out.println("count nut my " + Board1.countNutMy);

        return check;
    }
    /*
        this method check Necessary conditions
            return true If the conditions are right else return false
    */
    public boolean canPlaceNut(int x, int y, Nut nut, Cell[][] array) {
        Nut nut1 = new Nut(nut.getHealth());
        try {
            if (x + nut1.width > array.length || y + nut1.height > array.length) {
                return false;
            }
        } catch (NullPointerException e) {
            System.out.println("he he he he");
        }
        int toX = (x + nut1.width + 1 <= array.length) ? x + nut1.width + 1 : x + nut1.width;
        int toY = (y + nut1.height + 1 <= array.length) ? y + nut1.height + 1 : y + nut1.height;

        for (int i = (x - 1 >= 0) ? x - 1 : x; i < toX; i++) {
            for (int j = (y - 1 >= 0) ? y - 1 : y; j < toY; j++) {
                if (array[i][j].nut != null) {
                    return false;
                }
            }
        }
        return true;

    }
    /*
     this method create new nut in map of game
    */
    public boolean putnut(int x, int y, Nut nut, Cell[][] array) {
        System.out.println("in putnut " + nut.type);
        if (canPlaceNut(x, y, nut, array)) {
            Nut nut1 = new Nut(nut.type);
            for (int i = x; i < x + nut1.width; i++) {
                for (int j = y; j < y + nut1.height; j++) {
                    System.out.println("in putnut method " + nut1.type);
                    System.out.println("size = " + nut1.height * nut1.width);
                    array[i][j].nut = nut1;
                    array[i][j].setFill(Color.BROWN);

                }
            }
            return true;
        }
        return false;
    }
    /*
        move nut at the beggining of the game
    */
    public boolean move(int newX, int newY, int x, int y, Nut nut, Cell[][] array) {
        if (canPlaceNut(newX, newY, nut, array)) {
            putnut(newX, newY, nut, array);
            int startX = x;
            int startY = y;
            if (x - 1 >= 0 && array[x - 1][y].nut != null && array[x - 1][y].nut.price == nut.price) {
                startX = x - 1;
                if (x - 2 >= 0 && array[x - 2][y].nut != null && array[x - 2][y].nut.price == nut.price) {
                    startX = x - 2;
                }
            }
            if (y - 1 >= 0 && array[x][y - 1].nut != null && array[x][y - 1].nut.price == nut.price) {
                startY = y - 1;
                if (y - 2 >= 0 && array[x][y - 2].nut != null && array[x][y - 2].nut.price == nut.price) {
                    startY = y - 2;
                }
            }
            System.out.println("x********************y" + startX + " " + startY);

            for (int i = startX; i < startX + nut.width; i++) {
                for (int j = startY; j < startY + nut.height; j++) {
                    array[i][j].nut = null;
                    array[i][j].setFill(Color.ANTIQUEWHITE);
                    array[i][j].setStroke(Color.BLACK);
                }
            }
            return true;
        }
        return false;
    }
    /*
        move nut in scene 4 & scene 5
    */
    public boolean putMove(int x, int y, int oldX, int oldY, Cell[][] array, Nut nut) {
        if (canPlaceNut(x, y, nut, array)) {
            if (nut.type.equals(Type.SOLDIER) && Math.abs(x - oldX) + Math.abs(y - oldY) <= 1) {

                putnut(x, y, nut, array);
                return true;
            } else if (nut.type.equals(Type.HORSEBACK) && Math.abs(x - oldX) + Math.abs(y - oldY) <= 2) {
                putnut(x, y, nut, array);
                return true;
            }
        }
        return false;
    }
    /*
        delet nut from map of game
    */
    public boolean delete(int x, int y, Cell[][] array) {
        nut = array[x][y].nut;
        System.out.println("nut in delete" + nut.type);
        if (nut.type != Type.KING && nut.type != Type.CASTLE) {
            int startX = x;
            int startY = y;
            if (x - 1 >= 0 && array[x - 1][y].nut != null && array[x - 1][y].nut.price == nut.price) {
                startX = x - 1;
                if (x - 2 >= 0 && array[x - 2][y].nut != null && array[x - 2][y].nut.price == nut.price) {
                    startX = x - 2;
                }
            }
            if (y - 1 >= 0 && array[x][y - 1].nut != null && array[x][y - 1].nut.price == nut.price) {
                startY = y - 1;
                if (y - 2 >= 0 && array[x][y - 2].nut != null && array[x][y - 2].nut.price == nut.price) {
                    startY = y - 2;
                }
            }
            int toX = startX + nut.width;
            int toY = startY + nut.height;

            try {
                for (int i = startX; i < toX; i++) {
                    System.out.println("in first for");
                    for (int j = startY; j < toY; j++) {
                        System.out.println("in secend for");
                        array[i][j].nut = null;
                        array[i][j].setFill(Color.ANTIQUEWHITE);
                        array[i][j].setStroke(Color.BLACK);
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("null pointer in cell class 279 line");
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cell{" + "nut=" + nut + ", wasShot=" + wasShot + '}';
    }

}
