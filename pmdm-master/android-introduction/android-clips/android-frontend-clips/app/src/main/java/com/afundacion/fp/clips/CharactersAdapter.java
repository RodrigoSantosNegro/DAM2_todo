package com.afundacion.fp.clips;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CharactersAdapter extends RecyclerView.Adapter<CharacterViewHolder> {
    private CharactersList characters;

    public CharactersAdapter(CharactersList charactersList) {
        this.characters = charactersList;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cellView = inflater.inflate(R.layout.character_recycler_cell, parent, false);
        CharacterViewHolder cellViewHolder = new CharacterViewHolder(cellView);
        return cellViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {

        Character dataForThisCell = this.characters.getCharacters().get(position);
        holder.showData(dataForThisCell);
    }

    @Override
    public int getItemCount() {return this.characters.getCharacters().size();}

}

