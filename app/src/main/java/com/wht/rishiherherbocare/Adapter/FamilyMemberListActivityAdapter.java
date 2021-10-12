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
import com.wht.rishiherherbocare.Object.FamilyMember;
import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.inside_user_pages.AddConsultationRequestActivity;
import com.wht.rishiherherbocare.my_library.Constants;
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

import static android.view.View.GONE;

public class FamilyMemberListActivityAdapter extends RecyclerView.Adapter<FamilyMemberListActivityAdapter.SponsorsHolder> {

    private ArrayList<FamilyMember> familyMemberArrayList;
    private View itemView;
    private Context context;
    private ProgressDialog progressDialog;
    private int flag=0;

    public class SponsorsHolder extends RecyclerView.ViewHolder {
        public TextView tv_member_name, tv_mobile, tv_emailAddress, tv_member_age;
        private Button btn_select, btn_delete;

        public SponsorsHolder(View view) {
            super(view);
            context = view.getContext();

            tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
            tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);
            tv_emailAddress = (TextView) view.findViewById(R.id.tv_emailAddress);
            tv_member_age = (TextView) view.findViewById(R.id.tv_member_age);
            btn_select = (Button) view.findViewById(R.id.btn_select);
            btn_delete = (Button) view.findViewById(R.id.btn_delete);


        }
    }


    public FamilyMemberListActivityAdapter(ArrayList<FamilyMember> familyMemberArrayList) {
        this.familyMemberArrayList = familyMemberArrayList;
    }

    @Override
    public FamilyMemberListActivityAdapter.SponsorsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_family_member, parent, false);

        return new FamilyMemberListActivityAdapter.SponsorsHolder(itemView);
    }


    @Override
    public void onBindViewHolder(FamilyMemberListActivityAdapter.SponsorsHolder holder, final int position) {
        final FamilyMember familyMember = familyMemberArrayList.get(position);

        holder.tv_member_name.setTypeface(holder.tv_member_name.getTypeface(), Typeface.BOLD);
        holder.tv_member_name.setText(familyMember.getName());
        holder.tv_mobile.setText(familyMember.getContact());
        holder.tv_emailAddress.setText(familyMember.getEmail());
        holder.tv_member_age.setText("Age : "+familyMember.getAge());
        
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove_alert(familyMember.getId(), position);
            }
        });

        if(familyMember.getRelation().equals("self")){
            holder.btn_delete.setVisibility(GONE);
        }else {
            holder.btn_delete.setVisibility(View.VISIBLE);
        }

        holder.btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddConsultationRequestActivity.class);
                intent.putExtra(Constants.FlagToSetInfo, "0");
                Shared_Preferences.setPrefs(context, Constants.CounsultMemberName, familyMember.getName());
                Shared_Preferences.setPrefs(context, Constants.CounsultMemberEmail, familyMember.getEmail());
                Shared_Preferences.setPrefs(context, Constants.CounsultMemberContact, familyMember.getContact());
                Shared_Preferences.setPrefs(context, Constants.CounsultMemberID, familyMember.getId());
                context.startActivity(intent);
            }
        });

    }

    private void remove_alert(final String memberId, final int position1)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Are You Sure You Want to Remove this Person?");
        alertDialog.setIcon(R.drawable.ic_baseline_delete_24);
        alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteFamilyMember(memberId, position1);
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
        return familyMemberArrayList.size();
    }

    private interface GetOrderAPI {

        @FormUrlEncoded
        @POST(MyConfig.DRBHOR + "/app_user_family_member_delete")
        public Call<ResponseBody> getFamilyMembers(
                @Field("id") String id,
                @Field("user_id") String user_id
        );
    }

    private void deleteFamilyMember(String memberId, final int position) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();
        GetOrderAPI api = MyConfig.getRetrofit(MyConfig.JSON_BASE_URL).create(GetOrderAPI.class);
        final Call<ResponseBody> result = api.getFamilyMembers(
                memberId,
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

                        familyMemberArrayList.remove(position);
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

}

