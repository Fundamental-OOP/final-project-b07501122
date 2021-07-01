package controller;

import model.Direction;
import model.World;
import pikachu.Pikachu;

public class Game extends GameLoop {
    private final Pikachu p1;
    private final Pikachu p2;
    private final World world;
    private int p1Score = 0;
    private int p2Score = 0;
    private int winScore;
    
    public Game(World world, Pikachu p1, Pikachu p2, int winScore) {
        this.p1 = p1;
        this.p2 = p2;
        this.world = world;
        this.winScore = winScore;
        this.world.setGame(this);
    }

    public void movePikachu(int playerNumber, Direction direction) {
        getPlayer(playerNumber).move(direction);
    }

    public void stopPikachu(int playerNumber, Direction direction) {
        getPlayer(playerNumber).stop(direction);
    }

    public void jump(int playerNumber) {
        getPlayer(playerNumber).jump();
    }
    
    public void attack(int playerNumber) {
    	getPlayer(playerNumber).attack();
    }
    
    public Pikachu getPlayer(int playerNumber) {
        return playerNumber == 1 ? p1 : p2;
    }

    public int[] getScores() {
        return new int[]{p1Score, p2Score};
    }
    
    public void roundOver(int Scorer) {  	
    	addScore(Scorer);
    	if(hasWiner()) {
    		this.stop();		 
    	}
    	else {   		
    		world.reset(Scorer);
    		this.delay(120);
    	}
    }
    
    private boolean hasWiner() {
    	if((p1Score >= winScore || p2Score >= winScore) && !duce())
    		return true;
    	else
    		return false;
	}

	private boolean duce() {
		return Math.abs(p1Score - p2Score) <= 1 ? true : false;
	}

	private void addScore(int scorer) {
		if(scorer == 1)
			p1Score++;
		else
			p2Score++;
	}

	@Override
    protected World getWorld() {
        return world;
    }
}
