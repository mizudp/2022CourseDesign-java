package team.skadi.rental.ui;

import java.util.HashMap;

public class LockLogin implements Runnable {

	private HashMap<String, Integer> loinTimes;
	private String lockAccount;
	private int lockTime = 30;
	private StatusListener l;

	public LockLogin(StatusListener l) {
		this.l = l;
	}

	public boolean isAccountLock(String account) {
		return lockAccount != null && lockAccount.equals(account);
	}

	public void addCount(String account) {
		Integer integer = loinTimes.get(account);
		Integer times = loinTimes.put(account, integer == null ? 1 : integer + 1);
		if (times >= 5) {
			lockAccount = account;
			start();
		}
	}

	private void start() {
		new Thread(this).start();
		l.lock(lockAccount);
	}

	private void finish() {
		l.unlock(lockAccount);
		lockAccount = null;
	}

	@Override
	public void run() {
		while (lockTime > 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		finish();
	}

	public static interface StatusListener {
		void lock(String account);

		void unlock(String account);

		void update(int time);
	}
}
