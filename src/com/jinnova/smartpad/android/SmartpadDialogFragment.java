package com.jinnova.smartpad.android;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jinnova.smartpad.R;

public class SmartpadDialogFragment extends DialogFragment {
	
	private String text;
	
    public static SmartpadDialogFragment createDialog(String text) {
        SmartpadDialogFragment f = new SmartpadDialogFragment();

        Bundle args = new Bundle();
        args.putString("text", text);
        f.setArguments(args);
        f.text = text;
        
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_go_action_dialog, container, false);
        View titleText = view.findViewById(R.id.titleFD);
        ((TextView)titleText).setText("Post info detail");
        
        /*String text = savedInstanceState.getString("text");
        if (text == null) {
        	text = "";
        }*/
        ((TextView) view.findViewById(R.id.messageFD)).setText(text);
        
        // TODO Watch for button clicks.
        Button button = (Button)view.findViewById(R.id.backBtnFD);
        button.setOnClickListener(new OnClickListener() {
        	
        	@Override
            public void onClick(View v) {
        		SmartpadDialogFragment.this.setCancelable(true);
            }
        });

        return view;
    }
}
