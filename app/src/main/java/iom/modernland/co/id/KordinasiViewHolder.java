package iom.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class KordinasiViewHolder extends RecyclerView.ViewHolder {

    TextView txtNomor, txtPerihal, txtStatus, txtID,txtFrom,txtUsername;

    public KordinasiViewHolder(@NonNull final View itemView) {
        super(itemView);

        txtID = (TextView) itemView.findViewById(R.id.txtID);
        txtNomor = (TextView) itemView.findViewById(R.id.txtNomor);
        txtPerihal = (TextView) itemView.findViewById(R.id.txtPerihal);
        txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
        txtFrom = (TextView) itemView.findViewById(R.id.txtFrom);
        txtUsername = (TextView) itemView.findViewById(R.id.txtUsername);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentKordinasiActivity h = (ContentKordinasiActivity) itemView.getContext();

                String id = txtID.getText().toString();
                String nomor = txtNomor.getText().toString();
                String username = txtUsername.getText().toString();

                KordinasiDetailFragment ld = new KordinasiDetailFragment();

                Bundle b = new Bundle();
                b.putString("idnya",id);
                b.putString("nomornya",nomor);
                b.putString("username",username);

                ld.setArguments(b);

                h.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameKordinasi, ld)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }
}
