package iom.modernland.co.id;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ApproveViewPOHolder extends RecyclerView.ViewHolder {

    TextView txtIDpo, txtNomorPO,txtDescPO,txtStatusap;

    public ApproveViewPOHolder(@NonNull final View itemView) {
        super(itemView);

        txtIDpo = (TextView) itemView.findViewById(R.id.txtIDpo);
        txtNomorPO = (TextView) itemView.findViewById(R.id.txtNomorPO);
        txtDescPO = (TextView) itemView.findViewById(R.id.txtDescPO);
        txtStatusap = (TextView) itemView.findViewById(R.id.txtStatusap);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentApproveActivity c = (ContentApproveActivity) itemView.getContext();

                String id = txtIDpo.getText().toString();
                String nomor = txtNomorPO.getText().toString();

                ApproveDetailFragment ad = new ApproveDetailFragment();

                Bundle b = new Bundle();
                b.putString("idnya",id);
                b.putString("nomornya",nomor);

                ad.setArguments(b);

                c.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameApprove, ad)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }
}
