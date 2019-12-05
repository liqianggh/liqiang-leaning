package concurrent.tools.FutureTask;

import java.util.Vector;
import java.util.concurrent.*;

/**
 * @description TODO
 * @author Jann Lee
 * @date 2019-06-01 20:39
 **/
public class FetureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test2();
    }

    public static void test1() throws ExecutionException, InterruptedException {
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(3);
                return "hello" + Thread.currentThread().getName();
            }
        };
        FutureTask fetureTask = new FutureTask(task);
        fetureTask.run();
        System.out.println(fetureTask.get());
        System.out.println(fetureTask.isDone());
        System.out.println(fetureTask.get());
    }

    public static void test2() throws ExecutionException, InterruptedException {
        // 线程池容量设置
        int cpuCount = Runtime.getRuntime().availableProcessors();
        // io密性型应尽量设置多的线程数，因为并不一定总在执行任务，需要文件准备等工作  cpu*2；cpu密集型 cpu+1
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, cpuCount +1, 100, TimeUnit.SECONDS,new LinkedBlockingDeque<>(100));
        Callable<String> task = () -> {
            TimeUnit.SECONDS.sleep(3);
            return "hello" + Thread.currentThread().getName();
        };

        FutureTask fetureTask = new FutureTask(task);
        threadPoolExecutor.execute(fetureTask);
        System.out.println(fetureTask.get());
    }


}
