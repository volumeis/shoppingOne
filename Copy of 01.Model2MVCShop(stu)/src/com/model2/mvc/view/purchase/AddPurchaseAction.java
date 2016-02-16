package com.model2.mvc.view.purchase;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction  extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Action - AddPurchaseAction Start...");
		
		Calendar c = Calendar.getInstance();
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		PurchaseService puchaseService	= new PurchaseServiceImpl();
		ProductService productService	= new ProductServiceImpl();
		
		Product product = productService.getProduct(prodNo);
		User	user		= (User)request.getSession().getAttribute("user");
		
		//VO ����
		Purchase purchase = new Purchase();
		//����������
		purchase.setBuyer(user);
		//�������ּ� receiverAddr
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		//��������
		purchase.setDivyDate(request.getParameter("receiverDate"));
		//2016-02-25
		//��ۿ�û����
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		//�ֹ��Ͻ�
		purchase.setOrderDate(new java.sql.Date(c.getTimeInMillis()));
		//���Ź��
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		//���Ź�ǰ
		purchase.setPurchaseProd(product);
		//�������̸�
		purchase.setReceiverName(request.getParameter("receiverName"));
		//�����ڿ���ó
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		//��ۻ��� : 0-�Ǹ���, 1-���ſϷ�, 2-�����, 3-��ۿϷ�
		purchase.setTranCode("1");		
		
		//�������μ���
		puchaseService.addPurchase(purchase);

		request.setAttribute("--Add purchase : ", purchase);
		
		System.out.println("Action - AddPurchaseAction End...");
		return "forward:/purchase/addPurchase.jsp";
	}

}
