package com.wt.hibernate.entity;

import static org.junit.Assert.*;

import java.util.Date;

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
	 * 
	 */
	@Test
	public void testSave(){
		News news = new News();
		news.setTitle("AA");
		news.setAuthor("aa");
		news.setDate(new Date());
		
		System.out.println(news);
		
		session.save(news);
		
		System.out.println(news);
	}
	
	
	/**
	 *  Clear(): 清理缓存
	 */
	@Test
	public void testClear() {
		News news1 = (News) session.get(News.class, 1);
		
		session.clear();
		
		News news2 = (News) session.get(News.class, 1);
	}
	
	/**
	 *  refresh(): 会强制发送 select 语句， 以使 Session 缓存中对象的状态和数据表中对应的记录保持一致；
	 *  
	 */
	@Test
	public void testRefresh() {
		News news = (News) session.get(News.class, 1);
		System.out.println(news);
		
		session.refresh(news);
		System.out.println(news);
		
	}
	
	/**
	 * flush: 使数据表中的记录和 Session 缓存中的对象的状态保持一致，为了保持一致，
	 * 则可能会发送对应的 SQL 语句,
	 * 1. 调用 Transaction 的 commit() 方法中：先调用 session 的flush方法，在提交事务
	 * 2. flush 方法可能会发送 SQL 语句，不会提交事务
	 * 3. 注意: 在未提交事务或显式的调用 session.flush() 方法之前也有可能会进行 flush() 操作 
	 *  3.1> 执行 HQL 或 QBC 查询，会先进行 flush() 操作， 以得到数据表的最新的记录
	 *  3.2> 若记录的 ID 是由数据库使用自增的方式生成的，则在调用 save() 方法后，就会立即发送 insert 语句，
	 *  因为 save 方法后必须保证对象的 ID 是存在的
	 *  
	 */
	
	@Test
	public void testSessionFlush2() {
		News news = new News("HTML", "haha", new Date());
		session.save(news);
	}
	
	@Test
	public void testSessionFlush() {
		News news = (News) session.get(News.class, 1);
		news.setAuthor("hohott");
		
//		session.flush();
//		System.out.println("flush");
		
		News news2 = (News) session.createCriteria(News.class).uniqueResult();
		System.out.println(news2);
		
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
