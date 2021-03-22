package com.example.repositiories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.EnrolledCourseVideo;
import com.example.entity.EnrolledCourses;
import com.example.entity.Video;


@Repository
public interface EnrolledCourseVideoRepo extends JpaRepository<EnrolledCourseVideo, Integer>{
	public List<EnrolledCourseVideo> findAllByEc(EnrolledCourses ec);
	public boolean deleteAllByEc(EnrolledCourses ec);
	
	
	@Query(value="Select COUNT(completed)  as completed FROM enrolled_course_video where ecourse_id=?1 and completed is true;",nativeQuery = true)
    public int noOfCompletedVideo(int ecid);
	
	@Query(value="Select completed  FROM enrolled_course_video where ecourse_id=?1 ",nativeQuery = true)
    public List<Boolean> videoStatus(int ecid);
}