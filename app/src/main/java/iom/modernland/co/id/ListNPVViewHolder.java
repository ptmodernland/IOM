package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ListNPVViewHolder extends RecyclerView.ViewHolder {

    TextView txtKodeUnit, txtCluster, txtNama;

    public ListNPVViewHolder(@NonNull View itemView) {
        super(itemView);

        txtKodeUnit = (TextView) itemView.findViewById(R.id.txtKdUnitNpv);
        txtCluster = (TextView) itemView.findViewById(R.id.txtClusterNpv);
        txtNama = (TextView) itemView.findViewById(R.id.txtNamaNpv);

    }
}
