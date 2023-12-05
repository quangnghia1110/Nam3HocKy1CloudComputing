package hcmute.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hcmute.model.audit.UserDateAudit;
import hcmute.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feedbacks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Feedback extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
<<<<<<< HEAD
	@JoinColumn(name = "laptop_id", referencedColumnName = "id")
	private Laptop laptop;
=======
	@JoinColumn(name = "book_id", referencedColumnName = "id")
	private Book book;
>>>>>>> branch 'master' of https://github.com/quangnghia1110/doancuoiky.git
	
	@Column(name = "content", length = 200)
	private String content;
	
	@Column(name = "rating")
	private int rating;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	
}
