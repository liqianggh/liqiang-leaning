package umltest.test04aggregation;

/**
 * @author Jann Lee
 * @date 2019-07-21 11:03
 **/
public class Computer {

    /**
     * 显示器可以与computer分离
     */
    private Monitor monitor;

    /**
     * 鼠标可以与computer分离
     */
    private Mouse mouse;

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }
}
