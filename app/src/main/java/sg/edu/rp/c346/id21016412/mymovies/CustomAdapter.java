package sg.edu.rp.c346.id21016412.mymovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Movies> movieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movies> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        movieList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
        ImageView ivRating = rowView.findViewById(R.id.imageViewRating);
        TextView tvGenre = rowView.findViewById(R.id.textViewGenre);

        Movies currentItem = movieList.get(position);
        tvTitle.setText(currentItem.getTitle());
        tvGenre.setText(currentItem.getGenre());
        tvYear.setText(String.valueOf(currentItem.getYear()));

        String draw = "";
        String imageUrl = "";

        if (currentItem.getRating().equals("M18")){
            imageUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16282-05127b2.jpg?quality=90&webp=true&fit=300,300";
            Picasso.with(getContext()).load(imageUrl).into(ivRating);
        } else if (currentItem.getRating().equals("PG")) {
            imageUrl = "https://images.immediate.co.uk/production/volatile/sites/28/2019/02/16278-28797ce.jpg";
            Picasso.with(getContext()).load(imageUrl).into(ivRating);
        }
        else {
            draw = "rating_" + currentItem.getRating().toLowerCase();
            int id = parent_context.getResources().getIdentifier(draw, "drawable", parent_context.getPackageName());
            Drawable img = parent_context.getResources().getDrawable(id);
            ivRating.setImageDrawable(img);
        }

        return rowView;

    }
}

