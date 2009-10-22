package se.opendataexchange.ethernetip4j;

import java.util.LinkedList;

public class ThreadPool {
	private final PoolWorker[] threads;

	private final LinkedList<Runnable> queue;

	public ThreadPool(int nThreads) {
		queue = new LinkedList<Runnable>();
		threads = new PoolWorker[nThreads];
		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public void execute(Runnable r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	private class PoolWorker extends Thread {
		public void run() {
			Runnable r;
			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}
					r = (Runnable) queue.removeFirst();
				}
				try {
					r.run();
				} catch (RuntimeException e) {
				}
			}
		}
	}
}
