package pl.shockah.audio;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

public abstract class Audio {
	public abstract void play() throws LineUnavailableException, IOException;
	public abstract void loop() throws LineUnavailableException, IOException;
	public abstract void pause();
	public abstract void stop();
	public abstract boolean isPlaying();
	public abstract boolean isPaused();
	public abstract boolean isStopped();
	public abstract boolean isLooping();
}