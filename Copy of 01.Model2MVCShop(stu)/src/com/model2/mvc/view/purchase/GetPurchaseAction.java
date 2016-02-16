package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class GetPurchaseAction  extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Action - GetPurchaseAction Start...");
		
		int tranNo = Integer.parseInt( request.getParameter("tranNo") );

		PurchaseService purchaseService = new PurchaseServiceImpl(); ///
		Purchase purchaseVO = purchaseService.getPurchase(tranNo);

		System.out.println("purchaseVO : " + purchaseVO);
		request.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println("Action - GetPurchaseAction End...");
		return "forward:/purchase/getPurchase.jsp";
	}

}
