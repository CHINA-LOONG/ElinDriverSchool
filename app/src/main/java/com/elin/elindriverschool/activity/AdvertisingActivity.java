package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.fragment.CompanyAdvertiseFragment;
import com.elin.elindriverschool.fragment.PersonalAdvertiseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdvertisingActivity extends BaseActivity {


    CompanyAdvertiseFragment companyAdvertiseFragment;
    PersonalAdvertiseFragment personalAdvertiseFragment;
    @Bind(R.id.img_advertising_title_back)
    ImageView imgAdvertisingTitleBack;
    @Bind(R.id.rb_advertiding_title)
    RadioGroup rbAdvertidingTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);
        ButterKnife.bind(this);
        if (companyAdvertiseFragment == null) {
            companyAdvertiseFragment = new CompanyAdvertiseFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.rl_advertising_container, companyAdvertiseFragment);
        transaction.commit();
        rbAdvertidingTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                hideFragment(transaction);
                switch (checkedId){
                    case R.id.tv_company_advertising:
                            if(companyAdvertiseFragment==null){
                                companyAdvertiseFragment = new CompanyAdvertiseFragment();
                                transaction.add(R.id.rl_advertising_container,companyAdvertiseFragment);
                            }else {
                                transaction.show(companyAdvertiseFragment);
                            }
                        break;
                    case R.id.tv_personal_advertising:
                            if(personalAdvertiseFragment==null){
                                personalAdvertiseFragment = new PersonalAdvertiseFragment();
                                transaction.add(R.id.rl_advertising_container,personalAdvertiseFragment);
                            }else {
                                transaction.show(personalAdvertiseFragment);
                            }
                        break;
                }
                transaction.commit();
            }
        });
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (companyAdvertiseFragment != null) {
            transaction.hide(companyAdvertiseFragment);
        }
        if (personalAdvertiseFragment != null) {
            transaction.hide(personalAdvertiseFragment);
        }
    }

    @OnClick(R.id.img_advertising_title_back)
    public void onClick() {
        finish();
    }
}
