import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);
        int numberOfDigits=0;
        int numberOfPossSymb=0;
        try{
            System.out.println("Please, enter the secret code's length");
            numberOfDigits=Integer.parseInt(scan.nextLine());
            System.out.println("Please, enter the number of possible symbols");
            numberOfPossSymb=Integer.parseInt(scan.nextLine());
        }catch (Exception e){
            System.out.println("Error: invalid number");
            System.exit(0);
        }



        if (numberOfDigits > numberOfPossSymb || numberOfDigits<1) {
            System.out.println("Error: The number of digits cannot be greater than the number of possible symbols.");
            System.exit(0);
        }
        else if(numberOfPossSymb>36){
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }







        Code code = new Code(numberOfDigits,numberOfPossSymb);



        String secretNumber=code.generateCode(numberOfDigits,numberOfPossSymb);
        String message=code.numberOfPossSymb<11?"The secret is prepared: "+"*".repeat(numberOfDigits)+" (0-"+code.allSymbols[numberOfPossSymb-1]+").":"The secret is prepared: "+"*".repeat(numberOfDigits)+" (0-9, a-"+code.allSymbols[numberOfPossSymb-1]+").";
        System.out.println(message);

        int sayac=1;
        String guess;
        do {
            System.out.println("Turn "+sayac+":");
            guess= scan.nextLine();

            System.out.println("Turn "+sayac+":");

            int bull=0;
            int cow=0;
            for (int i = 0; i <secretNumber.length() ; i++) {
                for (int j = 0; j < guess.length(); j++) {
                    if(secretNumber.charAt(i)==guess.charAt(j)){
                        if(i==j){
                            bull++;
                            if(bull==numberOfDigits){
                                System.out.println("Grade: " +numberOfDigits+ " bulls");
                                System.out.println("Congratulations! You guessed the secret code.");
                                return;
                            }
                        }
                        else{
                            cow++;
                        }
                    }
                }
            }
            sayac++;
            System.out.println("Grade: "+bull+" bull(s) and "+cow+" cow(s).");
        }
        while(guess!=secretNumber);




    }



}

class Code {

    int numberOfDigits;
    int numberOfPossSymb;
    String secretNumber = "";
    String[] allSymbols= new String[]{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f",
            "g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    public Code(int numberOfDigits, int numberOfPossSymb) {

        this.numberOfDigits = numberOfDigits;
        this.numberOfPossSymb = numberOfPossSymb;

    }

    public  String generateCode(int numberOfDigits, int numberOfPossSymb) {
        Random random = new Random();
        String secretNumber = "";
        if (numberOfDigits > 36) {
            System.out.println("Error: can't generate a secret number with a length of 36 because there aren't enough unique digits.");


        }
        else{


            String[] usedSymbols=decideSymbols(this.allSymbols,this.numberOfPossSymb);

            for (int i = 0; i <numberOfDigits ; i++) {

                secretNumber+=addSecretNumber(usedSymbols,random);
                usedSymbols=reArrangeUsedSymbols(usedSymbols,secretNumber);

            }
        }

        return secretNumber;
    }

    private static String[] reArrangeUsedSymbols(String[] usedSymbols,String secretCode) {
        String[]usedNumbers2=new String[usedSymbols.length-1];
        for (int j = 0, k = 0; j < usedSymbols.length ; j++) {
            if(!usedSymbols[j].equals(String.valueOf(secretCode.charAt(secretCode.length()-1)))) {

                usedNumbers2[k]= usedSymbols[j];
                ++k;
            }
        }
        return usedNumbers2;
    }

    private String[] decideSymbols(String[] allSymbols,int numberOfPossSymb){
        String[] usedSymbols=new String[numberOfPossSymb];
        for (int i = 0; i <numberOfPossSymb ; i++) {
            usedSymbols[i]=allSymbols[i];
        }
        return usedSymbols;
    }

    private String addSecretNumber(String[] usedSymbols,Random random){
        return usedSymbols[random.nextInt(usedSymbols.length)];

    }

}