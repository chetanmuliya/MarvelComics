package cm.velotio.marvelcomics.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.databinding.ItemMarvelCharacterBinding;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

    public List<CharacterEntity> list = new ArrayList<>();
    private Context context;

    public CharactersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CharactersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMarvelCharacterBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersAdapter.ViewHolder holder, int position) {
        CharacterEntity character = list.get(position);
        Glide.with(context).load(character.thumbnail+"."+character.extension)
                .into(holder.binding.ivCharacter);

    }

    public void submitList(List<CharacterEntity> newList){
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMarvelCharacterBinding binding;

        public ViewHolder(ItemMarvelCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    /*static class CharacterDiff extends DiffUtil.ItemCallback<CharacterEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull CharacterEntity oldItem, @NonNull CharacterEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CharacterEntity oldItem, @NonNull CharacterEntity newItem) {
            return oldItem.name.equals(newItem.name);
        }
    }*/
}