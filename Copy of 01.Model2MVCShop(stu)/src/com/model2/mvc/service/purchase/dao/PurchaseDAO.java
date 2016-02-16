package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDAO {

	//Field
	
	//Constructor
	public PurchaseDAO(){
		
	}
	
	//Method
	public Purchase findPurchase(int tranNo) throws Exception {
		
		System.out.println("PurchaseDAO - findPurchase Start...");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT prod_no, buyer_id, payment_option, receiver_name, receiver_phone, dlvy_addr, dlvy_request, tran_status_code, order_date, dlvy_date"
				+ "  FROM transaction WHERE tran_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1,tranNo);

		ResultSet rs = pStmt.executeQuery();
		
		Purchase purchase = null;
		
		while(rs.next()){
			purchase = new Purchase();
			purchase.setTranNo(tranNo);
			purchase.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
			purchase.setBuyer(new UserDAO().findUser(rs.getString("buyer_id")));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("dlvy_addr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setOrderDate(rs.getDate("order_date"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("PurchaseDAO - findPurchase End...");
		
		return purchase;
	}
	
public Purchase findPurchase2(int prodNo) throws Exception {
		
		System.out.println("PurchaseDAO - findPurchase2 Start...");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT tran_no, prod_no, buyer_id, payment_option, receiver_name, receiver_phone, dlvy_addr, dlvy_request, tran_status_code, order_date, dlvy_date"
				+ "  FROM transaction WHERE prod_no = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1,prodNo);


		ResultSet rs = pStmt.executeQuery();
		
		Purchase purchase = null;
		
		while(rs.next()){
			purchase = new Purchase();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
			purchase.setBuyer(new UserDAO().findUser(rs.getString("buyer_id")));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("dlvy_addr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setOrderDate(rs.getDate("order_date"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("PurchaseDAO - findPurchase2 End...");
		
		return purchase;
	}
	

	public Map<String, Object >getPurchaseList(Search search, String buyer) throws Exception {
		System.out.println("PurchaseDAO - getPurchaseList Start...");
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		String sql = "SELECT tran_no, prod_no, buyer_id, payment_option, receiver_name, receiver_phone, dlvy_addr, dlvy_request, tran_status_code, order_date, dlvy_date"
				+ " FROM transaction WHERE buyer_id = '"+buyer+"' ";

		System.out.println("PurchaseDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
	
		System.out.println(search);
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			Purchase purchase = new Purchase();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
			purchase.setBuyer(new UserDAO().findUser(rs.getString("buyer_id")));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("dlvy_addr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code").trim());
			purchase.setOrderDate(rs.getDate("order_date"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			
			list.add(purchase);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();

		System.out.println("PurchaseDAO - getPurchaseList End...");
	
		return map;
	}
	
	public Map<String, Object >getSaleList(Search search) throws Exception{
		
		System.out.println("PurchaseDAO - getSaleList Start...");
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		Connection con = DBUtil.getConnection();
		String sql = "SELECT * "
				+ " FROM transaction ts, "
				+ " (SELECT product.prod_no PRODNO, product.PROD_NAME , product.PRICE FROM product ) pt "
				+ " WHERE pt.PRODNO = ts.prod_no(+) ";
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")  ) {
				sql += " AND PROD_NO='" + search.getSearchKeyword()	+ "'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("") ) {
				sql += " AND PROD_NAME='" + search.getSearchKeyword() + "'";
			} else if (search.getSearchCondition().equals("2") &&  !search.getSearchKeyword().equals("") )  {
				sql += " AND PRICE='" + search.getSearchKeyword() + "'";
			}
		}
		
		System.out.println("PurchaseDAO::Original SQL :: " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			Purchase purchase = new Purchase();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(new ProductServiceImpl().getProduct(rs.getInt("PRODNO")));
			purchase.setBuyer(new UserServiceImpl().getUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			String tranCode = rs.getString("TRAN_STATUS_CODE");
			purchase.setTranCode(tranCode!=null ? tranCode.trim() : "0");
			purchase.setOrderDate(rs.getDate("ORDER_DATE"));
			String dlvyDate = rs.getString("DLVY_DATE");
			purchase.setDivyDate(dlvyDate != null ? dlvyDate : "2100-01-01") ;
				
				list.add(purchase);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);
		
		rs.close();
		pStmt.close();
		con.close();
		System.out.println("PurchaseDAO - getSaleList End...");
	
		return map;
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		System.out.println("PurchaseDAO - findPurchase Start...");
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction"
				+ " VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,?,?)"; 
				
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchase.getBuyer().getUserId());
		pStmt.setString(3 , purchase.getPaymentOption());
		pStmt.setString(4 , purchase.getReceiverName());
		pStmt.setString(5 , purchase.getReceiverPhone());
		pStmt.setString(6 , purchase.getDivyAddr());
		pStmt.setString(7 , purchase.getDivyRequest());
		pStmt.setString(8 , purchase.getTranCode());
		pStmt.setDate(9 , purchase.getOrderDate());
		//널조건처리필요
		pStmt.setDate(10 , new Date( new SimpleDateFormat("yyyy-MM-dd").parse(purchase.getDivyDate()).getTime() )  );
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println("PurchaseDAO - findPurchase End...");
	}

	public void updatePurchase(Purchase purchase) throws Exception{

		System.out.println("PurchaseDAO - updatePurchase Start...");
		
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction"
				+ " SET payment_option=?, RECEIVER_NAME=?, RECEIVER_PHONE=?, DLVY_ADDR=?, DLVY_REQUEST=?,  DLVY_DATE=? " 
				+ " WHERE tran_no=?";
		
		System.out.println("updateProduct : " + purchase);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		///update
		//구매방법
		pStmt.setString(1, purchase.getPaymentOption());
		//구매자이름
		pStmt.setString(2, purchase.getReceiverName());
		//구매자 연락처
		pStmt.setString(3, purchase.getReceiverPhone());
		//구매자주소
		pStmt.setString(4, purchase.getDivyAddr() ); 
		//구매요청사항
		pStmt.setString(5, purchase.getDivyRequest() );
		//배송희망일자
		pStmt.setDate(6, new Date(new SimpleDateFormat("yyyy-MM-dd").parse(purchase.getDivyDate()).getTime() ));
		//구매등록번호
		pStmt.setInt (7, purchase.getTranNo() );
		pStmt.executeUpdate();
		
		pStmt.close();
		con.close();
		
		System.out.println("PurchaseDAO - updatePurchase End...");
	}
	
	public void updateTranCode(Purchase purchaseVO) throws Exception{
		System.out.println("PurchaseDAO - updateTranCode Start...");
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction"
				+ " SET tran_status_code = ? "
				+ " WHERE tran_no = ?";
		PreparedStatement pStmt = con.prepareStatement(sql);
		
		pStmt.setString(1, purchaseVO.getTranCode());
		pStmt.setInt(2, purchaseVO.getTranNo());
		
		pStmt.executeQuery();
		
		pStmt.close();
		con.close();
		
		System.out.println("PurchaseDAO - updateTranCode End...");	
	}
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("PurchaseDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}
