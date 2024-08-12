package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import vo.ProductVo;

public class Product_in_DaoImpl implements ProductDao{

	SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public List<ProductVo> selectList() {
		return sqlSession.selectList("product_in.product_in_list");
	}

	@Override
	public int insert(ProductVo vo) {
		return sqlSession.insert("product_in.product_in_insert", vo);
	}

	@Override
	public int update(ProductVo vo) {
		return sqlSession.update("product_in.product_in_update",vo);
	}

	@Override
	public int delete(int idx) {
		return sqlSession.update("product_in.product_in_delete",idx);
	}

	@Override
	public ProductVo selectOne(int idx) {
		return sqlSession.selectOne("product_in.product_in_one_idx", idx); 
	}

}
