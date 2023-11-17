package com.afundacion.fp.clips;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClipViewHolder extends RecyclerView.ViewHolder {
    private TextView clipTitle;
    private Clip clip;
    public ClipViewHolder(@NonNull View itemView) {
        super(itemView);
        clipTitle = itemView.findViewById(R.id.myTextView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clipId = clip.getId();
                String clipUrl = clip.getVideoUrl();
                Context context = view.getContext();

                Toast.makeText(context, "Tap on ViewHolder with clipId: " + clipId, Toast.LENGTH_SHORT).show();
                // Y ahora... ¡Iniciamos la VideoActivity!
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra(VideoActivity.INTENT_CLIP_ID, clipId);
                intent.putExtra(VideoActivity.INTENT_CLIP_URL, clipUrl);
                context.startActivity(intent);

            }
        });

    }
    public void bindData(Clip clip) {
        this.clipTitle.setText(clip.getVideoTitle());
        this.clip = clip;
    }

}
