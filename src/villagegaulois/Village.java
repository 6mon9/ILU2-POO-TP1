package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

import java.util.List;
import java.util.ArrayList;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nombreEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nombreEtals);
	}

	private static class Marche {
		private Etal[] etals;

		public Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			for (int i = 0; i < nombreEtals; i++) {
				etals[i] = new Etal();
			}
		}

		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (indiceEtal >= 0 && indiceEtal < etals.length) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			} else {
				System.out.println("Indice invalide");
			}
		}

		public int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if ((etals[i].isEtalOccupe()) == false) {
					return i;
				}
			}
			return -1;
		}

		public Etal[] trouverEtals(String produit) {
			int count = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					if (etals[i].contientProduit(produit)) {
						count++;
					}
				}
			}
			Etal[] result = new Etal[count];
			int index = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					if (etals[i].contientProduit(produit)) {
						result[index++] = etals[i];
					}
				}
			}
			return result;
		}

		public Etal trouverVendeur(Gaulois vendeur) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == vendeur) {
					return etals[i];
				}
			}
			return null;
		}

		public String afficherMarche() {
			StringBuilder result = new StringBuilder();
			int nbEtalVide = 0;

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					result.append(etals[i].afficherEtal()).append("\n");
				} else {
					nbEtalVide++;
				}
			}

			if (nbEtalVide > 0) {
				result.append("Il reste ").append(nbEtalVide).append(" étals non utilisés dans le marché.\n");
			}

			return result.toString();
		}

	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException("definissez un chef");
		}
		
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder texte = new StringBuilder();
		texte.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int nombreEtal = marche.trouverEtalLibre();
		if (nombreEtal == -1) {
			texte.append("Tous les étals sont occupé .\n");
		} else {
			marche.utiliserEtal(nombreEtal, vendeur, produit, nbProduit);
			texte.append(
					"Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + nombreEtal + "\n");
		}
		return texte.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder texte = new StringBuilder();
		Etal[] Produit = marche.trouverEtals(produit);
		if (Produit.length == 0) {
			texte.append("Aucun vendeur ne vend de " + produit + " dans le marché.\n");
		} else if (Produit.length == 1) {
			texte.append("Seul le vendeur " + (Produit[0].getVendeur()).getNom() + " propose des " + produit
					+ " au marché.\n");
		} else {
			texte.append("Les vendeurs qui proposent des fleurs sont :\n");
			for (int i = 0; i < Produit.length; i++) {
				texte.append("- " + (Produit[i].getVendeur()).getNom() + "\n");
			}
		}
		return texte.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}

	public String afficherMarche() {
		return marche.afficherMarche();
	}

	public class VillageSansChefException extends Exception {
		private static final long serialVersionUID =1L;
		
		
		public VillageSansChefException(String message) {
			super(message);
		}
	}
}
