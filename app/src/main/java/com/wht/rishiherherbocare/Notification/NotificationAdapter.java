package com.wht.rishiherherbocare.Notification;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.util.Linkify;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wht.rishiherherbocare.R;

import java.util.List;

/**
 * Created by YoYoNituSingh on 7/14/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<Notification> notificationList;
    private DatabaseSqliteHandler db;
    private NoData noData;


    public NotificationAdapter(Context context, List<Notification> notificationList, NoData noData) {
        this.context = context;
        this.notificationList = notificationList;
        this.noData = noData;
    }

    public interface NoData {
        void on_Remove(List<Notification> notificationList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_notification_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        db = DatabaseSqliteHandler.getInstance(context);
        final Notification m = notificationList.get(position);
        db.UpdateNotification();

        // load the animation
        //Animation fadeInAnimation  = AnimationUtils.loadAnimation(convertView.getContext(), R.anim.fade_in);

        // title
        DisplayMetrics metricscard = context.getResources().getDisplayMetrics();
        int cardwidth = metricscard.widthPixels;
        int cardheight = (int) (metricscard.heightPixels);
        Log.d("height", "onBindViewHolder: "+cardwidth+","+cardheight);
        holder.iv_notification_image.getLayoutParams().width = (int) (cardwidth / 3);
      //  holder.iv_notification_image.getLayoutParams().height = (int) (cardheight/3);
        if (m.getNotification_image()!= null && !m.getNotification_image().isEmpty() && !m.getNotification_image().equals("null")) {
            Glide.with(context)
                    .load(m.getNotification_image())
                    .into(holder.iv_notification_image);
        }
        else {
            Glide.with(context)
                    .load(R.drawable.final_logo)
                    .into( holder.iv_notification_image);
        }
        if (m.getNotification_title()!= null && !m.getNotification_title().isEmpty() && !m.getNotification_title().equals("null")) {
            holder.tv_notification_title.setText(m.getNotification_title());
        }
        else {
            holder.tv_notification_title.setVisibility(View.GONE);
        }
        if (m.getNotification_message()!= null && !m.getNotification_message().isEmpty() && !m.getNotification_message().equals("null")) {
            //holder.tv_notification_message.setText(m.getNotification_message());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.tv_notification_message.setText(Html.fromHtml(m.getNotification_message(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.tv_notification_message.setText(Html.fromHtml(m.getNotification_message()));
            }
        }
        else {
            holder.tv_notification_message.setVisibility(View.GONE);
        }
        if (m.getNotification_date_time()!= null && !m.getNotification_date_time().isEmpty() && !m.getNotification_date_time().equals("null")) {
            holder.notification_date_time.setText(m.getNotification_date_time());
        }
        else {
            holder.notification_date_time.setVisibility(View.GONE);
        }
        if (m.getNotification_link()!= null && !m.getNotification_link().isEmpty() && !m.getNotification_link().equals("null")) {
            Log.d("link", "onBindViewHolder: "+m.getNotification_link());
            holder.tv_notification_link.setText(m.getNotification_link());
        }
        else {
            holder.tv_notification_link.setVisibility(View.GONE);
        }


        Linkify.addLinks(holder.tv_notification_message, Linkify.ALL);

      /*  Glide.with(context).load(m.getNotification_image())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.iv_notification_image.setImageResource(R.mipmap.ic_launcher);

                        return true;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.iv_notification_image.setImageDrawable(resource);

                        return true;
                    }
                })
                .into(holder.iv_notification_image);
*/

       /* holder.iv_notification_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (m.getNotification_image() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("image_url", m.getNotification_image());
                    Intent intent = new Intent(context, ViewImage.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });*/

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              /* new CustomAlertDialog(context, CustomAlertDialog.WARNING_TYPE)
                       //.setThemeColor(R.color.red_light)
                       .setCancelText(context.getResources().getString(R.string.no))
                       .setConfirmText(context.getResources().getString(R.string.yes))
                       .setContentText(context.getResources().getString(R.string.delete_message))
                       .setTitleText(context.getResources().getString(R.string.confirm_delete))
                       .setConfirmClickListener(new CustomAlertDialog.OnCMEDialogClickListner() {
                           @Override
                           public void onClick(CustomAlertDialog customDialog) {
                               db.delete_notification(m.getNotification_id());
                               notificationList.remove(position);
                               notifyDataSetChanged();
                               Toast.makeText(context, R.string.noti_delete, Toast.LENGTH_SHORT).show();
                               //if (notificationList.size() == 0)
                               noData.on_Remove(notificationList);
                               customDialog.dismiss();
                           }
                       })
                       .setCancelClickListener(new CustomAlertDialog.OnCMEDialogClickListner() {
                           @Override
                           public void onClick(CustomAlertDialog customDialog) {

                               customDialog.dismiss();
                           }
                       }).show();*/
               /*Intent intent=new Intent(context, NotificationActivity.class);
               intent.putExtra("Title",m.getNotification_title());
               intent.putExtra("Message",m.getNotification_message());
               intent.putExtra("Image",m.getNotification_image());
               intent.putExtra("Link",m.getNotification_link());
               context.startActivity(intent);*/
           }
       });



    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_notification_title, tv_notification_message, notification_date_time,tv_notification_link;
        ImageView iv_notification_image;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_notification_title = (TextView) itemView.findViewById(R.id.tv_notification_title);
            tv_notification_message = (TextView) itemView.findViewById(R.id.tv_notification_message);
            tv_notification_link = (TextView) itemView.findViewById(R.id.tv_notification_link);
            notification_date_time = (TextView) itemView.findViewById(R.id.notification_date_time);
            iv_notification_image = (ImageView) itemView.findViewById(R.id.iv_notification_image);
        }
    }
}
