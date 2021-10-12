package com.wht.rishiherherbocare.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.wht.rishiherherbocare.Constant.IConstant;
import com.wht.rishiherherbocare.Object.ConsulationReqObject;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.inside_user_pages.ConsulatationRequestDetailsActivity;
import com.wht.rishiherherbocare.my_library.Constants;
import com.wht.rishiherherbocare.my_library.DateTimeFormat;
import com.wht.rishiherherbocare.my_library.MyConfig;
import com.wht.rishiherherbocare.my_library.Shared_Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ConsulatationListActivityAdapter extends RecyclerView.Adapter<ConsulatationListActivityAdapter.SponsorsHolder> {

    private ArrayList<ConsulationReqObject> consulationReqObjectArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;
    private int flag = 0;
    private String path="";
    private RefreshActivity refreshActivity;

    public ConsulatationListActivityAdapter(ArrayList<ConsulationReqObject> consulationReqObjectArrayList, String path,
                                            RefreshActivity refreshActivity) {
        this.consulationReqObjectArrayList = consulationReqObjectArrayList;
        this.path = path;
        this.refreshActivity = refreshActivity;
    }

    @Override
    public ConsulatationListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.consultation_adapter_row_item, parent, false);

        return new ConsulatationListActivityAdapter.SponsorsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConsulatationListActivityAdapter.SponsorsHolder holder, final int position) {
        final ConsulationReqObject consulationReqObject = consulationReqObjectArrayList.get(position);

        holder.tvProfileName.setTypeface(holder.tvProfileName.getTypeface(), Typeface.BOLD);
        holder.tvStatus.setTypeface(holder.tvStatus.getTypeface(), Typeface.BOLD);
        holder.tvProfileName.setText(consulationReqObject.getName() + " (" + consulationReqObject.getRelation() + ")");
        holder.tvHospitalName.setText(consulationReqObject.getClinic_name());
        holder.tvDoctor.setText("Consulatation Dr. : "+consulationReqObject.getDoctor_name());
        holder.tvDisease.setText("Disease : " + consulationReqObject.getDisease());

        if (consulationReqObject.getSymptomsToDisplayArrayList().size() > 0) {
            String symptoms = "";
            for (int i = 0; i < consulationReqObject.getSymptomsToDisplayArrayList().size(); i++) {
                symptoms = symptoms + consulationReqObject.getSymptomsToDisplayArrayList().get(i).getName() + ", ";
            }
            holder.tvSymtoms.setText("Symptoms: " + symptoms);
        } else {
            holder.tvSymtoms.setText("No Symptoms");
        }
        holder.tvReqDateAndTime.setText("" + DateTimeFormat.getDate(consulationReqObject.getDate_time()));

        holder.btn_cancel_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove_alert(consulationReqObject.getId(), position);
            }
        });

        if (consulationReqObject.getStatus().equals("0")) {
            holder.tvStatus.setText("Pending");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.blue));
            holder.btn_cancel_appointment.setVisibility(View.VISIBLE);
        }
        else if (consulationReqObject.getStatus().equals("1")){
            holder.tvStatus.setText("Completed");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.btn_cancel_appointment.setVisibility(View.INVISIBLE);
        }
        else if (consulationReqObject.getStatus().equals("2")){
            holder.tvStatus.setText("Cancelled");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.primary_light));
            holder.btn_cancel_appointment.setVisibility(View.INVISIBLE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ConsulatationRequestDetailsActivity.class);
                intent.putParcelableArrayListExtra(Constants.CounsultList, consulationReqObjectArrayList);
                intent.putExtra(Constants.Position, position);
                intent.putExtra(Constants.Path, path);
                context.startActivity(intent);
            }
        });

    }

    private void remove_alert(final String memberId, final int position1)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Are You Sure You Want to Cancel This Request?");
        alertDialog.setIcon(R.drawable.ic_baseline_delete_24);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cancelRequest(memberId, position1);
            }
        });

        alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return consulationReqObjectArrayList.size();
    }

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        public TextView tvProfileName, tvHospitalName, tvDoctor, tvDisease, tvSymtoms, tvReqDateAndTime, tvStatus;
        private Button btn_cancel_appointment;

        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();

            tvProfileName = (TextView) view.findViewById(R.id.tvProfileName);
            tvHospitalName = (TextView) view.findViewById(R.id.tvHospitalName);
            tvDoctor = (TextView) view.findViewById(R.id.tvDoctor);
            tvDisease = (TextView) view.findViewById(R.id.tvDisease);
            tvSymtoms = (TextView) view.findViewById(R.id.tvSymtoms);
            tvReqDateAndTime = (TextView) view.findViewById(R.id.tvReqDateAndTime);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
            btn_cancel_appointment = (Button) view.findViewById(R.id.btn_cancel_appointment);

        }
    }

    private interface GetOrderAPI {

        @FormUrlEncoded
        @POST(MyConfig.DRBHOR + "/app_consultationsRequest_cancel")
        public Call<ResponseBody> getFamilyMembers(
                @Field("consult_id") String consult_id,
                @Field("user_id") String user_id
        );
    }

    private void cancelRequest(String reuestId, final int position) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.getFamilyMembers(
                reuestId,
                Shared_Preferences.getPrefs(context, IConstant.USER_ID)
        );
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String output = "";
                try {
                    output = response.body().string();
                    Log.d("my_tag", "onResponse11: " + output);
                    JSONObject jsonObject = new JSONObject(output);
                    boolean res = jsonObject.getBoolean("result");

                    // Log.d(TAG, "onResponse: " + object.getString("Message"));
                    if (res) {
                        refreshActivity.Refresh();
                        //consulationReqObjectArrayList.se(position);
                        notifyDataSetChanged();

                    } else {
                        progressDialog.dismiss();
                    }
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    public interface RefreshActivity {
        public void Refresh();
    }

}

