package com.smhrd.bigdata.dto;

import java.util.List;

import com.smhrd.bigdata.entity.Comment;
import com.smhrd.bigdata.entity.NoticeBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Post extends NoticeBoard{
	
	private List<Comment> replies;
	
	private int viewCount;
	
	private int likeCount;
	
	public void setBoard(NoticeBoard board) {
        this.setNoticeSeq(board.getNoticeSeq());
        this.setTitle(board.getTitle());
        this.setNoticeAt(board.getNoticeAt());
        this.setUserId(board.getUserId());
        this.setDetails(board.getDetails());
	}
}
