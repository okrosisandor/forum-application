package com.app.forum.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.app.forum.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Message extends BaseEntity {
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@Column(length = 1000)
	private String message;
	
	private String fromUsername;
	
	private String receiverUserName;
	
	private boolean isRead;
	
	private boolean globalMessage;
	
	private boolean createdByAdmin;
	
}
