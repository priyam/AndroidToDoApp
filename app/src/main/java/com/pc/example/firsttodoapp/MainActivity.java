package com.pc.example.firsttodoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.pc.example.firsttodoapp.EditItemDialog.EditItemDialogListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends FragmentActivity implements EditItemDialogListener {

	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private int updatedPosition;
	private final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        updatedPosition = -1;
        //populateArrayItems();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setUpListViewListener();
        
    }
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		items = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch(IOException e) {
    		items = new ArrayList<String>();
    		e.printStackTrace();
    	}
    }
    
    private void saveItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, items);
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    private void setUpListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
		});
		
		lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Intent i = new Intent(MainActivity.this, EditItemActivity.class);
				//i.putExtra("itemValue", items.get(position));
				//updatedPosition = position;
				//startActivityForResult(i, REQUEST_CODE);
				updatedPosition = position;
				showEditDialog(items.get(position));
			}
		});
		
	}

	//private void populateArrayItems(){
    //	items = new ArrayList<String>();
    //	items.add("item 1");
    //}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    		
    	if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
    		String updatedText = data.getExtras().getString("updatedItem");
    	    items.set(updatedPosition, updatedText);
    	    itemsAdapter.notifyDataSetChanged();
    	    saveItems();
    	  }
    }
    
    public void onAddClick(View v){
    	
    	String itemValue;
    	itemValue = etNewItem.getText().toString();
    	items.add(itemValue);
    	etNewItem.setText("");
    	saveItems();
    }
    
    private void showEditDialog(String itemText) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editNameDialog = EditItemDialog.newInstance("Edit",itemText);
        editNameDialog.show(fm, "fragment_edit_item");
    }

	@Override
	public void onFinishEditDialog(String inputText) {
		items.set(updatedPosition, inputText);
	    itemsAdapter.notifyDataSetChanged();
	    saveItems();
	}
}
