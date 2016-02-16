package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model2.mvc.framework.Action;

public class ListSaleAction  extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("Action - ListSaleAction Start...");
		
//		request.setAttribute("map", (HashMap<String, Object>)request.getAttribute("map"));
//		request.setAttribute("searchVO", (SearchVO)request.getAttribute("searchVO"));		
		
		System.out.println("Action - ListSaleAction end...");
		
		return "forward:/purchase/listSale.jsp";
	}

}
