
public class Encadreur {
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String categorie;
	private String direction;

	public void setid(int id) {
		this.id=id;
	}
	public void setnom(String s) {
		this.nom=s;
	}
	public void setprenom(String s){
		this.prenom=s;
	}
	public void setcategorie(String s) {
		this.categorie=s;
	}
	public void setdirection(String s) {
		this.direction=s;
	}
	public int getid() {
		return this.id;
	}
	public void setemail(String s) {
		this.email=s;
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
	public String getemail() {
		return this.email;
	}
	public String getdirection() {
		return this.direction;
	}
}
