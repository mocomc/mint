package pep.per.mint.common.data;


import com.fasterxml.jackson.annotation.JsonIgnore;


public class Foo extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5496801455909505638L;
	

	String name;
	int age;
	String regDate;

	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	@JsonIgnore
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("id:").append(id).append("\n");
		sb.append("name:").append(name).append("\n");
		sb.append("age:").append(age).append("\n");
		sb.append("regDate:").append(regDate).append("\n");
		return  sb.toString();
	}
	
	

}
