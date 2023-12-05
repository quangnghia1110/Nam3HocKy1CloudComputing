package hcmute.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import hcmute.dto.BlogForm;
import hcmute.dto.PageResponse;
import hcmute.model.Blog;

public interface IBlogService {

	List<Blog> getAllBlogs();

	void deleteById(Long blogId);

	void addBlog(BlogForm blogForm) throws IOException;

	Blog getById(Long blogId);

	void updateBlog(BlogForm blogForm) throws IOException;

	void updateImage(MultipartFile file);

	List<Blog> getLatestBlogs();

	PageResponse<Blog> getBlogByPage(int parseInt);


}
