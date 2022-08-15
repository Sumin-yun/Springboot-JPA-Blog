package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor  //빈생성자
@AllArgsConstructor
@Builder
@Entity
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  //auto_Increment
  private int id;

  @Column(nullable = false, length=100)
  private String title;

  @Lob                                           //대용량 데이터 다룰떄 사용 
  private String content;            //섬머노트 라이브러리 툴 사용:  <html> 태그가 섞여서 디자인 됨 

  private int count;                    //조회

  @ManyToOne(fetch = FetchType.EAGER)                        //1대 다 관계 설정
  @JoinColumn(name="userId")
  private User user;                   //DB는 오브젝트를 설정할 수 없다.
  // 하지만 JPA를 사욯마면 FK 자바는 오브젝트를 설정할 수 없다.

  @OneToMany (mappedBy = "board", fetch = FetchType.EAGER)       //FK가 필요 없기때문에 조인컬럼 필요없음.
  private List<Reply> reply;                            //mappedBy -> 연관관계의 주인x. DB에 테이블을 만들지않음.

  @CreationTimestamp            //빌드할때 현재시간을 자동으로 업데이트
  private Timestamp createDate;

}
