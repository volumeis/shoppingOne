package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction  extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("Action - UpdatePurchaseAction Start...");
		PurchaseService service = new PurchaseServiceImpl();
		
		Purchase purchase = service.getPurchase(Integer.parseInt(request.getParameter("tranNo")));
		
		///update
		//���Ź��
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		//�������̸�
		purchase.setReceiverName(request.getParameter("receiverName"));
		//������ ����ó
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		//�������ּ�
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		//���ſ�û����
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		//����������
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		service.updatePurcahse(purchase);	
		
		request.setAttribute("purchase", purchase);
		System.out.println("--UpdatePurchase : " + purchase);
		
		System.out.println("Action - UpdatePurchaseAction End...");	
		
		return "forward:/purchase/updatePurchase.jsp";
	}

}
