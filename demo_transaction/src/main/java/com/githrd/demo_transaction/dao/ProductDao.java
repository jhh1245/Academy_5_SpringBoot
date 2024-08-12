package dao;

import java.util.List;

import vo.ProductVo;

public interface ProductDao {
	List<ProductVo>     selectList();
	ProductVo 			selectOne(int idx);
	default ProductVo   selectOne(String name) { return null; } // 선택적으로 재정의 할 수 있다 
	int                	insert(ProductVo vo);
	int                	update(ProductVo vo);
	int					delete(int idx);
}
