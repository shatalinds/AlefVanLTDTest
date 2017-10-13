package alefvanltd.alefvanltdtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private Network mNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        mNetwork = Network.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNetwork.getService()
                .list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .map(responseBody -> mapList(responseBody))
                .filter(stringList -> stringList != null)
                .subscribe(stringList -> onResponse(stringList), throwable -> Timber.e(throwable));
    }

    private List<String> mapList(ResponseBody responseBody) {
        List<String> photosList = null;
        String string;
        try {
            string = responseBody.string().replace("[", "").replace("]", "").replace("\"", "");
            photosList = new ArrayList<>(Arrays.asList(string.split(",")));
        } catch (IOException e) {
            Timber.e(e);
        }
        return photosList;
    }

    private void onResponse(List<String> stringList) {
        setupRecyclerView(stringList.size());

        int size;
        if (getResources().getBoolean(R.bool.isTablet))
            size = getWindow().getDecorView().getMeasuredWidth() / 3;
        else
            size = getWindow().getDecorView().getMeasuredWidth() / 2;

        recyclerView.setAdapter(new RecycleViewAdapter(this, size, stringList));
    }

    private void setupRecyclerView(int size) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(size + 1);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
}