
public class Stagiare {
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String niveau;
	private String categorie;
	private String etabalissement;
	private String travail_demander;
	private String etat;
	private String encadreur;
	private String evaluation;
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
	public void setniveau(String s) {
		this.niveau=s;
	}
	public void setdirection(String s) {
		this.direction=s;
	}
	public void setcategorie(String s) {
		this.categorie=s;
	}
	public void setetabalissement(String s) {
		this.etabalissement=s;
	}
	public void settravail(String s) {
		this.travail_demander=s;
	}
	public void setetat(String s) {
		this.etat=s;
	}
	public void setemail(String s) {
		this.email=s;
	}
	public void setencadreur(String s) {
		this.encadreur=s;
	}
	public void setevaluation(String s) {
		this.evaluation=s;
	}
	public int getid() {
		return this.id;
	}
	public String getnom() {
		return this.nom;
	}
	public String getprenom(){
		return this.prenom;
	}
	public String getniveau() {
		return this.niveau;
	}
	public String getcategorie() {
		return this.categorie;
	}
	public String getetabalissement() {
		return this.etabalissement;
	}
	public String gettravail() {
		return this.travail_demander;
	}
	public String getetat() {
		return this.etat;
	}
	public String getemail() {
		return this.email;
	}
	public String getencadreur() {
		return this.encadreur;
	}
	public String getevaluation() {
		return this.evaluation;
	}
	public String getdirection() {
		return this.direction;
	}
	

}
