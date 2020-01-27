package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ApprovePBJAdapter extends RecyclerView.Adapter<ApprovePBJViewHolder> {

    ArrayList<ListPermohonan> data;

    @NonNull
    @Override
    public ApprovePBJViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View p = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_approve_pbj, viewGroup, false);

        ApprovePBJViewHolder av = new ApprovePBJViewHolder(p);

        return av;
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovePBJViewHolder approvePBJViewHolder, int i) {

        ListPermohonan av = data.get(i);

        if (av.status.equals("C")){
            approvePBJViewHolder.txtStatusPermo.setText("Not Recommended");
        }
        if (av.status.equals("T")){
            approvePBJViewHolder.txtStatusPermo.setText("Waiting Approved");
        }
        if (av.status.equals("K")){
            approvePBJViewHolder.txtStatusPermo.setText("Waiting Approval Recommendation");
        }

        approvePBJViewHolder.txtNoPermo.setText(av.nomor);
        approvePBJViewHolder.txtJenisPermo.setText(av.jenis + "");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
