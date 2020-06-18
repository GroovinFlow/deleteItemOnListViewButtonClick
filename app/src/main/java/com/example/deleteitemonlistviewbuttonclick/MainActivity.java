package com.example.deleteitemonlistviewbuttonclick;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity
{
    private ListView listviewitems;
    private ItemAdapter adapteritem;
    private ArrayList<Item> arrListItems;
    private boolean isEditMode=false;
    private Button btnEdit,btnClearAll;
    private TextView txtNoitemfound;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNoitemfound = (TextView) findViewById(R.id.txtNoFound);
        listviewitems = (ListView) findViewById(R.id.lstviewitems);

        //Add Some items in arraylist................
        arrListItems=new ArrayList<Item>();
        for (int i = 0; i < 10; i++)
        {
            Item favourite=new Item();
            favourite.setTitle("Item "+ i);
            arrListItems.add(favourite);
        }


        //Fill data into listview.............
        if(arrListItems.size()>0)
        {
            adapteritem=new ItemAdapter(MainActivity.this, arrListItems);
            listviewitems.setAdapter(adapteritem);
        }
        else
        {
            txtNoitemfound.setVisibility(View.VISIBLE);
            listviewitems.setVisibility(View.GONE);
            btnEdit.setVisibility(View.INVISIBLE);
            btnClearAll.setVisibility(View.INVISIBLE);

        }

        //Edit Mode for delete item.............................
        btnEdit=(Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(arrListItems.size()>0)
                {
                    if(btnEdit.getText().toString().trim().equalsIgnoreCase("Edit"))
                    {
                        isEditMode=true;
                        btnEdit.setText("Done");
                        adapteritem.notifyDataSetChanged();
                    }
                    else
                    {
                        isEditMode=false;
                        btnEdit.setText("Edit");
                        adapteritem.notifyDataSetChanged();
                    }
                }
            }
        });

        //Clear All item from Listview............................
        btnClearAll=(Button) findViewById(R.id.btnClearAll);
        btnClearAll.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(arrListItems.size()>0)
                    removeAllitemsFromList();
            }
        });

    }


    //FavouriteAdapter.......................................
    public class ItemAdapter extends BaseAdapter
    {
        private LayoutInflater lyt_Inflater = null;

        private ArrayList<Item> arrlstitems;
        private Context context;

        public ItemAdapter(Context cnt,ArrayList<Item> items)
        {
            this.context=cnt;
            this.arrlstitems = items;
        }


        @Override
        public int getCount()
        {
            return arrlstitems.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            View view_lyt = convertView;
            try
            {
                if(arrlstitems.size()>0)
                {
                    final Item item =arrlstitems.get(position);
                    String Name=item.getTitle();

                    lyt_Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view_lyt = lyt_Inflater.inflate(R.layout.row_item, null);

                    TextView txtnm=(TextView) view_lyt.findViewById(R.id.txtTitle);
                    ImageButton imgbtnDelete=(ImageButton) view_lyt.findViewById(R.id.imgbtnDelete);
                    txtnm.setText(Name);

                    if(isEditMode)
                    {
                        imgbtnDelete.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        imgbtnDelete.setVisibility(View.GONE);
                    }

                    view_lyt.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {


                        }
                    });
                    imgbtnDelete.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            if(arrListItems.size()>0)
                                removeItemFromList(position);
                        }
                    });

                }

            }
            catch (Exception e)
            {
                Log.i("Exception==", e.toString());
            }

            return view_lyt;
        }
    }

    // Method for remove Single item from list
    protected void removeItemFromList(int position)
    {
        final int deletePosition = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alert!");
        builder.setMessage("Do you want delete this item?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        arrListItems.remove(deletePosition);
                        adapteritem.notifyDataSetChanged();
                        adapteritem.notifyDataSetInvalidated();

                        if(arrListItems.size()==0)
                        {
                            txtNoitemfound.setVisibility(View.VISIBLE);
                            listviewitems.setVisibility(View.GONE);
                            btnEdit.setVisibility(View.INVISIBLE);
                            btnClearAll.setVisibility(View.INVISIBLE);
                        }
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog  alertDialog = builder.create();
        alertDialog.show();

    }
    // Method for remove Single item from list
    protected void removeAllitemsFromList()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Alert!");
        builder.setMessage("Do you want delete all this items?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        arrListItems.removeAll(arrListItems);
                        adapteritem.notifyDataSetChanged();
                        adapteritem.notifyDataSetInvalidated();

                        if(arrListItems.size()==0)
                        {
                            txtNoitemfound.setVisibility(View.VISIBLE);
                            listviewitems.setVisibility(View.GONE);
                            btnEdit.setVisibility(View.INVISIBLE);
                            btnClearAll.setVisibility(View.INVISIBLE);
                        }


                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog  alertDialog = builder.create();
        alertDialog.show();

    }
}