package com.wt.hibernate.entity;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
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
		// ������
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
		// ������
		// System.out.println("destroy");

		transaction.commit();
		session.close();
		sessionFactory.close();
	}
	
	@Test
	public void testComponent() {
		Worker worker = new Worker();
		Pay pay = new Pay();
		
		pay.setMonthlyPay(1000);
		pay.setYearPay(80000);
		pay.setVocationWithPay(5);
		
		worker.setName("ABCD");
		worker.setPay(pay);
		
		session.save(worker);
	}

	@Test
	public void testBlob() throws Exception {
//		News news = new News();
//		news.setAuthor("cc");
//		news.setContent("CONTENT");
//		news.setDate(new Date());
//		news.setDesc("DESC");
//		news.setTitle("CC");
//
//		InputStream stream = new FileInputStream("img.jpg");
//		Blob image = Hibernate.getLobCreator(session)
//							  .createBlob(stream, stream.available());
//		news.setImage(image);
//		
//		session.save(news);

		News news = (News) session.get(News.class, 1);
		Blob image = news.getImage();

		InputStream in = image.getBinaryStream();
		System.out.println(in.available());
	}

	@Test
	public void testPropertyUpdate() {
		News news = (News) session.get(News.class, 1);
		news.setTitle("aaaaaa");

		System.out.println(news.getDesc());
	}

	@Test
	public void testIdGenerator() throws InterruptedException {
		News news = new News("AA", "aa", new Date());

		session.save(news);

	}

	@Test
	public void testDynamicUpdate() {
		News news = (News) session.get(News.class, 3);
		news.setAuthor("hoho");

	}

	@Test
	public void testDoWork() {
		session.doWork(new Work() {

			@Override
			public void execute(Connection connection) throws SQLException {
				// TODO Auto-generated method stub
				System.out.println(connection);

				// ���ô洢���̣�
			}
		});

	}

	/**
	 * exict : �� session �����а�ָ���ĳ־û������Ƴ�
	 * 
	 */
	@Test
	public void testEvict() {
		News news3 = (News) session.get(News.class, 3);
		News news4 = (News) session.get(News.class, 4);

		news3.setTitle("QQ");
		news4.setTitle("HH");

		session.evict(news3);
	}

	/**
	 * Delete: ִ��ɾ��������ֻҪ OID �����ݱ�׼�е�һ����¼��Ӧ���ͻ�ִ�� delete ���� �� OID
	 * �����ݱ���û�����Ӧ�Ķ������׳��쳣
	 * 
	 * ����ͨ������ hibernate �����ļ� hibernate.use_identifier_rollback Ϊtrue�� ʹɾ������󣬰���
	 * OID ��Ϊ null
	 */
	@Test
	public void testDelete() {
		// �������
		// News news = new News();
		// news.setId(1);

		// �־û�����
		News news = (News) session.get(News.class, 2);

		session.delete(news);

		System.out.println(news);
	}

	/**
	 * ע�⣺ 1. �� OID ��Ϊnull�� �����ݱ��л�û�к����Ӧ�ļ�¼�����׳�һ���쳣 2. �˽� OID ��ֵ���� unsave-value
	 * ����ֵ�Ķ���Ҳ����Ϊ��һ���������
	 * 
	 */
	@Test
	public void testSaveOrUpdate() {
		News news = new News("FF", "ff", new Date());
		news.setId(1);
		session.saveOrUpdate(news);
	}

	/**
	 * update: 1. ������һ���־û����󣬲���Ҫ��ʾ���õ� update ������ ��Ϊ �ڵ��� Transaction ��commit()
	 * ����ʱ������ִ�� session �� flush ������ 2. ����һ�����������Ҫ��ʽ�ĵ��� session �� update ������
	 * ���԰�һ���������Ϊ�־û�����
	 * 
	 * ��Ҫע��ģ� 1. ����Ҫ���µ������������ݱ�ļ�¼�Ƿ񣬶��ᷢ�� update ��� ������� update ��������äĿ�ķ��� update
	 * ����أ� ������� �� ��.hbm.xml �ļ���class �ڵ������е� select-before-update="true"
	 * (Ĭ��Ϊfalse), ��ͨ������Ҫ���ø����ԡ�
	 * 
	 * 2. �����ݱ���û�ж�Ӧ�ļ�¼������������ update ���������׳��쳣
	 * 
	 * 3. �� update ����һ���������ʱ�� ����� session �Ļ������Ѿ�������ͬ�� OID �ĳ־ö��󣬻��׳��쳣 ��Ϊ��
	 * session�����в��������� OID ��ͬ�Ķ���
	 */
	@Test
	public void testUpdate() {
		News news = (News) session.get(News.class, 1);

		transaction.commit();
		session.close();

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		// news.setAuthor("dddddd");

		News news2 = (News) session.get(News.class, 1);

		session.update(news);
	}

	/**
	 * get & load : 1. ִ�� get �������������ض��� ִ�� load ����������ʹ�øö����򲻻�����ִ�в�ѯ������������һ���������
	 * 
	 * get ������������load ���ӳټ���
	 * 
	 * 2. load ���ܻ��׳� LazyInitializationException �쳣�� ����Ҫ��ʼ��ʱ��������Ѿ��ر� ��session
	 * 
	 * 3. �����ݿ����û����Ӧ�ļ�¼�� get ���� null load �׳��쳣
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
	 * persist Ҳ��ִ�� insert ����
	 * 
	 * �� save ������: ��persist ����֮ǰ�����Ѿ��� id �ˣ� �򲻻�ִ�� insert ���������׳��쳣
	 */
	@Test
	public void testPersist() {
		News news = new News();
		news.setTitle("DD");
		news.setAuthor("dd");
		news.setDate(new Date());

		session.persist(news);
	}

	/**
	 * 1. save() ���� 1.1> ʹһ����ʱ�����Ϊ�־û����� 1.2> Ϊ������� ID 1.3> ��flush ����ʱ�ᷢ��һ��
	 * insert ��� 1.4> �� save() ����֮ǰ�� ID ����Ч�� 1.5> �־û������ ID �ǲ��ܱ��޸ĵģ�
	 */
	@Test
	public void testSave() {
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
	 * Clear(): ������
	 */
	@Test
	public void testClear() {
		News news1 = (News) session.get(News.class, 1);

		session.clear();

		News news2 = (News) session.get(News.class, 1);
	}

	/**
	 * refresh(): ��ǿ�Ʒ��� select ��䣬 ��ʹ Session �����ж����״̬�����ݱ��ж�Ӧ�ļ�¼����һ�£�
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
	 * flush: ʹ���ݱ��еļ�¼�� Session �����еĶ����״̬����һ�£�Ϊ�˱���һ�£� ����ܻᷢ�Ͷ�Ӧ�� SQL ���, 1. ����
	 * Transaction �� commit() �����У��ȵ��� session ��flush���������ύ���� 2. flush �������ܻᷢ��
	 * SQL ��䣬�����ύ���� 3. ע��: ��δ�ύ�������ʽ�ĵ��� session.flush() ����֮ǰҲ�п��ܻ���� flush() ����
	 * 3.1> ִ�� HQL �� QBC ��ѯ�����Ƚ��� flush() ������ �Եõ����ݱ�����µļ�¼ 3.2> ����¼�� ID
	 * �������ݿ�ʹ�������ķ�ʽ���ɵģ����ڵ��� save() �����󣬾ͻ��������� insert ��䣬 ��Ϊ save ��������뱣֤����� ID
	 * �Ǵ��ڵ�
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

		// session.flush();
		// System.out.println("flush");

		News news2 = (News) session.createCriteria(News.class).uniqueResult();
		System.out.println(news2);

	}

	@Test
	public void test() {
		// ������
		// System.out.println("test");

		News news = (News) session.get(News.class, 1);
		System.out.println(news);

		News news2 = (News) session.get(News.class, 1);
		System.out.println(news2);

	}

}
