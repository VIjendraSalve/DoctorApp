package com.wht.rishiherherbocare.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wht.rishiherherbocare.Object.PrescriptionImageObject;
import com.wht.rishiherherbocare.R;

import java.util.ArrayList;
import java.util.List;

import static com.wht.rishiherherbocare.my_library.Constants.loadImageDoc;
import static com.wht.rishiherherbocare.my_library.Constants.showDocument;


public class AdapterPrescriptionImages extends RecyclerView.Adapter<AdapterPrescriptionImages.ImageViewHolder> {

    Context context;
    int IMG_TYPE, DOC_TYPE;
    String IMG_URL, id, doc_name;
    List<PrescriptionImageObject> prescriptionImageObjectArrayList = new ArrayList<>();
    boolean isShow = false;
    private ProgressDialog progressDialog;


    public AdapterPrescriptionImages(Context context, String IMG_URL, List<PrescriptionImageObject> prescriptionImageObjectArrayList) {
        this.context = context;
        this.IMG_URL = IMG_URL;
        this.prescriptionImageObjectArrayList = prescriptionImageObjectArrayList;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_prescription_image_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        final PrescriptionImageObject prescriptionImageObject = prescriptionImageObjectArrayList.get(position);

        if (!prescriptionImageObject.getFile_name().equalsIgnoreCase(""))
            Glide.with(context).load(IMG_URL + prescriptionImageObject.getFile_name())
                    .thumbnail(0.5f)
                    .into(holder.iv_tag_img);
        else
            loadImageDoc(context, IMG_URL + prescriptionImageObject.getFile_name(), holder.iv_tag_img);

        holder.iv_tag_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ImagePathQwertt", "onClick: " + IMG_URL + prescriptionImageObject.getFile_name());
                showDocument(context, IMG_URL + prescriptionImageObject.getFile_name());
            }
        });

    }

    @Override
    public int getItemCount() {
        return prescriptionImageObjectArrayList.size();
    }

    public interface ImageUpdate {
        void onUpdateImage(int IMG_TYPE, int position, ImageView imageView);
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv_tag_img;

        public ImageViewHolder(View itemView) {
            super(itemView);
            iv_tag_img = (ImageView) itemView.findViewById(R.id.iv_tag_img);

        }
    }
}
