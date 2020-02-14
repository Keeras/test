package adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_harayo.R;

import java.io.EOFException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.FoundPostModel;
import url.Url;

public class FoundAdapter  extends RecyclerView.Adapter<FoundAdapter.FoundAdapterViewHolder> {

    Context mContext;
    public List<FoundPostModel> foundlist;

    public FoundAdapter(Context mContext, List<FoundPostModel> foundlist) {
        this.mContext = mContext;
        this.foundlist = foundlist;
    }

    @NonNull
    @Override
    public FoundAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.found_items,parent,false);
        return new FoundAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoundAdapterViewHolder holder, int position) {
        final FoundPostModel foundPostModel = foundlist.get(position);
//        final String postImage = Url.BASE_URL + "/uploads/" + foundPostModel.getPostphoto();
        StrictMode();

//        try {
//            URL url = new URL(postImage);
//            holder.photo.setImageBitmap(BitmapFactory.decodeStream((InputStream)url.getContent()));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        holder.fname.setText(foundPostModel.getName());
        holder.fcolor.setText(foundPostModel.getColor());
        holder.fdescription.setText(foundPostModel.getDescription());
        holder.flandmark.setText(foundPostModel.getFnlandmark());
        holder.fcity.setText(foundPostModel.getFncity());
        holder.relandmark.setText(foundPostModel.getRelandmark());
        holder.recity.setText(foundPostModel.getRecity());


    }



    @Override
    public int getItemCount() {
        return foundlist.size();
    }

    public class FoundAdapterViewHolder extends RecyclerView.ViewHolder{
//        private ImageView photo;
        public TextView fname, fcolor, fdescription, flandmark, fcity, relandmark, recity ;
        public FoundAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

//            photo = itemView.findViewById(R.id.ivFPhoto);
            fname = itemView.findViewById(R.id.tvFName);
            fcolor = itemView.findViewById(R.id.tvColor);
            fdescription = itemView.findViewById(R.id.tvDescription);
            flandmark = itemView.findViewById(R.id.tvFLandmark);
            fcity = itemView.findViewById(R.id.tvFCity);
            relandmark = itemView.findViewById(R.id.returnLocation);
            recity = itemView.findViewById(R.id.tvRECity);
        }
    }
    private void StrictMode() {
        android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
        android.os.StrictMode.setThreadPolicy(policy);
    }
}
