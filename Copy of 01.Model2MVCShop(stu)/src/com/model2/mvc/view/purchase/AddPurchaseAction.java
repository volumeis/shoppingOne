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
		
		//VO 생성
		Purchase purchase = new Purchase();
		//구매자정보
		purchase.setBuyer(user);
		//구매자주소 receiverAddr
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		//배송희망일
		purchase.setDivyDate(request.getParameter("receiverDate"));
		//2016-02-25
		//배송요청사항
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		//주문일시
		purchase.setOrderDate(new java.sql.Date(c.getTimeInMillis()));
		//구매방법
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		//구매물품
		purchase.setPurchaseProd(product);
		//구매자이름
		purchase.setReceiverName(request.getParameter("receiverName"));
		//구매자연락처
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		//배송상태 : 0-판매중, 1-구매완료, 2-배송중, 3-배송완료
		purchase.setTranCode("1");		
		
		//구매프로세스
		puchaseService.addPurchase(purchase);

		request.setAttribute("--Add purchase : ", purchase);
		
		System.out.println("Action - AddPurchaseAction End...");
		return "forward:/purchase/addPurchase.jsp";
	}

}
