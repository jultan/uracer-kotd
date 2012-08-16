
package com.bitfire.uracer.game.screens;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bitfire.uracer.ScalingStrategy;
import com.bitfire.uracer.game.Game;
import com.bitfire.uracer.game.actors.CarPreset.Type;
import com.bitfire.uracer.screen.Screen;

public class GameScreen extends Screen {
	private Game game = null;

	@Override
	public void init (ScalingStrategy scalingStrategy) {

		// simulate slowness
		// try { Thread.sleep( 1000 ); } catch( InterruptedException e ) {}

		String levelName = "tutorial-1";

		game = new Game(levelName, scalingStrategy);

		// simulate the player choosing a car type
		game.setPlayer(Type.L2_PinkBeast);
	}

	@Override
	public void dispose () {
		game.dispose();
		game = null;
	}

	@Override
	public void tick () {
		game.tick();
	}

	@Override
	public void tickCompleted () {
		game.tickCompleted();
	}

	@Override
	public void render (FrameBuffer dest) {
		game.render(dest);
	}

	@Override
	public void pause () {
		game.pause();
	}

	@Override
	public void resume () {
		game.resume();
	}

	@Override
	public void debugRender () {
		game.debugUpdate();
	}
}