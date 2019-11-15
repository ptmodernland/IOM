package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListNPVAdapter extends RecyclerView.Adapter<ListNPVViewHolder> {

    ArrayList<ListNPV> data;

    @NonNull
    @Override
    public ListNPVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View ln = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_list_npv, viewGroup, false);

        ListNPVViewHolder lnv = new ListNPVViewHolder(ln);

        return lnv;
    }

    @Override
    public void onBindViewHolder(@NonNull ListNPVViewHolder listNPVViewHolder, int i) {

        ListNPV lnv = data.get(i);

        if (lnv.Status.equals("T")){

            listNPVViewHolder.txtStatus.setText("Approved");

        }

        else if (lnv.Status.equals("C")){

            listNPVViewHolder.txtStatus.setText("Rejected");

        }

        listNPVViewHolder.txtKodeUnit.setText(lnv.Lot_no);
        listNPVViewHolder.txtNoNpv.setText(lnv.Npv_no);
        listNPVViewHolder.txtCluster.setText(lnv.Cluster);
        listNPVViewHolder.txtNama.setText(lnv.Nama + "");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
