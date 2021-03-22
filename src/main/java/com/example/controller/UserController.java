package com.example.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Comment;
import com.example.entity.Course;
import com.example.entity.EnrolledCourses;
import com.example.entity.Feedback;
import com.example.entity.Profile;
import com.example.entity.User;
import com.example.entity.Video;
import com.example.service.UserService;
import com.example.service.UserServiceImpl;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import io.micrometer.core.ipc.http.HttpSender.Request;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

	@Autowired
	UserService uservice = new UserServiceImpl();

	@PostMapping("/comment")
	public ResponseEntity<String> postComment(@RequestParam int userid, @RequestParam int courseid,
			@RequestParam String comment_msg) {
		Comment count = uservice.addComment(userid, courseid, comment_msg);

		if (count == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add comment");
		}
		return ResponseEntity.status(HttpStatus.OK).body("comment added");

	}

	@PostMapping("/feedback")
	public ResponseEntity<String> postfeedback(@RequestParam int userid, @RequestParam int courseid,
			@RequestParam String feedback_msg, @RequestParam int rating) {
		Feedback count = uservice.addFeedback(userid, courseid, feedback_msg, rating);

		if (count == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add feedback");
		}
		return ResponseEntity.status(HttpStatus.OK).body("feedback added");

	}

	@DeleteMapping("/comment")
	public ResponseEntity<String> deleteComment(@RequestParam int commentid) {

		uservice.deleteComment(commentid);
		return null;

	}

	@DeleteMapping("/feedback")
	public ResponseEntity<String> deletefeedback(@RequestParam int feedbackid) {

		uservice.deleteFeedback(feedbackid);
		return null;

	}

	@PutMapping("/comment")
	public ResponseEntity<String> upateComment(@RequestParam int userid, @RequestParam int courseid,
			@RequestParam int commentid, @RequestParam String comment_msg) {
		Comment count = uservice.updateComment(userid, courseid, commentid, comment_msg);

		if (count == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add comment");
		}
		return ResponseEntity.status(HttpStatus.OK).body("comment added");
	}

	@PutMapping("/feedback")
	public ResponseEntity<String> upatefeedback(@RequestParam int userid, @RequestParam int courseid,
			@RequestParam int feedbackid, @RequestParam String feedback_msg) {
		Feedback count = uservice.updateFeedback(userid, courseid, feedbackid, feedback_msg);

		if (count == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot add feedback");
		}
		return ResponseEntity.status(HttpStatus.OK).body("feedback added");
	}

	@GetMapping("/comment")
	public List<Comment> fetchComment(@RequestParam int courseid) {
		return uservice.fetchComment(courseid);
	}

