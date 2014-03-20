package com.jinnova.smartpad.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jinnova.smartpad.R;

public class SmartpadDialogFragment extends Fragment {
	
	private String decription;
	
	public static final SmartpadDialogFragment createDialog(String decription) {
		SmartpadDialogFragment result = new SmartpadDialogFragment();
		result.decription = decription;
		return result;
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_go_action_dialog, container, false);
        View titleText = view.findViewById(R.id.titleFD);
        ((TextView)titleText).setText("Info detail");
        
        ((TextView) view.findViewById(R.id.messageFD)).setText(decription);
        
        Button button = (Button) view.findViewById(R.id.backBtnFD);
        button.setOnClickListener(new OnClickListener() {
        	
        	@Override
            public void onClick(View v) {
				SmartpadDialogFragment.this.getFragmentManager()
						.beginTransaction().remove(SmartpadDialogFragment.this)
						.commit();
            }
        });
        return view;
    }
}