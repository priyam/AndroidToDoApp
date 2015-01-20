package com.pc.example.firsttodoapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


public class EditItemDialog extends DialogFragment implements OnEditorActionListener  {

	private EditText mEditText;
	private Button btnEditText;
	
	public interface EditItemDialogListener {
        void onFinishEditDialog(String inputText);
    }
	
	public EditItemDialog() {
		// Empty constructor required for DialogFragment
	}
	
	public static EditItemDialog newInstance(String title, String itemText) {
		EditItemDialog frag = new EditItemDialog();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("itemText", itemText);
		frag.setArguments(args);
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.fragment_edit_item, container);
		//mEditText = (EditText) view.findViewById(R.id.txt_edit_text);
		View view = inflater.inflate(R.layout.activity_edit_item, container);
		mEditText = (EditText) view.findViewById(R.id.etEditItemText);
		btnEditText = (Button) view.findViewById(R.id.btnEdit);
		String title = getArguments().getString("title", "Edit Item");
		getDialog().setTitle(title);
		// Show soft keyboard automatically
		mEditText.requestFocus();
		mEditText.setText(getArguments().getString("itemText"));
		mEditText.setSelection(mEditText.getText().length());
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		mEditText.setOnEditorActionListener(this);
		btnEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditItemDialogListener listener = (EditItemDialogListener) getActivity();
	            listener.onFinishEditDialog(mEditText.getText().toString());
	            dismiss();
			}
		});
		return view;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
			EditItemDialogListener listener = (EditItemDialogListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString());
            dismiss();
            return true;
        }
		return false;
	}
}