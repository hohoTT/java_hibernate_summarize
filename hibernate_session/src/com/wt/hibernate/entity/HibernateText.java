package com.wt.hibernate.entity;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HibernateText {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		// 测试用
		// System.out.println("init");

		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		session = sessionFactory.openSession();
		
		transaction = session.beginTransaction();
	}

	@After
	public void destroy() {
		// 测试用
		// System.out.println("destroy");
	
		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	/**
	 * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致，为了保持一致，
	 * 则可能会发送对应的 SQL 语句,
	 * 1. 调用 Transaction 的 commit() 方法中：先调用 session 的flush方法，在提交事务
	 * 2. flush 方法可能会发送 SQL 语句，不会提交事务
	 */
	
	@Test
	public void testSessionFlush() {
		News news = (News) session.get(News.class, 1);
		news.setAuthor("hoho");
		
		session.flush();
		System.out.println("flush");
	}

	@Test
	public void test() {
		// 测试用
		// System.out.println("test");
		
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
		
		News news2 = (News) session.get(News.class, 1);
		System.out.println(news2);
		
	}

}
