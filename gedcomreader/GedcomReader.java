/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gedcomreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;


public class GedcomReader {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        // TODO code application logic here
        GED ged = new GED();
        //in order to improve the universality, the method should be added a parameter of Path
        //if so, the print methods confirm whether the GED exist or not
        ged.traversal();
        
        ged.individualsPrint();
        
        ged.familiesPrint();
        ged.errorsPrint();

        ged.listPrint(ged.deceased,"US29: Deceased individuals:\r\n");
        ged.listPrint(ged.livingMarried,"US30 Living Married individuals:\r\n");
        ged.listPrint(ged.orphans,"US33 Orphan:\r\n");

    }
    
}
