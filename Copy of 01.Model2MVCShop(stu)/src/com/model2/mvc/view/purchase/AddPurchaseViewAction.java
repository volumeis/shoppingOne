package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction  extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Action - AddPurchaseViewAction Start...");
		
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		ProductService productService	= new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		System.out.println("--product.toString : "+product);
		
		System.out.println("Action - AddPurchaseViewAction End...");
		
		if(CommonUtil.isLogin(request.getSession()) ){
			return "forward:/purchase/addPurchaseView.jsp";
		} else 
			return "redirect:/user/loginView.jsp";
	}
}
