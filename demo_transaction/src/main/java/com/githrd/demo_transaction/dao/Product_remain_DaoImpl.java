package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.ProductVo;

public class Product_remain_DaoImpl implements ProductDao{

	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<ProductVo> selectList() {
		return sqlSession.selectList("product_remain.product_remain_list");
	}

	@Override
	public ProductVo selectOne(String name) {
		return sqlSession.selectOne("product_remain.product_remain_one_name", name);
	}
	
	
	@Override
	public int insert(ProductVo vo) {
		return sqlSession.insert("product_remain.product_remain_insert", vo);
	}

	@Override
	public int update(ProductVo vo) {
		return sqlSession.update("product_remain.product_remain_update",vo);
	}

	@Override
	public int delete(int idx) {
		return sqlSession.update("product_remain.product_remain_delete",idx);
	}

	@Override
	public ProductVo selectOne(int idx) {
		return sqlSession.selectOne("product_remain.product_remain_one_idx", idx);
	}

}
