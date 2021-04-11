import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class StreamDemo {
    public static void main(String[] args) {

        String [] chaine = {"f","o","o","b","a","r"};

        System.out.println(concatString(chaine));

        System.out.println(concatString2(chaine));

        System.out.println(concatString3(chaine));

        orderString(chaine);

        int[] array = { 1, 4, 5, 6, 7, -1 };

        //System.out.println(array[closeIndex]);

        System.out.println(stringJoin());

        List<String> myString=Arrays.asList("L","a","r","b","i");
        System.out.println(concatString(myString));

        findDifferentWords();
        findDistinctWords2();

    }

    // Exercice 1 : Determiner la valuer la plus proche de zero dans un tableau

    // Avant java 8
    public static int closestToZero1(int[] array) {
        int closeIndex = 0;
        int diff = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            int abs = abs(array[i]);
            if (abs < diff) {
                closeIndex = i;
                diff = abs;
            } else if (abs == diff && array[i] > 0 && array[closeIndex] < 0) {
                // same distance to zero but positive
                closeIndex = i;
            }
        }
        return array[closeIndex];
    }

    // Avec Java 8
    public static int closestToZero2( int [] array) {
        return Arrays.stream(array)
                .filter(i -> i != 0)
                .reduce((a, b) -> abs(a) < abs(b) ? a : (abs(a) == abs(b) ? max(a, b) : b))
                .getAsInt();
    }

    //Autre méthode à implémenter => Diviser le tableau en deux

    // Exercice 2 : Concaténation des chaines de caractères

    // Méthode 1
    public static String concatString(String [] chaine) {
        return Arrays.stream(chaine)
                .map(e -> e.toString())
                .reduce("", String::concat);
    }

    // Méthode 2
    public static StringBuilder concatString2(String [] chaine) {

        StringBuilder b = new StringBuilder();
        Arrays.stream(chaine).forEach(b::append);
        return b;
    }

    // Méthode 3
    public static String concatString3(String [] chaine) {
        return Arrays.stream(chaine)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    // Méthode 4
    public static String stringJoin() {
        StringJoiner stringJoiner=new StringJoiner(", "); // utilisation d'un delimiter ici
        stringJoiner.add("Larbi");
        stringJoiner.add("Ben");
        stringJoiner.add("Romdhane");
        return stringJoiner.toString();

    }

    // Méthode 5
    public static String concatString(List <String> chaine) {
        return chaine.stream().collect(Collectors.joining(""));
    }

    //Excercice 3: Afficher les élèments d'une liste commençant par b en majuscule et dans l'ordre

    public static void orderString(String [] chaine) {
        Arrays.stream(chaine)
                .filter(s -> s.startsWith("b"))
                .map(String::toUpperCase)
                .sorted().forEach(System.out::println);

    }

    //Exercice 4:Afficher le nombre de paires uniques dans un tableau
    public static int countPairs(int [] array,int n) {

        int count =0;
        HashSet<Integer> s= new HashSet<Integer>();
        for (int i=0;i<n;i++) {
            s.add(array[i]);
        }
        count = (int) Math.pow(s.size(), 2);
        return count;
    }

    //Exercice 5 : Calculer la somme d'un tableau de integer

    public static int somme1(int [] arr) {
        return Arrays.stream(arr).reduce(0, (a,b) -> a+b);
    }

    public static int somme2(int [] arr) {
        return Arrays.stream(arr).reduce(0, Integer::sum);
    }

    public static int somme3(int [] arr) {
        return Arrays.stream(arr).reduce(0, (a,b) -> a+b);
    }

    //Exercice 6 : Former une chaine de caractére de type a|b|c|d

    public static String formString(String [] s) {
        return Arrays.stream(s).reduce("", (a,b) -> a + "|" + b);
    }

    public static String formString2(String [] s) {
        return String.join("|", s);
    }

    // Exercice 7: Convertir une liste de caractère en Majuscule

    public static List<String> convertToUpper(String [] chaine){
        return Arrays.stream(chaine).map(String::toUpperCase).collect(Collectors.toList());
    }

    // Exercice 8: Multiplier chaque élèment d'une liste par deux

    public static List<Integer> multilplyBy2(List <Integer> array){
        return array.stream().map(n -> n*2).collect(Collectors.toList());
    }

    // Exercice 9: Trouver les 3 petites différentes valeur dans un tableau
    //Pour les streams , il y a 3 différentes parties :
    //1-Create
    //2-Process
    //3-Consume
    public static void findSmallestValues(int [] numbers) {
        IntStream.of(numbers)    // Create
                .distinct()     // Process
                .sorted()      // Process
                .limit(3)      // Process
                .forEach(System.out::println);  // Consume
    }

    // Exercice 10 : Déterminer les mots différents dans un champ texte
    // Méthode 1 sans les streams
    public static void findDifferentWords() {

        // Lire le fichier texte
        Path path= Paths.get("C:\\Users\\lbenromd\\Desktop\\test.txt");
        Set setOfWords = new HashSet();
        try {
            List<String> lines= Files.readAllLines(path);
            for(String line:lines) {
                String [] words =line.split("\\s+"); // Splitter pour un ou plusieurs espaces
                for (String word:words) {
                    setOfWords.add(word);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Distinct words from traditional approach are : " +setOfWords);
    }

   // Méthode 2 : avec stream

    public static void findDistinctWords2() {
        Path path=Paths.get("C:\\Users\\lbenromd\\Desktop\\test.txt");
        try {
            Files.lines(path)
                    .map(e -> e.split("\\s+"))
                    // Stream of String array : Stream [String []]
                    .flatMap(Arrays::stream) // Stream of stream Stream(Stream<String>)
                    .distinct()
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
