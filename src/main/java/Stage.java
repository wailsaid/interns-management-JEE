import java.sql.Date;

public class Stage {
	int id;
	String titre;
	String categorie;
	String niveau;
	String description;
	String etat;
	Date datedebut;
	Date datefin;
	
	
	public void setid(int i) {
		this.id=i;
	}
	public void setitret(String s) {
		this.titre=s;
	}
	public void setniveau(String s) {
		this.niveau=s;
	}
	public void setcategorie(String s) {
		this.categorie=s;
	}
	public void setdescription(String s) {
		this.description=s;
	}
	public void setetat(String s) {
		this.etat=s;
	}
	public void setdateD(Date s) {
		this.datedebut=s;
	}
	public void setdateF(Date s) {
		this.datefin=s;
	}
	public int getid() {
		return this.id;
	}
	public String gettitre() {
		return this.titre;
	}
	public String getcategorie() {
		return this.categorie;
	}
	public String getniveau() {
		return this.niveau;
	}
	public String getdescription() {
		return this.description;
	}
	public String getetat() {
		return this.etat;
	}
	public Date getdateD() {
		return this.datedebut;
	}
	public Date getdateF() {
		return this.datefin;
	}

}
