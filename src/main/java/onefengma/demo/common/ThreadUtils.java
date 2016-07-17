package onefengma.demo.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chufengma on 16/7/17.
 */
public class ThreadUtils {

    private static ThreadUtils threadUtils;

    private ExecutorService executorService;

    public static ThreadUtils instance() {
        if (threadUtils == null) {
            threadUtils = new ThreadUtils();
        }
        return threadUtils;
    }

    public ThreadUtils() {
        executorService = Executors.newCachedThreadPool();
    }

    public void post(Runnable runnable) {
        executorService.execute(runnable);
    }

}
