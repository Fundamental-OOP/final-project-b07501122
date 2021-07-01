package model;

import java.awt.Point;


public interface CollisionHandler {

	void handle(Point originalLocation, Sprite from, Sprite to);

}
