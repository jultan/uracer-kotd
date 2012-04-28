package com.bitfire.uracer.game.logic.hud.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitfire.uracer.Art;
import com.bitfire.uracer.ScalingStrategy;
import com.bitfire.uracer.game.input.Replay;
import com.bitfire.uracer.game.logic.LapState;
import com.bitfire.uracer.game.logic.hud.HudElement;
import com.bitfire.uracer.game.logic.hud.HudLabel;
import com.bitfire.uracer.utils.NumberString;

public class PlayerLapTimes extends HudElement {

	private HudLabel best, curr, last;
	private LapState lapState;

	public PlayerLapTimes( ScalingStrategy scalingStrategy, LapState lapState ) {
		this.lapState = lapState;

		int gridX = (int)((float)Gdx.graphics.getWidth() / 5f);

		// laptimes component
		best = new HudLabel( scalingStrategy, Art.fontCurseYRbig, "BEST  TIME\n--.--" );
		curr = new HudLabel( scalingStrategy, Art.fontCurseYRbig, "YOUR  TIME\n--.--" );
		last = new HudLabel( scalingStrategy, Art.fontCurseYRbig, "LAST  TIME\n--.--" );

		curr.setPosition( gridX, 50 * scalingStrategy.invTileMapZoomFactor );
		last.setPosition( gridX * 3, 50 * scalingStrategy.invTileMapZoomFactor );
		best.setPosition( gridX * 4, 50 * scalingStrategy.invTileMapZoomFactor );
	}

	@Override
	public void dispose() {
	}

	@Override
	public void onTick() {
		// current time
		curr.setString( "YOUR  TIME\n" + NumberString.format( lapState.getElapsedSeconds() ) + "s" );

		// render best lap time
		Replay rbest = lapState.getBestReplay();

		// best time
		if( rbest != null && rbest.isValid ) {
			// has best
			best.setString( "BEST  TIME\n" + NumberString.format( rbest.trackTimeSeconds ) + "s" );
		} else {
			// temporarily use last track time
			if( lapState.hasLastTrackTimeSeconds() ) {
				best.setString( "BEST  TIME\n" + NumberString.format( lapState.getLastTrackTimeSeconds() ) + "s" );
			} else {
				best.setString( "BEST TIME\n--:--" );
			}
		}

		// last time
		if( lapState.hasLastTrackTimeSeconds() ) {
			// has only last
			last.setString( "LAST  TIME\n" + NumberString.format( lapState.getLastTrackTimeSeconds() ) + "s" );
		} else {
			last.setString( "LAST  TIME\n--:--" );
		}
	}

	@Override
	public void onRender( SpriteBatch batch ) {
		curr.render( batch );
		best.render( batch );
		last.render( batch );
	}

	@Override
	public void onReset() {
	}
}