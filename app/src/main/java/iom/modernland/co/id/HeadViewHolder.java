package iom.modernland.co.id;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class HeadViewHolder extends RecyclerView.ViewHolder {

    TextView tHead,txNomorMemo,txIDMemo;

    public HeadViewHolder(@NonNull final View itemView) {
        super(itemView);

        tHead = (TextView) itemView.findViewById(R.id.tHead);
        txNomorMemo = (TextView) itemView.findViewById(R.id.txNomorMemo);
        txIDMemo = (TextView) itemView.findViewById(R.id.txIDMemo);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        DialogHeadActivity c = (DialogHeadActivity) itemView.getContext();

                String head = tHead.getText().toString();
                String nomor = txNomorMemo.getText().toString();
                String id = txIDMemo.getText().toString();

        SaveDialogFragment ad = new SaveDialogFragment();

                Bundle b = new Bundle();
                b.putString("head",head);
                b.putString("nomor",nomor);
                b.putString("id",id);
                ad.setArguments(b);

        c.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameHead, ad)
                .addToBackStack(null)
                .commit();
            }
        });
    }
}
