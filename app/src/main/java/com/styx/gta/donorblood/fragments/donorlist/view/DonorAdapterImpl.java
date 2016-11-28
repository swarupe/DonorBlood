package com.styx.gta.donorblood.fragments.donorlist.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.styx.gta.donorblood.R;
import com.styx.gta.donorblood.constants.Constants;
import com.styx.gta.donorblood.constants.UserAction;
import com.styx.gta.donorblood.fragments.donorlist.presenter.DonorPresenterImpl;
import com.styx.gta.donorblood.models.BloodGroup;
import com.styx.gta.donorblood.models.Donor;
import com.styx.gta.donorblood.utilities.Utilities;

import java.util.ArrayList;

/**
 * Created by amal.george on 25-11-2016.
 */

public class DonorAdapterImpl extends RecyclerView.Adapter<DonorAdapterImpl.ViewHolder> implements DonorAdapter {
    private static String TAG = "DonorAdapterImpl";
    private final int layoutID = R.layout.item_donor;
    private final ArrayList<Donor> list = new ArrayList<>();
    private final DonorPresenterImpl presenter;
    private Context context;

    public DonorAdapterImpl(Context context) {
        this.presenter = new DonorPresenterImpl(this);
        this.context = context;
    }

    public DonorAdapterImpl(Context context, BloodGroup bloodGroup) {
        this.presenter = new DonorPresenterImpl(this, bloodGroup);
        this.context = context;
    }

    @Override
    public void addItem(Donor donor) {
        list.add(donor);
        notifyDataSetChanged();
    }

    @Override
    public void request() {
        presenter.request();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(layoutID, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Donor thisDonor = list.get(position);
        holder.bind(thisDonor);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name, tv_age, tv_sex, tv_contact, tv_blood_group;
        public ImageView iv_call_icon, icon_user;

        public ViewHolder(View view) {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_age = (TextView) view.findViewById(R.id.tv_age);
            tv_sex = (TextView) view.findViewById(R.id.tv_sex);
            tv_contact = (TextView) view.findViewById(R.id.tv_contact);
            tv_blood_group = (TextView) view.findViewById(R.id.tv_blood_group);
            iv_call_icon = (ImageView) view.findViewById(R.id.iv_call_icon);
            icon_user = (ImageView) view.findViewById(R.id.icon_user);
        }

        public void bind(final Donor thisDonor) {
            tv_name.setText(thisDonor.getName());
            tv_age.setText(String.valueOf(Utilities.getAge(thisDonor.getDob())));
            iv_call_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.doCall(context, thisDonor.getContact());
                }
            });
            tv_sex.setText(thisDonor.getSex());
            if (thisDonor.getSex().equalsIgnoreCase(Donor.Sex.female)) {
                icon_user.setImageResource(R.drawable.ic_female);
            }
            tv_contact.setText(thisDonor.getContact());
            tv_blood_group.setText(thisDonor.getBloodGroupCanonicalName());
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle mBundle = new Bundle();
                    mBundle.putString(Constants.FragmentParameters.objectID, thisDonor.getObjectID());
                    Utilities.getApp(context).doUserAction(UserAction.DONOR_DETAIL_FRAGMENT, mBundle);
                }
            });

        }
    }
}