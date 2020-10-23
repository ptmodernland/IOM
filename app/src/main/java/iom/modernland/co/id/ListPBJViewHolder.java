package iom.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ListPBJViewHolder extends RecyclerView.ViewHolder {

    TextView txtNoPermo, txtJenisPermo, txtStatusPermo;

    public ListPBJViewHolder(@NonNull final View itemView) {
        super(itemView);

        txtNoPermo = (TextView) itemView.findViewById(R.id.txtNoPermoL);
        txtStatusPermo = (TextView) itemView.findViewById(R.id.txtStatusPermoL);
        txtJenisPermo = (TextView) itemView.findViewById(R.id.txtJenisPermoL);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentListPBJActivity c = (ContentListPBJActivity) itemView.getContext();

                String nomor = txtNoPermo.getText().toString();
                String jenis = txtJenisPermo.getText().toString();

                ListDetailPBJFragment ld = new ListDetailPBJFragment();

                Bundle b = new Bundle();
                b.putString("jenisnya",jenis);
                b.putString("no_permintaan",nomor);

                ld.setArguments(b);

                c.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.framePermohonanBJ, ld)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}
