package repository.myfridge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import java.io.IOException;

import java.text.DateFormat;
import java.util.Calendar;

import android.widget.Toast;
import android.view.Gravity;


public class MainActivity extends AppCompatActivity
 {
    private static final String FILE_PRODUSE="produse.txt"; //data/data/repository.proiect
    EditText input_produs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);


         //produse curenta
        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());

        TextView tvDate= findViewById(R.id.tvDate);
        tvDate.setText(currentDate);

        //input produse
        input_produs= findViewById(R.id.inputProdus);

        //lista tip RecyclerView

        RecyclerView rvListProduse;
        AdaptorRecView adapter;
        List<String> produse;

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rvListProduse = findViewById(R.id.rvListProduse);
        rvListProduse.setLayoutManager(layoutManager);

        produse = readLinesFromFile("produse.txt");

        adapter = new AdaptorRecView(this, produse);
        rvListProduse.setAdapter(adapter);
    }

     private List<String> readLinesFromFile(String filename)
     {
         filename="produse.txt";
         List<String> lines = new ArrayList<>();

         try {
             FileInputStream fis = openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader bufferedReader = new BufferedReader(isr);

             String line;
             while ((line = bufferedReader.readLine()) != null) {
                 lines.add(line);
             }

             bufferedReader.close();
             isr.close();
             fis.close();
         } catch (IOException e) {
             e.printStackTrace();
         }

         return lines;
     }


    public void saveInFile(View v)
    {
      String produs=input_produs.getText().toString();
        FileOutputStream fos=null;
        try {
               fos=openFileOutput(FILE_PRODUSE, MODE_APPEND);
               fos.write(produs.getBytes());
               fos.write(("\n").getBytes());

               input_produs.getText().clear();

            Toast toast = Toast.makeText(this,"Adaugat", Toast.LENGTH_LONG);
            //nu merge gravitatea
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
            }
        catch (IOException e) {
            throw new RuntimeException(e);
        } finally
        {
            if(fos!=null)
            {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

     public static class AdaptorRecView extends RecyclerView.Adapter<AdaptorRecView.ViewHolder>
     {

         private final List<String> produseList;
         private final LayoutInflater inflater;

         public AdaptorRecView(Context context, List<String> produseList)
         {
             this.inflater = LayoutInflater.from(context);
             this.produseList = produseList;
         }

         @NonNull
         @Override
         public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
         {
             View view = inflater.inflate(R.layout.layout_recyclerview, parent, false);
             return new ViewHolder(view);
         }

         @Override
         public void onBindViewHolder(@NonNull ViewHolder holder, int position)
         {
             String line = produseList.get(position);
             holder.tvRecViewProdus.setText(line);
         }

         @Override
         public int getItemCount()
         {
             return produseList.size();
         }

         public static class ViewHolder extends RecyclerView.ViewHolder
         {
             TextView tvRecViewProdus;

             public ViewHolder(@NonNull View itemView) {
                 super(itemView);
                 tvRecViewProdus = itemView.findViewById(R.id.tvRecViewProdus);
             }
         }
     }
 }
