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
public class CommunityHits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hitsSeq;

	@ManyToOne
	@JoinColumn(name = "noticeSeq", referencedColumnName = "noticeSeq", nullable = true, foreignKey = @ForeignKey(name = "fk_notice_board_to_community_hits", foreignKeyDefinition = "FOREIGN KEY (notice_seq) REFERENCES notice_board(notice_seq) ON DELETE SET NULL"))
	private NoticeBoard noticeSeq;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
	private Date hitsAt;
}
