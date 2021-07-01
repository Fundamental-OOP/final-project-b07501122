package controller;

import media.AudioPlayer;
import model.World;
import utils.Constant;

public abstract class GameLoop {
	public static final String BGM = "bgm";
	public static final String AUDIO_WIN = "win_sound";
    private boolean gameOver;
    private View view;
    
    public void setView(View view) {
        this.view = view;
    }

    public void start() {
        new Thread(this::gameLoop).start();
    }

    private void gameLoop() {
    	AudioPlayer.playSounds(BGM);
        gameOver = false;
        while (!gameOver) {
            World world = getWorld();
            world.update();
            view.render(world);
            delay(Constant.DELAY_TIME);
        }
    }

    protected abstract World getWorld();
    
    public void stop() {
    	AudioPlayer.playSounds(AUDIO_WIN);
        gameOver = true;
    }

    public void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public interface View {

        void render(World world);
    }
}
