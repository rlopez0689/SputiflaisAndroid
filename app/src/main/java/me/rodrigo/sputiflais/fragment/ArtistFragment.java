package me.rodrigo.sputiflais.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import me.rodrigo.sputiflais.ItemDividerDecoration;
import me.rodrigo.sputiflais.adapter.ArtistAdapter;
import me.rodrigo.sputiflais.io.SpotifyApiAdapter;
import me.rodrigo.sputiflais.ItemOffsetDecoration;
import me.rodrigo.sputiflais.MainActivity;
import me.rodrigo.sputiflais.R;
import me.rodrigo.sputiflais.io.model.ArtistResponse;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ArtistFragment extends Fragment{

    public static final String ARG_SECTION_TITLE = "section_number";
    private EditText mEdit;
    private RecyclerView artistsList;
    private ArtistAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
    private LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity(), 1, false);

    public static ArtistFragment newInstance(String sectionTitle) {
        ArtistFragment fragment = new ArtistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_TITLE, sectionTitle);
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistFragment() {
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
                    ((MainActivity) getActivity()).hideKeyboard();
                    if (mEdit.getText().toString().matches(""))
                        Toast.makeText(getActivity(), "Insert query", Toast.LENGTH_SHORT).show();
                    else
                        requestHypedArtists(mEdit.getText().toString());
                    return true;
                }
                return false;
            }
        });

        artistsList = (RecyclerView) view.findViewById(R.id.artist_list);
        mAdapter = new ArtistAdapter(getActivity());

        setupList();
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater)
    {
        MenuItem item = menu.add(0, 7, 0, R.string.settings_item);
        item.setIcon(R.drawable.ic_list_white_18dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(artistsList.getLayoutManager() == mGridLayoutManager) {
            item.setIcon(R.drawable.ic_dashboard_white_18dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            artistsList.setLayoutManager(mLinearLayoutManager);
        }
        else{
            item.setIcon(R.drawable.ic_list_white_18dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            artistsList.setLayoutManager(mGridLayoutManager);
        }
        return true;
    }


    private void setupList() {
        artistsList.setLayoutManager(mGridLayoutManager);
        artistsList.setAdapter(mAdapter);
        artistsList.addItemDecoration(new ItemOffsetDecoration(getActivity(), R.integer.offset_grid));
        artistsList.addItemDecoration(new ItemDividerDecoration(getActivity(), 0));
    }

    private void requestHypedArtists(String query) {
        SpotifyApiAdapter.getArtist(query)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<ArtistResponse>() {
            @Override
            public void call(ArtistResponse artistResponse) {
                mAdapter.addAll(artistResponse.getArtists());
            }
        });
    }
}
