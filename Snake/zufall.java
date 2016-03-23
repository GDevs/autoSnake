public class zufall
{
    int rounds =100000;
    
    public zufall()
    {
        int rigth=0;
        int wrong=0;
        for(int i = 0;i<rounds;i++){
            double zZ1 = Math.random()*100000; /** zufallsZahl1 */
            double zZ2 = Math.random()*100000; /** zufallsZahl2 */
            double iZ  = Math.random()*100000; /** imaginäre Zahl */

            if (iZ > zZ1) /** iZ ist GRÖßER als zZ1*/
            {
                if(zZ2>zZ1){  /** deshalb sollte zZ2 auch GRÖßER als zZ1 sein*/
                    rigth++;
                }else wrong++;
            }
            
            if (iZ < zZ1) /** iZ ist KLEINER als zZ1*/
            {
                if(zZ2<zZ1){ /** deshalb sollte zZ2 auch KLEINER als zZ1 sein*/
                    rigth++;
                }else wrong++;
            }
            
            /** wenn iZ keine auswirkung auf das Ergebnis hat sollte rigth = wrong sein wegen 50/50 chance*/
        }
        System.out.println("Rigth : "+rigth+" Wrong : "+wrong);
    }
    
   /**ERKLÄRUNG
    * 
    * es wird eine zZ "aufgedeckt" und mit unserer imaginären Zahl verglichen
    * 
    * Möglichkeiten wie die zahlen zusammengesetzt sein können:
    * 
    *          zZ1 <          iZ  ---> die zweite zZ kann also an drei stellen sein:   
    * (hier) < zZ1 < (hier) < iZ < (und hier)
    * 
    * --> also wenn die iZ GRÖßER ist als zZ1 ist zZ2 warscheinlich eher auch größer 
    *      --> entsprechend andersherum wenn  iZ KLEINER ist als zZ1
    */
}