package com.afundacion.fp.clips;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private ImageView imageView;
    private Character character;


    public CharacterViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.text_view_character);
        imageView = itemView.findViewById(R.id.image_view_character);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String characterName = character.getName();
                Context context = view.getContext();

                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra(VideoActivity.INTENT_CLIP_ID, characterName);
                intent.putExtra(VideoActivity.INTENT_CLIP_URL, character.getUrlImage());
                context.startActivity(intent);

            }
        });
    }

    public void showData(Character character) {
        this.nameTextView.setText(character.getName());
        Util.downloadBitmapToImageView(character.getUrlImage(), this.imageView);
    }

    public void bindData(Character character) {
        this.nameTextView.setText(character.getName());
        this.character = character;
    }
}

