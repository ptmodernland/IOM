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
                .inflate(R.layout.card_memo, viewGroup, false);

        KordinasiViewHolder k = new KordinasiViewHolder(l);

        return k;
    }

    @Override
    public void onBindViewHolder(@NonNull KordinasiViewHolder kordinasiViewHolder, int i) {

        ListMemo m = data.get(i);

        if (m.status.equals("K")){

            kordinasiViewHolder.txtStatus.setText("Kordinasi");

        }
        if (m.status.equals("C")){

            kordinasiViewHolder.txtStatus.setText("Reject");

        }


        kordinasiViewHolder.txtNomor.setText(m.nomor);
        kordinasiViewHolder.txtID.setText(m.id_iom);
        kordinasiViewHolder.txtPerihal.setText(m.perihal + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
