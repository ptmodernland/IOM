package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ApproveAdapter extends RecyclerView.Adapter<ApproveViewHolder> {

    ArrayList<ListMemo> data;

    @NonNull
    @Override
    public ApproveViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View l = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_approve, viewGroup, false);

        ApproveViewHolder a = new ApproveViewHolder(l);

        return a;
    }

    @Override
    public void onBindViewHolder(@NonNull ApproveViewHolder approveViewHolder, int i) {

        ListMemo a = data.get(i);

        if (a.status.equals("C")){
            approveViewHolder.txtStatusap.setText("Not Recommended");
        }
        if (a.status.equals("T")){
            approveViewHolder.txtStatusap.setText("Waiting Approved");
        }
        if (a.status.equals("K")){
            approveViewHolder.txtStatusap.setText("Waiting Approval Recommendation");
        }
        approveViewHolder.txtNomorap.setText(a.nomor);
        approveViewHolder.txtIDap.setText(a.id_iom);
        approveViewHolder.txtPerihalap.setText(a.perihal + "");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
