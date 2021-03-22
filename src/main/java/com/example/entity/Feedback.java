package com.example.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int feedbackId;
	private String feedback;
	private int rating;

//            @ManyToOne
//            @JoinColumns({@JoinColumn(name="courseId", referencedColumnName = "courseId"),@JoinColumn(name="userId", referencedColumnName = "userId")})
//            private Course course;
//            private User user;

	@ManyToOne(targetEntity = Course.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId", referencedColumnName = "courseId")
	private Course course;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	public Feedback(int feedbackId, String feedback, Course course, User user) {
		super();
		this.feedbackId = feedbackId;
		this.feedback = feedback;
		this.course = course;
		this.user = user;
	}

	public Feedback(String feedback, Course course, User user,int rating) {
		super();
		this.feedback = feedback;
		this.course = course;
		this.user = user;
		this.rating = rating;
	}

	public Feedback() {
		super();
	}

	public Feedback(int feedbackId, String feedback) {
		super();
		this.feedbackId = feedbackId;
		this.feedback = feedback;
	}

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	@JsonIgnore
	@JsonProperty(value = "course")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getUser() {
		return user.getUsername();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	

}
