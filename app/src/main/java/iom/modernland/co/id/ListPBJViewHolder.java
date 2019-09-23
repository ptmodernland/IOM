package iom.modernland.co.id;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ListPBJViewHolder extends RecyclerView.ViewHolder {

    TextView txtNoPermo, txtJenisPermo;

    public ListPBJViewHolder(@NonNull View itemView) {
        super(itemView);

        txtNoPermo = (TextView) itemView.findViewById(R.id.txtNoPermoL);
        txtJenisPermo = (TextView) itemView.findViewById(R.id.txtJenisPermoL);

    }
}
