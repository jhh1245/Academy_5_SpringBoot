package com.githrd.demo_mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.githrd.demo_mybatis.vo.DeptVo;


@Mapper // 데이터베이스 연결하기 위한 Mapper 
public interface DeptDao {
    
    // XML의 Mapper 정보 이용하려면 
    // 1. 파일명 DeptDao == DeptDao.xml 
    // 2. namespace = "Mapper 정보"
    //    namespace = "com.githrd.demo_mybatis.dao.DeptDao"
    // 3. mapper id = "메소드명"
    //           id = "selectList"

    // mapper 사용
    // @Select("select * from dept")
    List<DeptVo> selectList(); 
    // select 해서 arrayList로 포장 

    // 어노테이션 사용
    @Select("select * from dept where deptno = #{ deptno }")
    List<DeptVo> selectListFromDeptNo(int deptno);

    // mapper 사용 
    List<DeptVo> selectListFromLoc(String loc);


}
