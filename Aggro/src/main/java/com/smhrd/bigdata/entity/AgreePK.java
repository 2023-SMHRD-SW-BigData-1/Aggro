package com.smhrd.bigdata.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AgreePK implements Serializable {

	@ManyToOne
	@JoinColumn(name = "noticeSeq",
	referencedColumnName = "noticeSeq",
	nullable = true,
	foreignKey = @ForeignKey(name = "fk_notice_board_to_agree",
							foreignKeyDefinition = "FOREIGN KEY (notice_seq) REFERENCES notice_board(notice_seq) ON DELETE SET NULL"))
	private NoticeBoard noticeSeq;

	@ManyToOne
	@JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_user_info_to_agree", foreignKeyDefinition = "FOREINGN KEY (user_id) REFERENCES user_info(user_id) ON DELETE SET NULL"))
	private UserInfo userId;

}
