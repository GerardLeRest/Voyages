package logicielslibres.fr.voyage3;

public class NombreMots {

    public String texte;

    public NombreMots(String texte){
        this.texte = texte;
    }

    /**
     * Comptage des mots dans le texte
    */
    public int compterMots(){
        String[] mots = texte.trim().split("\\s+"); //mots: tableau des mots
        return mots.length; //longueur du tableau
    }

     /**
     * occurences du titre dans le texte
     */
    public int occurence(String titre) {
        String[] mots = null;
        //Séparer les mots du texte
        mots = texte.split(" ");
        int compteur = 0;
        for (String mot : mots) {
            //Chercher le mot
            if (mot.equals(titre)) {
                // Si présent, incrémenter le nombre
                compteur++;
            }
        }
        return compteur;
    }
}
