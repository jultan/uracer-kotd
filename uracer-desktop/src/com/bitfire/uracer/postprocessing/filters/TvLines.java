package com.bitfire.uracer.postprocessing.filters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bitfire.uracer.postprocessing.IFilter;
import com.bitfire.uracer.utils.ShaderLoader;

public final class TvLines extends Filter<TvLines> {
	private float time, offset;
	private Vector2 resolution;
	private Vector3 vtint;
	private Color tint;

	public enum Param implements Parameter {
		// @formatter:off
		Texture0("u_texture0",0),
		Time("time",0),
		Resolution("resolution",2),
		Tint("tint",3),
		Offset("offset",0)
		;
		// @formatter:on

		private final String mnemonic;
		private int elementSize;

		private Param( String m, int elementSize ) {
			this.mnemonic = m;
			this.elementSize = elementSize;
		}

		@Override
		public String mnemonic() {
			return this.mnemonic;
		}

		@Override
		public int arrayElementSize() {
			return this.elementSize;
		}
	}

	public TvLines() {
		super( ShaderLoader.fromFile( "screenspace", "tv-lines" ) );
		time = 0;
		resolution = new Vector2();
		vtint = new Vector3();
		tint = new Color( 0.8f, 1.0f, 0.7f, 1.0f );

		rebind();
	}

	public void setTime( float time ) {
		this.time = time;
		setParam( Param.Time, time );
	}

	public void setResolution( float width, float height ) {
		resolution.set( width, height );
		setParam( Param.Resolution, resolution );
	}

	public void setOffset( float offset ) {
		this.offset = offset;
		setParam( Param.Offset, this.offset );
	}

	public void setTint( Color color ) {
		tint.set( color );
		vtint.set( tint.r, tint.g, tint.b );
		setParam( Param.Tint, vtint );
	}

	@Override
	public void rebind() {
		setParams( Param.Texture0, u_texture0 );
		setParams( Param.Time, time );
		setParams( Param.Resolution, resolution );

		vtint.set( tint.r, tint.g, tint.b );
		setParams( Param.Tint, vtint );
		endParams();
	}

	@Override
	protected void compute() {
		TextureWrap u = inputTexture.getUWrap();
		TextureWrap v = inputTexture.getVWrap();
		inputTexture.setWrap( TextureWrap.Repeat, TextureWrap.Repeat );
		// inputTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );

		inputTexture.bind( u_texture0 );

		program.begin();
		IFilter.quad.render( program );
		program.end();

		inputTexture.setWrap( u, v );
	}
}
