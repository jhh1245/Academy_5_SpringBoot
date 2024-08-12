package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ProductDao;
import vo.ProductVo;

public class ProductServiceImpl implements ProductService{

	ProductDao product_in_dao;     // 입고 인터페이스
	ProductDao product_out_dao;    // 출고 인터페이스
	ProductDao product_remain_dao; // 재고 인터페이스
	
	
	// Constructor Injection 
	public ProductServiceImpl(ProductDao product_in_dao, ProductDao product_out_dao, ProductDao product_remain_dao) {
		super();
		this.product_in_dao = product_in_dao;
		this.product_out_dao = product_out_dao;
		this.product_remain_dao = product_remain_dao;
	}

	@Override
	public Map<String, List<ProductVo>> selectTotalMap() {

		List<ProductVo> in_list     = product_in_dao.selectList();     // 입고목록 
		List<ProductVo> out_list    = product_out_dao.selectList();    // 출고목록 
		List<ProductVo> remain_list = product_remain_dao.selectList(); // 재고목록 
		
		
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
		res = product_in_dao.insert(vo);
		
		// 2. 재고 등록(수정처리)
		ProductVo remainVo = product_remain_dao.selectOne(vo.getName());
		
		if(remainVo == null) {
			// 등록 추가(등록 상품이 없다) 
			res = product_remain_dao.insert(vo);
		} else {
			// 상품 기 등록 상태 : 수령 수정 
			// 재고 수량 = 기존 재고 수량 + 추가 수량 
			int cnt = remainVo.getCnt() + vo.getCnt();
			remainVo.setCnt(cnt);
			
			res = product_remain_dao.update(remainVo);
		}
		
		
		return res;
	}

	
	@Override
	public int insert_out(ProductVo vo) throws Exception {
		int res = 0;
		
		// 1. 출고등록 
		res = product_out_dao.insert(vo);
		
		// 2. 재고 정보 얻어오기
		ProductVo remainVo = product_remain_dao.selectOne(vo.getName());
		
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
			res = product_remain_dao.update(remainVo);
		}
		
		
		
		return res;
	}

	@Override
	public int delete_in(int idx) throws Exception {
		int res = 0;
		
		//0.취소할 입고상품정보 얻어오기
		ProductVo deleteVo = product_in_dao.selectOne(idx);
		
		//1.입고상품 삭제
		product_in_dao.delete(idx);
		
		
		//2.재고상품 수정
		// 원래 그 상품 재고 목록
		ProductVo remain_vo = product_remain_dao.selectOne(deleteVo.getName()); 
		
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
		
		res = product_remain_dao.update(remain_vo);	
		
		
		return res;
		
		
		
	}

	@Override
	public int delete_out(int idx) throws Exception {
		int res = 0;
		
		//0.취소할 출고상품정보 얻어오기
		ProductVo deleteVo = product_out_dao.selectOne(idx);
		
		//1.출고상품 삭제
		product_out_dao.delete(idx);
		
		
		//2.재고상품 수정
		// 재고수량 = 원래 재고수량 + 취소할 출고수량
		
		// 원래 그 상품 재고 목록
		ProductVo vo = product_remain_dao.selectOne(deleteVo.getName());
		System.out.println(vo.getCnt());
		
		int cnt = vo.getCnt() + deleteVo.getCnt();
		
		vo.setCnt(cnt);
		
		res = product_remain_dao.update(vo);	
		
		return res;
	}
	
}
