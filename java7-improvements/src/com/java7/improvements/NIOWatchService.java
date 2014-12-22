package com.java7.improvements;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


/**
 * @see http://www.thecoderscorner.com/team-blog/java-and-jvm/java-nio/36-watching-files-in-java-7-with-watchservice#.VJgDiAT1I
 * @author Dave 
 *
 */
public class NIOWatchService {

	/** change this as appropriate for your file system structure. */
	public static final String DIRECTORY_TO_WATCH = "C:\\WORKSPACE";

	public static void main(String[] args) throws Exception {
		// get the directory we want to watch, using the Paths singleton class
		Path toWatch = Paths.get(DIRECTORY_TO_WATCH);
		if (toWatch == null) {
			throw new UnsupportedOperationException("Directory not found");
		}

		// make a new watch service that we can register interest in
		// directories and files with.
		WatchService myWatcher = toWatch.getFileSystem().newWatchService();

		// start the file watcher thread below
		MyWatchQueueReader fileWatcher = new MyWatchQueueReader(myWatcher);
		Thread th = new Thread(fileWatcher, "FileWatcher");
		th.start();

		// register a file
		toWatch.register(myWatcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
		th.join();
	}

	/**
	 * This Runnable is used to constantly attempt to take from the watch queue,
	 * and will receive all events that are registered with the fileWatcher it
	 * is associated. In this sample for simplicity we just output the kind of
	 * event and name of the file affected to standard out.
	 */
	private static class MyWatchQueueReader implements Runnable {

		/** the watchService that is passed in from above */
		private WatchService myWatcher;

		public MyWatchQueueReader(WatchService myWatcher) {
			this.myWatcher = myWatcher;
		}

		/**
		 * In order to implement a file watcher, we loop forever ensuring
		 * requesting to take the next item from the file watchers queue.
		 */
		@Override
		public void run() {
			try {
				// get the first event before looping
				WatchKey key = myWatcher.take();
				while (key != null) {
					// we have a polled event, now we traverse it and
					// receive all the states from it
					for (WatchEvent event : key.pollEvents()) {
						System.out.printf("Received %s event for file: %s\n", event.kind(), event.context());
					}
					key.reset();
					key = myWatcher.take();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Stopping thread");
		}
	}

}
