
public class Admin {
	private int id;
	private String nom;
	private String prenom;
	private String username;
	private String password;
	private String categorie;
	private String direction;
	public void setid(int s) {
		this.id=s;
	}
	public void setusername(String s) {
		this.username=s;
	}
	public void setpassword(String s) {
		this.password=s;
	}
	public void setnom(String s) {
		this.nom=s;
	}
	public void setprenom(String s){
		this.prenom=s;
	}
	public void setcategorie(String s){
		this.categorie=s;
	}
	public void setdirection(String s) {
		this.direction=s;
	}
	public int getid() {
		return this.id;
	}
	public String getusername() {
		return this.username;
	}
	public String getpassword() {
		return this.password;
	}
	public String getnom() {
		return this.nom;
	}
	public String getprenom(){
		return this.prenom;
	}
	public String getcategorie() {
		return this.categorie;
	}
	public String getdirection() {
		return this.direction;
	}
}
