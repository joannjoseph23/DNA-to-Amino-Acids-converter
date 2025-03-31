package dmsproj;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.math3.util.Permutations;

import java.util.*;

public class DNAtoAminoAcids {

    public static void main(String[] args) {
        // Step 1: Generate all 64 permutations and combinations of A, T, C, G
        Set<String> nucleotides = new HashSet<>(Arrays.asList("A", "T", "C", "G"));
        Set<String> allPermutations = generatePermutations(nucleotides);
        Set<String> allCombinations = generateCombinations(nucleotides);

        Set<String> setA = new HashSet<>();
        setA.addAll(allPermutations);
        setA.addAll(allCombinations);

        // Step 2: Ask the user to enter a DNA sequence
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter DNA sequence: ");
        String dnaSequence = scanner.nextLine().toUpperCase();

        // Step 3: Convert the DNA sequence into mRNA
        String mRNA = convertToMRNA(dnaSequence);

        // Step 4: Identify codons and print the sequence of amino acids
        identifyCodonsAndPrintAminoAcids(mRNA, setA);
    }

    private static Set<String> generatePermutations(Set<String> nucleotides) {
        List<String> nucleotideList = new ArrayList<>(nucleotides);
        Permutations<String> permutations = new Permutations<>(nucleotideList, 3);

        Set<String> result = new HashSet<>();
        for (int[] perm : permutations) {
            StringBuilder sb = new StringBuilder();
            for (int index : perm) {
                sb.append(nucleotideList.get(index));
            }
            result.add(sb.toString());
        }

        return result;
    }

    private static Set<String> generateCombinations(Set<String> nucleotides) {
        List<String> nucleotideList = new ArrayList<>(nucleotides);
        int[] indices = new int[3];
        Set<String> result = new HashSet<>();

        for (int i = 0; i < nucleotideList.size(); i++) {
            indices[0] = i;
            for (int j = 0; j < nucleotideList.size(); j++) {
                indices[1] = j;
                for (int k = 0; k < nucleotideList.size(); k++) {
                    indices[2] = k;
                    Arrays.sort(indices);
                    StringBuilder sb = new StringBuilder();
                    for (int index : indices) {
                        sb.append(nucleotideList.get(index));
                    }
                    result.add(sb.toString());
                }
            }
        }

        return result;
    }

    private static String convertToMRNA(String dnaSequence) {
        // Replace each base with its complementary base
        StringBuilder mRNA = new StringBuilder();
        for (char base : dnaSequence.toCharArray()) {
            switch (base) {
                case 'A':
                    mRNA.append('U');
                    break;
                case 'T':
                    mRNA.append('A');
                    break;
                case 'C':
                    mRNA.append('G');
                    break;
                case 'G':
                    mRNA.append('C');
                    break;
                default:
                    // Handle unexpected characters, if any
                    System.out.println("Invalid DNA base: " + base);
            }
        }
        return mRNA.toString();
    }


    private static void identifyCodonsAndPrintAminoAcids(String mRNA, Set<String> codonSet) {
        List<String> codons = new ArrayList<>();

        for (int i = 0; i <= mRNA.length() - 3; i += 3) {
            codons.add(mRNA.substring(i, i + 3));
        }

        System.out.println("Codons: " + codons);

        List<String> aminoAcids = new ArrayList<>();
        for (String codon : codons) {
            if (codonSet.contains(codon)) {
                aminoAcids.add(getAminoAcid(codon));
            } else {
                System.out.println("Invalid codon: " + codon);
            }
        }

        System.out.println("Amino Acids: " + aminoAcids);
    }

    private static String getAminoAcid(String codon) {
        // Add your logic to map codons to amino acids
        // This is a simple example, you may need a more detailed mapping
        Map<String, String> codonToAminoAcidMap = new HashMap<>();
        codonToAminoAcidMap.put("UUU", "Phenylalanine");
        codonToAminoAcidMap.put("UUC", "Phenylalanine");
        codonToAminoAcidMap.put("UUA", "Leucine");
        // ... add more mappings as needed

        return codonToAminoAcidMap.getOrDefault(codon, "Unknown");
    }
}
