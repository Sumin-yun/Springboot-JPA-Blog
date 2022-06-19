package com.cos.blog.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)  //auto_Increment
  private int id;

  @Column (nullable = false,  length =200)                                //대용량 데이터 다룰떄 사용 
  private String content;                                                             //섬머노트 라이브러리 툴 사용:  <html> 태그가 섞여서 디자인 됨

  @ManyToOne
  @JoinColumn(name = "boardId")
  private Board board;

  @ManyToOne
  @JoinColumn(name = "userId")
  private User user;

  @CreationTimestamp
  private Timestamp createDate;
}
