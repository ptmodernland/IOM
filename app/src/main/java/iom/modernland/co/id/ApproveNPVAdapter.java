package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ApproveNPVAdapter extends RecyclerView.Adapter<ApproveNPVViewHolder> {

    ArrayList<ListNPV> data;

    @NonNull
    @Override
    public ApproveNPVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View apn = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_approve_npv, viewGroup, false);

        ApproveNPVViewHolder anv = new ApproveNPVViewHolder(apn);

        return anv;
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveNPVViewHolder approveNPVViewHolder, int i) {

        ListNPV lnv = data.get(i);
        approveNPVViewHolder.txtKodeUnit.setText(lnv.Lot_no);
        approveNPVViewHolder.txtNoNpv.setText(lnv.Npv_no);
        approveNPVViewHolder.txtCluster.setText(lnv.Cluster);
        approveNPVViewHolder.txtNama.setText(lnv.Nama + "");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
