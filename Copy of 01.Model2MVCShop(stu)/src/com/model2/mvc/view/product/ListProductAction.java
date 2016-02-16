package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("Action - ListProductAction Start...");
		
		//조회시 사용하는 VO
		Search search=new Search();
		
		//request의 현재페이지
		int currentPage=1;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		// Business logic 수행
		PurchaseService service=new PurchaseServiceImpl();
		Map<String,Object> map = service.getSaleList(search);
		
		String menu = request.getParameter("menu").equals("manage") ? "manage" : "search";
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
			
		String uri = "";
		//System.out.println("UsersRole : " + ((User)request.getSession().getAttribute("user")).getRole());
		
		// Model 과 View 연결
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		System.out.println(((User)request.getSession().getAttribute("user")).getRole().equals("user") );
		System.out.println( ((User)request.getSession().getAttribute("user")));
		System.out.println(menu);
		
		System.out.println("Action - ListProductAction end...");
		if ( ((User)request.getSession().getAttribute("user"))==null || ((User)request.getSession().getAttribute("user")).getRole().equals("user")  ){
			System.out.println("1");
			uri = "/product/listProductUser.jsp";
		} else {

			System.out.println("2");
			uri = "/product/listProductAdmin.jsp";
				
			if( menu.equals("manage")){		
				System.out.println("3");
				uri = "/listSale.do";
			}
		}
		
		return "forward:"+ uri;
	}
}