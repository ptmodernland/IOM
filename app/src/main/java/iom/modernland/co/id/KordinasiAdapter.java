package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class KordinasiAdapter extends RecyclerView.Adapter<KordinasiViewHolder> {

    ArrayList<ListMemo> data;

    @NonNull
    @Override
    public KordinasiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View l = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_kordinasi, viewGroup, false);

        KordinasiViewHolder k = new KordinasiViewHolder(l);

        return k;
    }

    @Override
    public void onBindViewHolder(@NonNull KordinasiViewHolder kordinasiViewHolder, int i) {

        ListMemo m = data.get(i);

        if (m.status_kor.equals("Y") && m.status_email.equals("T")  ){
            kordinasiViewHolder.txtStatus.setText("Waiting Approved Cordination");
        }
        if (m.status_kor.equals("T") && m.status_email.equals("T")  ){
            kordinasiViewHolder.txtStatus.setText("Approved Cordination");
        }
        kordinasiViewHolder.txtNomor.setText(m.nomor);
        kordinasiViewHolder.txtID.setText(m.id_iom);
        kordinasiViewHolder.txtFrom.setText(m.namaUser);
        kordinasiViewHolder.txtTo.setText(m.UserKordinasi);
        kordinasiViewHolder.txtUsername.setText(m.approve);
        kordinasiViewHolder.txtPerihal.setText(m.perihal + "");
        kordinasiViewHolder.txtUsername.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
