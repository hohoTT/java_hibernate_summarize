package com.wt.hibernate.helloWorld;

import static org.junit.Assert.*;

import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;

public class HibernateText {

	@Test
	public void test() {

		// 1. 创建一个 SessionFactory 对象
		SessionFactory sessionFactory = null;

		// 1.1>. 创建 Configuration 对象 : 对应 Hibernate的基本配置信息和对象关系映射信息
		Configuration configuration = new Configuration().configure();

		// 4.0 之前是这样创建 SessionFactory 对象的
		// sessionFactory = configuration.buildSessionFactory();

		// 1.2>. 创建一个 serviceRegistry 对象: hibernate 4.x 新添加的对象
		// hibernate 的任何配置和服务都需要在该对象中注册后才能有效
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();

		// 1.3>.
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		// 2. 创建一个 Session 对象
		// Session 是应用程序与数据库之间交互操作的一个单线程对象，是 Hibernate 运作的中心
		Session session = sessionFactory.openSession();
		
		// 3. 开启事务
		Transaction transaction = session.beginTransaction();
		
		// 4. 执行保存操作
		News news = new News("Mysql", "aa", new Date(new java.util.Date().getTime()));
		session.save(news);
	
		
		// 查询语句
//		News news2 = (News) session.get(News.class, 4);
//		System.out.println(news2);
//		session.delete(news2);
		
		
//		session.delete(news2);
		
		// 5. 提交事务
		transaction.commit();

		// 6. 关闭 Session
		session.close();
		
		// 7. 关闭 SessionFactory 对象
		sessionFactory.close();
		
	}

}
