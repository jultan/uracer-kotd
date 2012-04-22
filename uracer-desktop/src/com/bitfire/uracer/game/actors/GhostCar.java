package com.bitfire.uracer.game.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.bitfire.uracer.game.input.Replay;
import com.bitfire.uracer.game.logic.PhysicsStep;
import com.bitfire.uracer.game.world.GameWorld;

/** Implements an automated Car, playing previously recorded events. It will
 * ignore car-to-car collisions, but will respect in-track collisions and
 * responses.
 *
 * @author manuel */

public final class GhostCar extends Car {
	private static final int FadeEvents = 30;
	private Replay replay;
	private int indexPlay;
	private boolean hasReplay;

	public GhostCar( World box2dWorld, PhysicsStep physicsStep, GameWorld gameWorld, CarModel model, Aspect aspect ) {
		super( box2dWorld, physicsStep, gameWorld, model, aspect );
		indexPlay = 0;
		hasReplay = false;
		replay = null;
		this.inputMode = InputMode.InputFromReplay;
		this.renderer.setAlpha( 0.5f );

		setActive( false );
		resetPhysics();
	}

	// input data for this car cames from a Replay object
	public void setReplay( Replay replay ) {
		this.replay = replay;
		hasReplay = (replay != null && replay.getEventsCount() > 0);

		setActive( hasReplay );
		resetPhysics();

		if( hasReplay ) {
			// System.out.println( "Replaying " + replay.id );
			restart( replay );
		}

		// else
		// {
		// if(replay==null)
		// System.out.println("Replay disabled");
		// else
		// System.out.println("Replay has no recorded events, disabling replaying.");
		// }
	}

	private void restart( Replay replay ) {
		pos( replay.carPosition );
		orient( replay.carOrientation );
		indexPlay = 0;
	}

	@Override
	public void reset() {
		super.reset();
		setReplay( null );
		renderer.setAlpha( 0 );
	}

	@Override
	public void onRender( SpriteBatch batch ) {
		if( hasReplay && isActive() && renderer.getAlpha() > 0 ) {
			renderer.render( batch, stateRender );
		}
	}

	@Override
	protected void onComputeCarForces( CarForces forces ) {
		forces.reset();

		if( hasReplay ) {
			if( indexPlay == replay.getEventsCount() ) {
				// System.out.println( "Playing finished, restarting." );
				restart( replay );
			}

			forces.set( replay.forces[indexPlay++] );

			// also change opacity, fade in/out based on
			// events played, events remaining
			if( indexPlay <= FadeEvents ) {
				renderer.setAlpha( ((float)indexPlay / (float)FadeEvents) * 0.5f );
			} else if( replay.getEventsCount() - indexPlay <= FadeEvents ) {
				float val = (float)(replay.getEventsCount() - indexPlay) / (float)FadeEvents;
				renderer.setAlpha( val * 0.5f );
			}
		}
	}
}
