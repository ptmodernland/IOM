package iom.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MemoViewHolder extends RecyclerView.ViewHolder {

    TextView txtNomor, txtPerihal, txtStatus, txtID;

    public MemoViewHolder(@NonNull final View itemView) {
        super(itemView);

        txtID = (TextView) itemView.findViewById(R.id.txtID);
        txtNomor = (TextView) itemView.findViewById(R.id.txtNomor);
        txtPerihal = (TextView) itemView.findViewById(R.id.txtPerihal);
        txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentListActivity h = (ContentListActivity) itemView.getContext();

                String id = txtID.getText().toString();
                String nomor = txtNomor.getText().toString();

                ListDetailFragment ld = new ListDetailFragment();

                Bundle b = new Bundle();
                b.putString("idnya",id);
                b.putString("nomornya",nomor);

                ld.setArguments(b);

                h.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameMemo, ld)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }
}
