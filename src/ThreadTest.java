import java.util.concurrent.Callable;

/**
 * @author Jann Lee
 * @className Thread
 * @description TODO
 * @date 2018-12-22 15:44
 **/
public class ThreadTest {
    private static int count = 10;
    public static void main(String [] args) throws Exception {
        SayHello hello = new SayHello();
        for(int i = 0; i < 2 ; i++ ){
            Thread thread = new Thread(hello,"线程:"+i);
            thread.start();
        }

        for(int i = 0; i < 10 ; i++ ){
            Thread thread =new Thread(){
                @Override
                public void run() {
                    while (count > 0) {
                        System.out.println(Thread.currentThread().getName()+" hello world: " +count--);
                    }
                }
            };
            thread.start();
            Thread.sleep(12321);
        }

         Callable call = new  Callable(){

             @Override
             public Object call() throws Exception {
                 return null;
             }
         };

        call.call();
    }

    static class SayHello implements Runnable{

        int i = 10;
        @Override
        public void run() {
            while (i > 0) {
                System.out.println(Thread.currentThread().getName()+" hello world: " +i--);
            }
        }
    }


}
