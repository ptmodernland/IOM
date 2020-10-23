package iom.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class KordinasiPBJViewHolder extends RecyclerView.ViewHolder {

    TextView txtNoPermo, txtJenisPermo, txtStatusPermo;

    public KordinasiPBJViewHolder(@NonNull final View itemView) {
        super(itemView);

        txtNoPermo = (TextView) itemView.findViewById(R.id.txtNoPermoL);
        txtStatusPermo = (TextView) itemView.findViewById(R.id.txtStatusPermoL);
        txtJenisPermo = (TextView) itemView.findViewById(R.id.txtJenisPermoL);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentKordinasiPBJActivity c = (ContentKordinasiPBJActivity) itemView.getContext();

                String nomor = txtNoPermo.getText().toString();
                String jenis = txtJenisPermo.getText().toString();

                KordinasiDetailPBJFragment ld = new KordinasiDetailPBJFragment();

                Bundle b = new Bundle();
                b.putString("jenisnya",jenis);
                b.putString("no_permintaan",nomor);

                ld.setArguments(b);

                c.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameKordinasiPBJ, ld)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
