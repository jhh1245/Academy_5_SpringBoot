package com.githrd.demo_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data // get, set 자동생성. lombok 
@Entity // JPA로 연결되는 객체 
@Table(name="dept") // entity명 = 테이블명 동일하면 생략 가능
public class Dept {
    // @GeneratedValue(strategy=GenerationType.IDENTITY) // IDENTITY : 유일한 값 
    @Id // primary key 
    int    deptno;

    // 컬럼명/제약조건 
    // DB의 dname 컬럼을 아래 이름으로 사용. name과 Entity property(바로 아래 쓴거)가 동일하면 생략가능 
    @Column(name="dname", nullable = false) 
    String dname; 

    String loc;
}
