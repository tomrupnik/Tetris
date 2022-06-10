import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Panel extends JPanel {

    private Game game;
    protected static final int BORDER = 50;
    protected static final int BOX = 30; // Size of one grid component.


    public Panel() {
        setBackground(new Color(32, 42, 68));
        setPreferredSize(new Dimension(700, 700));
        setMinimumSize(new Dimension(450,450));
        setFocusable(true);

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

                char key = e.getKeyChar();
                if (key == 'd' | key == 'D') {
                    if (game.isMoveable(1, 0)) {
                        game.setShapePosition(game.getShapePosition().move(1, 0));
                    }
                }
                else if (key == 'a' | key == 'A') {
                    if (game.isMoveable(-1, 0)) {
                        game.setShapePosition(game.getShapePosition().move(-1, 0));
                    }
                }
                else if (key == 'e' | key == 'E') {
                    if (game.isRotatable(true)) {
                        if (game.getShape().getShapeType() != ShapeType.O) { // If shape is square do not rotate.
                            game.getShape().shapeRotate(true);
                        }
                        try {
                            File soundFile = new File("src/Rotation.wav");
                            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioIn);
                            clip.start();
                        } catch (UnsupportedAudioFileException ex) {
                            ex.printStackTrace();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                else if (key == 'q' | key == 'Q') {
                    if (game.isRotatable(false)) {
                        if (game.getShape().getShapeType() != ShapeType.O) { // If shape is square do not rotate.
                            game.getShape().shapeRotate(false);
                        }
                        try {
                            File soundFile = new File("src/Rotation.wav");
                            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioIn);
                            clip.start();
                        } catch (UnsupportedAudioFileException ex) {
                            ex.printStackTrace();
                        } catch (LineUnavailableException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                else if (key == 's' | key == 'S') {
                    if (!game.isFastFalling()) {
                        game.setFastFalling(true);
                    }
                }

            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        double panelHeight = ((double)getHeight() - getPreferredSize().getHeight()) / getPreferredSize().getHeight(); // 700 = minimum size
        double panelWidth = ((double)getWidth() - getPreferredSize().getWidth()) / getPreferredSize().getWidth(); // 700 = minimum size
        double factor = 1 + Math.min(panelHeight, panelWidth);
        int size = (int)(Panel.BOX * factor);
        int border = (int)(Panel.BORDER * factor);

        if (game == null) {
            return;
        }

        if (game.isGameOver()) {
            // Red curtain
            g.setColor(new Color(255, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("GAME OVER", (getWidth() / 2 - 95), (getHeight() / 2));
            g.drawString(String.format("SCORE: %d", game.getPoints()), (getWidth() / 2 - 75), (getHeight() / 2) + 50);
        }

        if (!game.isGameOver()) {
            int x0 = 50;
            int y0 = 50;
            int width = game.getWidth();
            int height = game.getHeight();

            // Draw
            g.setColor(new Color(128, 128, 128));
            g.fillRect(border, border, width * size, height * size);
            g.setColor(Color.BLACK);
            g.drawRect(border, border, width * size, height * size);
            g.setColor(new Color(128, 128, 128));
            g.fillRect(border + width * size + border, border, 8 * size, 5 * size);
            g.setColor(new Color(128, 128, 128));
            g.drawRect(border + width * size + border, border, 8 * size, 5 * size);
            g.setFont(new Font("Arial", Font.BOLD, size));
            g.drawString(String.format("Points: %d", game.getPoints()), 2 * border + width * size, 2 * border + 5 * size);

            Shape shape = game.getShape();
            Position shapePosition = game.getShapePosition();
            System.out.println(shapePosition.getY());

            for (Position position : shape.getPositions()) {
                int x = shapePosition.getX() + position.getX();
                int y = shapePosition.getY() + position.getY();
                if (y >= 0) {
                    g.setColor(shape.getColor());
                    g.fillRect(border + x * size, border + y * size, size, size);
                }
            }

            // Draw next shape in the left grid.
            for (Position position : game.getNextShape().getPositions()) {
                int x = position.getX();
                int y = position.getY();
                g.setColor(game.getNextShape().getColor());
                g.fillRect(2 * border + 13 * size + x * size, border + 2 * size + y * size, size, size);
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Color color = game.getColor(new Position(i, j));
                    if (color != null) {
                        g.setColor(color);
                        g.fillRect(border + i * size, border + j * size, size, size);
                    }
                }
            }
            // MAIN GRID
            g.setColor(Color.BLACK);

            for (int i = border + size; i < border + width * size; i = i + size) {
                g.drawLine(i, border, i, border + height * size);
            }

            for (int i = border + size; i < border + height * size; i = i + size) {
                g.drawLine(border, i, border + width * size, i);
            }

            for (int i = 2 * border + width * size + size; i <= 2 * border + width * size + 8 * size; i = i + size) {
                g.drawLine(i, border, i, border + 5 * size);
            }

            for (int i = border; i < 5 * size + border; i = i + size) {
                g.drawLine(2 * border + width * size, i, border + width * size + 8 * size + border, i);
            }
        }
    }

    public void setGame(Game game) {
        this.game = game;
        repaint();
    }

}
