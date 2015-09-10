package com.wcecil.rts.lifecycle;

import org.springframework.context.SmartLifecycle;

public abstract class BasicLifecycle implements SmartLifecycle {
	boolean running = false;

	@Override
	public void start() {
		running = true;
	}

	@Override
	public void stop() {
		running = false;
	}

	@Override
	public void stop(Runnable callback) {
		stop();
		callback.run();
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}
}
