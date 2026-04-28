package com.example.matchupgame;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Integer> cardImages;
    private ImageButton firstCard, secondCard;
    private int firstCardId, secondCardId;
    private boolean isBusy = false;
    private int matchedPairs = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        cardImages = new ArrayList<>();
        cardImages.add(R.drawable.card_a);
        cardImages.add(R.drawable.card_a);
        cardImages.add(R.drawable.card_b);
        cardImages.add(R.drawable.card_b);
        cardImages.add(R.drawable.card_c);
        cardImages.add(R.drawable.card_c);
        cardImages.add(R.drawable.card_d);
        cardImages.add(R.drawable.card_d);

        Collections.shuffle(cardImages);

        for (int i = 0; i < cardImages.size(); i++) {
            final int cardId = cardImages.get(i);
            final ImageButton button = new ImageButton(this);
            GridLayout.LayoutParams params = new
            GridLayout.LayoutParams();
            params.width = 800;
            params.height = 800;
            params.setMargins(8,8,8,8);
            button.setLayoutParams(params);

            button.setScaleType(ImageView.ScaleType.FIT_CENTER);

            button.setImageResource(R.drawable.card_back);
            button.setBackgroundResource(0);
            button.setTag(cardId);
            button.setOnClickListener(v -> flipCard(button, cardId));
            gridLayout.addView(button);

        }
    }


    private void flipCard(ImageButton button, int cardId) {
        if (isBusy) return;

        button.setImageResource(cardId);

        if (firstCard == null) {
            firstCard = button;
            firstCardId = cardId;
        } else if (secondCard == null && button != firstCard) { // Fixed typo: nulL
            secondCard = button;
            secondCardId = cardId;

            if (firstCardId == secondCardId) {
                firstCard.setEnabled(false);
                secondCard.setEnabled(false);
                matchedPairs++;
                resetTurn();
                if (matchedPairs == cardImages.size() / 2) { // Fixed typo: natchedPairs
                    Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
                }
            } else {
                isBusy = true;
                button.postDelayed(() -> {
                    firstCard.setImageResource(R.drawable.card_back);
                    secondCard.setImageResource(R.drawable.card_back);
                    resetTurn();
                }, 1000);
            }
        }
    }

    private void resetTurn() {
        firstCard = null;
        secondCard = null;
        isBusy = false;
    }
}
