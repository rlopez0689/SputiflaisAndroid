package me.rodrigo.sputiflais.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.rodrigo.sputiflais.domain.Album;
import me.rodrigo.sputiflais.R;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder> {
    ArrayList<Album> albums;
    Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public AlbumAdapter(Context context) {

        this.context = context;
        this.albums = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlbumHolder onCreateViewHolder(ViewGroup container, int position) {
        View artistView = LayoutInflater.from(context)
                .inflate(R.layout.item_artists, container, false);

        return new AlbumHolder(artistView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AlbumHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Album currentArtist = albums.get(position);

        holder.setName(currentArtist.getName());

        holder.setImage(currentArtist.getImage());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return albums.size();
    }


    public void addAll(ArrayList<Album> albums) {
        if (albums == null)
            throw new NullPointerException("The items cannot be null");
        this.albums.clear();
        this.albums.addAll(albums);
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class AlbumHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public ImageView image;

        public AlbumHolder(View v) {
            super(v);
            this.name = (TextView) v.findViewById(R.id.artist_name);
            this.image = (ImageView) v.findViewById(R.id.artist_img);
        }

        public void setName(String name){
            this.name.setText(name);
        }

        public void setDefaultImage(){
            Picasso.with(context)
                    .load(R.drawable.placeholder)
                    .into(image);
        }

        public void setImage(String urlImage){
            Picasso.with(context)
                    .load(urlImage)
                    .placeholder(R.drawable.placeholder)
                    .into(image);
        }

    }
}
