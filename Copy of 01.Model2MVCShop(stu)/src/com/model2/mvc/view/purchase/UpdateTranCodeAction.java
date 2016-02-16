package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction  extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("Action - UpdateTranCodeAction Start...");	
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchaseVO = null;
		String uri = "";

		String tranCode = request.getParameter("tranCode");
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));

		purchaseVO = service.getPurchase(tranNo);
		purchaseVO.setTranCode(tranCode);		
		service.updateTranCode(purchaseVO);
		//tranCode Change
		System.out.println("::: tranCodeChange :::\n"
				+ "tranCode : " + tranCode
				+ "purchaseVO : " + purchaseVO );
		
		//updateTranCodeByProd.do?prodNo=10000&tranCode=2
		//if(tranCode.equals("2")){
		//	uri = "/listSale.do";
		//} else 
		if(tranCode.equals("3")){
			uri = "/listPurchase.do";
		}
		
		System.out.println("Action - UpdateTranCodeAction End...");	
		
		return "forward:"+ uri;
	}

}
