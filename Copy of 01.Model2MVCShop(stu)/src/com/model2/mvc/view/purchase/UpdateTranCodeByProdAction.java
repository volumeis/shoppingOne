package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;	
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction  extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("Action - UpdateTranCodeByProdAction Start...");	
		
		String uri = "";
		String tranCode = request.getParameter("tranCode");
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = null;
		
		System.out.println("::: tranCodeChange1 :::"
				+ "\ntranCode : " + tranCode
				+ "\npurchaseVO : " + purchase );
		
		purchase =  service.getPurchase2(prodNo);
		purchase.setTranCode(tranCode);
		service.updateTranCode(purchase);		
		
		//tranCode Change
		System.out.println("::: tranCodeChange3 :::"
				+ "\ntranCode : " + tranCode
				+ "\npurchaseVO : " + purchase );
		
		if(tranCode.equals("2")){
			uri = "/listProduct.do?menu=manage";
		}
		
		System.out.println("Action - UpdateTranCodeByProdAction End...");	
		
		return "forward:"+ uri;
	}

}
