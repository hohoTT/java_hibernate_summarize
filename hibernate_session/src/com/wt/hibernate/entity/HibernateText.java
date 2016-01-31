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
	 * update:
	 * 1. 若更新一个持久化对象，不需要显示调用的 update 方法，
	 * 因为 在调用 Transaction 的commit() 方法时，会先执行 session 的 flush 方法：
	 * 2. 更新一个游离对象，需要显式的调用 session 的 update 方法， 
	 * 可以把一个游离对象化为持久化对象
	 * 
	 * 需要注意的：
	 * 1. 无论要更新的游离对象和数据表的记录是否，都会发送 update 语句
	 */
	@Test
	public void testUpadte() {
		News news = (News) session.get(News.class, 1);
		
		transaction.commit();
		session.close();
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
//		news.setAuthor("dddddd");
		
		session.update(news);
	}
	
	/**
	 * get & load :
	 * 1. 执行 get 方法会立即加载对象
	 * 	      执行 load 方法，若不使用该对象，则不会立即执行查询操作，而返回一个代理对象
	 * 
	 * 	  get 是立即检索，load 是延迟检索
	 * 
	 * 2. load 可能会抛出  LazyInitializationException 异常：
	 *    在需要初始化时代理对象已经关闭 了session
	 * 
	 * 3. 若数据库表中没有相应的记录，
	 * 	  get 返回 null
	 * 	  load 抛出异常
	 * 
	 */
	@Test
	public void testLoad() {
		News news = (News) session.load(News.class, 1);
		System.out.println(news.getClass().getName());
		
		session.close();
		
		System.out.println(news);
	}
	
	@Test
	public void testGet() {
		News news = (News) session.get(News.class, 1);
		
		session.close();
		
		System.out.println(news);
	}
	
	/**
	 * persist 也会执行 insert 操作
	 * 
	 * 和 save 的区别: 在persist 方法之前对象已经有 id 了， 则不会执行 insert 操作，会抛出异常
	 */
	@Test
	public void testPersist	() {
		News news = new News();
		news.setTitle("DD");
		news.setAuthor("dd");
		news.setDate(new Date());
		
		session.persist(news);
	}
	
	
	/**
	 * 1. save() 方法
	 *  1.1> 使一个临时对象变为持久化对象
	 *  1.2> 为对象分配 ID
	 *  1.3> 在flush 缓存时会发送一条 insert 语句
	 *  1.4> 在 save() 方法之前的 ID 是无效的
	 *  1.5> 持久化对象的 ID 是不能被修改的！
	 */
	@Test
	public void testSave(){
		News news = new News();
		news.setTitle("CC");
		news.setAuthor("cc");
		news.setDate(new Date());
		news.setId(666);
		
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
