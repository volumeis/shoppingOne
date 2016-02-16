package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;

public class PurchaseServiceImpl implements PurchaseService {

	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}
	
	@Override
	public void addPurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseServiceImpl - addPurchase");
		purchaseDAO.insertPurchase(purchaseVO);		
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		System.out.println("PurchaseServiceImpl - getPurchase");
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int prodNo) throws Exception {
		System.out.println("PurchaseServiceImpl - getPurchase2");
		return purchaseDAO.findPurchase2(prodNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		System.out.println("PurchaseServiceImpl - getPurchaseList");
		return purchaseDAO.getPurchaseList(search, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		System.out.println("PurchaseServiceImpl - getPurchaseList");
		return purchaseDAO.getSaleList(search);
	}

	@Override
	public void updatePurcahse(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseServiceImpl - updatePurcahse");
		purchaseDAO.updatePurchase(purchaseVO);
	}

	@Override
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseServiceImpl - updateTranCode");
		purchaseDAO.updateTranCode(purchaseVO);
	}

}
