package cm.velotio.marvelcomics.ui.detail_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import cm.velotio.marvelcomics.R;
import cm.velotio.marvelcomics.constant.Response;
import cm.velotio.marvelcomics.databinding.ActivityCharacterDetailsBinding;
import cm.velotio.marvelcomics.model.ItemsItem;
import cm.velotio.marvelcomics.ui.home_screen.MarvelCharactersViewModel;

public class CharacterDetailsActivity extends AppCompatActivity {

    private ActivityCharacterDetailsBinding binding;
    private CharactersDetailViewModel viewModel;
    private MutableLiveData<Response> responseObserver = new MutableLiveData<>();

    public static String CHARACTER_ID;
    private String characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_character_details);

        setUpUI();
    }

    private void setUpUI() {
        if (getIntent().getStringExtra(CHARACTER_ID)!=null){
            characterId = getIntent().getStringExtra(CHARACTER_ID);
        }else {
            Toast.makeText(CharacterDetailsActivity.this,"Character id missing",Toast.LENGTH_LONG).show();
            return;
        }

        setUpViewModel();
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(CharactersDetailViewModel.class);
        viewModel.getCharactersById(characterId,responseObserver).observe(CharacterDetailsActivity.this, new Observer<List<ItemsItem>>() {
            @Override
            public void onChanged(List<ItemsItem> itemsItems) {
                Log.d("*************", "onChanged: "+itemsItems.size());
               /* binding.tvComics1.setText(itemsItems.get(0).getName());
                binding.tvComics2.setText(itemsItems.get(1).getName());
                binding.tvComics3.setText(itemsItems.get(2).getName());
                binding.tvComics4.setText(itemsItems.get(3).getName());
                binding.tvComics5.setText(itemsItems.get(4).getName());*/
            }
        });
    }
}