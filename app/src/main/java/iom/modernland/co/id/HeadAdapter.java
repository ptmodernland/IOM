package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HeadAdapter extends RecyclerView.Adapter<HeadViewHolder> {

    ArrayList<ListHead> data;

    @NonNull
    @Override
    public HeadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View ll = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.dialog_pilih_head, viewGroup, false);

        HeadViewHolder lp = new HeadViewHolder(ll);

        return lp;
    }

    @Override
    public void onBindViewHolder(@NonNull HeadViewHolder HeadViewHolder, int i) {

        ListHead ab = data.get(i);
        HeadViewHolder.tHead.setText(ab.namaUser);
        HeadViewHolder.txNomorMemo.setText(ab.nomornya);
        HeadViewHolder.txNomorMemo.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
