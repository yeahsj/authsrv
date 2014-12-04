package net.suntec.oauthsrv.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import junit.framework.TestCase;

/** 
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 
 * @当前版本： 1.0
 * @创建时间: 2014-9-11 下午02:33:37
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class TestIautoUserCacheMap extends TestCase {
	static Logger logger = Logger.getLogger(TestIautoUserCacheMap.class.getName());

	public static void main(String[] args) throws InterruptedException {
		testByExcuter();
	}

	public static void testByExcuter() throws InterruptedException {
		final IautoUserCacheMap instance = IautoUserCacheMap.getInstance();
		ExecutorService service = Executors.newFixedThreadPool(3);
		Collection<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		tasks.add(Executors.callable(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					instance.put("thread1_" + i + "", i + 1 + "");
				}
				logger.info("thread1 success ........... ");
			}
		}));
		tasks.add(Executors.callable(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					instance.put("thread2_" + i + "", i + 1 + "");
				}
				logger.info("thread2 success ........... ");
			}
		}));
		tasks.add(Executors.callable(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					// System.out.println("thread3 = " + i);
					instance.put("thread3_" + i + "", i + 1 + "");
				}
				logger.info("thread3 success ........... ");
			}
		}));

		List<Future<Object>> t = service.invokeAll(tasks);
		for (Future<Object> future : t) {
			if (future.isDone()) {
				System.out.println(future.getClass() + " isDone success ........ ");
			} else if (future.isCancelled()) {
				System.out.println(future.getClass() + " isCancelled ........ ");
			}
		}
		service.shutdown();
		if (service.isShutdown()) {
			System.out.println("Shutdown success ........ ");
		}
		if (service.isTerminated()) {
			System.out.println("Terminated success ........ ");
		}
		// service.awaitTermination(100, TimeUnit.SECONDS);
		HashMap<String, String> map = instance.IAUTO_USER_MAP;
		System.out.println(map.size());

		service = null;
		instance.IAUTO_USER_MAP.clear();
//		instance.IAUTO_USER_MAP = null;
	}

	public static void testByExcuter2() throws InterruptedException {
		final IautoUserCacheMap instance = IautoUserCacheMap.getInstance();
		ExecutorService service = Executors.newFixedThreadPool(1);
		Future<?> future1 = service.submit(new Runnable() {
			@Override
			public void run() {
				logger.info("thread1 start ........... ");
				for (int i = 0; i < 1000; i++) {
					instance.put("thread1_" + i + "", i + 1 + "");
				}
				logger.info("thread1 success ........... ");
			}
		});
		Future<?> future2 = service.submit(new Runnable() {
			@Override
			public void run() {
				logger.info("thread2 start ........... ");
				for (int i = 0; i < 1000; i++) {
					instance.put("thread2_" + i + "", i + 1 + "");
				}
				logger.info("thread2 success ........... ");
			}
		});
		Future<?> future3 = service.submit(new Runnable() {
			@Override
			public void run() {
				logger.info("thread3 start ........... ");
				for (int i = 0; i < 1000; i++) {
					// logger.info("thread3 = " + i);
					instance.put("thread3_" + i + "", i + 1 + "");
				}
				logger.info("thread3 success ........... ");
			}
		});

		while (true) {
			//相当于Thread.join
			if (future1.isDone() && future2.isDone() && future3.isDone()) {
				service.shutdown();
				break;
			}
		}

		if (service.isShutdown()) {
			logger.info("Shutdown success ........ ");
		}
		if (service.isTerminated()) {
			logger.info("Terminated success ........ ");
		}
		// service.awaitTermination(100, TimeUnit.SECONDS);
		HashMap<String, String> map = instance.IAUTO_USER_MAP;
		logger.info(map.size() + "");

		service = null;
		instance.IAUTO_USER_MAP.clear();
//		instance.IAUTO_USER_MAP = null;
	}

	public void testByThread() throws InterruptedException {
		final IautoUserCacheMap instance = IautoUserCacheMap.getInstance();
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					// logger.info("thread1 = " + i);
					instance.put("thread1_" + i + "", i + 1 + "");
				}
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					// logger.info("thread2 = " + i);
					instance.put("thread2_" + i + "", i + 1 + "");
				}
			}
		});

		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					// logger.info("thread3 = " + i);
					instance.put("thread3_" + i + "", i + 1 + "");
				}
			}
		});

		thread1.start();
		thread2.start();
		thread3.start();

		thread1.join();
		thread2.join();
		thread3.join();

		HashMap<String, String> map = instance.IAUTO_USER_MAP;
		logger.info(map.size() + "");

		instance.IAUTO_USER_MAP.clear();
		instance.IAUTO_USER_MAP = null;
		thread1 = null;
		thread2 = null;
		thread3 = null;
		// Set<String> keys = map.keySet();
		// for (String key : keys) {
		// logger.info("result: " + key + " = " + map.get(key));
		// }
	}
}
