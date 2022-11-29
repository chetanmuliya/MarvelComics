package cm.velotio.marvelcomics.ui.detail_screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import cm.velotio.marvelcomics.R;
import cm.velotio.marvelcomics.constant.Response;
import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.databinding.ActivityCharacterDetailsBinding;
import cm.velotio.marvelcomics.model.ItemsItem;
import cm.velotio.marvelcomics.ui.home_screen.MainActivity;
import cm.velotio.marvelcomics.ui.home_screen.MarvelCharactersViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CharacterDetailsActivity extends AppCompatActivity {

    private ActivityCharacterDetailsBinding binding;
    private CharactersDetailViewModel viewModel;
    private MutableLiveData<Response> responseObserver = new MutableLiveData<>();

    public static String CHARACTER_ID;
    private int characterId;
    private CharacterEntity characterObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_character_details);

        setUpUI();
    }

    private void setUpUI() {
        if (getIntent().getStringExtra(CHARACTER_ID)!=null){
            characterId = Integer.parseInt(getIntent().getStringExtra(CHARACTER_ID));
            setUpViewModel();
        }else {
            Toast.makeText(CharacterDetailsActivity.this,"Character id missing",Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void setUpImageAndDesc() {
        Glide.with(this).load(characterObject.thumbnail+"."+characterObject.extension).into(binding.ivCharacterProfilePic);
        binding.tvCharacterName.setText(characterObject.name);
        binding.tvCharacterDesc.setText(characterObject.name);
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(CharactersDetailViewModel.class);
        viewModel.getCharactersById(characterId,responseObserver).observe(CharacterDetailsActivity.this, new Observer<List<ItemsItem>>() {
            @Override
            public void onChanged(List<ItemsItem> itemsItems) {
                //Log.d("*************", "onChanged: "+itemsItems.size());
                if(itemsItems!=null) {
                    binding.tvComics1.setText(itemsItems.get(0).getName());
                    binding.tvComics2.setText(itemsItems.get(1).getName());
                    binding.tvComics3.setText(itemsItems.get(2).getName());
                    binding.tvComics4.setText(itemsItems.get(3).getName());
                    binding.tvComics5.setText(itemsItems.get(4).getName());
                }
            }
        });
        viewModel.getCharactersById(getIntent().getStringExtra(CHARACTER_ID)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CharacterEntity>() {
                    @Override
                    public void onSuccess(@NonNull CharacterEntity characterEntity) {
                        characterObject = characterEntity;
                        setUpImageAndDesc();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        characterObject = null;
                    }
                });

        responseObserver.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                switch (response.status){
                    case LOADING: {
                        binding.progressBar2.setVisibility(View.VISIBLE);
                        break;
                    } case ERROR: {
                        binding.progressBar2.setVisibility(View.GONE);
                        Toast.makeText(CharacterDetailsActivity.this,response.error,Toast.LENGTH_LONG).show();
                        break;
                    }case SUCCESS: {
                        binding.progressBar2.setVisibility(View.GONE);
                        break;
                    }
                }
            }
        });
    }
}