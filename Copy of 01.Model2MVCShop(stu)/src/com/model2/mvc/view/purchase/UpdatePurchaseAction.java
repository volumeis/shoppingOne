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
		//구매방법
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		//구매자이름
		purchase.setReceiverName(request.getParameter("receiverName"));
		//구매자 연락처
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		//구매자주소
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		//구매요청사항
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		//배송희망일자
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		service.updatePurcahse(purchase);	
		
		request.setAttribute("purchase", purchase);
		System.out.println("--UpdatePurchase : " + purchase);
		
		System.out.println("Action - UpdatePurchaseAction End...");	
		
		return "forward:/purchase/updatePurchase.jsp";
	}

}
