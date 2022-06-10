import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    Panel panel;
    Game game;

    public Window() {
        setTitle("Tetris");
        panel = new Panel();
        add(panel);
        game = new Game(10, 20);
        game.setPlane(panel);
        panel.setGame(game);
        setMinimumSize(new Dimension(450, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
        JMenu menu = new JMenu("Game");
        bar.add(menu);
        JMenuItem item = new JMenuItem("New Game");
        final JMenuItem itemPause = new JMenuItem("Pause");
        item.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               itemPause.setText("Pause");
               game.newGame();
           }
        });
        menu.add(item);
        itemPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setPause(!game.isPause());
                if (game.isPause())
                    itemPause.setText("Continue");
                else if (!game.isPause())
                    itemPause.setText("Pause");
            }
        });
        menu.add(itemPause);
        pack();
    }

}
