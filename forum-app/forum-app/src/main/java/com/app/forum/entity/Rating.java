package com.app.forum.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.app.forum.enums.VoteType;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rating {
	
	@Id
	@GeneratedValue
    private long id;
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;
	
	@Enumerated(EnumType.STRING)
    private VoteType voteType;
	
}
