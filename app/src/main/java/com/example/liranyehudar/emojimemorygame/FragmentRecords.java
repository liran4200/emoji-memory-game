package com.example.liranyehudar.emojimemorygame;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class FragmentRecords extends Fragment {

    private static final String TAG = "FragmentRecords";
    DBHandler db ;

    String[] headers = {"ID" ,"Name", "Age", "Result"};
    String[][] data;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_records,container,false);
        db = new DBHandler(getActivity());
        final TableView<String[]> tb = (TableView<String[]>) view.findViewById(R.id.tableView);

        tb.setColumnCount(headers.length);
        initData();

        tb.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(),headers));
        tb.setDataAdapter(new SimpleTableDataAdapter(getActivity(), data));

        return view;
    }

    public void initData() {
        /*
        Player p1 = new Player(1,"Liran Yehudar",25,100);
        Player p2 = new Player(2,"Nir finz",26,125);
        Player p3 = new Player(3,"Liran Yehudar",25,100);
        Player p4 = new Player(4,"Liran Yehudar",25,100);
        Player p5 = new Player(5,"Liran Yehudar",25,100);
        Player p6 = new Player(6,"Liran Yehudar",25,100);
        Player p7 = new Player(7,"Liran Yehudar",25,100);
        Player p8 = new Player(8,"Liran Yehudar",25,100);
        Player p9 = new Player(9,"Liran Yehudar",25,100);
        Player p10 = new Player(10,"Liran Yehudar",25,100);

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        players.add(p5);
        players.add(p6);
        players.add(p7);
        players.add(p8);
        players.add(p9);
        players.add(p10);

        data = new String[players.size()][headers.length];
        for(int i=0; i < players.size(); i ++){
            Player temp = players.get(i);

            data[i][0] = temp.getId() +"";
            data[i][1] = temp.getName();
            data[i][2] = temp.getAge() +"";
            data[i][3] = temp.getResult()+"";
        }*/

        Cursor c = db.getAllData();
        int countData  = c.getCount();
        data = new String[c.getCount()][headers.length];
        int row =0;
        while (c.moveToNext()){
            data[row][0] = c.getString(0);
            data[row][1] = c.getString(1);
            data[row][2] = c.getString(2);
            data[row][3] = c.getString(3);
            row++;
        }
    }
}
