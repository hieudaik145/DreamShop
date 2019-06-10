package dreamshop.catalog;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="COMMENTS")
public class Comment  implements Serializable  {

	public static final long serialVersionUID = -7114101035786254953L;
	
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String email;
	private String text;
	public static enum CommentTypes{
		XACNHAN,CHUAXACNHAN;
	}
	private CommentTypes commentTypes;
	
	private int rating;
	private LocalDateTime time;
	
	public Comment() {
		super();
	}
	public Comment( String name, String email, String text, int rating,CommentTypes commentTypes, LocalDateTime time) {
		super();
		
		this.name = name;
		this.email = email;
		this.text = text;
		this.rating = rating;
		this.commentTypes = commentTypes;
		this.time = time;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public CommentTypes getCommentTypes() {
		return commentTypes;
	}
	public void setCommentTypes(CommentTypes commentTypes) {
		this.commentTypes = commentTypes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
