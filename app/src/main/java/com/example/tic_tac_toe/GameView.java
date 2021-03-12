package com.example.tic_tac_toe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import java.util.HashMap;

public class GameView extends AppCompatActivity {
    enum Player { empty, human, computer };
    private HashMap<Integer,Integer> viewsPos;
    private int[] ids;
    private Player[] area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        //create game area and init all boxes to empty
        area = new Player[9];
        for(int i = 0; i <area.length; i++) area[i] = Player.empty;

        //get boxes IDs
        ids = new int[]{R.id.v1,R.id.v2,R.id.v3,R.id.v4,R.id.v5,R.id.v6,R.id.v7,R.id.v8,R.id.v9};

        viewsPos = new HashMap<>();

        for(int i=0; i<ids.length; i++) {
            //insert each box info into the hashMap( Key=ID , Value=Position in the Game area)
            viewsPos.put(ids[i], i);

            //set Onclick Listener for all boxes
            findViewById(ids[i]).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Human play first
                    if(v.getBackground()==null) { //check if box is empty
                        v.setBackgroundResource(R.drawable.circle);
                        Integer j = viewsPos.remove(v.getId());
                        area[j] = Player.human;
                        if(win(Player.human)) {
                            messageBox("You Win").show();
                        }
                        else if(viewsPos.isEmpty()) {//drawn
                            messageBox("Drawn").show();
                        }
                        else {
                            //Computer turn
                            //getting is move by generating random numbers
                            do {
                                j = ((int) (Math.random() * 10))%9;
                            }while(findViewById(ids[j]).getBackground()!=null);

                            v = findViewById(ids[j]);
                            v.setBackgroundResource(R.drawable.close);
                            viewsPos.remove(v.getId());
                            area[j] = Player.computer;
                            if(win(Player.computer)) {
                                messageBox("You Loose").show();
                            }
                            else if(viewsPos.isEmpty()) {//drawn
                                messageBox("Drawn").show();
                            }
                        }
                    }
                }
            });
        }
    }

    boolean win(Player p) {
        if(area[0]==p && area[1]==p && area[2]==p) return true;
        if(area[3]==p && area[4]==p && area[5]==p) return true;
        if(area[6]==p && area[7]==p && area[8]==p) return true;
        if(area[0]==p && area[3]==p && area[6]==p) return true;
        if(area[1]==p && area[4]==p && area[7]==p) return true;
        if(area[2]==p && area[5]==p && area[8]==p) return true;
        if(area[0]==p && area[4]==p && area[8]==p) return true;
        if(area[6]==p && area[4]==p && area[2]==p) return true;
        return  false;
    }

    AlertDialog messageBox(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameView.this);
        builder.setCancelable(false);
        builder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setMessage(s);
        return builder.create();
    }
}