package iom.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ApproveNPVViewHolder extends RecyclerView.ViewHolder {

    TextView txtKodeUnit, txtNoNpv, txtCluster, txtNama;

    public ApproveNPVViewHolder(@NonNull final View itemView) {
        super(itemView);

        txtKodeUnit = (TextView) itemView.findViewById(R.id.txtKdUnitNpvA);
        txtNoNpv = (TextView) itemView.findViewById(R.id.txtNoNpvA);
        txtCluster = (TextView) itemView.findViewById(R.id.txtClusterNpvA);
        txtNama = (TextView) itemView.findViewById(R.id.txtNamaNpvA);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentApproveNPVActivity can = (ContentApproveNPVActivity) itemView.getContext();

                String kode_unit = txtKodeUnit.getText().toString();
                String npv_no = txtNoNpv.getText().toString();
                String cluster = txtCluster.getText().toString();
                String nama = txtNama.getText().toString();

                ApproveDetailNPVFragment anp = new ApproveDetailNPVFragment();

                Bundle b = new Bundle();
                b.putString("kode_unitnya",kode_unit);
                b.putString("npv_no",npv_no);
                b.putString("cluster",cluster);
                b.putString("nama",nama);

                anp.setArguments(b);

                can.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameApproveNPV, anp)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}
