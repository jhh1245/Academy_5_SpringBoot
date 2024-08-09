package com.githrd.demo_jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.githrd.demo_jpa.entity.Dept;


@Repository
public interface DeptRepository extends JpaRepository<Dept,Integer>{
    // 인터페이스인데, 스프링이 알아서 구현체를 만들어준다. 

    // 전체 조회 
    List<Dept> findAll();

    // deptno 부서별 조회 
    // 컬럼 이름을 가지고 메소드 자동 생성됨 Deptno를 가지고 조회  
    Optional<Dept> findByDeptno(Integer deptno); 

}
