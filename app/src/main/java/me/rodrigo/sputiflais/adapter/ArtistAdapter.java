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

import me.rodrigo.sputiflais.domain.Artist;
import me.rodrigo.sputiflais.R;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {
    ArrayList<Artist> artists;
    Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ArtistAdapter(Context context) {

        this.context = context;
        this.artists = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup container, int position) {
        View artistView = LayoutInflater.from(context)
                .inflate(R.layout.item_artists, container, false);

        return new ArtistHolder(artistView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Artist currentArtist = artists.get(position);

        holder.setName(currentArtist.getName());

        holder.setImage(currentArtist.getImage());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return artists.size();
    }


    public void addAll(ArrayList<Artist> artists) {
        if (artists == null)
            throw new NullPointerException("The items cannot be null");
        this.artists.clear();
        this.artists.addAll(artists);
        notifyDataSetChanged();
    }

        // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ArtistHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;
        public ImageView image;

        public ArtistHolder(View v) {
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
