package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class KordinasiPBJAdapter extends RecyclerView.Adapter<KordinasiPBJViewHolder> {

    ArrayList<ListPermohonan> data;

    @NonNull
    @Override
    public KordinasiPBJViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View lpk = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_list_pbj, viewGroup, false);

        KordinasiPBJViewHolder kpk = new KordinasiPBJViewHolder(lpk);

        return kpk;
    }

    @Override
    public void onBindViewHolder(@NonNull KordinasiPBJViewHolder kordinasiPBJViewHolder, int i) {
        ListPermohonan lvpk = data.get(i);

        if (lvpk.status.equals("K")){

            kordinasiPBJViewHolder.txtStatusPermo.setText("Waiting");

        }

        else if (lvpk.status.equals("C")){

            kordinasiPBJViewHolder.txtStatusPermo.setText("Rejected");

        }

        kordinasiPBJViewHolder.txtNoPermo.setText(lvpk.nomor);

        kordinasiPBJViewHolder.txtJenisPermo.setText(lvpk.jenis + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
