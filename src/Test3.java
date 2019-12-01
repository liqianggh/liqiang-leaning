/**
 * @author Jann Lee
 * @className Test3
 * @description 信号灯法
 * @date 2018-12-24 23:48
 **/
public class Test3 {
    public static void main(String [] args){
        Tv tv = new Tv();
        new Watcher(tv).start();
        new Player(tv).start();
    }
    static class Watcher extends  Thread{
        Tv tv;

        public Watcher(Tv tv) {
            this.tv = tv;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10 ; i++ ){
                if(i%2==0) {
                    this.tv.play("hello");
                } else {
                    this.tv.play("world");
                }
            }
        }
    }

    static class Player extends Thread{
        Tv tv;

        public Player(Tv tv) {
            this.tv = tv;
        }

        @Override
        public void run() {
            for(int i = 0; i < 10 ; i++ ){
                tv.watch();
            }
        }
    }

    static class Tv {
        String vice;

        boolean flag;

        public Tv() {
         }

        public Tv(String vice, boolean flag) {
            this.vice = vice;
            this.flag = flag;
        }

        public  void play(String vice) {
            if (flag) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.vice = vice;
            System.out.println("表演了"+vice);
            this.notifyAll();
            this.flag = !this.flag;
        }

        public  void watch(){
            if (!flag) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("听到了"+vice);
            this.notifyAll();
            this.flag = !this.flag;
        }
    }

}
