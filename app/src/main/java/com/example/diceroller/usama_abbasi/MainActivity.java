package com.example.diceroller.usama_abbasi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements RollLengthDialogFragment.OnRollLengthSelectedListener
{

    private long lengthOfTimer = 2000;
    public static final int DISPLAYED_DICE_MAXIMUM = 3;
    private int visibleDice;
    private Dice[] diceObjectsArray; //Declaring the diceObjectArray
    private ImageView[] diceImageViews;

    private Menu menuReference;
    private CountDownTimer timer;

    private int currentDice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //creating array of Dice objects and storing them into the array & setting the dice number
        diceObjectsArray = new Dice[DISPLAYED_DICE_MAXIMUM]; //instantiating the diceObjectsArrays
        //filling the array with the dice object
        for (int i = 0; i < DISPLAYED_DICE_MAXIMUM; i++) {
            diceObjectsArray[i] = new Dice(i + 1); //creating the new dice object and storing it in
            //the diceObjectArray
        }

        diceImageViews = new ImageView[DISPLAYED_DICE_MAXIMUM];
        diceImageViews[0] = findViewById(R.id.dice_01);
        diceImageViews[1] = findViewById(R.id.dice_02);
        diceImageViews[2] = findViewById(R.id.dice_03);


        //initializing the dices to be visible. All the dices are visible in the first launch of the application
        visibleDice = DISPLAYED_DICE_MAXIMUM;

        showDice();
        for (int i = 0; i < diceImageViews.length; i++)
        {
            registerForContextMenu(diceImageViews[i]);
            diceImageViews[i].setTag(i);
        }
        registerForContextMenu((diceImageViews[2]));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        currentDice = (int) v.getTag();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == R.id.add_one){
            diceObjectsArray[currentDice].addOne();
            showDice();
            return true;
        }else if (item.getItemId() == R.id.subtract_one){
            diceObjectsArray[currentDice].subtractOne();
            showDice();
            return true;
        }else if (item.getItemId() == R.id.roll){
            rollDice();
            return true;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        menuReference = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void showDice() {
        //Display the visible dice
        for (int i = 0; i < visibleDice; i++) {
            Drawable diceDraw = ContextCompat.getDrawable(this, diceObjectsArray[i].getImageID());
            diceImageViews[i].setImageDrawable(diceDraw);
            diceImageViews[i].setContentDescription(Integer.toString(diceObjectsArray[i].getNumber()));
        }
    }


    @Override
    public void onRollLengthClick(int which){
        lengthOfTimer = 1000 * (which + 1);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_one) {
            changeVisibility(1);
            showDice();
            return true;
        } else if (item.getItemId() == R.id.action_two) {
            changeVisibility(2);
            showDice();
            return true;
        } else if (item.getItemId() == R.id.action_three) {
            changeVisibility(3);
            showDice();
            return true;
        } else if (item.getItemId() == R.id.stop_button) {
            timer.cancel();
            item.setVisible(false);
            return true;
        } else if (item.getItemId() == R.id.roll_button) {
            rollDice();
            return true;
        } else if (item.getItemId() == R.id.roll_length_selection){
            RollLengthDialogFragment dialog = new RollLengthDialogFragment();
            dialog.show(getSupportFragmentManager(), "rollLengthDialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeVisibility(int diceToShow){
        visibleDice = diceToShow;

        for (int i = 0; i < visibleDice; i++){
            diceImageViews[i].setVisibility(View.VISIBLE);
        }

        for (int i = visibleDice; i < DISPLAYED_DICE_MAXIMUM; i++){
            diceImageViews[i].setVisibility(View.GONE);
        }
    }

    //Function to roll the Dice
    private void rollDice(){
        menuReference.findItem(R.id.stop_button).setVisible(true);

        if(timer!= null){
            timer.cancel();
        }

        timer = new CountDownTimer(lengthOfTimer,100) {
            @Override
            public void onTick(long milliSecondsUntilFinished) {
                for (int i = 0 ; i < visibleDice; i++){
                    diceObjectsArray[i].roll();
                }
                showDice();
            }

            @Override
            public void onFinish() {
                    menuReference.findItem(R.id.stop_button).setVisible(false);
            }
        }.start();
    }
}