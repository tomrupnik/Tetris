import java.awt.Color;

public class Shape {

    private int rotationDirection;
    private Color color;
    private Position startingPosition;
    private ShapeType shapeType;
    private Position[] positions;


    public Shape(ShapeType shapeType) {

        int x = 0;
        int y = 0;
        this.shapeType = shapeType;

        switch (shapeType) {

            case I:
//                this.positions = new Position[]{new Position(x, y), new Position(x + 1, y),
//                        new Position(x + 2, y), new Position (x + 3, y)};
                this.positions = new Position[]{new Position(x - 1, y), new Position(x, y),
                        new Position(x + 1, y), new Position (x + 2, y)};
                this.color = new Color(204, 0, 0);
                break;

            case L:
//                this.positions = new Position[]{new Position(x, y), new Position(x, y + 1),
//                    new Position(x + 1, y), new Position (x + 2, y)};
                this.positions = new Position[]{new Position(x, y + 1), new Position(x, y),
                        new Position(x + 1, y), new Position (x + 2, y)};
                this.color = new Color(153, 51, 153);
                break;

            case Z:
//                this.positions = new Position[]{new Position(x, y), new Position(x + 1, y),
//                        new Position(x + 1, y + 1), new Position (x + 2, y + 1)};
                this.positions = new Position[]{new Position(x , y + 1), new Position (x + 1, y + 1),
                        new Position(x - 1, y), new Position(x , y)};
                this.color = new Color(255, 204, 0);
                break;

            case O:
                this.positions = new Position[]{new Position(x, y + 1), new Position (x + 1, y + 1),
                        new Position(x, y), new Position(x + 1, y)};
                this.color = new Color(0, 153, 51);
                break;

            case T:
//                this.positions = new Position[]{new Position(x, y), new Position(x + 1, y),
//                        new Position(x + 2, y), new Position (x + 1, y + 1)};
                this.positions = new Position[]{new Position (x, y + 1), new Position(x - 1, y), new Position(x, y),
                        new Position(x + 1, y)};
                this.color = new Color(0, 102, 255);
                break;

            case S:
//                this.positions = new Position[]{new Position(x, y + 1), new Position(x + 1, y + 1),
//                        new Position(x + 1, y), new Position(x + 2, y)};
                this.positions = new Position[]{new Position(x , y + 1), new Position(x - 1, y + 1),
                        new Position(x, y), new Position(x + 1 , y)};
                this.color = new Color(51, 204, 255);

        }

    }

    public void shapeMove() {
        for (int i = 0; i < positions.length; i++) {
            positions[i] = positions[i].move(0, 1);
        }
    }

    public int getRotationDirection() {
        return rotationDirection;
    }

    public void setRotationDirection(int rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Position getStartingPosition() {
        return startingPosition;
    }

    public void setStartingPosition(Position startingPosition) {
        this.startingPosition = startingPosition;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public Position[] getPositions() {
        return positions;
    }

    public void setPositions(Position[] positions) {
        this.positions = positions;
    }

    public void shapeRotate(boolean direction) {

        if (direction == true) {
            for (int i = 0; i < positions.length; i++) {
                int x = positions[i].getX() * 0 + positions[i].getY() * (-1);
                int y = positions[i].getX() * 1 + positions[i].getY() * 0;
                positions[i] = new Position(x, y);
            }
        }

        else if (direction == false) {
            for (int i = 0; i < positions.length; i++) {
                int x = positions[i].getX() * 0 + positions[i].getY() * (1);
                int y = positions[i].getX() * (-1) + positions[i].getY() * 0;
                positions[i] = new Position(x, y);
            }
        }
    }
}
