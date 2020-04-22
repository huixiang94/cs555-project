/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gedcomreader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class GED {
    Map<String, Individual> individuals;
    Map<String, Family> families;
    Set<String> errors;
    Set<String> deceased;
    Set<String> livingMarried;
    Set<String> orphans;

    public GED() {
        this.individuals = new LinkedHashMap();
        this.families = new LinkedHashMap();
        this.errors = new LinkedHashSet();
        this.deceased = new LinkedHashSet();
        this.livingMarried = new LinkedHashSet();
        this.orphans = new LinkedHashSet<>();

    }

    private void checkErrors() {// user story check
//        individualsPrint();
//        familiesPrint();
//        datesBeforeCurrentDate();//US01
//        birthBeforeMarriage();//US02
//        birthBeforeDeath();//US03
//        marriageBeforeDivorce();//US04
//        marriageBeforeDeath();//US05
//        divorceBeforeDeath();//US06
//        lessThan150();//US07
//        birthBeforeMarriageOfParents();//US08
//        BirthBeforeDeathOfParents ();//US09
//        MarriageAfter14();//US10
//        noBigamy();//US11
//        parentNotoold();//US12
//        fewerThan15Siblings(); //US15
//        correctGender(); //US21
//        listDeceased();//US29
//        listLivingMarried(); //US30
        listorphans();
        listLargeAgeDifferences();

    }

    public void traversal() throws FileNotFoundException, IOException, ParseException {
        String indKey = null, famKey = null;
        File GEDfile = new File("resource/family-tree.ged");
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
        String line;

        try {
            Scanner sc = new Scanner(GEDfile);

            while (sc.hasNextLine()) {
                line = sc.nextLine().trim();

                if (line.startsWith("0") && line.endsWith("INDI")) {
                    indKey = line.substring(line.indexOf('@') + 1, line.lastIndexOf('@'));
                    Individual person = new Individual();
                    individuals.put(indKey, person);
                    individuals.get(indKey).setID(indKey);
                } else if (line.contains("NAME")) {
                    if (indKey != null) {
                        individuals.get(indKey).setName(line.substring(7));
                    }
                } else if (line.contains("SEX")) {
                    individuals.get(indKey).setGender(line.charAt(line.length() - 1));
                } else if (line.contains("BIRT")) {
                    if (sc.hasNextLine()) {
                        line = sc.nextLine().trim();

                        if (line.startsWith("2") && line.contains("DATE")) {
                            individuals.get(indKey).setBirthday(formatter.parse(line.substring(7)));
                        }
                    }
                } else if (line.contains("DEAT")) {
                    individuals.get(indKey).setAlive(false);

                    if (sc.hasNextLine()) {
                        line = sc.nextLine().trim();

                        if (line.startsWith("2") && line.contains("DATE")) {
                            individuals.get(indKey).setDeath(formatter.parse(line.substring(7)));
                        }
                    }
                } else if (line.contains("FAMC")) {
                    individuals.get(indKey).addFAMC(line.substring(line.indexOf('@') + 1, line.lastIndexOf('@')));
                } else if (line.contains("FAMS")) {
                    individuals.get(indKey).addFAMS(line.substring(line.indexOf('@') + 1, line.lastIndexOf('@')));
                } else if (line.startsWith("0") && line.endsWith("FAM")) {
                    famKey = line.substring(line.indexOf('@') + 1, line.lastIndexOf('@'));
                    Family family = new Family();
                    families.put(famKey, family);
                    families.get(famKey).setID(famKey);
                } else if (line.contains("HUSB")) {
                    families.get(famKey).setHusbandID(line.substring(line.indexOf('@') + 1, line.lastIndexOf('@')));
                } else if (line.contains("WIFE")) {
                    families.get(famKey).setWifeID(line.substring(line.indexOf('@') + 1, line.lastIndexOf('@')));
                } else if (line.contains("CHIL")) {
                    families.get(famKey).addChildren(line.substring(line.indexOf('@') + 1, line.lastIndexOf('@')));
                } else if (line.contains("MARR")) {
                    if (sc.hasNextLine()) {
                        line = sc.nextLine().trim();

                        if (line.startsWith("2") && line.contains("DATE")) {
                            families.get(famKey).setMarried(formatter.parse(line.substring(7)));
                        }
                    }
                } else if (line.contains("DIV")) {
                    if (sc.hasNextLine()) {
                        line = sc.nextLine().trim();

                        if (line.startsWith("2") && line.contains("DATE")) {
                            families.get(famKey).setDivorced(formatter.parse(line.substring(7)));
                        }
                    }
                }
            }

            //get husband's name and wife's name by the connection between two classes

            for (Map.Entry<String, Family> famEnt : families.entrySet()) {
                famEnt.getValue().setHusbandName(individuals.get(famEnt.getValue().getHusbandID()).getName());
                famEnt.getValue().setWifeName(individuals.get(famEnt.getValue().getWifeID()).getName());
            }

            sc.close();
        } catch (IOException | ParseException e) {
            System.err.println(e.toString());
        }

    }
    private static int getAgeByBirthAndDeath(Date birthday, Date Death) {
        int age = 0;

        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());

            Calendar birth = Calendar.getInstance();
            Calendar death = Calendar.getInstance();
            birth.setTime(birthday);

            if(Death == null){
            }
            if (birth.after(now)) {
                age = 0;
            } else{
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                    age -= 1;
                }
            }

            if(Death != null){
                death.setTime(Death);
                if(birth.after(death)){
                    age = 0;
                }else{
                    age = death.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                    if (death.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                        age -= 1;
                    }
                }
            }

            return age;
        } catch (Exception e) {
            System.err.println(e.toString());
            return -1;
        }
    }
    private static int getAgeByBirth(Date birthday) {//US27
        int age = 0;

        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
                    age -= 1;
                }
            }
            return age;
        } catch (Exception e) {
            System.err.println(e.toString());
            return -1;
        }
    }

    public void individualsPrint() {
        String indId, nam, gend, indBirthday, age, alive, death, child, spouse;
        SimpleDateFormat indFormatNoE = new SimpleDateFormat("yyyy-MM-dd");
        File fileOut = new File("resource/family-tree.txt");
        ConsoleTable tI = new ConsoleTable(9, true);

        tI.appendRow();
        tI.appendColum("ID").appendColum("Name").appendColum("Gender").appendColum("Birthday").appendColum("Age").appendColum("Alive").appendColum("Death").appendColum("Child").appendColum("Spouse");


        try {
            for (Map.Entry<String, Individual> indEnt : individuals.entrySet()) {
                indId = indEnt.getValue().getID();

                nam = indEnt.getValue().getName();

                gend = String.valueOf(indEnt.getValue().getGender());

                indBirthday = indFormatNoE.format(indEnt.getValue().getBirthday());

                age = String.valueOf(GED.getAgeByBirth(indEnt.getValue().getBirthday()));

                child = "{}";
                spouse = "{}";

                if (indEnt.getValue().getDeath() == null) {
                    alive = "True";
                } else alive = "False";

                if (indEnt.getValue().getDeath() == null) {
                    death = "NA";
                } else {
                    death = indFormatNoE.format(indEnt.getValue().getDeath());
                }

                if (indEnt.getValue().getFAMC().isEmpty()) {
                    child = "NA";
                } else {
                    Iterator<String> it = indEnt.getValue().getFAMC().iterator();
                    child = getString(child, it);
                }

                if (indEnt.getValue().getFAMS().isEmpty()) {
                    spouse = "NA";
                } else {
                    Iterator<String> it = indEnt.getValue().getFAMS().iterator();
                    spouse = getString(spouse, it);
                }

                tI.appendRow();
                tI.appendColum(indId).appendColum(nam).appendColum(gend).appendColum(indBirthday).appendColum(age).appendColum(alive).appendColum(death).appendColum(child).appendColum(spouse);
            }

            if (!fileOut.exists()) {
                fileOut.createNewFile();
            }

            FileWriter fw = new FileWriter(fileOut, true);
            BufferedWriter out = new BufferedWriter(fw);

            out.write("Individuals:\n");
            out.write(tI.toString());
            out.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }

    }

    private String getString(String spouse, Iterator<String> it) {
        StringBuilder sb2 = new StringBuilder(spouse);

        while (it.hasNext()) {
            String str = it.next();
            sb2.insert(sb2.length() - 1, "'" + str + "',");
        }

        if (sb2.toString().endsWith(",}")) {
            sb2.replace(sb2.toString().length() - 2, sb2.toString().length() - 1, "");
        }

        spouse = sb2.toString();
        return spouse;
    }

    public void familiesPrint() {
        String idF, married, divorced, husbId, husbName, wifeId, wifName, children;
        SimpleDateFormat formatNoE = new SimpleDateFormat("yyyy-MM-dd");
        File fileOut = new File("resource/family-tree.txt");
        ConsoleTable tF = new ConsoleTable(8, true);

        tF.appendRow();
        tF.appendColum("ID").appendColum("Married").appendColum("Divorced").appendColum("Husband ID").appendColum("Husband Name").appendColum("Wife ID").appendColum("Wife Name").appendColum("Children");


        try {
            for (Map.Entry<String, Family> famEnt : families.entrySet()) {
                idF = famEnt.getValue().getID();

                married = formatNoE.format(famEnt.getValue().getMarried());

                if (famEnt.getValue().getDivorced() == null) {
                    divorced = "NA";
                } else {
                    divorced = formatNoE.format(famEnt.getValue().getDivorced());
                }

                husbId = famEnt.getValue().getHusbandID();

                husbName = famEnt.getValue().getHusbandName();

                wifeId = famEnt.getValue().getWifeID();

                wifName = famEnt.getValue().getWifeName();

                children = "{}";

                if (famEnt.getValue().getChildren().isEmpty()) {
                    children = "NA";
                } else {
                    Iterator<String> it = famEnt.getValue().getChildren().iterator();
                    children = getString(children, it);
                }
                tF.appendRow();
                tF.appendColum(idF).appendColum(married).appendColum(divorced).appendColum(husbId).appendColum(husbName).appendColum(wifeId).appendColum(wifName).appendColum(children);
            }

            if (!fileOut.exists()) {
                fileOut.createNewFile();
            }

            FileWriter fw = new FileWriter(fileOut, true);
            BufferedWriter out = new BufferedWriter(fw);

            out.write("Families:\n");
            out.write(tF.toString());
            out.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }

    }

    private void datesBeforeCurrentDate() {//US01
        Date now = new Date();

        try {
            for (Map.Entry<String, Individual> indEnt : individuals.entrySet()) {
                if (indEnt.getValue().getBirthday() == null) {
                } else if (indEnt.getValue().getBirthday().after(now))
                    errors.add("Error US01: Birthday of " + indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ") occurrs after the current date");

                if (indEnt.getValue().getDeath() == null) {
                } else if (indEnt.getValue().getDeath().after(now))
                    errors.add("Error US01: Death day of " + indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ") occurrs after the current date");
            }

            for (Map.Entry<String, Family> famEnt : families.entrySet()) {
                if (famEnt.getValue().getMarried() == null) {
                } else if (famEnt.getValue().getMarried().after(now))
                    errors.add("Error US01: Married day of " + famEnt.getValue().getHusbandName() + " and " + famEnt.getValue().getWifeName() + "(family:" + famEnt.getValue().getID() + ") occurrs after the current date");

                if (famEnt.getValue().getDivorced() == null) {
                } else if (famEnt.getValue().getDivorced().after(now))
                    errors.add("Error US01: Divorced day of " + famEnt.getValue().getHusbandName() + " and " + famEnt.getValue().getWifeName() + "(family:" + famEnt.getValue().getID() + ") occurrs after the current date");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void birthBeforeMarriage() {//US02


        try {
            for (Map.Entry<String, Individual> indEnt : individuals.entrySet()) {
                for (String str : indEnt.getValue().getFAMS()) {
                    if (families.get(str).getMarried() == null) {
                    } else if (families.get(str).getMarried().before(indEnt.getValue().getBirthday()))
                        errors.add("Error US02: Marriaged date of " + indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ") in the family of " + families.get(str).getID() + " is before the birthday");
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void birthBeforeDeath() { //US03
        Iterator<Map.Entry<String, Individual>> indIt = individuals.entrySet().iterator();

        try {
            while (indIt.hasNext()) {
                Map.Entry<String, Individual> indEnt = indIt.next();

                if (indEnt.getValue().getBirthday() == null) {

                } else if (indEnt.getValue().getDeath() == null) {

                } else if (!indEnt.getValue().getBirthday().before(indEnt.getValue().getDeath()))
                    errors.add("Error US03: Birth date of " + indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ")" + " occurs after death date.");

            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void marriageBeforeDivorce() { //US04
        Iterator<Map.Entry<String, Family>> famIt = families.entrySet().iterator();

        try {
            while (famIt.hasNext()) {
                Map.Entry<String, Family> famEnt = famIt.next();

                if (famEnt.getValue().getMarried() == null) {

                } else if (famEnt.getValue().getDivorced() == null) {

                } else if (!famEnt.getValue().getMarried().before(famEnt.getValue().getDivorced()))
                    errors.add("Error US04: Family" + "(" + famEnt.getValue().getID() + ")" + " Husband: "
                            + famEnt.getValue().getHusbandName() + "(" + famEnt.getValue().getHusbandID() + ")" + " Wife: "
                            + famEnt.getValue().getWifeName() + "(" + famEnt.getValue().getWifeID() + ")" + " married date occurs after divorced.");

            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void marriageBeforeDeath() { //US05
        Iterator<Map.Entry<String, Individual>> indIt = individuals.entrySet().iterator();
        try {
            while (indIt.hasNext()) {
                Map.Entry<String, Individual> indEnt = indIt.next();
                Iterator<String> spIt = indEnt.getValue().getFAMS().iterator();
                Individual person = indEnt.getValue();

                while (person.getFAMS() != null && spIt.hasNext()) {
                    String str = spIt.next();
                    if (families.get(str).getMarried() == null) {
                    } else if (person.getDeath() == null) {
                    } else if (compareDate(families.get(str).getMarried(), person.getDeath()))
                        errors.add("Error US05: Married date of " + person.getName() + "(" + person.getID() + ") in the family of " + families.get(str).getID() + " is after the death date.");
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * define resuable function instead of sophisticated expression
     * @param first
     * @param second
     * @return
     */
    public static boolean compareDate(Date first, Date second) {
        if (first.after(second)) {
            return true;
        }
        return false;
    }

    private void divorceBeforeDeath() { //US06
        Iterator<Map.Entry<String, Individual>> indIt = individuals.entrySet().iterator();

        try {
            while (indIt.hasNext()) {
                Map.Entry<String, Individual> indEnt = indIt.next();
                Iterator<String> spIt = indEnt.getValue().getFAMS().iterator();
                while (indEnt.getValue().getFAMS() != null && spIt.hasNext()) {
                    String str = spIt.next();
                    if (families.get(str).getDivorced() == null) {
                    } else if (indEnt.getValue().getDeath() == null) {
                    } else if (compareDate(families.get(str).getDivorced(), indEnt.getValue().getDeath()))
                        errors.add("Error US06: Divorced date of " + indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ") in the family of " + families.get(str).getID() + " is after the death date.");
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    private void lessThan150() { //US07
        Date now = new Date();
        Iterator<Map.Entry<String, Individual>> indIt = individuals.entrySet().iterator();

        try {
            while(indIt.hasNext()){
                Map.Entry<String, Individual> indEnt = indIt.next();
                Iterator<String> spIt = indEnt.getValue().getFAMS().iterator();
                int age;
                if (indEnt.getValue().getDeath() == null){
                    age = GED.getAgeByBirthAndDeath(indEnt.getValue().getBirthday(), now);
                }
                else{
                    age = GED.getAgeByBirthAndDeath(indEnt.getValue().getBirthday(),indEnt.getValue().getDeath());
                }
                if (age > 150){
                    errors.add("Error US07: age ofDinkar /Chikane/is greater than 150.");
                }
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void birthBeforeMarriageOfParents() { //US08
        Iterator<Map.Entry<String, Individual>> indIt = individuals.entrySet().iterator();
        Calendar birth = Calendar.getInstance();
        Calendar divorced = Calendar.getInstance();

        try {
            while (indIt.hasNext()) {
                Map.Entry<String, Individual> indEnt = indIt.next();
                Iterator<String> famcIt = indEnt.getValue().getFAMC().iterator();

                while (famcIt.hasNext()) {
                    String str = famcIt.next();
                    birth.setTime(indEnt.getValue().getBirthday());
                    divorced.setTime(families.get(str).getMarried());

                    if (families.get(str).getMarried() == null) {

                    }
                    else if (indEnt.getValue().getBirthday() == null) {

                    }else if (families.get(str).getDivorced() == null) {

                    }
                    else if (families.get(str).getMarried().after(indEnt.getValue().getBirthday()))
                        errors.add("Error US08: Birthday of " + indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ") in the family of " + families.get(str).getID() + " is before the marriage of parents.");
                    else if (((birth.get(Calendar.YEAR) - divorced.get(Calendar.YEAR))*12 + (birth.get(Calendar.MONTH) - divorced.get(Calendar.MONTH)) > 9))
                        errors.add("Error US08: Birthday of " + indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ") in the family of " + families.get(str).getID() + " is more than 9 months after parents divorce.");
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
   public void BirthBeforeDeathOfParents () {//US09
        try {
            for (Map.Entry<String,Family> entry: families.entrySet()) {
                if (individuals.get(entry.getValue().getHusbandID()).getDeath() != null ||
                        individuals.get(entry.getValue().getWifeID()).getDeath() != null) {
                    for (String child : entry.getValue().getChildren()) {
                        if (individuals.get(entry.getValue().getHusbandID()).getDeath() != null) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(individuals.get(entry.getValue().getHusbandID()).getDeath());
                            cal.add(Calendar.MONTH, 9);
                            Date tempDate = cal.getTime();
                            if (individuals.get(child).getBirthday().after(tempDate))
                                errors.add("Error US09: Birthday of " + individuals.get(child).getName() + "(" + child + ") is after 9 months of father's Death date in the family of " + entry.getValue().getID());
                        }
                        else if (individuals.get(entry.getValue().getWifeID()).getDeath() != null && individuals.get(child).getBirthday().after(individuals.get(entry.getValue().getWifeID()).getDeath())) {
                            errors.add("Error US09: Birthday of " + individuals.get(child).getName() + "(" + child + ") is after mother's Death date in the family of " + entry.getValue().getID());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void MarriageAfter14() {//US10
        try {
            for (Map.Entry<String, Family> entry: families.entrySet()) {
                if (getAgeByBirth(individuals.get(entry.getValue().getHusbandID()).getBirthday()) < 14) {
                    errors.add("Error US10: " + individuals.get(entry.getValue().getHusbandID()).getName() + "（" + entry.getValue().getHusbandID() + ")" + " is less than 14, he illegally married, in the family of " + entry.getValue().getID());
                }
                else if (getAgeByBirth(individuals.get(entry.getValue().getWifeID()).getBirthday()) < 14) {
                    errors.add("Error US10: " + individuals.get(entry.getValue().getWifeID()).getName() + "（" + entry.getValue().getWifeID() + ")" + " is less than 14; she illegally married, in the family of " + entry.getValue().getID());
                }
                else {
                    Calendar marriedCal = Calendar.getInstance();
                    marriedCal.setTime(entry.getValue().getMarried());
                    Calendar tempBirth = Calendar.getInstance();
                    tempBirth.setTime(individuals.get(entry.getValue().getHusbandID()).getBirthday());
                    if (marriedCal.get(Calendar.YEAR) - tempBirth.get(Calendar.YEAR) <  14) {System.err.println(tempBirth.get(Calendar.YEAR) +" < "+ marriedCal.get(Calendar.YEAR)+" - 14");
                        errors.add("Error US10: " + individuals.get(entry.getValue().getHusbandID()).getName() + "（" + entry.getValue().getHusbandID() + ")" + " was less than 14, when he got married, in the family of " + entry.getValue().getID());
                    }
                    tempBirth.setTime(individuals.get(entry.getValue().getWifeID()).getBirthday());
                    if (marriedCal.get(Calendar.YEAR) - tempBirth.get(Calendar.YEAR) <  14)
                        errors.add("Error US10: " + individuals.get(entry.getValue().getWifeID()).getName() + "（" + entry.getValue().getWifeID() + ")" + " was less than 14, when she got married, in the family of " + entry.getValue().getID());
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    public void noBigamy(){ //US11
        Iterator<Map.Entry<String, Family>> famIt = families.entrySet().iterator();
        Set<String> id = new HashSet<>();
        try {
                while (famIt.hasNext()) {
                    String str = famIt.next().getKey();
                    if ( families.get(str).getDivorced()==null) {
                        if(id.contains(families.get(str).HusbandID)){
                            errors.add("Error US11: "+families.get(str).HusbandName + " is a bigamist");
                        }
                        else if(id.contains(families.get(str).WifeID)){
                            errors.add("Error US11: "+families.get(str).WifeName + " is a bigamist");
                        }
                        else{
                            id.add(families.get(str).HusbandID);
                            id.add(families.get(str).WifeID);
                        }
                    }
                }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    public void parentNotoold(){ //US12
        Iterator<Map.Entry<String, Family>> famIt = families.entrySet().iterator();
        Calendar husbandBirth = Calendar.getInstance();
        Calendar wifeBirth = Calendar.getInstance();
        Calendar childBirth = Calendar.getInstance();

        try {
            while (famIt.hasNext()) {
                Map.Entry<String, Family> famEnt = famIt.next();
                Iterator<String> chIt = famEnt.getValue().getChildren().iterator();

                while (chIt.hasNext()) {
                    String str = chIt.next();
                    husbandBirth.setTime(individuals.get(famEnt.getValue().getHusbandID()).getBirthday());
                    wifeBirth.setTime(individuals.get(famEnt.getValue().getWifeID()).getBirthday());
                    childBirth.setTime(individuals.get(str).getBirthday());

                    if (famEnt.getValue().getHusbandID() == null) {
                    }
                    else if(childBirth.get(Calendar.YEAR) - husbandBirth.get(Calendar.YEAR) >= 80 ){
                        errors.add("Error US12: Age of father " + famEnt.getValue().getHusbandName() + "(" + famEnt.getValue().getHusbandID() + ") is more than 80 years older than his children " + individuals.get(str).getName() + "(" + individuals.get(str).getID() + ").");
                    }

                    if (famEnt.getValue().getWifeID() == null) {
                    }else if(childBirth.get(Calendar.YEAR) - wifeBirth.get(Calendar.YEAR) >= 60){
                        errors.add("Error US12: Age of mother " + famEnt.getValue().getWifeName() + "(" + famEnt.getValue().getWifeID() + ") is more than 60 years older than her children " + individuals.get(str).getName() + "(" + individuals.get(str).getID() + ").");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    private void fewerThan15Siblings() { //US15
        Iterator<Map.Entry<String, Family>> famIt = families.entrySet().iterator();

        try {
            while (famIt.hasNext()) {
                Map.Entry<String, Family> famEnt = famIt.next();
                Iterator<String> chIt = famEnt.getValue().getChildren().iterator();
                int count = 0;

                while (chIt.hasNext()) {
                    String str = chIt.next();
                    if (famEnt.getValue().getChildren() == null) {
                    }else{
                        count += 1;
                    }
                }
                //System.out.print(famEnt.getValue().getID()+" "  + count +" ");
                if(count >= 15)
                    errors.add("Error US15: Family (" + famEnt.getValue().getID() + ") " + "have 15 or more siblings");
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void correctGender() { //US21
        Iterator<Map.Entry<String, Family>> famIt = families.entrySet().iterator();

        try {
            while (famIt.hasNext()) {
                Map.Entry<String, Family> famEnt = famIt.next();
                if (individuals.get(famEnt.getValue().getHusbandID()).getGender() == 'F') {
                    errors.add("Error US21: Husband in family "+famEnt.getValue().getID()+ " has a wrong gender.");
                }
                if (individuals.get(famEnt.getValue().getWifeID()).getGender() == 'M') {
                    errors.add("Error US21: Wife in family "+famEnt.getValue().getID()+" has a wrong gender.");
                }
            }
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void listDeceased() { //US29
        Iterator<Map.Entry<String, Individual>> indIt = individuals.entrySet().iterator();

        try {
            while (indIt.hasNext()) {
                Map.Entry<String, Individual> indEnt = indIt.next();

                if(indEnt.getValue().getDeath() !=null) {
                    deceased.add( indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ")");
                }
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    private void listLivingMarried() { //US30
        Iterator<Map.Entry<String, Individual>> indIt = individuals.entrySet().iterator();

        try {
            while (indIt.hasNext()) {
                Map.Entry<String, Individual> indEnt = indIt.next();
                Iterator<String> famsIt = indEnt.getValue().getFAMS().iterator();

                if(indEnt.getValue().getDeath() == null) {
                    while (famsIt.hasNext()) {

                        livingMarried.add(indEnt.getValue().getName() + "(" + indEnt.getValue().getID() + ")");
                        break;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    private void listorphans(){//US33
        Iterator<Map.Entry<String,Family>> famIt = families.entrySet().iterator();
        try {
            while (famIt.hasNext()) {
                Map.Entry<String, Family> famEnt = famIt.next();
                if(famEnt.getValue().Children!=null){
                    if(individuals.get(famEnt.getValue().getHusbandID()).Death!=null&&individuals.get(famEnt.getValue().getWifeID()).Death!=null)
                    {
                        for(String child:famEnt.getValue().Children){
                            orphans.add(individuals.get(child).Name+child);
                        }
                    }
                }

            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    private void listLargeAgeDifferences() { //US34
        Iterator<Map.Entry<String,Family>> famIt = families.entrySet().iterator();
        Set<String> ageList = new LinkedHashSet<>();
        int hAge;
        int wAge;

        try {
            while (famIt.hasNext()) {
                Map.Entry<String, Family> famEnt = famIt.next();
                hAge = GED.getAgeByBirthAndDeath(individuals.get(famEnt.getValue().getHusbandID()).getBirthday(),famEnt.getValue().getMarried());
                wAge = GED.getAgeByBirthAndDeath(individuals.get(famEnt.getValue().getWifeID()).getBirthday(),famEnt.getValue().getMarried());
                if (hAge >= wAge * 2 || wAge >= hAge * 2){
                    errors.add("Family "+famEnt.getValue().getID()+" has a large age difference.");
                }
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public void errorsPrint() {
        checkErrors();//check with users stories
        File fileOut = new File("resource/family-tree.txt");
        Iterator<String> errIt = errors.iterator();

        try {
            if (!fileOut.exists()) {
                fileOut.createNewFile();
            }

            FileWriter fw = new FileWriter(fileOut, true);
            BufferedWriter out = new BufferedWriter(fw);

            out.write("Errors:\n");


            while (errIt.hasNext()) {
                String errStr = errIt.next();
                out.write(errStr + "\n");
            }

            out.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    public void listPrint (Set<String> s, String title) {
        if(s != null){
            File fileOut = new File("resource/family-tree.txt");
            Iterator<String> listIt = s.iterator();

            try {
                if (!fileOut.exists()) {
                    fileOut.createNewFile();
                }

                FileWriter fw = new FileWriter(fileOut, true);
                BufferedWriter out = new BufferedWriter(fw);

                out.write("\r\n");
                out.write(title);

                while (listIt.hasNext()) {
                    String list = listIt.next();
                    out.write(list + "\r\n");
                }

                out.close();
            } catch (IOException e) {
                System.err.println(e.toString());
            }
        }
    }
}
