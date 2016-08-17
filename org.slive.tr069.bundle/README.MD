简介 
-----
针对tr069协议的客户端简单实现，可打包成OSGI 插件方式。

流程
-----
1、初始化tr069方法和解析器关系，见Activator.java
2、tcp传送的数据经过ACSServlet.java解析后进入ResponseParse.java,ACSConfirmMethod.java 的解析方法进行解析，确认是将要执行tr069对应的方法；

实例
---------
具体的业务解析需要继承DefaultResponseHandleAdpter.java，实现相应业务流程。
参考ACSServlet.java和InformHandleExample实现；

运行
-------
可打包成bundle在可在OSGI容器中运行，并且需在web容器支持，可在OSGI中嵌入jetty实现。
