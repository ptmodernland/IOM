package iom.modernland.co.id;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ApproveKategoriAdapter extends RecyclerView.Adapter<ApproveKategoriViewHolder> {

    ArrayList<ListMemo> data;

    @NonNull
    @Override
    public ApproveKategoriViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View l = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_approve, viewGroup, false);


        ApproveKategoriViewHolder a = new ApproveKategoriViewHolder(l);
        return a;

    }

    @Override
    public void onBindViewHolder(@NonNull ApproveKategoriViewHolder ApproveKategoriViewHolder, int i) {

        ListMemo a = data.get(i);

        if (a.status.equals("Y") && a.status_email.equals("T") && a.kordinasi.equals("T")){
            ApproveKategoriViewHolder.txtStatusap.setText("Waiting Approved");
        }
        if (a.status.equals("Y") && a.status_email.equals("T") && a.kordinasi.equals("Y")){
            ApproveKategoriViewHolder.txtStatusap.setText("Recommended Approved");
        }
        if (a.status.equals("Y") && a.status_email.equals("T") && a.kordinasi.equals("C")){
            ApproveKategoriViewHolder.txtStatusap.setText("Not Recommended Cordination");
        }
        ApproveKategoriViewHolder.txtNomorap.setText(a.nomor);
        ApproveKategoriViewHolder.txtIDap.setText(a.id_iom);
        ApproveKategoriViewHolder.txtPerihalap.setText(a.perihal + "");
        ApproveKategoriViewHolder.txtKategori.setText(a.kategori);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
