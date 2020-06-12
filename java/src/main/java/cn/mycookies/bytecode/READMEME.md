操作字节码相关测试

目标： 要将所有类中catch代码块中的异常进行统计

1. ASM:  指令层操作字节码 ｜ 加载前修改
查看字节码指令的相关IDEA插件：ASM Bytecode Outline

2. Javassist: 源码层操作字节码 ｜ 加载前修改

3. Instrument: 修改已加载的类聚