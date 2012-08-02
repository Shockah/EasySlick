package pl.shockah.audio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;

public class MIDI extends Audio implements ControllerEventListener {
	protected static int[] controllers;
	
	static {
		controllers = new int[128];
		for (int i = 0; i < controllers.length; i++) controllers[i] = i;
	}
	
	protected Sequencer sequencer;
	protected boolean paused = false, looping = false;
	protected float factor = 1;
	
	public MIDI(File file) throws MidiUnavailableException,InvalidMidiDataException,IOException {
		sequencer = MidiSystem.getSequencer();
		sequencer.open();
		sequencer.setSequence(MidiSystem.getSequence(file));
		sequencer.addControllerEventListener(this,controllers);
	}
	public MIDI(URL url) throws MidiUnavailableException,InvalidMidiDataException,IOException {
		sequencer = MidiSystem.getSequencer();
		sequencer.open();
		sequencer.setSequence(MidiSystem.getSequence(url));
		sequencer.addControllerEventListener(this,controllers);
	}
	public MIDI(InputStream is) throws MidiUnavailableException,InvalidMidiDataException,IOException {
		sequencer = MidiSystem.getSequencer();
		sequencer.open();
		sequencer.setSequence(MidiSystem.getSequence(is));
		sequencer.addControllerEventListener(this,controllers);
	}
	
	public void play() {play(false);}
	public void loop() {play(true);}
	public void play(boolean loop) {
		if (!isPaused()) stop();
		looping = loop;
		paused = false;
		sequencer.setLoopCount(looping ? Sequencer.LOOP_CONTINUOUSLY : 0);
		sequencer.start();
	}
	public void pause() {
		paused = true;
		sequencer.stop();
	}
	public void stop() {
		paused = false;
		looping = false;
		sequencer.stop();
		sequencer.setTickPosition(0);
	}
	public boolean isPlaying() {
		return sequencer.isRunning();
	}
	public boolean isPaused() {
		return paused;
	}
	public boolean isStopped() {
		return !isPaused() && !isPlaying();
	}
	public boolean isLooping() {
		return looping;
	}
	
	public float getTempoFactor() {
		return factor;
	}
	public void setTempoFactor(float factor) {
		this.factor = factor;
		sequencer.setTempoFactor(factor);
	}
	
	public void controlChange(ShortMessage shortMsg) {
		if (shortMsg.getCommand() == ShortMessage.START) paused = false;
	}
}