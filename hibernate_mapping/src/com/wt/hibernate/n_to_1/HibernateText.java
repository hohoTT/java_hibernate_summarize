package com.wt.hibernate.n_to_1;

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
	
//	@Test
//	public void testDelete() {
//		// �ڲ��趨������ϵ������£��� 1 ��һ�˵Ķ���  �� n �����������ã�����ֱ��ɾ�� 1 ��һ�˵Ķ��� 
//		Customer customer = (Customer) session.get(Customer.class, 1);
//		session.delete(customer);
//	}
//	
//	@Test
//	public void testUpdate() {
//		Order order = (Order) session.get(Order.class, 1);
//		order.getCustomer().setCustomerName("AAA");
//	}
//	
//	@Test
//	public void testManyToOneGet() {
//		// 1. ����ѯ���һ�˵�һ��������Ĭ������£�ֻ��ѯ�˶��һ�˵Ķ���
//		// ��û�в�ѯ�Ĺ�����һ��һ�˵Ķ�����������ӳټ���
//		Order order = (Order) session.get(Order.class, 1);
//		System.out.println(order.getOrderName());
//		
//		// 2. ����Ҫʹ�õ������Ķ���ʱ���ŷ��Ͷ�Ӧ�� SQL ���
//		Customer customer = order.getCustomer();
//		System.out.println(customer.getCustomerName());
//		
//		
//		// 3. �ڲ�ѯ Customer ����ʱ���ɶ��һ�˵�����һ��һ��ʱ��
//		// ���ܻᷢ�� LazyInitializationException �쳣
//		
//		// 4. ��ȡ Order ����ʱ��Ĭ������£�������� Customer ������һ���������
//		System.out.println(customer.getClass().getName());
//	}
	
	@Test
	public void testManyToOneSave() {
		System.out.println("testManyToOneSave");
		Customer customer = new Customer();
		customer.setCustomerName("BB");
		
//		Order order1 = new Order();
//		order1.setOrderName("ORDER-3");
		
		Order order2 = new Order();
		order2.setOrderName("ORDER-4");

		// �趨������ϵ
//		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		// ִ�� save ���� �Ȳ��� customer���ٲ��� order �����Ϊִ������insert
		// �Ȳ���һ��һ�ˣ��ٲ�����һ�ˣ�ֻ�� insert ���
		session.save(customer);
		
//		session.save(order1);
		session.save(order2);	
//		
//		// ִ�� save ���� �Ȳ��� order���ٲ���  customer
//		// ���Ϊִ������ insert������ update 
//		// �Ȳ�����һ�ˣ��ٲ���һ��һ�ˣ����� update ���
//		// ��Ϊ�ڲ�����һ��ʱ�޷�ȷʵһ��һ�˵����ֵ������ֻ�ܵ�һ��һ�˲��룬�ٶ��ⷢ�� update ���
//		// �Ƽ��Ȳ���һ��һ�ˣ��������һ��
//		session.save(order1);
//		session.save(order2);	
//		
//		session.save(customer);
		
	}

}
