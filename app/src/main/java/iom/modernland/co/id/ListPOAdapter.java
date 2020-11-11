package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListPOAdapter extends RecyclerView.Adapter<POViewHolder> {

    ArrayList<ListPO> data;

    @NonNull
    @Override
    public POViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View l = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_approve_po, viewGroup, false);

        POViewHolder m = new POViewHolder(l);

        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull POViewHolder POViewHolder, int i) {

        ListPO a = data.get(i);

        if (a.status.equals("Y") && a.status_email.equals("T")){
            POViewHolder.txtStatusap.setText("Waiting Approved");
        }

        POViewHolder.txtNomorPO.setText(a.no_po);
        POViewHolder.txtIDpo.setText(a.id_po);
        POViewHolder.txtDescPO.setText(a.desc_po);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
