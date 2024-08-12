package com.githrd.demo_transaction.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.githrd.demo_transaction.dao.ProductInMapper;
import com.githrd.demo_transaction.dao.ProductOutMapper;
import com.githrd.demo_transaction.dao.ProductRemainMapper;
import com.githrd.demo_transaction.vo.ProductVo;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductInMapper productInDao;     // 입고 인터페이스

	@Autowired
	ProductOutMapper productOutDao;    // 출고 인터페이스

	@Autowired
	ProductRemainMapper productRemainDao; // 재고 인터페이스

	@Override
	public Map<String, List<ProductVo>> selectTotalMap() {

		List<ProductVo> in_list     = productInDao.selectList();     // 입고목록 
		List<ProductVo> out_list    = productOutDao.selectList();    // 출고목록 
		List<ProductVo> remain_list = productRemainDao.selectList(); // 재고목록 
		
		
		Map<String, List<ProductVo>> map = new HashMap<String, List<ProductVo>>();
		map.put("in_list", in_list);
		map.put("out_list", out_list);
		map.put("remain_list", remain_list);
		
		return map;
	}


	@Override
	public int insert_in(ProductVo vo) throws Exception {
		int res = 0;
		
		// 1. 입고 등록하기
		res = productInDao.insert(vo);
		
		// 2. 재고 등록(수정처리)
		ProductVo remainVo = productRemainDao.selectOneFromName(vo.getName());
		
		if(remainVo == null) {
			// 등록 추가(등록 상품이 없다) 
			res = productRemainDao.insert(vo);
		} else {
			// 상품 기 등록 상태 : 수령 수정 
			// 재고 수량 = 기존 재고 수량 + 추가 수량 
			int cnt = remainVo.getCnt() + vo.getCnt();
			remainVo.setCnt(cnt);
			
			res = productRemainDao.update(remainVo);
		}
		
		
		return res;
	}

	
	@Override
	public int insert_out(ProductVo vo) throws Exception {
		int res = 0;
		
		// 1. 출고등록 
		res = productOutDao.insert(vo);
		
		// 2. 재고 정보 얻어오기
		ProductVo remainVo = productRemainDao.selectOneFromName(vo.getName());
		
		// 재고 처리 
		if(remainVo == null) {
			// 재고 목록에 상품 없을 경우 
			throw new Exception("remain_not");
		} else {
			// 재고 수량 = 원래 재고수량 - 출고수량 
			int cnt = remainVo.getCnt() - vo.getCnt();
			
			if(cnt < 0) { // 100개 있는데, 101개 출고 
				// 재고 수량 부족한 경우 
				throw new Exception("remain_lack");
			}
			
			// 재고수량 수정 (정상처리) 
			remainVo.setCnt(cnt);
			res = productRemainDao.update(remainVo);
		}
		
		
		
		return res;
	}

	@Override
	public int delete_in(int idx) throws Exception {
		int res = 0;
		
		//0.취소할 입고상품정보 얻어오기
		ProductVo deleteVo = productInDao.selectOne(idx);
		
		//1.입고상품 삭제
		productInDao.delete(idx);
		
		
		//2.재고상품 수정
		// 원래 그 상품 재고 목록
		ProductVo remain_vo = productRemainDao.selectOneFromName(deleteVo.getName()); 
		
		// 기존 재고 수량 - 입고 취소 수량 
		int cnt = remain_vo.getCnt() - deleteVo.getCnt();
		
		// 재고목록 -> cnt가 -일 경우
		// 출고된 경우는 재고 입고 - 재고가 마이너스 될 수 있다 
		// 재고가 0 되야됨
		if(cnt < 0) { 
			throw new Exception("delete_in_lack");			
		} 
		
		// 삭제할 때 재고 목록이 0이면은 재고목록에서 지움
		// 재고목록에서 delete
		
		
		remain_vo.setCnt(cnt);		
		
		res = productRemainDao.update(remain_vo);	
		
		
		return res;
		
		
		
	}

	@Override
	public int delete_out(int idx) throws Exception {
		int res = 0;
		
		//0.취소할 출고상품정보 얻어오기
		ProductVo deleteVo = productOutDao.selectOne(idx);
		
		//1.출고상품 삭제
		productOutDao.delete(idx);
		
		
		//2.재고상품 수정
		// 재고수량 = 원래 재고수량 + 취소할 출고수량
		
		// 원래 그 상품 재고 목록
		ProductVo vo = productRemainDao.selectOneFromName(deleteVo.getName());
		System.out.println(vo.getCnt());
		
		int cnt = vo.getCnt() + deleteVo.getCnt();
		
		vo.setCnt(cnt);
		
		res = productRemainDao.update(vo);	
		
		return res;
	}
	
}
