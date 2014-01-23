package gestionDesTags;

public class Tag {
	private String nom;			//	nom du tag
	private String coul;		// 	couleur du tag
	
	public Tag(){
		this.nom = "New Tag";
		this.coul = randomCoul();
	}
	
	public Tag(String nom){
		this.nom = nom;
		this.coul = randomCoul();
	}
	
	public Tag(String nom, String coul){
		this.nom = nom;
		this.coul = coul;
	}
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCoul() {
		return coul;
	}
	public void setCoul(String coul) {
		this.coul = coul;
	}
	
	public String randomCoul(){
		String random = new String();
		char tmp =' ';
		int temp;
		for (int i = 0; i<6 ; i++){
			temp = (int) (Math.random() * 16);
			
			switch (temp){
			case 0:
				tmp = '0';
				break;
			case 1:
				tmp = '1';
				break;
			case 2:
				tmp = '2';
				break;
			case 3:
				tmp = '3';
				break;
			case 4:	
				tmp = '4';
				break;
			case 5:
				tmp = '5';
				break;
			case 6:
				tmp = '6';
				break;
			case 7:
				tmp = '7';
				break;
			case 8:
				tmp = '8';
				break;
			case 9:
				tmp = '9';
				break;
			case 10:
				tmp = 'a';
				break;
			case 11:
				tmp = 'b';
				break;
			case 12:	
				tmp = 'c';
				break;
			case 13:
				tmp = 'd';
				break;
			case 14:
				tmp = 'e';
				break;
			case 15:	
				tmp = 'f';
				break;
			}
			random = random + tmp;
		}
		
		return random;
	}
}
