package views;

import controller.Game;
import controller.GameLoop;
import model.Direction;
import model.World;
import utils.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameView extends JFrame {
    public static final int HEIGHT = Constant.WORLD_HEIGHT;
    public static final int WIDTH = Constant.WORLD_WIDTH;
    public static final int P1 = 1;
    public static final int P2 = 2;
    private final Canvas canvas = new Canvas();
    private final Game game;

    public GameView(Game game) throws HeadlessException {
        this.game = game;
        game.setView(canvas);
    }

    public void launch() {
        // GUI Stuff
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(canvas);
        setSize(WIDTH, HEIGHT);
        setContentPane(canvas);
        setVisible(true);

        // Keyboard listener
        addKeyListener(new KeyAdapter() {
        	//TODO vel change
            @Override
            public void keyPressed(KeyEvent keyEvent) {
            	switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_R:
                    game.jump(P1);
                    break;
                // case KeyEvent.VK_S:
                //     game.movePikachu(P1, Direction.DOWN);
                //     break;
                case KeyEvent.VK_D:
                    game.movePikachu(P1, Direction.LEFT);
                    break;
                case KeyEvent.VK_G:
                    game.movePikachu(P1, Direction.RIGHT);
                    break;
                case KeyEvent.VK_Z:
                    game.attack(P1);
                    break;
                case KeyEvent.VK_UP:
                    game.jump(P2);
                    break;
                // case KeyEvent.VK_K:
                //     game.movePikachu(P2, Direction.DOWN);
                //     break;
                case KeyEvent.VK_LEFT:
                    game.movePikachu(P2, Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    game.movePikachu(P2, Direction.RIGHT);
                    break;
                case KeyEvent.VK_ENTER:
                    game.attack(P2);
                    break;
            	}
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            	switch (keyEvent.getKeyCode()) {
                // case KeyEvent.VK_W:
                //     game.stopPikachu(P1, Direction.UP);
                //     break;
                // case KeyEvent.VK_S:
                //     game.stopPikachu(P1, Direction.DOWN);
                //     break;
                case KeyEvent.VK_D:
                    game.stopPikachu(P1, Direction.LEFT);
                    break;
                case KeyEvent.VK_G:
                    game.stopPikachu(P1, Direction.RIGHT);
                    break;
                // case KeyEvent.VK_I:
                //     game.stopPikachu(P2, Direction.UP);
                //     break;
                // case KeyEvent.VK_K:
                //     game.stopPikachu(P2, Direction.DOWN);
                //     break;
                case KeyEvent.VK_LEFT:
                    game.stopPikachu(P2, Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    game.stopPikachu(P2, Direction.RIGHT);
                    break;
            	}
            }
        });
    }

    public static class Canvas extends JPanel implements GameLoop.View {
        private World world;

        @Override
        public void render(World world) {
            this.world = world;
            repaint(); // ask the JPanel to repaint, it will invoke paintComponent(g) after a while.
        }

        @Override
        protected void paintComponent(Graphics g /*paintbrush*/) {
            super.paintComponent(g);
            
            world.renderBackground(g);
            world.render(g); // ask the world to paint itself and paint the sprites on the canvas
        }
    }
}
