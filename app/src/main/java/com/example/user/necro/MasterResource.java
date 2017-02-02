package com.example.user.necro;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class MasterResource extends AppCompatActivity {
    DatabaseHelper myDBResource;
    EditText editNameResource, editMarkResource, editLoadResource;
    Button btnAddResource, btnViewResource, btnRetrieve;
    ArrayList<String> arrayResource = new ArrayList<String>();
    ArrayAdapter<String> adapterResource;
    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_resource);
        myDBResource = new DatabaseHelper(this);
        editNameResource = (EditText) findViewById(R.id.editTextNameResource);
        editMarkResource = (EditText) findViewById(R.id.editMarkResource);
        editLoadResource = (EditText) findViewById(R.id.editLoadResource);
        btnAddResource = (Button) findViewById(R.id.buttonAddResource);
        btnViewResource = (Button) findViewById(R.id.buttonViewResource);
        gv = (GridView) findViewById(R.id.gridViewResource);
        registerForContextMenu(gv);
        btnRetrieve = (Button) findViewById(R.id.buttonRetrieve);
        adapterResource = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayResource);
        addResource();
        viewResource();
        viewGridResource();

    }

    public void addResource() {
        btnAddResource.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInsertedResource = myDBResource.insertData(editNameResource.getText().toString().toUpperCase().trim()
                                , editMarkResource.getText().toString(), editLoadResource.getText().toString());
                        if (isInsertedResource == true) {
                            makeText(MasterResource.this, "Resource Inserted", LENGTH_LONG).show();
                            editNameResource.setText(null);
                            editLoadResource.setText(null);
                            editMarkResource.setText(null);
                            btnRetrieve.performClick();
                        } else
                            makeText(MasterResource.this, "Add Resource Failed", LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewResource() {
        btnViewResource.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDBResource.getAllData();
                        if (res.getCount() == 0) {
                            //showMessage
                            showMessage("Error", "No Data Found");
                            return;

                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :" + res.getString(0) + "\n");
                            buffer.append("Name :" + res.getString(1) + "\n");
                            buffer.append("Mark :" + res.getString(2) + "\n");
                            buffer.append("Load :" + res.getString(3) + "\n\n");
                        }

                        //Show all Data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    // RETRIEVE
    public void viewGridResource() {
        btnRetrieve.setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayResource.clear();

                        //RETRIEVE
                        Cursor c = myDBResource.getAllResGrid();
                        while (c.moveToNext()) {
                            String name = c.getString(1);
                            arrayResource.add(name);
                        }
                        myDBResource.close();
                        gv.setAdapter(adapterResource);
                    }
                });
    }

    //MENU ON LIST VIEW
    public void onCreateContextMenu(ContextMenu menuResource, View vResource, ContextMenu.ContextMenuInfo menuInfoResource) {
        super.onCreateContextMenu(menuResource, vResource, menuInfoResource);
        if (vResource.getId() == R.id.gridViewResource) {
            MenuInflater inflaterResource = getMenuInflater();
            inflaterResource.inflate(R.menu.master_resource_menu, menuResource);
        }
    }


    public boolean onContextItemSelected(MenuItem itemResource) {
        AdapterView.AdapterContextMenuInfo infoResource = (AdapterView.AdapterContextMenuInfo) itemResource.getMenuInfo();

        switch (itemResource.getItemId()) {
            case R.id.add_resource:
                //CODE ADD HERE
                return true;
            case R.id.edit_resource:
                //CODE ADD HERE
                return true;
            case R.id.delete_resource:
                //CODE ADD HERE

//                String namaDihapus = arrayResource.get(2);
//                arrayResource.remove(2);
//                adapterResource.notifyDataSetChanged();
//                Toast.makeText(MasterResource.this, "Resource Deleted", Toast.LENGTH_LONG).show();

//                Integer isDeletedResource = myDBResource.deleteData(adapterResource.getItem(0));
//                String selectedResource = (gv.getItemAtPosition().toString());

                Integer isDeletedResource = myDBResource.deleteData(arrayResource.get(infoResource.position));
                if (isDeletedResource > 0) {
                    Toast.makeText(MasterResource.this, "Resource Deleted", Toast.LENGTH_LONG).show();
                    btnRetrieve.performClick();
                } else
                    Toast.makeText(MasterResource.this, "Resource Deletion Failed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(itemResource);
        }
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
