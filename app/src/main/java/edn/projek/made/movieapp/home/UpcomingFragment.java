package edn.projek.made.movieapp.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.home.adapter.MovieAdapter;
import edn.projek.made.movieapp.home.presenter.HomeContract;
import edn.projek.made.movieapp.home.presenter.HomePresenter;
import edn.projek.made.movieapp.model.Movie;


public class UpcomingFragment extends Fragment implements HomeContract.View {
    MovieAdapter adapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    HomePresenter homePresenter;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    public static UpcomingFragment newInstance() {
        UpcomingFragment fragment = new UpcomingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        homePresenter=new HomePresenter(getActivity(),this,getActivity().getSupportLoaderManager());

        if (savedInstanceState!=null){
            createRecyclerView((List<Movie>) savedInstanceState.getSerializable("Upcoming"));
        }else{
            homePresenter.getMovieUpcoming();
        }


    }
    public void createRecyclerView(List<Movie>movies){
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new MovieAdapter(movies,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovies(List<Movie> movies) {
        Bundle saveInstanceState= new Bundle();
        saveInstanceState.putSerializable("Upcoming", (Serializable) movies);
        onSaveInstanceState(saveInstanceState);
        createRecyclerView(movies);
    }

    @Override
    public void showDialog() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void dismissDialog() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void hideEmpty() {

    }

    @Override
    public void showEmpty() {

    }
}
