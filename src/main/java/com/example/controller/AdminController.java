package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.service.AdminServiceImpl;

import com.example.entity.Category;
import com.example.entity.Course;
import com.example.entity.User;
import com.example.entity.Video;

@RestController
@CrossOrigin
public class AdminController {
	
	@Autowired
	private AdminServiceImpl asi;

	// show all categories
	@GetMapping("/category")
	public ResponseEntity<List<Category>> AllCategory() {

		List<Category> li = asi.getAllCategory();
		for (Category l : li) {
			System.out.println(l);
		}
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

	// show category by id
	@GetMapping(value = "/category/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Optional<Category> CategoryById(@PathVariable int id) {
		System.out.println(id);
		return asi.getCategoryById(id);

	}

	// add category
	@PostMapping("/category")
	public boolean addCategory(@RequestBody Category c) {
		return asi.addCategory(c);
//		if(c.getCategoryId()==0) {
//			asi.addCategory(c);
//		}else {
//			asi.updateCategory(c);
//		}
//		return "redirect:/category";

	}

	// delete category by id
	@DeleteMapping("/category/{id}")
	public void deleteCategory(@PathVariable int id) {
		asi.deleteCategory(id);
	}

	// update category by id
	@PutMapping("/category/{cat_id}")
	public boolean updateCategory(@RequestBody Category c, @PathVariable int cat_id) {
		c.setCategoryId(cat_id);
		Optional<Category> ctest=asi.getCategoryById(cat_id);
		return asi.updateCategory(c,ctest,cat_id);

	}
	
	// total categories
	@GetMapping("/category/total")
	public long totalCategory() {
		return asi.getCategoryCount();
	}
	
	

	// show all courses
	@GetMapping("/course")
	public ResponseEntity<List<Course>> AllCourse() {
		List<Course> li = asi.getAllCourse();
		for (Course l : li) {
			System.out.println(l);
		}
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

	// show course by id
	@GetMapping("/course/{id}")
	public Optional<Course> CourseById(@PathVariable int id) {
		System.out.println(id);
		return asi.getCourseById(id);

	}

	// add course
	@PostMapping("/course/{cat_id}")
	public boolean addCourse(@RequestBody Course c, @PathVariable int cat_id) {
		return asi.addCourse(c, cat_id);
	}

	// delete course by id
	@DeleteMapping("/course/{id}")
	public void deleteCourse(@PathVariable int id) {
		asi.deleteCourse(id);
	}

	// update category by id
	@PutMapping("/course/{co_id}/{cat_id}")
	public boolean updateCourse(@RequestBody Course c, @PathVariable int co_id,@PathVariable int cat_id) {
		
		c.setCourseId(co_id);
		Optional<Course> ctest=asi.getCourseById(co_id);
		return asi.updateCourse(c,ctest);

	}

	// total courses
		@GetMapping("/course/total")
		public long totalCourses() {
			return asi.getCourseCount();
		}
	
	
	// show all videos
	@GetMapping("/video")
	public ResponseEntity<List<Video>> AllVideos() {
		List<Video> li2 = asi.getAllVideo();
		for (Video l : li2) {
			System.out.println(l);
		}
		return ResponseEntity.status(HttpStatus.OK).body(li2);
	}

	// show video by id
	@GetMapping("/video/{id}")
	public Optional<Video> VideoById(@PathVariable int id) {
		return asi.getVideoById(id);

	}

	// add video
	@PostMapping("/video/{co_id}")
	public boolean addVideo(@RequestBody Video c, @PathVariable int co_id) {
		return asi.addVideo(c,co_id);
	}

	// delete video
	@DeleteMapping("/video/{id}")
	public void deleteVideo(@PathVariable int id) {
		asi.deleteVideo(id);
	}

	// update video by id
	@PutMapping("/video/{v_id}/{co_id}")
	public boolean updateVideo(@RequestBody Video v, @PathVariable int v_id,@PathVariable int co_id) {
		v.setVideoId(v_id);
		return asi.updateVideo(v,co_id);
	}
	
	
	// total courses
	@GetMapping("/video/total")
	public long totalVideos() {
		return asi.getVideoCount();
	}
	
	//USERS
	
	@GetMapping("/user")
	public ResponseEntity<List<User>> AllUsers() {

		List<User> li = asi.getAllUser();
		for (User l : li) {
			System.out.println(l);
		}
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}

	
	@GetMapping(path="/lockedusers")
	public List<User> getLocked(){
		return asi.getLockedAccount();
	}
	@PutMapping(path="/unlockuser/{u_id}")
	public boolean unlock(@PathVariable int u_id){
		return asi.unlocakAccount(u_id); 
	}
	@PutMapping(path="/lockuser")
	public boolean lock(){
		return asi.lockAccount(4);
	}

}