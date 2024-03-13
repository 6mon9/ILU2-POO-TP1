package histoire;

import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.Village.VillageSansChefException;

public class ScenarioCasDegrade {
	public static void main(String[] args) {

		Village village = new Village("le village des irréductibles", 10, 5);

		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);

		Gaulois bonemine = new Gaulois("Bonemine", 7);
		village.ajouterHabitant(bonemine);
		System.out.println(village.installerVendeur(bonemine, "fleurs", 10));
		
//		try {
//			Etal etalFleur = village.rechercherEtal(bonemine);
//			System.out.println(etalFleur.acheterProduit(-5, abraracourcix));
//			
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		}
		
		Village village2 = new Village("Nom du village", 100,5);

		try {
            village2.afficherVillageois();
        } catch (VillageSansChefException e) {
            e.printStackTrace();
        }
		
		System.out.println("Fin du test");
	}
}
