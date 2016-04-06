package fitnessapp.supinfo.fitnessapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fitnessapp.supinfo.fitnessapp.model.Runner;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater layoutinflater;
    private List<Runner> runners;
    private Context context;

    public CustomAdapter(Context context, List<Runner> runners) {
        this.context = context;
        this.runners = runners;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return runners.size();
    }

    @Override
    public Runner getItem(int position) {
        return runners.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(this.context);
        Runner item = getItem(position);
        return text;
    }

}