//	@GetMapping("/feedback")
//	public List<Comment> fetchFeedback(@RequestParam int feedbackid){
//		return uservice.fetchComment(feedbackid);
//	}
//	
	@GetMapping("/feedback")
	public List<Feedback> fetchFeedbackbyCourseID(@RequestParam int courseid) {
		return uservice.fetchFeedbacks(courseid);
	}

	@GetMapping("/completedCourses")
	public List<Course> completedCourses(@RequestParam int userId) {
		return uservice.fetchcompletedCourses(userId);
	}

	@GetMapping("/courses")
	public ResponseEntity<List<Course>> AllCourse() {
		List<Course> li = uservice.AllCourse();
		for (Course l : li) {
			System.out.println(l);
		}
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

	@GetMapping("/videos")
	public ResponseEntity<List<Video>> getVideos(@RequestParam int courseid) {
		List<Video> li = uservice.getVideos(courseid);
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

	// show course by id
	@GetMapping("/course")
	public Optional<Course> CourseById(@RequestParam int courseid) {
		return uservice.getCourseById(courseid);
	}

	// total number of feedbacks
	@GetMapping("/feedbackcount")
	public int feedbackcount(@RequestParam int courseid) {
		return uservice.feedbackcount(courseid);
	}

	// total number of comments
	@GetMapping("/commentcount")
	public int commentcount(@RequestParam int courseid) {
		return uservice.commentcount(courseid);
	}

	@PutMapping(path = "/like/{cid}/{uid}")
	public String like(@PathVariable int cid, @PathVariable int uid) {

		// 11 is the user id
		boolean status = uservice.like(uid, cid);
		if (status) {
			return "Liked";
		}
		return "can not like";
	}

	@DeleteMapping(path = "/unlike/{cid}/{uid}")
	public String unlike(@PathVariable int cid, @PathVariable int uid) {

		// 11 is the user id
		boolean status = uservice.unlike(uid, cid);
		if (status) {
			return "Un Liked";
		}
		return "can not unlike";
	}

	@GetMapping("/isliked/{cid}/{uid}")
	public boolean isliked(@PathVariable int cid, @PathVariable int uid) {

		return uservice.isliked(cid, uid);

	}

	@GetMapping("/isenrolled/{cid}/{uid}")
	public boolean isenrolled(@PathVariable int cid, @PathVariable int uid) {

		return uservice.isenrolled(cid, uid);

	}

	@PutMapping(path = "enroll/{cid}/{uid}")
	public String enroll(@PathVariable int cid, @PathVariable int uid) {

		// 11 is the user id
		boolean status = uservice.Enroll(cid, uid);
		if (status) {
			return "Enrolled";
		}
		return "can not enroll";
	}

	@GetMapping(path = "/enrolledcourses/video/{cid}/{uid}")
	public List<Video> getVideo(@PathVariable int cid, @PathVariable int uid) {
		return uservice.getEnrolledCourseVideo(uid, cid);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping(path = "nextvideo/{cid}/{uid}/{vid}")
	public boolean nextVideo(@PathVariable int cid, @PathVariable int uid, @PathVariable int vid) {

		return uservice.nextVideo(cid, uid, vid);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@PutMapping(path = "completevideo/{cid}/{uid}/{vid}")
	public boolean completeVideo(@PathVariable int cid, @PathVariable int uid, @PathVariable int vid) {

		return uservice.completeVideo(cid, uid, vid);
	}

	@GetMapping(path = "iscompleted/{cid}/{uid}")
	public boolean completedcourse(@PathVariable int cid, @PathVariable int uid) {
		return uservice.isCourseCompleted(cid, uid);
	}

	@GetMapping(path = "/generatePdf/{cid}/{uid}")
	public boolean generatepdf(@PathVariable int cid, @PathVariable int uid) {
		return uservice.generateCompeletionCerti(uid, cid);
	}

	@GetMapping(path = "/regotp")
	public String regotp(@RequestParam int userid, HttpServletRequest request)
			throws AddressException, MessagingException {
		HttpSession session = request.getSession();
		int random = uservice.regotp(userid, session);
		return Integer.toString(random);
	}

	@PutMapping("/incrementfa/{username}")
	public int ifa(@PathVariable String username) throws AddressException, MessagingException {
		return uservice.incrementfailed(username);
	}

	@PutMapping("/clearfa/{userid}")
	public boolean cfa(@PathVariable int userid) throws AddressException, MessagingException {
		return uservice.clearfalied(userid);

	}

	@GetMapping("/isLocked/{username}")
	public boolean isLocked(@PathVariable String username) {
		return uservice.isLocked(username);

	}

	@PostMapping(path = "/adduser")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String createUser(@RequestBody User user) {
		return uservice.createUser(user);
	}

	@GetMapping(path = "isActivated/{userid}")
	public boolean isActivated(@PathVariable int userid) {
		return uservice.isActivated(userid);
	}

	@PutMapping(path = "/unlockuser/{userid}")
	public boolean unlock(@PathVariable int userid) throws AddressException, MessagingException {
		return uservice.unlocakAccount(userid);
	}

	@PutMapping(path = "/lockuser/{userid}")
	public boolean lock(@PathVariable int userid) throws AddressException, MessagingException {
		return uservice.lockAccount(userid);
	}

	@GetMapping(path = "/verifyreg/{status}/{username}")
	public boolean verifyreg(@PathVariable boolean status, @PathVariable String username) {
		return uservice.verifyOtp(username, status);
	}

	@GetMapping(path = "forgototp/{email}")
	public String forgototp(@PathVariable String email) throws AddressException, MessagingException {
		int random = uservice.forgototp(email);
		return Integer.toString(random);
	}

	@GetMapping(path = "changepass/{password}/{email}")
	public String changepassword(@PathVariable String password, @PathVariable String email) {
		uservice.changepassword(password, email);
		return "changed";
	}

	@GetMapping(path = "sendcert/{courseid}/{userid}")
	public String sendcert(@PathVariable int courseid, @PathVariable int userid)
			throws AddressException, MessagingException, IOException {
		
		uservice.sendcert( userid, courseid);
		return "sent";

	}

	@PostMapping("/profile/{userid}") // we have send userid while creating profile
	@ResponseStatus(code = HttpStatus.CREATED)
	public Profile createProfile(@PathVariable int userid, @RequestBody Profile profile) {
		// file from model attribute
//		
//		//profile.setUserImage(file.getInputStream());
//		try {
//			InputStream inputStream = file.getInputStream();
//			byte[] b = IOUtils.toByteArray(inputStream);
//			SerialBlob blob = new javax.sql.rowset.serial.SerialBlob(b);
//			profile.setUserImage(blob);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return uservice.createProfile(userid, profile);
	}

	@GetMapping("/profile/{userid}")
	public Profile getProfileDetails(@PathVariable int userid) {
		return uservice.getProfileDetails(userid);
	}

	@GetMapping("/isProfileCreated/{userid}")
	public boolean isPC(@PathVariable int userid) {
		return uservice.isProfileCreated(userid);
	}

	@GetMapping("/username/{username}")
	public boolean checkUsername(@PathVariable String username) {
		return uservice.checkUsername(username);
	}

	@GetMapping("/email/{email}")
	public boolean checkEmail(@PathVariable String email) {
		return uservice.checkEmail(email);
	}


	@DeleteMapping("/delete/{id}")
	public boolean deleteUser(@PathVariable int id) {
		return uservice.deleteUser(id);
	}

	@GetMapping("/coursesbycatId/{catId}")
	public List<Course> getCourseById(@PathVariable int catId) {
		return uservice.findCourseByCat(catId);
	}
	@GetMapping(path = "getcert/{courseid}/{userid}")
	public String getCerti(@PathVariable int courseid, @PathVariable int userid) {
		return uservice.getCerti(courseid, userid);
		
	}
	@GetMapping(path="enrolled/{userid}")
	public List<Course> getenrolled(@PathVariable int userid) {
		return uservice.getEnrolledCourse(userid);
		
	}
	@GetMapping(path="enrolleddates/{userid}")
	public List<Date> getenrolledDates(@PathVariable int userid) {
		return uservice.getEDates(userid);
		
	}
	@GetMapping(path="finished/{userid}")
	public List<Course> getFinishedCourse(@PathVariable int userid) {
		return uservice.finishedCourse(userid);
		
	}
	@GetMapping(path="finisheddates/{userid}")
	public List<Date> getFinishedCourseDates(@PathVariable int userid) {
		return uservice.getFDates(userid);
		
	}
	@GetMapping(path="certificates/{userid}")
	public List<String> getCerties(@PathVariable int userid) {
		return uservice.getCertiPaths(userid);
		
	}
	@GetMapping(path = "getVideoStatus/{courseid}/{userid}")
	public List<String> getVideoStatus(@PathVariable int courseid, @PathVariable int userid) {
		return uservice.getVideoStatus(userid, courseid);
		
	}
	
	


}
