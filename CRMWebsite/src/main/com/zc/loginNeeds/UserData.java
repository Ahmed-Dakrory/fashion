package main.com.zc.loginNeeds;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


/**
 * @author dakrory
 *
 */


@NamedQueries({
	
	
	@NamedQuery(name="UserData.getAll",
		     query="SELECT c FROM UserData c"
		     )
	,
	@NamedQuery(name="UserData.getById",
	query = "from UserData d where d.id = :id"
			)
	
	,
	@NamedQuery(name="UserData.getByEmail",
	query = "from UserData d where d.email = :email"
			)
	
	,
	@NamedQuery(name="UserData.getByMailAndPassword",
	query = "from UserData d where d.email = :email and d.password = :password and active = :active"
			)
	
})

@Entity
@Table(name = "userData")
public class UserData {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "fullName")
	private String fullName;
	
	/*
	 * A7med Dakrory
	 * 0 email
	 * 1 facebook
	 * 2 linkedin
	 * 3 friends
	 * 4 newsletter
	 */
	@Column(name = "whereYknowAboutUs")
	private Integer whereYknowAboutUs;
	

	@Column(name = "email")
	private String email;
	

	@Column(name = "password")
	private String password;
	

	@Column(name = "mobile")
	private String mobile;
	

	@Column(name = "age")
	private String age;
	
	
	@Column(name = "nationalId")
	private String nationalId;
	
	/*
	 * A7med Dakrory
	 * 0 Mr
	 * 1 Ms
	 * 2 Eng
	 * 3 Dr
	 */
	@Column(name = "prefix")
	private Integer prefix;
	
	/*
	 * 0 male
	 * 1 female
	 */
	@Column(name = "gender")
	private Integer gender;
	

	@Column(name = "university")
	private String university;
	
	@Column(name = "faculty")
	private String faculty;

	@Column(name = "major")
	private String major;

	@Column(name = "study_year")
	private String study_year;
	

	@Column(name = "graduation_experiences")
	private Integer graduation_experiences;
	
	/*
	 * A7med Dakrory
	 * 0 for yes
	 * 1 for not
	 */
	@Column(name = "zewailianeOrNot")
	private Integer zewailianeOrNot;
	

	@Column(name = "work")
	private String work;
	

	@Column(name = "residenceGovernorate")
	private String residenceGovernorate;
	
	@Column(name = "homeGovernorate")
	private String homeGovernorate;

	@Column(name="image")
	@Lob
	private byte[] image;


	@Column(name = "active")
	private Integer active;
	/*
	 * Mode Admin 0
	 * Mode User 1
	 */
	@Column(name = "mode")
	private Integer mode;
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public Integer getWhereYknowAboutUs() {
		return whereYknowAboutUs;
	}


	public void setWhereYknowAboutUs(Integer whereYknowAboutUs) {
		this.whereYknowAboutUs = whereYknowAboutUs;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getNationalId() {
		return nationalId;
	}


	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}


	public Integer getPrefix() {
		return prefix;
	}


	public void setPrefix(Integer prefix) {
		this.prefix = prefix;
	}


	public Integer getGender() {
		return gender;
	}


	public void setGender(Integer gender) {
		this.gender = gender;
	}


	public String getUniversity() {
		return university;
	}


	public void setUniversity(String university) {
		this.university = university;
	}


	
	public String getFaculty() {
		return faculty;
	}


	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}


	public String getMajor() {
		return major;
	}


	public void setMajor(String major) {
		this.major = major;
	}


	public String getStudy_year() {
		return study_year;
	}


	public void setStudy_year(String study_year) {
		this.study_year = study_year;
	}


	public Integer getGraduation_experiences() {
		return graduation_experiences;
	}


	public void setGraduation_experiences(Integer graduation_experiences) {
		this.graduation_experiences = graduation_experiences;
	}


	public String getWork() {
		return work;
	}


	public void setWork(String work) {
		this.work = work;
	}



	public String getResidenceGovernorate() {
		return residenceGovernorate;
	}


	public void setResidenceGovernorate(String residenceGovernorate) {
		this.residenceGovernorate = residenceGovernorate;
	}


	public String getHomeGovernorate() {
		return homeGovernorate;
	}


	public void setHomeGovernorate(String homeGovernorate) {
		this.homeGovernorate = homeGovernorate;
	}


	public byte[] getImage() {
		return image;
	}


	public void setImage(byte[] image) {
		this.image = image;
	}

	

	public Integer getZewailianeOrNot() {
		return zewailianeOrNot;
	}


	public void setZewailianeOrNot(Integer zewailianeOrNot) {
		this.zewailianeOrNot = zewailianeOrNot;
	}


	
	public Integer getActive() {
		return active;
	}


	public void setActive(Integer active) {
		this.active = active;
	}


	
	public Integer getMode() {
		return mode;
	}


	public void setMode(Integer mode) {
		this.mode = mode;
	}


	public String getphoto() {
		String imageString= new String(Base64.encodeBase64(image));

		return "data:image/png;base64, "+imageString;
		
	}

}
