package com.wt.hibernate.union.subclass;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	
	/**
	 * ʹ�� union-Subclass �� �ŵ㣺
	 * 1. ����Ҫʹ�ñ������
	 * 2. ������е��ֶ�����ӷǿ�Լ��
	 * 
	 * ʹ�� union-Subclass �� ȱ�㣺
	 * 1. ����������ֶ�
	 * 2. �����¸�����ֶΣ� ����µ�Ч�ʽϵ�
	 * 
	 */
	
	@Test
	public void testUpdate() {
		
		String hql = "UPDATE Person p SET p.age = 20";
		
		session.createQuery(hql).executeUpdate();
	}
	
	/**
	 * ��ѯ��
	 * 1. ��ѯ�����¼����Ѹ�����ӱ���ܵ�һ��������ѯ�������Բ
	 * 2. ���������¼��Ҳֻ��Ҫ��ѯһ�����ݱ�
	 */
	@Test
	public void testQuery() {
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size());
		

		List<Student> students = session.createQuery("FROM Student").list();
		System.out.println(students.size());
	}
	
	/**
	 *  ���������
	 *  1. �����������ֻ��Ѽ�¼���뵽һ�����ݱ���
	 */
	@Test
	public void testSave(){
		
		Person person = new Person();
		person.setName("AA");
		person.setAge(11);
		
		session.save(person);
		
		Student student = new Student();
		student.setName("BB");
		student.setAge(22);
		student.setSchool("QDU");
		
		session.save(student); 
		
	}
	
}
