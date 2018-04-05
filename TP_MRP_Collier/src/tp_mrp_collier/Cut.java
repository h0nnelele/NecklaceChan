package tp_mrp_collier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.*;


public class Cut {

    /**
     * Le tableau représentant une coupe
     */
    private final int[] theCut;

    /**
     * Crée une coupe initiale, intialisée à 0,1,2,...
     * @param t le nombre de coupes
     */
    public Cut(int t) {
        theCut = IntStream.range(0, t).toArray();
    }

    /**
     * C'est un constructeur privé, il initialise une coupe avec des données
     * fournies
     * @param arr
     */
    private Cut(int[] arr) {
        theCut = Arrays.copyOf(arr, arr.length);
    }

    @Override
    public String toString() {
        return "Cut : " + Arrays.stream(theCut).mapToObj(n -> "" + n).collect(Collectors.joining(","));
    }

    /**
     * Effectue un affichage du résultat du découpage
     * @param neckl le collier à découper
     */
    public void applyTo(Necklace neckl) {
        int ind = 0;
        System.out.println("######################");
        int[] chunk;
        for (int c : theCut) {
            chunk = neckl.extract(ind, c + 1);
            System.out.print('[');
            System.out.print(Arrays.stream(chunk).mapToObj(n -> "" + n).collect(Collectors.joining(",")));
            System.out.print("],");
            ind = c + 1;
        }

        chunk = neckl.extract(ind);
        System.out.print('[');
        System.out.print(Arrays.stream(chunk).mapToObj(n -> "" + n).collect(Collectors.joining(",")));
        System.out.println("]");
    }

    /**
     * Détermine si la coupe assure que chaque frère obtienne le même nombre de
     * perles de chaque type
     *
     * @param neckl le collier à découper
     * @return vrai si la coupe est correcte, faux sinon
     */
    public boolean isFair(Necklace neckl) {
        int ind = 0;
        ArrayList<int[]> parts = new ArrayList();
        for (int c : theCut) {
            parts.add(neckl.extract(ind, c + 1));
            ind = c + 1;
        }
        parts.add(neckl.extract(ind));

        int[] brother1 = new int[neckl.getBeads()];
        int[] brother2 = new int[neckl.getBeads()];
        Arrays.fill(brother1, 0);
        Arrays.fill(brother2, 0);

        int lenParts = parts.size();
        for (int i = 0; i < lenParts; i += 2) {
            int[] currentPart = parts.get(i);
            if (currentPart.length == 0) {
                throw new Error("Problème");
            }
            for (int elt : currentPart) {
                brother1[elt]++;
            }
        }

        for (int i = 1; i < lenParts; i += 2) {
            int[] currentPart = parts.get(i);
            if (currentPart.length == 0) {
                throw new Error("Problème");

            }
            for (int elt : currentPart) {
                brother2[elt]++;
            }
        }

        for (int i = 0; i < brother1.length; i++) {
            if (brother1[i] != brother2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cette fonction teste l'égalité des coupes. Ceci permet de comparer les
     * coupes par la méthode equals ou tester l'appartenance à une liste
     * @param obj l'objet à comparer
     * @return vrai uniquement s'il s'agit de la même coupe
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Cut)) {
            return false;
        }
        Cut cut = (Cut) obj;
        for (int i = 0; i < theCut.length; i++) {
            if (theCut[i] != cut.theCut[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cette méthode détermine les actions possibles à partir d'une coupe
     *
     * @param len la longueur du collier
     * @return la liste des coupes que l'on peut dériver
     */
    public ArrayList<Cut> actions(int len) {
        ArrayList<Cut> res = new ArrayList();
        for (int i = 0; i < theCut.length; i++) {
            theCut[i]++;
            if (isValid(theCut, len)) {
                res.add(new Cut(theCut));
            }
            theCut[i]--;

            theCut[i]--;
            if (isValid(theCut, len)) {
                res.add(new Cut(theCut));
            }
            theCut[i]++;
        }
        return new ArrayList<>(res.stream().filter(
                (Cut cut) -> isValid(cut.theCut, len))
                .collect(Collectors.toList())
        );
    }

    private boolean isValid(int[] cut, int len) {
        if (Arrays.stream(cut).filter(i -> i < 0 || i >= len - 1).findFirst().isPresent()) {
            return false;
        }
        for (int i = 1; i < cut.length; i++) {
            if (cut[i] == cut[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    public static void solveDepth(){
        //TODO
    }
    
    public static void solveWidth(){
        //TODO
    }
    
    //cette méthode n'est pas obligatoire
    //mais peut servir à comparer les performances
    public static void solveRandom(){
        //TODO
    }
    
    
    public static void solveHeuristic1(){
        //TODO
    }
    
    public static void solveHeuristic2(){
        //TODO
    }
    
    public static void solveHeuristic3(){
        //TODO
    }
    
    public static void solveHeuristic4(){
        //TODO
    }
}
