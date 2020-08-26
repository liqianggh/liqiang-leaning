基准测试工具-JMH（Java Micro-benchmark Harness）

if和switch那个快？ArrayList和LinkedList不同场景下的性能差异多少？各种序列化方式的性能差异有多大？
做了性能优化，到底性能提高了多少？JMH对优化的结果进行量化！

## 原理化
    1.通过一些功能来规避由 JVM 中的 JIT 或者其他优化对性能测试造成的影响。
    
## 注解说明
@Threads(n)：每个fork进程使用多少个线程区执行测试方法，默认是 Runtime.getRuntime().availableProcessors()
@Fork(n): 开启N个进程

## 结果可视分析

JMH可以在控制台打印出结果，同时会将结果输出到文件中。除此以外，如果你想将测试结果以图表的形式可视化，可以试下这些网站：
JMH Visual Chart：http://deepoove.com/jmh-visual-chart/
JMH Visualizer：https://jmh.morethan.io/
比如将上面测试例子结果的 json 文件导入，就可以实现可视化：