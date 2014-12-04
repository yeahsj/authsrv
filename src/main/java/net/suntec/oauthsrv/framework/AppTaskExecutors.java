package net.suntec.oauthsrv.framework;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.suntec.oauthsrv.framework.job.WritePathLogJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述: 定时JOB
 * @当前版本： 1.0
 * @创建时间: 2014-6-17 下午08:28:41
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class AppTaskExecutors {
	Logger logger = LoggerFactory.getLogger(getClass());
	// 定时job
	ScheduledThreadPoolExecutor taskExecutor = null;
	// 日志job
	ThreadPoolExecutor logThreadPoolExecutor = null;
	static AppTaskExecutors instance;
	static {
		instance = new AppTaskExecutors();
	}

	public static AppTaskExecutors getInstance() {
		return instance;
	}

	private AppTaskExecutors() {
		taskExecutor = new ScheduledThreadPoolExecutor(5);
		logThreadPoolExecutor = new ThreadPoolExecutor(5, 10, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public void shudown() {
		logger.info(" start shutdown ..... ");

		try {
			taskExecutor.shutdown();
			// Wait a while for existing tasks to terminate
			if (!taskExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
				taskExecutor.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!taskExecutor.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			taskExecutor.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		} catch (Exception ie) {
			ie.printStackTrace();
		}
		try {
			logThreadPoolExecutor.shutdown();
			// Wait a while for existing tasks to terminate
			if (!logThreadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
				logThreadPoolExecutor.shutdownNow(); // Cancel currently
				// executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!logThreadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			logThreadPoolExecutor.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		} catch (Exception ie) {
			ie.printStackTrace();
		}

		if (taskExecutor.isShutdown()) {
			logger.info("  taskExecutor shutdown success ..... ");
		}
		if (taskExecutor.isTerminated()) {
			logger.info("  taskExecutor Terminated success ..... ");
		}
		if (logThreadPoolExecutor.isShutdown()) {
			logger.info("  logThreadPoolExecutor shutdown success ..... ");
		}
		if (logThreadPoolExecutor.isTerminated()) {
			logger.info("  logThreadPoolExecutor Terminated success ..... ");
		}
		taskExecutor = null;
		logThreadPoolExecutor = null;
		logger.info("  shutdown success ..... ");
	}

	public void addTask(Runnable command, long initialDelay, long period, TimeUnit unit) {
		taskExecutor.scheduleAtFixedRate(command, initialDelay, period, unit);
	}

	public void addLogTask(WritePathLogJob job) {
		logThreadPoolExecutor.execute(job);
	}
}
