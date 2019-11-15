package iom.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ListNPVViewHolder extends RecyclerView.ViewHolder {

    TextView txtKodeUnit, txtNoNpv, txtStatus, txtCluster, txtNama;

    public ListNPVViewHolder(@NonNull final View itemView) {
        super(itemView);

        txtKodeUnit = (TextView) itemView.findViewById(R.id.txtKdUnitNpv);
        txtNoNpv = (TextView) itemView.findViewById(R.id.txtNoNpv);
        txtCluster = (TextView) itemView.findViewById(R.id.txtClusterNpv);
        txtNama = (TextView) itemView.findViewById(R.id.txtNamaNpv);
        txtStatus = (TextView) itemView.findViewById(R.id.txtStatusNpv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentListNpvActivity cln = (ContentListNpvActivity) itemView.getContext();

                String kode_unit = txtKodeUnit.getText().toString();
                String npv_no = txtNoNpv.getText().toString();
                String cluster = txtCluster.getText().toString();
                String nama = txtNama.getText().toString();

                ListDetailNPVFragment lnp = new ListDetailNPVFragment();

                Bundle b = new Bundle();
                b.putString("kode_unit_list",kode_unit);
                b.putString("npv_no_list",npv_no);
                b.putString("cluster_list",cluster);
                b.putString("nama_list",nama);

                lnp.setArguments(b);

                cln.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameNPV, lnp)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
