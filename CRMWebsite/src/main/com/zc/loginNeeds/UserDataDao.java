package main.com.zc.loginNeeds;


public class UserDataDao {

	private Integer id;

	private String fullName;
	
	/*
	 * A7med Dakrory
	 * 0 email
	 * 1 facebook
	 * 2 linkedin
	 * 3 friends
	 * 4 newsletter
	 */
	private Integer whereYknowAboutUs;
	

	private String email;
	

	private String password;
	

	private String mobile;
	

	private String age;
	
	
	private String nationalId;
	
	/*
	 * A7med Dakrory
	 * 0 Mr
	 * 1 Ms
	 * 2 Eng
	 * 3 Dr
	 */
	private Integer prefix;
	
	/*
	 * 0 male
	 * 1 female
	 */
	private Integer gender;
	

	private String university;
	
	private String faculty;

	private String major;

	private String study_year;
	

	private Integer graduation_experiences;
	
	/*
	 * A7med Dakrory
	 * 0 for yes
	 * 1 for not
	 */
	private Integer zewailianeOrNot;
	

	private String work;
	

	private String residenceGovernorate;
	
	private String homeGovernorate;



	private Integer active;
	

	private Integer mode;
	private String image;
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
	public Integer getZewailianeOrNot() {
		return zewailianeOrNot;
	}
	public void setZewailianeOrNot(Integer zewailianeOrNot) {
		this.zewailianeOrNot = zewailianeOrNot;
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
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	
	
}
