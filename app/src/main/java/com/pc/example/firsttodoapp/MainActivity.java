package com.pc.example.firsttodoapp;

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

import com.pc.example.firsttodoapp.EditItemDialog.EditItemDialogListener;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements EditItemDialogListener {

	private ArrayList<TodoItem> items;
	private ArrayAdapter<TodoItem> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private int updatedPosition;
	private final int REQUEST_CODE = 1;
    private static TodoItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        updatedPosition = -1;
        //populateArrayItems();
        readItems();
        itemsAdapter = new ArrayAdapter<TodoItem>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setUpListViewListener();
        
    }

    private void readItems(){
        if(db == null)
        {
            db = new TodoItemDatabase(this);
        }
        items = (ArrayList<TodoItem>)db.getAllTodoItems();
    }

    private void setUpListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
                db.deleteTodoItem(items.get(position));
				items.remove(position);
				itemsAdapter.notifyDataSetChanged();
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
				showEditDialog(items.get(position).getBody());
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
            TodoItem item = items.get(updatedPosition);
            db.updateTodoItem(item);
            item.setBody(updatedText);
    	    itemsAdapter.notifyDataSetChanged();

    	  }
    }
    
    public void onAddClick(View v){
    	
    	String itemValue;
    	itemValue = etNewItem.getText().toString();
        TodoItem item = new TodoItem(itemValue, 1);
        db.addTodoItem(item);
    	items.add(item);
    	etNewItem.setText("");
    }
    
    private void showEditDialog(String itemText) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog editNameDialog = EditItemDialog.newInstance("Edit",itemText);
        editNameDialog.show(fm, "fragment_edit_item");
    }

	@Override
	public void onFinishEditDialog(String inputText) {

        TodoItem item = items.get(updatedPosition);
        db.updateTodoItem(item);
        item.setBody(inputText);
        itemsAdapter.notifyDataSetChanged();
	}
}
