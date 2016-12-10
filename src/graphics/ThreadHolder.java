package graphics;

import java.util.ArrayList;
import java.util.List;

public class ThreadHolder {

	private static final ThreadHolder instance = new ThreadHolder();
	
	private List<Thread> threads = new ArrayList<Thread>();

	public static ThreadHolder getInstance() {
		return instance;
	}
	
	public List<Thread> getThreads() {
		return threads;
	}
}
