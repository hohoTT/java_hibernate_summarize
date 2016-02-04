package com.wt.hibernate.n_to_1.both;

import java.util.HashSet;
import java.util.Set;

public class Customer {

	private Integer customerId;
	private String customerName;
	
	/**
	 * 1. ������������ʱ����Ҫʹ�ýӿ����ͣ���Ϊ hibernate �ڻ�ȡ��������ʱ
	 * ���ص��� Hibernate ���õļ������ͣ������� JavaSE һ����׼�ļ���ʵ��
	 * 2. ��Ҫ�Ѽ��Ͻ��г�ʼ�������Է�ֹ��ָ���쳣
	 */
	private Set<Order> orders = new HashSet<>();
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
}
