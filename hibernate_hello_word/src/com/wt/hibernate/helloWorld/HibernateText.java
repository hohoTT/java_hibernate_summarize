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

		// 1. ����һ�� SessionFactory ����
		SessionFactory sessionFactory = null;

		// 1>. ���� Configuration ���� : ��Ӧ Hibernate�Ļ���������Ϣ�Ͷ����ϵӳ����Ϣ
		Configuration configuration = new Configuration().configure();

		// 4.0 ֮ǰ���������� SessionFactory �����
		// sessionFactory = configuration.buildSessionFactory();

		// 2>. ����һ�� serviceRegistry ����: hibernate 4.x �����ӵĶ���
		// hibernate ���κ����úͷ�����Ҫ�ڸö�����ע��������Ч
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.buildServiceRegistry();

		// 3>.
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		// 2. ����һ�� Session ����
		Session session = sessionFactory.openSession();
		
		// 3. ��������
		Transaction transaction = session.beginTransaction();
		
		// 4. ִ�б������
		News news = new News("Java", "hohoTT", new Date(new java.util.Date().getTime()));
		session.save(news);
		
		// 5. �ύ����
		transaction.commit();

		// 6. �ر� Session
		session.close();
		
		// 7. �ر� SessionFactory ����
		sessionFactory.close();
		
	}

}