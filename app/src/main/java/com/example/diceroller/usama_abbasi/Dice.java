package com.example.diceroller.usama_abbasi;

import java.util.Random;

public class Dice {
    public static int LARGEST_NUMBER = 6;
    public static int SMALLEST_NUMBER = 1;

    private int numberToSet = SMALLEST_NUMBER;
    private int imageID;
    private Random randomGenerator;

    public Dice (int numberParameter){
        setNumber(numberParameter);
        randomGenerator = new Random();
    }

    public int getNumber(){
        return numberToSet;
    }

    public void setNumber (int number){
        if(number >= SMALLEST_NUMBER && number <= LARGEST_NUMBER){
            numberToSet = number;
            switch (number){
                case 1:
                    imageID = R.drawable.dice_1;
                    break;

                case 2:
                    imageID = R.drawable.dice_2;
                    break;

                case 3:
                    imageID = R.drawable.dice_3;
                    break;

                case 4:
                    imageID = R.drawable.dice_4;
                    break;

                case 5:
                    imageID = R.drawable.dice_5;
                    break;

                case 6:
                    imageID = R.drawable.dice_6;
                    break;
            }
        }
    }

    public int getImageID(){
        return imageID;
    }

    public void addOne(){
        setNumber(numberToSet + 1);
    }

    public void subtractOne(){
        setNumber(numberToSet - 1);
    }

    public void roll(){
        setNumber(randomGenerator.nextInt(LARGEST_NUMBER) + 1);
    }
}
