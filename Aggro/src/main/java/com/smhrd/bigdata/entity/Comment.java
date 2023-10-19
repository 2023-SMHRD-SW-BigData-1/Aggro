package com.smhrd.bigdata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Table
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentSeq;

	@ManyToOne
	@JoinColumn(name = "noticeSeq", referencedColumnName = "noticeSeq", nullable = true, foreignKey = @ForeignKey(name = "fk_notice_board_to_comment", foreignKeyDefinition = "FOREIGN KEY (notice_seq) REFERENCES notice_board(notice_seq) ON DELETE SET NULL"))
	private NoticeBoard noticeSeq;

	@ManyToOne
	@JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_user_info_to_comment", foreignKeyDefinition = "FOREINGN KEY (user_id) REFERENCES user_info(user_id) ON DELETE SET NULL"))
	private UserInfo userId;

	@Column(length = 100, nullable = false)
	private String details;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
	private Date commentAt;
}
