package cn.mycookies.bytecode.asm;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;


/**
 * 要被字节码增强 的测试类
 *
 * @author Jann Lee
 * @date 2020-06-11 5:46 下午
 **/
public class BaseHandler implements Runnable{
    Logger logger = LoggerFactory.getLogger(BaseHandler.class);
    // todo 行数   item，tag 告警人；
    public void handle() throws InterruptedException {
        while(true) {
            preHandle();
            try {
                System.out.println("-_--_--_--_--_- hello asm...... -_--_--_--_-");
                if (Math.random() > 0.5D) {
                    throw new RuntimeException();
                } else {
                    throw new Exception();
                }
            } catch (RuntimeException e) {
                logger.error("=========RuntimeException==========");
            } catch (Exception e)
            {
                logger.error("=========Exception==========");
            }
            Thread.sleep(3000);
            System.out.println("方法执行结束");
        }
    }

    public int preHandle() {
         try {
            System.out.println("-_--_--_--_--_- preHandle...... -_--_--_--_-");
            return 1;
        } catch (RuntimeException e) {
            logger.error("=========RuntimeException==========");
        }
        return 0;
    }

    public static <T extends Throwable> void passException(T e, String methodName, int count, String tag) {
        System.out.println(methodName + " 发生了异常， 异常所属tag： " + tag +"， 累加值：" + count);
    }

    @Override
    public void run() {
        try {
            handle();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
