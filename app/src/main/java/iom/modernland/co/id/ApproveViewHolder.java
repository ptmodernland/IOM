package iom.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ApproveViewHolder extends RecyclerView.ViewHolder {

    TextView txtNomorap, txtPerihalap, txtIDap;

    public ApproveViewHolder(@NonNull final View itemView) {
        super(itemView);

        txtIDap = (TextView) itemView.findViewById(R.id.txtIDap);
        txtNomorap = (TextView) itemView.findViewById(R.id.txtNomorap);
        txtPerihalap = (TextView) itemView.findViewById(R.id.txtPerihalap);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentApproveActivity c = (ContentApproveActivity) itemView.getContext();

                String id = txtIDap.getText().toString();
                String nomor = txtNomorap.getText().toString();

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
