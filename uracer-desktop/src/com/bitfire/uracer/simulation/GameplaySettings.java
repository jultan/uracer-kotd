package com.bitfire.uracer.simulation;

public class GameplaySettings
{
	// difficulty
	public static final int Easy = 1;
	public static final int Medium = 2;
	public static final int Hard = 3;

	public float linearVelocityAfterFeedback = 0;

	public static GameplaySettings create( int difficulty )
	{
		GameplaySettings s = new GameplaySettings();

		switch( difficulty )
		{
		default:
		case Easy:
			s.linearVelocityAfterFeedback = 0.99f;
			break;

		case Medium:
			s.linearVelocityAfterFeedback = 0.975f;
			break;

		case Hard:
			s.linearVelocityAfterFeedback = 0.95f;
			break;
		}

		return s;
	}
}
