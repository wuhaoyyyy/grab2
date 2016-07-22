package com.purang.grab.util;

public class DistributeUniqueId {

	private static int seq = 0;
	private static long lastFetchTime;
	private static long index = 99;

	public static final String INDEX_KEY = "uniqueId.index.key";

	private synchronized static long getSequence() {
		long now = System.currentTimeMillis();
		if (now - lastFetchTime >= 1000) {
			seq = 0;
		}
		lastFetchTime = now;
		return seq++;
	}

	public static long getIndex() {
		return index;
	}

	public static long getValue() {
		long time = System.currentTimeMillis() / 1000;
		long result = (time << 32) | (getSequence() << 13) | index;
		return result;
	}

}
