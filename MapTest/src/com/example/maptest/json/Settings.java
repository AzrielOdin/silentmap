package com.example.maptest.json;

public class Settings {
	private boolean silent;
	private boolean vibrate;

	public Settings(boolean silent, boolean vibrate) {
		super();
		this.silent = silent;
		this.vibrate = vibrate;
	}

	public Settings(int silent, int vibrate) {
		super();

		this.silent = (silent == 1);
		this.vibrate = (vibrate == 1);
	}

	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	public boolean isVibrate() {
		return vibrate;
	}

	public void setVibrate(boolean vibrate) {
		this.vibrate = vibrate;
	}
}
