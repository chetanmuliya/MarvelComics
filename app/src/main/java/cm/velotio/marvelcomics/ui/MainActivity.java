package cm.velotio.marvelcomics.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cm.velotio.marvelcomics.R;
import cm.velotio.marvelcomics.constant.Response;
import cm.velotio.marvelcomics.constant.Util;
import cm.velotio.marvelcomics.database.local.CharacterEntity;
import cm.velotio.marvelcomics.database.remote.MarvelComicsService;
import cm.velotio.marvelcomics.database.remote.RetrofitClient;
import cm.velotio.marvelcomics.databinding.ActivityMainBinding;
import cm.velotio.marvelcomics.model.CharactersResponse;
import cm.velotio.marvelcomics.model.ResultsItem;
import cm.velotio.marvelcomics.ui.adapter.CharactersAdapter;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MarvelCharactersViewModel viewModel;
    private CharactersAdapter adapter;
    private MutableLiveData<Response> responseObserver = new MutableLiveData<>();

    public static final String TAG = "MAINACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        setUpAdapter();
        setupViewModel();
        setUpUI();
    }

    private void setUpUI() {
        binding.etSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.getSearchList(s.toString()).observe(MainActivity.this, new Observer<List<CharacterEntity>>() {
                    @Override
                    public void onChanged(List<CharacterEntity> characterEntities) {
                        Log.d(TAG, "onChanged: addTextChangedListener "+s);
                        adapter.submitList(characterEntities);
                    }
                });
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        //swiperefreshlayout
        binding.swipeRefreshLay.setRefreshing(false);
        binding.swipeRefreshLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getAllCharacters(responseObserver,true).observe(MainActivity.this, new Observer<List<CharacterEntity>>() {
                    @Override
                    public void onChanged(List<CharacterEntity> list) {
                        adapter.submitList(list);
                    }
                });
            }
        });
    }

    private void setUpAdapter() {
        adapter = new CharactersAdapter(this);
        binding.rvCharacters.setLayoutManager(new GridLayoutManager(this,3));
        binding.rvCharacters.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MarvelCharactersViewModel.class);
        viewModel.getAllCharacters(responseObserver,false).observe(this, new Observer<List<CharacterEntity>>() {
            @Override
            public void onChanged(List<CharacterEntity> list) {
                adapter.submitList(list);
            }
        });

        responseObserver.observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                Log.d(TAG, "onChanged: status "+response.status);
                switch (response.status){
                    case LOADING: {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        Log.d(TAG, "onChanged: loading");
                        break;
                    } case ERROR: {
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this,response.error,Toast.LENGTH_LONG).show();
                        break;
                    }case SUCCESS: {
                        binding.swipeRefreshLay.setRefreshing(false);
                        binding.progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "onChanged: Success");
                        break;
                    }
                }
            }
        });
    }
}