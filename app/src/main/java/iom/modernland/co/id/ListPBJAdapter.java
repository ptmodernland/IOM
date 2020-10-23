package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ListPBJAdapter extends RecyclerView.Adapter<ListPBJViewHolder> {

    ArrayList<ListPermohonan> data;

    @NonNull
    @Override
    public ListPBJViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View lp = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_list_pbj, viewGroup, false);

        ListPBJViewHolder lv = new ListPBJViewHolder(lp);

        return lv;
    }

    @Override
    public void onBindViewHolder(@NonNull ListPBJViewHolder listPBJViewHolder, int i) {

        ListPermohonan lv = data.get(i);

        if (lv.status.equals("T")){

            listPBJViewHolder.txtStatusPermo.setText("Approved");

        }

        else if (lv.status.equals("C")){

            listPBJViewHolder.txtStatusPermo.setText("Rejected");

        }

        listPBJViewHolder.txtNoPermo.setText(lv.nomor);

        listPBJViewHolder.txtJenisPermo.setText(lv.jenis + "");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
