package utils;

public class UserDescription {
	
	
	private long id;
	
	private String name;
	
	private String surname;
	
	private String email;
	
	private String pseudo;

	private String current_skin;
	
	public UserDescription() {}
	
	public UserDescription(long id, String name, String surname, String email, String pseudo, String current_skin) {
		this.setId(id);
		this.name = name;
		this.surname = surname; 
		this.email = email;
		this.pseudo = pseudo;
		this.current_skin = current_skin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getCurrent_skin() {
		return current_skin;
	}

	public void setCurrent_skin(String current_skin) {
		this.current_skin = current_skin;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
		
}
