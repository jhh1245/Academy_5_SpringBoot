package com.githrd.demo_mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.githrd.demo_mybatis.vo.SawonVo;


@Mapper // 데이터베이스 연결하기 위한 Mapper 
public interface SawonDao {
    
    // XML의 Mapper 정보 이용하려면 
    // 1. 파일명 DeptDao == DeptDao.xml 
    // 2. namespace = "Mapper 정보"
    //    namespace = "com.githrd.demo_mybatis.dao.DeptDao"
    // 3. mapper id = "메소드명"
    //           id = "selectList"

    // @Select("select * from dept")
    // List<SawonVo> selectList(); 
    // select 해서 arrayList로 포장 

    List<SawonVo> selectList(Map<String,Object> map);
}
