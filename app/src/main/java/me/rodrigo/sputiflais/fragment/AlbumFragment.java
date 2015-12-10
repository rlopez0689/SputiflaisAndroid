package me.rodrigo.sputiflais.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import me.rodrigo.sputiflais.ItemDividerDecoration;
import me.rodrigo.sputiflais.MainActivity;
import me.rodrigo.sputiflais.adapter.AlbumAdapter;
import me.rodrigo.sputiflais.io.SpotifyApiAdapter;
import me.rodrigo.sputiflais.io.model.AlbumResponse;
import me.rodrigo.sputiflais.R;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class AlbumFragment extends Fragment{

    public static final String ARG_SECTION_TITLE = "section_number";
    private EditText mEdit;
    private RecyclerView albumsList;
    private AlbumAdapter mAdapter;

    public static AlbumFragment newInstance(String sectionTitle) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.section_fragment, container, false);
        mEdit = (EditText) view.findViewById(R.id.editText);

        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((MainActivity)getActivity()).hideKeyboard();
                    if(mEdit.getText().toString().matches(""))
                        Toast.makeText(getActivity(), "Insert query", Toast.LENGTH_SHORT).show();
                    else
                        requestAlbums(mEdit.getText().toString());
                    return true;
                }
                return false;
            }
        });

        albumsList = (RecyclerView) view.findViewById(R.id.artist_list);
        mAdapter = new AlbumAdapter(getActivity());

        setupList();

        return view;
    }

    private void setupList() {
        albumsList.setLayoutManager(new LinearLayoutManager(getActivity(), 1, false));
        albumsList.setAdapter(mAdapter);
        albumsList.addItemDecoration(new ItemDividerDecoration(getActivity(),0));
    }

    private void requestAlbums(String query) {
        SpotifyApiAdapter.getAlbum(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AlbumResponse>() {
                    @Override
                    public void call(AlbumResponse albumResponse) {
                        mAdapter.addAll(albumResponse.getAlbums());

                    }
                });
    }
}
