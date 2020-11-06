package iom.modernland.co.id;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ApprovePOAdapter extends RecyclerView.Adapter<ApproveViewPOHolder> {

    ArrayList<ListPO> data;

    @NonNull
    @Override
    public ApproveViewPOHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View l = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_approve_po, viewGroup, false);


        ApproveViewPOHolder a = new ApproveViewPOHolder(l);
        return a;

    }



    @Override
    public void onBindViewHolder(@NonNull ApproveViewPOHolder ApproveViewPOHolder, int i) {

        ListPO a = data.get(i);

        if (a.status.equals("Y") && a.status_email.equals("T")){
            ApproveViewPOHolder.txtStatusap.setText("Waiting Approved");
        }

        ApproveViewPOHolder.txtNomorPO.setText(a.no_po);
        ApproveViewPOHolder.txtIDpo.setText(a.id_po);
        ApproveViewPOHolder.txtDescPO.setText(a.desc_po);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
