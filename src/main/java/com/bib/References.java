package com.bib;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class References {
    
    private final List<Reference> list = new ArrayList<>();

    public void add(Reference reference) {
        if (isDuplicateKey(reference.getKey())) {
            System.out.println("Reference key already exists!");
            return;
        }

        list.add(reference);
        
        try {
            saveToFile();
        } catch (Exception e) {
            System.err.println(e);
        }
        
    }

    
    public boolean edit(Reference oldRef, Reference newRef) {
        if (!list.contains(oldRef)) {
            return false;
        }
        list.remove(oldRef);
        list.add(newRef);

        try {
            saveToFile();
        } catch (Exception e) {
            System.err.println(e);
        }
        return true;
    }


    public boolean delete(Reference reference) {
        return list.remove(reference);
        //tallentaa prompter luokassa
    }


    /**
     * Listaa kaikki viitteet hakuparametrien mukaan
     * @param searchParam Käyttäjän antama hakusana
     * @param fieldname Type, data, tag tai null
     */
    public void printReferences(String searchParam, String fieldname) {
        boolean match = false;

        if (searchParam == null || searchParam.equalsIgnoreCase("")) {
            for (Reference ref : list) {
                match = true;
                System.out.println(ref);
                System.out.println("----------------------------------");
            }
        }

        else if (fieldname.equals("Type")) {
            for (Reference ref : list) {
                if (ref.getType().toLowerCase().contains(searchParam.toLowerCase())) {
                    match = true;
                    System.out.println(ref);
                    System.out.println("----------------------------------");
                }
            }
        }

        else if (fieldname.equals("Data")) {
            for (Reference ref : list) {
                if (containsValueCaseInsensitive(ref.getData().values(), searchParam)) {
                    match = true;
                    System.out.println(ref);
                    System.out.println("----------------------------------");
                }
            }
        }

        else if (fieldname.equals("Tag")) {
            for (Reference ref : list) {
                if (ref.getTag().toLowerCase().contains(searchParam.toLowerCase())) {
                    match = true;
                    System.out.println(ref);
                    System.out.println("----------------------------------");
                }
            }
        }
        if (!match) {
            System.out.println("\nReferences not found");
        }
    }


    /**
     * Etsitään lähde viiteavaimen perusteella ja palautetaan lähdeolio
     * @param key viitteen yksilöivä avain
     * @return viiteolio
     */
    public Reference findReferenceByKey(String key) {
        for (Reference ref : list) {
            if (ref.getKey().equals(key)) {
                return ref;
            }
        }
        return null;
    } 


    public int getSize() {
        return list.size();
    }


    /**
     * Tarkistetaan, onko avain jo olemassa 
     * @param key viitteen yksilöivä avain
     * @return totuusarvo siitä, onko viiteavain jo olemassa
     */
    public boolean isDuplicateKey(String key) {
        for (Reference ref : list) {
            if (ref.getKey().equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    } 

    public void saveToFile() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Reference ref : list) {
            StringBuilder refSB = new StringBuilder(ref.toString());
            
            sb.append("@" + refSB.substring(6, refSB.indexOf("\n")) + "{");
            refSB = new StringBuilder(refSB.substring(refSB.indexOf("\n") + 1));

            sb.append(refSB.substring(5, refSB.indexOf("\n")) + ",\n");
            refSB = new StringBuilder(refSB.substring(refSB.indexOf("\n") + 1));
            
            String temp = refSB.toString();
            temp = temp.replace(": ", " = {");
            refSB = new StringBuilder(temp);

            while (true) {
                if (refSB.indexOf("\n") == -1) {
                    break;
                }
                sb.append("\t" + refSB.substring(0, refSB.indexOf("\n")) + "},\n");

                refSB = new StringBuilder(refSB.substring(refSB.indexOf("\n") + 1));
            }
            sb.append("}\n\n");
        }

        Files.write(Paths.get("src/data/references.bib"), sb.toString().getBytes());
        System.out.println(sb);
    }


    /**
     * Apufunktio poistamaan isot kirjaimet datahausta
     * @param values data-arvot
     * @param searchParam hakuparametri
     * @return totuusarvo
     */
    private boolean containsValueCaseInsensitive(java.util.Collection<String> values, String searchParam) {
        for (String value : values) {
            if (value != null && value.toLowerCase().contains(searchParam.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}