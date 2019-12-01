import synchronizedtest.SynchronizedTest;

/**
 * 管程发
 * @author Jann Lee
 * @className Test2
 * @description TODO
 * @date 2018-12-24 23:28
 **/
public class Test2 {


    public static void main(String [] args){
        SynContainer container = new SynContainer();

        new Productor(container).start();

        new Consumer(container).start();


    }

    static class Productor extends Thread{
        SynContainer container;

        public Productor(SynContainer container) {
            this.container = container;
        }

        @Override
        public void run() {
           for(int i = 0; i < 100; i++ ){
               System.out.println("生产--->" + i + "个馒头");
               try {
                   container.push(new Steamedbun(i));
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    static class Consumer extends Thread{
        SynContainer container;

        public Consumer(SynContainer container) {
            this.container = container;
        }

        @Override
        public void run() {
            for(int i = 0; i < 100 ; i++ ){
                try {
                    System.out.println("消费-->" +container.pop().getId()+ "馒头");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
         }
    }

    static class SynContainer {
        Steamedbun [] buns = new Steamedbun[10];
        int count = 0;
        public void push(Steamedbun bun) throws InterruptedException {
            if (count == buns.length) {
                this.wait();
            }

            buns[count++] = bun;
            this.notify();
        }

        public Steamedbun pop() throws InterruptedException {
            if (count == 0) {
                this.wait();
            }
            Steamedbun result =  buns[--count];
            this.notifyAll();
            return result;
        }

    }

    static class Steamedbun {
        private int id;

        public Steamedbun(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Steamedbun{" + "id=" + id + '}';
        }
    }
}
