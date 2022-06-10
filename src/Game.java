import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Game {

    protected static final long MOVE_SLEEP = 500L;

    private Window window;

    private Shape shape;

    private boolean pause;

    private Position shapePosition;

    private Color[][] canvas;

    private Panel plane;

    private Shape nextShape;

    private int points;

    private boolean gameOver;

    private boolean fastFalling;

    public Game(int width, int height) {
        if (height <= 0 || width <= 0)
            throw new IllegalArgumentException();
        createCanvas(width, height);
        this.pause = false;
    }

    private void createCanvas(int width, int height) {
        canvas = new Color[height][width];

    }


    public void play() {
        fastFalling = false;
        shape = new Shape(ShapeType.randomShape());
        if (nextShape != null) {
            setShape(nextShape);
        }
        else {
            shape = new Shape(ShapeType.randomShape());
            setShape(shape);
        }
        nextShape = new Shape(ShapeType.randomShape());
        shapePosition = new Position(5, -2);
        setNextShape(nextShape);
        while (true) {
            System.out.println(isPause());
            if (!isPause() & !isGameOver()) {

                try {
                    Thread.sleep(isFastFalling() ? (int) (MOVE_SLEEP * 0.01) : MOVE_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (Position position : shape.getPositions()) {
                    boolean flag = false; // Indicator that the player has lost.
                    if (shapePosition.getY() + position.getY() + 1 >= 0 & shapePosition.getY() + position.getY() + 1 < getHeight()) {
                        if (getColor(new Position(position.getX() + shapePosition.getX(), position.getY() + shapePosition.getY() + 1)) != null) {
                            for (Position finalPosition : shape.getPositions()) {
                                if (finalPosition.getY() + shapePosition.getY() < 0) { // The block is not in the canvas/grid meaning the player has lost.
                                    flag = true;
                                } else {
                                    setColor(new Position(finalPosition.getX() + shapePosition.getX(), finalPosition.getY() + shapePosition.getY()), shape.getColor());
                                    setFastFalling(false);
                                }
                            }

                            if (flag) {
                                setGameOver(true);
                                break;
                            }
                            // Prepare for next round.
                            shapePosition = new Position(5, -2);
                            shape = nextShape;
                            nextShape = new Shape(ShapeType.randomShape());
                        }
                    } else if (shapePosition.getY() + position.getY() + 1 == getHeight()) { // Is at the bottom
                        for (Position finalPosition : shape.getPositions()) {
                            setColor(new Position(finalPosition.getX() + shapePosition.getX(), finalPosition.getY() + shapePosition.getY()), shape.getColor());
                            setFastFalling(false);
                        }
                        shapePosition = new Position(5, -2);
                        shape = nextShape;
                        nextShape = new Shape(ShapeType.randomShape());
                    }

                }
                shapePosition = shapePosition.move(0, 1);

                for (int i = 0; i < getHeight(); i++) {
                    if (isFullRow(i)) {
                        moveRows(i);
                        increasePoints();
                    }
                }

//            for (Position position : shape.getPositions()) {
//                if (shapePosition.getY() + position.getY() < 0) {
//
//                }
//            }

//            if (!isPause() & !isGameOver()) {
//                // GAME OVER
//                for (Position position : shape.getPositions()) {
//                    if ((shapePosition.getY() + position.getY() + 1) < getHeight())
//                        if (getColor(new Position(shapePosition.getX() + position.getX() ,shapePosition.getY() + position.getY() + 1)) != null) {
//                            for (Position otherPosition : shape.getPositions()) {
//                                if (otherPosition.getY() + shapePosition.getY() < 0) {
//                                    System.out.println("GAME OVER");
//                                    setPause(true);
//                                    setGameOver(true);
//                                }
//                            }
//                        }
//                }
//
//                try {
//                    Thread.sleep(isFastFalling() ? (int)(MOVE_SLEEP * 0.01) : MOVE_SLEEP);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                for (Position position : shape.getPositions()) {
//                    if (shapePosition.getY() + position.getY() < 0)
//                        break;
//                    else if (shapePosition.getY() + position.getY() + 1 == getHeight() ||
//                            getColor(new Position(position.getX() + shapePosition.getX(), shapePosition.getY() + position.getY() + 1)) != null) {
//                        for (Position setPosition : shape.getPositions()) {
//                            if (shapePosition.getY() + setPosition.getY() < 0) {
//                                setGameOver(true);
//                                plane.repaint();
//                            } else {
//                                setColor(new Position(shapePosition.getX() + setPosition.getX(), shapePosition.getY() + setPosition.getY()), shape.getColor());
//                            }
//                            setFastFalling(false);
//                        }
//                        play();
//                    }
//                }
//
//
//                shapePosition = shapePosition.move(0, 1);
//
//
//
//                for (int i = 0; i < getHeight(); i++) {
//                    if (isFullRow(i)) {
//                        moveRows(i);
//                        increasePoints();
//                    }
//                }
//
//
//            }

                plane.repaint();
            }
        }
    }

    public void setPlane(Panel plane) {
        this.plane = plane;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setShapePosition(Position shapePosition) {
        this.shapePosition = shapePosition;
    }

    public Shape getShape() {
        return this.shape;
    }

    public Position getShapePosition() {
        return this.shapePosition;
    }

    public int getWidth() {
        return canvas[0].length;
    }

    public int getHeight() {
        return canvas.length;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public Color getColor(Position position) {
        Color color = canvas[position.getY()][position.getX()];
        return color;
    }

    public void setColor(Position position, Color color) {
        canvas[position.getY()][position.getX()] = color;
    }

    public boolean isFullRow(int index) {
        for (int i = 0; i < getWidth(); i++) {
            if (canvas[index][i] == null)
                return false;
        }
        return true;
    }

    public void moveRows(int index) {
        for (int i = 0; i < getWidth(); i++) {
            for (int j = index; j > 0; j--) {
                canvas[j][i] = canvas[j - 1][i];
            }
            canvas[0][i] = null;
        }
    }

    public void fullRows() {
        for (int i = 0; i < getHeight(); i++) {
            if (isFullRow(i)) {
                moveRows(i);
            }
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Shape getNextShape() {
        return nextShape;
    }

    public void setNextShape(Shape nextShape) {
        this.nextShape = nextShape;
    }

    public void increasePoints() {
        setPoints(getPoints() + 1);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isFastFalling() {
        return fastFalling;
    }

    public void setFastFalling(boolean fastFalling) {
        this.fastFalling = fastFalling;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public boolean isMoveable(int moveX, int moveY) {
        // Checks if the shape can be moved from left to rigth or vice versa.
        for (Position position : getShape().getPositions()) {
            int x = position.getX() + shapePosition.getX() + moveX;
            int y = position.getY() + shapePosition.getY() + moveY;
            if (!(x >= 0) | !(x < canvas[0].length)) {
                return false;
            }
//            if (!(x >= 0) | !(x < canvas[0].length) | !(y >= 0) | !(y < canvas.length)) {
//                return false;
//            }
            else if (canvas[y][x] != null) {
                return false;
            }
        }
        return true;
    }

    public void newGame() {
        // Reset the game.
        createCanvas(getWidth(), getHeight());
        setGameOver(false);
        setPause(false);
        setNextShape(new Shape(ShapeType.randomShape()));
        setShapePosition(new Position(5,-3));
        setPoints(0);
        setFastFalling(false);
    }

    public boolean isRotatable(boolean direction) {
        // Checks if rotation is possible.
        if (direction) {
            for (Position position : getShape().getPositions()) {
                int x = position.getX() * 0 + position.getY() * (-1) + getShapePosition().getX();
                int y = position.getX() * 1 + position.getY() * 0 + getShapePosition().getY();
                if (!(x >= 0) | !(x < canvas[0].length) | !(y >= 0) | !(y < canvas.length)) {
                    return false;
                }
                else if (canvas[y][x] != null) {
                    return false;
                }
            }
        }
        else if (!direction) {
            for (Position position : getShape().getPositions()) {
                int x = position.getX() * 0 + position.getY() * (1) + getShapePosition().getX();
                int y = position.getX() * (-1) + position.getY() * 0 + getShapePosition().getY();
                if (!(x >= 0) | !(x < canvas[0].length) | !(y >= 0) | !(y < canvas.length)) {
                    return false;
                }
                else if (canvas[y][x] != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
