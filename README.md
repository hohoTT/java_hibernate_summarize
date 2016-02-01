# java_hibernate_summarize

有关java中hibernate的用法及其应用的总结，用于初学者的入门教程

第一个例子：使用hibernate创建数据库表，并添加数据的例子。

第二个例子：主要介绍hibernate中的session部分。

首先介绍一下Hibernate：

一个框架
一个Java领域的持久化框架
	狭义的理解：“持久化”仅仅指是把对象永久保存到数据库中
	广义的理解：“持久化”包括数据库相关的各种操作
		需要注意的是：加载--根据特定的OID，把一个对象从数据库加载到内存中
		为了在系统中能够找到所需的对象，需要为每一个对象分配一个唯一的标识号。
	   在关系数据库中称之为主键，而在对象术语中，则叫做对象标识（OID）
一个ORM框架 
	ORM--对象关系映射

	面向对象概念--面向关系概念
	类--表
	对象--表的行
	属性--表的列

	思想--把数据库的操作转换为对对象的操作

	元数据--描述数据的数据，主要用来描述对象-关系映射细节，
	元数据通常采用XML格式，并且存放在专门的对象-关系映射文件中
	
--------------------------------------------------------------------------------------------------------------------------------------

    这里需要特别留意注意的是第一个例子，这个例子主要是用来使用hibernate在保证有新建立的数据库条件下，创建数据库表并且向数据库表中添加数据。这里可能会出现的问题是，在 hibernate.cfg.xml 文件下注意 hibernate 所使用的方言
    即以下这条语句 ： <property name="dialect">org.hibernate.dialect.MySQLDialect</property>
    如果是 <property name="dialect">org.hibernate.dialect.MySQLInnoDBDialec</property> 的话可能会在创建数据库表的时候出现错误。
    这一点是特别需要注意的！！！
		
--------------------------------------------------------------------------------------------------------------------------------------

需要注意在第二个例子中的数据库的隔离级别的问题：

   相关的设置可以通过mysql的命令，同时也可以在 hibernate.cfg.xml 文件下进行对事务隔离级别的设置
设置为<property name="connection.isolation">2</property>，此时便修改为读已提交。这是在对refresh()的测试中便可以体现出refresh()的作用。

--------------------------------------------------------------------------------------------------------------------------------------

以下是对 hibernate 的映射文件的说明讲解：

  <hibernate-mapping></hibernate-mapping>标签中可以添加多个<class></class>标签，只需要在<hibernate-mapping></hibernate-mapping>标签中设置package=""，即包名，class为该包下的类即可。这样也省略了class 标签中的name一项，即统一了指定的相同包名。
  
  
